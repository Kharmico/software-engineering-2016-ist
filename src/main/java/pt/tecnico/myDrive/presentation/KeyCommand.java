package pt.tecnico.myDrive.presentation;


public class KeyCommand extends MdCommand {

    public KeyCommand(MdShell sh) {
        super(sh, "token", "It changes the current session returning a token, or returns the current session's token and username.\n" +
                "The username is optional.");
    }

    @Override
    public void execute(String args[]) {

        switch (args.length) {
            case 0: shell().println(((MdShell)shell()).getCurrentUser()
                    + " " + Long.toString(((MdShell)shell()).getCurrentToken()));
                    break;
            case 1:((MdShell) shell()).changeCurrentToken(args[0]);
                    shell().println("Current Token: " + ((MdShell) shell()).getCurrentToken());
                    break;
            default: throw new RuntimeException("USAGE: " + name() + " [username]");
        }

    }
}
