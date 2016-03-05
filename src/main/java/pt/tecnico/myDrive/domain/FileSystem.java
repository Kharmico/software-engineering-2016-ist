package pt.tecnico.myDrive.domain;

public class FileSystem extends FileSystem_Base {
    
    public FileSystem() {
        super();
        this.init();
        
        
    }
    
    private void init(){
    	// TODO : Missing important stuff
    	this.addUsers(new Root());
    	
    	Directory slash = new Directory(0, "/", getUserMask("root"), getRoot());
    	this.setSlash(slash);
    	
    }
    
    @Override
    public void addUsers(User u){
    	super.addUsers(u);
    }
    
    public String getUserMask(String username){
    	// TODO : Verify that it doesn't return null
    	return getUserByUsername(username).getUmask();
    }
    
    public User getUserByUsername(String name) {
        for (User user: super.getUsersSet())
            if (user.getUsername().equals(name))
                return user;
        return null;
    }
    
    @Override
    public void setSlash(Directory slash){
    	super.setSlash(slash);
    }
    
	public User getRoot() {
		// TODO Auto-generated method stub
		return null;
	}
    
}
