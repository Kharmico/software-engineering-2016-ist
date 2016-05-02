package pt.tecnico.myDrive.presentation;

import pt.tecnico.myDrive.service.ChangeDirectoryService;

/**
 * Created by xxlxpto on 23-04-2016.
 */
public class ChangeWorkingDirectoryCommand extends MdCommand {

    public ChangeWorkingDirectoryCommand(MdShell sh) { super(sh, "cwd", "it changes the working directory"); }
    @Override
    public void execute(String[] args) {
        ChangeDirectoryService cds;
        if (args.length > 1)
            throw new RuntimeException("USAGE: " + name() + " <path>");
        else if (args.length == 1)
            cds = new ChangeDirectoryService(((MdShell) shell()).getCurrentToken(), args[0]);
        else
            cds = new ChangeDirectoryService(((MdShell) shell()).getCurrentToken());
        cds.execute();
        shell().println(cds.result());
    }
}
