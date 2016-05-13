package pt.tecnico.myDrive.domain;

import org.joda.time.DateTime;

import pt.tecnico.myDrive.exception.InvalidEnvironmentVariableException;

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

    void addEnvironmentVariable(String name, String value) throws InvalidEnvironmentVariableException{
    	if(name.equals("") && value.equals("") || !name.equals("") && value.equals(""))
    		return;
 
    	if(!name.equals("")) {
	        for(EnvironmentVariable var : super.getVarSet()){
	            if(var.getName().equals(name)){
	                var.setValue(value);
	                return;
	            }
	        }
        addVar(new EnvironmentVariable(name, value, this));
    	} else {
    		throw new InvalidEnvironmentVariableException();
    	}
    }

    LinkedHashMap<String, String> listEnvironmentVariables() {
        LinkedHashMap<String, String> list = new LinkedHashMap<>();
        for(EnvironmentVariable var : super.getVarSet()){
            list.put(new String(var.getName()), new String(var.getValue()));
        }
        return list;
    }
    
}