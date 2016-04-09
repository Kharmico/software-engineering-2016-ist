package pt.tecnico.myDrive.domain;

import org.joda.time.DateTime;

public class Session extends Session_Base {
    
    public Session(long token, User user, Directory dir) {
        super();
        super.setToken(token);
        super.setCurrentUser(user);
        super.setCurrentDir(dir);
        super.setLastAccess(new DateTime());
    }

    public void remove(){
        deleteDomainObject();
    }
    
}