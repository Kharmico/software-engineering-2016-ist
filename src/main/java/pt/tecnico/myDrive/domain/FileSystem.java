package pt.tecnico.myDrive.domain;

public class FileSystem extends FileSystem_Base {
    
    public FileSystem() {
        super();
        this.init();
        
        
    }
    
    private void init(){
    	// TODO : Missing important stuff
    	this.addUsers(new Root());
    	
    	Directory barra = new Directory(0, "/", getUserMask("root"), getRoot());
    	this.setSlash(barra);
    	
    	
    }
    /* Users */
    
    @Override
    public void addUsers(User u){
    	super.addUsers(u);
    }
    
    public void removeUser(String username){
    	User u = getUserByUsername(username);
    	u.remove();
    	super.removeUsers(u);
    }
    
    public String getUserMask(String username){
    	// TODO : Verify that it doesn't return null
    	return getUserByUsername(username).getUmask();
    }
    
    public User getUserByUsername(String name) {
    	// TODO : throw exception instead of returning null
        for (User user: super.getUsersSet())
            if (user.getUsername().equals(name))
                return user;
        return null;
    }
    
    // FIXME: Joao - Restrictions
    
    /* Directory */
    
    public void createDirectory(){
    	
    }
    
    public void createPlainFile(){
    	
    }
    
    public void createLinkFile(){
    
    }
    
    public void createAppFile(){
    	
    }
    // FIXME: ls and cd
    
    /* Files */
    
    // FIXME: rm and cat
    
    @Override
    public void setSlash(Directory slash){
    	super.setSlash(slash);
    }
    
	public User getRoot() {
		return getUserByUsername("root");
	}
	
	public void remove(){
		this.getSlash().remove();
		setSlash(null);
		for (User user: super.getUsersSet()){
			this.removeUser(user.getUsername());
			user.remove();
		}
		deleteDomainObject();
	}
    
}
