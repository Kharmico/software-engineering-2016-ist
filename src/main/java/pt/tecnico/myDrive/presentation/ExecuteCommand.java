package pt.tecnico.myDrive.presentation;

import pt.tecnico.myDrive.service.ExecuteFileService;

/**
 * Created by jp_s on 4/30/2016.
 */
public class ExecuteCommand extends MdCommand{

    public ExecuteCommand(MdShell sh){super(sh, "do", "Executes the file given by a path with the give args\n" +
            "The args are optional.");}

    @Override
    public void execute(String[] args) {
        if (args.length < 1)
            throw new RuntimeException("USAGE: " + name() + " path [args]");
        else {
            String path = args[0];
            String[] arg = {};
            if (args.length == 1)
                System.arraycopy(args, 1, arg, 1, args.length);

            ExecuteFileService executeFileService = new ExecuteFileService(path, arg, ((MdShell) shell()).getCurrentToken());
            executeFileService.execute();
        }
    }
}


/*Execute: do path {args]
Executa o ficheiro contido na diretoria indicada pelo caminho (path),
com os argumentos args.*/

