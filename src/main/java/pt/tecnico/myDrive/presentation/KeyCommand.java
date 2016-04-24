package pt.tecnico.myDrive.presentation;


public class KeyCommand extends MdCommand {

    public KeyCommand(MdShell sh) {
        super(sh, "token", "it changes the current session returning a token or returns current session's token and username.\n" +
                "The username is optional.");
    }


    @Override
    void execute(String args[]) {

        if(args.length < 1)
            throw new RuntimeException("USAGE: " + name() + " <username>");
        else if(args.length == 1)
            return;
            //KeyService();
        else
            return;
            //KeyService();
    }
}
