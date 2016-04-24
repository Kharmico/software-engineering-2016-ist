package pt.tecnico.myDrive.presentation;

import pt.tecnico.myDrive.service.LoginUserService;

/**
 * Created by xxlxpto on 23-04-2016.
 */
public abstract class MdCommand extends Command {
    private long token = new LoginUserService("nobody","").result();

    public MdCommand(Shell sh, String n) { super(sh, n); }
    public MdCommand(Shell sh, String n, String h) { super(sh, n, h); }

    public long getToken(){
        return token;
    }

    public void setToken(long token){
        this.token = token;
    }
}
