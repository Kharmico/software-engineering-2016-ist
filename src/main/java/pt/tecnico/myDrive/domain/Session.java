package pt.tecnico.myDrive.domain;

import org.joda.time.DateTime;

import java.util.LinkedHashMap;

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

    public void addEnvironmentVariable(String name, String value) {
        for(EnvironmentVariable var : super.getVarSet()){
            if(var.getName().equals(name)){
                var.setValue(value);
                return;
            }
        }
        super.getVarSet().add(new EnvironmentVariable(name, value, this));
    }

    public LinkedHashMap<String, String> listEnvironmentVariables() {
        LinkedHashMap<String, String> list = new LinkedHashMap<>();
        for(EnvironmentVariable var : super.getVarSet()){
            list.put(var.getName(), var.getValue());
        }
        return list;
    }
    
}