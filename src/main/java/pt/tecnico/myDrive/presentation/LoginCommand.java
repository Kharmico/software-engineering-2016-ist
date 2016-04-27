package pt.tecnico.myDrive.presentation;

import pt.tecnico.myDrive.domain.MyDriveManager;
import pt.tecnico.myDrive.service.LoginUserService;

public class LoginCommand extends MdCommand {

    public LoginCommand(MdShell sh) { super(sh, "login", "does the login service");}

    @Override
    void execute(String[] args) {
        if (args.length < 0 || args.length > 3)
            throw new RuntimeException("USAGE: " + name() + " username <password>");
        else {
            //log.debug("LOGIN COMMAND", args[0] + " " + args[1]);
            LoginUserService lus = args.length == 1 ? new LoginUserService(args[0], "") : new LoginUserService(args[0], args[1]);
            lus.execute();
            ((MdShell) shell()).setCurrentToken(lus.result());
        }
    }

}
