package pt.tecnico.myDrive.domain;

public class User extends User_Base {
    
    private static final String DEFAULT_UMASK = "rwxd----";


	public User() {
        super();
    }
    
    public User(String username, Directory homeDirectory)/* TODO: throws */ {
    	this.init(username, username, username, DEFAULT_UMASK, homeDirectory);
    }
    

    
    protected void init(String username, String password, String name, String umask, Directory homeDirectory)/* TODO: throws */ {
    	if(username.equals("root")){
    		// TODO : throw exception
    	}
    	
    	this.setUsername(username);
    	this.setPassword(password);
    	this.setName(name);
    	this.setUmask(umask);
    	this.setHomeDirectory(homeDirectory);
    }
    
    @Override
	public void setUsername(String username){
    	super.setUsername(username);
    }
    
    @Override
	public void setPassword(String password){
    	super.setPassword(password);
    }
    
    @Override
	public void setName(String name){
    	super.setName(name);
    }
    
    @Override
	public void setUmask(String umask){
    	super.setUmask(umask);
    }
    
    @Override 
    public void setHomeDirectory(Directory homeDirectory){
    	super.setHomeDirectory(homeDirectory);
    }
    

    public void remove(){
    	this.setHomeDirectory(null);
    	deleteDomainObject();
    }
    
    
	public boolean isRoot() {
		return false;
	}
    
}
