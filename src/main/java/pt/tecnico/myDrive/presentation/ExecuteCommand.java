package pt.tecnico.myDrive.presentation;

import pt.tecnico.myDrive.service.ExecuteFileService;

/**
 * Created by jp_s on 4/30/2016.
 */
public class ExecuteCommand extends MdCommand{

    public ExecuteCommand(MdShell sh){super(sh, "do", "execute the file given by a path with the args");}

    @Override
    public void execute(String[] args) {
        if (args.length < 3)
            throw new RuntimeException("USAGE: " + name() + " path text");
        else {
            String path = args[1];
            String arg = "";
            for (int i = 2; i < args.length; i++) {
                arg += " ";
                arg += args[i];
            }
        }
     //   ExecuteFileService executeFileService = new ExecuteFileService(((MdShell) shell()).getCurrentToken(), path, arg);
     //   executeFileService.execute();
    }
}

/*Execute: do path {args]
Executa o ficheiro contido na diretoria indicada pelo caminho (path),
com os argumentos args.*/

