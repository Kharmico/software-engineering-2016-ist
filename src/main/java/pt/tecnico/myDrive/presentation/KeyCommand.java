package pt.tecnico.myDrive.presentation;


public class KeyCommand extends MdCommand {

    public KeyCommand(MdShell sh) {
        super(sh, "token", "it changes the current session returning a token, or returns current session's token and username.\n" +
                "The username is optional.");
    }


    @Override
    public void execute(String args[]) {

        switch (args.length) {
            case 0: shell().println("Current Token: " + ((MdShell) shell()).getCurrentToken() +
                    "\nCurrent User: " + ((MdShell) shell()).getCurrentUser());
                    break;
            case 1: ((MdShell) shell()).setCurrentUser(args[0]);
                    shell().println("Current Token: " + ((MdShell) shell()).getCurrentToken());
                    break;
            default: throw new RuntimeException("USAGE: " + name() + " <username>");
        }

    }
}
