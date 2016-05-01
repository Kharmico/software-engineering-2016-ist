package pt.tecnico.myDrive.presentation;

import pt.tecnico.myDrive.service.LoginUserService;

import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * Created by xxlxpto on 23-04-2016.
 */
public class MdShell extends Shell {
    private String currentUser;
    //private long currentToken;  -> imagine various sessions for the same username...
    private LinkedHashMap<String,Long> sessions = new LinkedHashMap<>(); //Can only be of active sessions!!!

    public static void main(String[] args) throws Exception {
        MdShell sh = new MdShell();
        sh.execute();
    }

    void addSession(String username, long token){
        sessions.put(username,token);
        setCurrentUser(username);
    }

    long getCurrentToken(){
        return sessions.get(currentUser);
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

    void init(){
        LoginUserService lus = new LoginUserService("nobody", "");
        lus.execute();
        setCurrentUser("nobody");
        sessions.put("nobody",lus.result());
    }

    public MdShell() { // add commands here
        super("MyDrive");
        init();
        new ChangeWorkingDirectoryCommand(this);
        new LoginCommand(this);
        new ListCommand(this);
        new EnvironmentCommand(this);
        new KeyCommand(this);
//        new CreateContact(this);
//        new RemovePerson(this);
//        new RemoveContact(this);
//        new Import(this);
//        new Export(this);
    }
}
