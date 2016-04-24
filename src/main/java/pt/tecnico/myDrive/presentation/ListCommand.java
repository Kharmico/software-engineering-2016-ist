package pt.tecnico.myDrive.presentation;

import pt.tecnico.myDrive.domain.MyDriveManager;
import pt.tecnico.myDrive.service.ListDirectoryService;

public class ListCommand extends MdCommand {

    public ListCommand(Shell sh) { super(sh, "ls", "it returns all files existing in the working directory. Path is optional"); }
    @Override
    void execute(String[] args) {
        if (args.length < 1)
            throw new RuntimeException("USAGE: " + name() + " <path> (path is optional)");
        // FIXME: We should get the token elsewhere, in this case we are breaking the layers to get it.
        else if (args.length == 1)
        	new ListDirectoryService(MyDriveManager.getInstance().getCurrentSession().getToken()).execute();
        else
        	new ListDirectoryService(MyDriveManager.getInstance().getCurrentSession().getToken(), args[1]).execute();
    }
}

/*List: ls [path]
Imprime a informação das entradas existentes na diretoria indicada pelo caminho (path) ou, 
caso este seja omitido, as entradas da diretoria corrente.*/