package pt.tecnico.myDrive.presentation;

import pt.tecnico.myDrive.domain.MyDriveManager;
import pt.tecnico.myDrive.service.ListDirectoryService;

public class ListCommand extends MdCommand {

    public ListCommand(MdShell sh) { super(sh, "ls", "it returns all files existing in the working directory. Path is optional"); }
    @Override
    public void execute(String[] args) {
        if (args.length > 1)
            throw new RuntimeException("USAGE: " + name() + " <path> (path is optional)");
        else if (args.length == 1) {
            ListDirectoryService listDirectoryService = new ListDirectoryService(((MdShell) shell()).getCurrentToken(),
                    args[0]);
            listDirectoryService.execute();
            shell().println(listDirectoryService.result());
        }
        else {
            ListDirectoryService listDirectoryService = new ListDirectoryService(((MdShell) shell()).getCurrentToken());
            listDirectoryService.execute();
            shell().println(listDirectoryService.result());
        }
    }
}

/*List: ls [path]
Imprime a informação das entradas existentes na diretoria indicada pelo caminho (path) ou, 
caso este seja omitido, as entradas da diretoria corrente.*/