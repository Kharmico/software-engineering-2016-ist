package pt.tecnico.myDrive.presentation;


import pt.tecnico.myDrive.service.WriteFileService;

public class WriteCommand extends MdCommand {

    public WriteCommand(MdShell sh) { super(sh, "update", "changes the content of a specific file given by a path with the given text"); }

    @Override
    public void execute(String[] args) {
        if (args.length < 2)
            throw new RuntimeException("USAGE: " + name() + " path text");
        else{
            String filename = args[0];
            String content = "";
            for(int i = 1; i < args.length; i++){
                content += args[i];
                if(i != args.length -1)
                    content += " ";
            }
            WriteFileService writeFileService = new WriteFileService(((MdShell) shell()).getCurrentToken(), filename, content);
            writeFileService.execute();
        }
    }
}
