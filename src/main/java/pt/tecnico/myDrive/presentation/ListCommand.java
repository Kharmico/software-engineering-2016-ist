package pt.tecnico.myDrive.presentation;

import pt.tecnico.myDrive.service.ListDirectoryService;

public class ListCommand extends MdCommand {

    public ListCommand(MdShell sh) { super(sh, "ls", "It returns all files existing in the working directory. Path is optional"); }
    @Override
    public void execute(String[] args) {
        if (args.length > 1)
            throw new RuntimeException("USAGE: " + name() + " [path]");
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