package pt.tecnico.myDrive.domain;

import org.joda.time.DateTime;

public class Session extends Session_Base {

    public Session(long token, User currentUser, Directory currentDir, MyDriveManager mgm) {
        super();
        super.setToken(token);
        super.setDir(currentDir.getPath());
        super.setUsername(currentUser.getUsername());
        super.setManager(mgm);
        super.setLastAccess(new DateTime());
    }

    public void remove(){
        deleteDomainObject();
    }

    public Directory getCurrentDir(){
        return getManager().getFilesystem()
                .getLastDirectory(getDir(), getManager().getFilesystem().getSlash(), getManager().getFilesystem()
                        .getRoot());
    }

    public void setCurrentDir(Directory dir){
        super.setDir(dir.getPath());
    }

    public User getCurrentUser(){
        return getManager().getFilesystem().getUserByUsername(getUsername());
    }


    
}