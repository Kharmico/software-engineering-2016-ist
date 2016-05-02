package pt.tecnico.myDrive.presentation;

import pt.tecnico.myDrive.service.LoginUserService;

import java.util.LinkedHashMap;

/**
 * Created by xxlxpto on 23-04-2016.
 */
public class MdShell extends Shell {
    private String currentUser;
    private long currentToken;
    private LinkedHashMap<String,Long> sessions = new LinkedHashMap<>(); //Can only be of active sessions!!!

    public static void main(String[] args) throws Exception {
        MdShell sh = new MdShell();
        sh.execute();
    }

    void addSession(String username, long token){
        sessions.put(username,token);
        setCurrentUser(username);
        currentToken = token;
    }
    void changeCurrentToken(String username){
        currentToken = sessions.get(username);
    }

    long getCurrentToken(){
        return currentToken;
    }

    public String getCurrentUser(){
        return currentUser;
    }

    String getCurrentTokenUser(){
        return sessions.get(currentUser) + " " + currentUser;
    }

    void setCurrentUser(String username){
        currentUser = username;
    }

    public LinkedHashMap<String,Long> getSessions(){
        return sessions;
    }

    private void init(){
        LoginUserService lus = new LoginUserService("nobody", "");
        lus.execute();
        setCurrentUser("nobody");
        sessions.put("nobody",lus.result());
        currentToken = lus.result();
    }

    public MdShell() { // add commands here
        super("MyDrive");
        init();
        new ChangeWorkingDirectoryCommand(this);
        new LoginCommand(this);
        new ListCommand(this);
        new EnvironmentCommand(this);
        new KeyCommand(this);
        new ExecuteCommand(this);
        new WriteCommand(this);
    }
}
