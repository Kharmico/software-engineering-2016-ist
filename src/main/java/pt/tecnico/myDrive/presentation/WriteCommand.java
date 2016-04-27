package pt.tecnico.myDrive.presentation;


import pt.tecnico.myDrive.service.WriteFileService;

public class WriteCommand extends MdCommand {

    public WriteCommand(MdShell sh) { super(sh, "update", "changes the content of a specific file given by a path with the given text"); }

    @Override
    void execute(String[] args) {
        if (args.length < 3)
            throw new RuntimeException("USAGE: " + name() + " path text");
        else{
            String filename = args[1];
            String content = "";
            for(int i = 2; i < args.length; i++){
                content += " ";
                content += args[i];
            }
            WriteFileService writeFileService = new WriteFileService(((MdShell) shell()).getCurrentToken(), filename, content);
            writeFileService.execute();
        }
    }
}
