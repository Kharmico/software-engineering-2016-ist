package pt.tecnico.myDrive.presentation;

import pt.tecnico.myDrive.service.LoginUserService;

public class LoginCommand extends MdCommand {

    private long _returnedToken;

    public LoginCommand(Shell sh) { super(sh, "login", "does the login service"); }

    @Override
    void execute(String[] args) {
        if (args.length < 1 || args.length > 3)
            throw new RuntimeException("USAGE: " + name() + " <password>");
        else {
            LoginUserService lus = args.length == 1 ? new LoginUserService(args[0], "") : new LoginUserService(args[0], args[1]);
            lus.execute();
            _returnedToken = lus.result();
        }
    }

    public long getReturnedToken(){
        return _returnedToken;
    }

}
