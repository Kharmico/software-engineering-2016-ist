package pt.tecnico.myDrive.domain;

public class FileSystem extends FileSystem_Base {
    
    private static final String ROOT_USER = "root";
	private static final String HOME_DIR = "home";

	public FileSystem() {
        super();
        this.init();
        
        
    }
    
    private void init(){
    	// TODO : Missing important stuff
    	Root root = new Root();
    	Directory rootHomeDirectory = new Directory(this.generateUniqueId(), 
				root.getUsername(), root.getUmask(), root);
    	root.setHomeDirectory(rootHomeDirectory);
    	
    	this.addUsers(root);
    	this.setSlash(new Directory(this.generateUniqueId(), 
    			"/", getUserMask(ROOT_USER), getRoot()));
    	
    	super.getSlash().addFile(new Directory(this.generateUniqueId(), HOME_DIR,
    			getUserMask(ROOT_USER), getRoot()));
    	this.addDirectorytoHome(rootHomeDirectory);
    	
    }
    /* Users */
    
    
    public void addUsers(String username){
    	if(hasUser(username)){
    		// TODO : throw
    	}else{
    		User toCreate = new User(username);
    		Directory homeDirectory = new Directory(this.generateUniqueId(), 
    				username, toCreate.getUmask(), toCreate); 
    		toCreate.setHomeDirectory(homeDirectory);
    		this.addDirectorytoHome(homeDirectory);
    		super.addUsers(toCreate);
    	}
    }
   
    public void removeUsers(String username){
    	if(!hasUser(username)){
    		// TODO : throw
    	}else{
	    	User u = getUserByUsername(username);
	    	u.remove();
	    	super.removeUsers(u);
    	}
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
    
    public boolean hasUser(String username) {
        return this.getUserByUsername(username) != null;
    }
    
    // FIXME: Joao - Restrictions, we need ifs & stuff
    
    /* Directory */
    
    public void createDirectory(String filename, Directory currentDirectory, User currentUser){
    	
    }
    
    
    // FIXME: ls and cd
    
    /* Files */
    
    // FIXME: rm and cat
    
    public void createPlainFile(String filename, Directory currentDirectory, User currentUser){
    	
    }
    
    public void createLinkFile(String filename, Directory currentDirectory, User currentUser){
    
    }
    
    public void createAppFile(String filename, Directory currentDirectory, User currentUser){
    	
    }
    
    public int generateUniqueId(){
    	// TODO : Add a real implementation
    	return 0;
    }
    
    @Override
    public void setSlash(Directory slash){
    	super.setSlash(slash);
    }
    
    private void addDirectorytoHome(Directory toAdd){
    	super.getSlash().getFileByName(HOME_DIR).addFile(toAdd);
    }
    
	public User getRoot() {
		return getUserByUsername(ROOT_USER);
	}
	
	public void remove(){
		this.getSlash().remove();
		setSlash(null);
		for (User user: super.getUsersSet()){
			this.removeUsers(user.getUsername());
			user.remove();
		}
		deleteDomainObject();
	}
    
}
