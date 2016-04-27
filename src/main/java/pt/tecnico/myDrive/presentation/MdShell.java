package pt.tecnico.myDrive.presentation;

import pt.tecnico.myDrive.domain.MyDriveManager;
import pt.tecnico.myDrive.service.LoginUserService;

/**
 * Created by xxlxpto on 23-04-2016.
 */
public class MdShell extends Shell {
    private long currentToken;

    public static void main(String[] args) throws Exception {
        MdShell sh = new MdShell();
        sh.execute();
    }
    void setCurrentToken(long token){
        currentToken = token;
    }

    long getCurrentToken(){
        return currentToken;
    }

    void init(){
        LoginUserService lus = new LoginUserService("nobody", "");
        lus.execute();
        setCurrentToken(lus.result());
    }

    public MdShell() { // add commands here
        super("MyDrive");
        init();
        new ChangeWorkingDirectoryCommand(this);
        new LoginCommand(this);
        new ListCommand(this);
        //new EnvironmentCommand(this);
        //new KeyCommand(this);
//        new CreateContact(this);
//        new RemovePerson(this);
//        new RemoveContact(this);
//        new Import(this);
//        new Export(this);
    }
}
