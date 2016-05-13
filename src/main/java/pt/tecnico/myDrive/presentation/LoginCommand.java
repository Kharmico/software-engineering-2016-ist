package pt.tecnico.myDrive.presentation;

import pt.tecnico.myDrive.service.LoginUserService;

public class LoginCommand extends MdCommand {

    public LoginCommand(MdShell sh) { super(sh, "login", "Does the login service, logging in the username with the given password.\n" +
            "The password is optional.");}

    @Override
    public void execute(String[] args) {
        if (args.length <= 0 || args.length > 3)
            throw new RuntimeException("USAGE: " + name() + " username [password]");
        else {
            LoginUserService lus = args.length == 1 ? new LoginUserService(args[0], "") : new LoginUserService(args[0], args[1]);
            lus.execute();
            ((MdShell) shell()).addSession(args[0],lus.result());
        }
    }

}
