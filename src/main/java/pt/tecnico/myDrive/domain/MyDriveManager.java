package pt.tecnico.myDrive.domain;

public class MyDriveManager extends MyDriveManager_Base {
    
    public MyDriveManager() {
        super();
        this.setCurrentUser(getFilesystem().getRoot());
    }
    
    @Override
    public void setCurrentDirectory(Directory currentDirectory){
    	super.setCurrentDirectory(currentDirectory);
    }
    
    @Override
    public void setCurrentUser(User currentUser){
    	super.setCurrentUser(currentUser);
    }
    
    
    @Override
    public void setFilesystem(FileSystem filesystem){
    	super.setFilesystem(filesystem);
    }
    
    public void remove(){
    	this.setCurrentDirectory(null);
    	this.setCurrentUser(null);
    	this.setFilesystem(null);
    	deleteDomainObject();
    }
    
    /* Users */
    
    public void addUser(String username){
    	super.getFilesystem().addUsers(username);
    }
    
    public void removeUser(String username){
    	super.getFilesystem().removeUsers(username);
    }
    
    /* Directory */
    
    public void createDirectory(String filename){
    	super.getFilesystem().createDirectory(filename, 
    			getCurrentDirectory(), getCurrentUser());
    }
    
    /* Files */ 
    
    public void createPlainFile(String filename){
    	super.getFilesystem().createPlainFile(filename, 
    			getCurrentDirectory(), getCurrentUser());
    }
    
    public void createLinkFile(String filename){
    	super.getFilesystem().createLinkFile(filename, 
    			getCurrentDirectory(), getCurrentUser());
    }
    
    public void createAppFile(String filename){
    	super.getFilesystem().createAppFile(filename, 
    			getCurrentDirectory(), getCurrentUser());
    }
    
    
}
