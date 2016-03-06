package pt.tecnico.myDrive.domain;

public class FileSystem extends FileSystem_Base {
    
    private static final String ROOT_USER = "root";
	private static final String HOME_DIR = "home";

	public FileSystem() {
        super();
        this.init();
        
        
    }
    
    private void init(){
    	
    	super.setIdSeed(new Integer(0));
    	Root root = new Root();
    	Directory rootHomeDirectory = new Directory(this.generateUniqueId(), 
				root.getUsername(), root.getUmask(), root);
    	root.setHomeDirectory(rootHomeDirectory);
    	
    	this.addUsers(root);
    	try{
	    	this.setSlash(new Directory(this.generateUniqueId(), 
	    			"/", getUserMask(ROOT_USER), getRoot()));
	    	
    	}catch(IllegalStateException e){
    		/* This exception should not occur it only exists to protect the method against
    		* bad programming
    		*/
    		e.printStackTrace();
    	}
    	super.getSlash().addFile(new Directory(this.generateUniqueId(), HOME_DIR,
    			getUserMask(ROOT_USER), getRoot()));
    	this.addDirectorytoHome(rootHomeDirectory);
    	
    }
    
    /* Users */
    
    public void addUsers(String username)/*TODO: throws*/{
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
    
    public void addUsers(User user)/*TODO: throws*/{
    	if(hasUser(user.getUsername())){
    		// TODO : throw
    	}else{
    		
    		super.addUsers(user);
    	}
    }
   
    public void removeUsers(String username)/*TODO: throws*/{
    	if(!hasUser(username) || username.equals(ROOT_USER)){
    		// TODO : throw
    	}else{
    		// Should we remove the user home dir?
	    	User toRemove = getUserByUsername(username);
	    	toRemove.remove();
	    	super.removeUsers(toRemove);
    	}
    }
    private String getUserMask(String username){
    	// TODO : Verify that it doesn't return null
    	return getUserByUsername(username).getUmask();
    }
    
    private User getUserByUsername(String name) {
    	// TODO : throw exception instead of returning null
        for (User user: super.getUsersSet())
            if (user.getUsername().equals(name))
                return user;
        return null;
    }
    
    private boolean hasUser(String username) {
        return this.getUserByUsername(username) != null;
    }
    
    // FIXME: Joao - Restrictions, we need ifs & stuff
    
    /* Directory */
    
    public void createDirectory(String filename, Directory currentDirectory, User currentUser) /*TODO: throws*/{
    	this.accessCheckerToCreate(filename, currentDirectory, currentUser);
    	Directory newDir = new Directory(this.generateUniqueId(), filename, currentUser.getUmask(), 
    			currentUser, currentDirectory);
    	currentDirectory.addFile(newDir);
    	
    }
    
    public Directory changeDirectory(String dirname, Directory currentDirectory, User currentUser){
    	// TODO : Implement cd and remove return null
    	
    	return null;
    }
    
    
    // FIXME: ls - JP and cd - JP
    
    /* Files */
    
    // FIXME: rm - Carina and cat - Daniel
    
    public void createPlainFile(String filename, Directory currentDirectory, User currentUser)/*TODO: throws*/{
    	this.accessCheckerToCreate(filename, currentDirectory, currentUser);
		PlainFile plainFile = new PlainFile(this.generateUniqueId(), filename, currentUser.getUmask(), 
    			currentUser);
    	currentDirectory.addFile(plainFile);
	
    }
    
    public void createPlainFile(String filename, Directory currentDirectory, User currentUser, String content)/*TODO: throws*/{
    	this.accessCheckerToCreate(filename, currentDirectory, currentUser);
		PlainFile plainFile = new PlainFile(this.generateUniqueId(), filename, currentUser.getUmask(), 
    			currentUser, content);
    	currentDirectory.addFile(plainFile);
	
    }
    
    public void createLinkFile(String filename, Directory currentDirectory, User currentUser)/*TODO: throws*/{
    	this.accessCheckerToCreate(filename, currentDirectory, currentUser);
		LinkFile linkFile = new LinkFile(this.generateUniqueId(), filename, currentUser.getUmask(), 
    			currentUser);
    	currentDirectory.addFile(linkFile);
    	
    }
    
    public void createLinkFile(String filename, Directory currentDirectory, User currentUser, String content)/*TODO: throws*/{
    	this.accessCheckerToCreate(filename, currentDirectory, currentUser);
		LinkFile linkFile = new LinkFile(this.generateUniqueId(), filename, currentUser.getUmask(), 
    			currentUser, content);
    	currentDirectory.addFile(linkFile);
  
    }
    
    public void createAppFile(String filename, Directory currentDirectory, User currentUser)/*TODO: throws*/{
    	this.accessCheckerToCreate(filename, currentDirectory, currentUser);
		AppFile appFile = new AppFile(this.generateUniqueId(), filename, currentUser.getUmask(), 
    			currentUser);
    	currentDirectory.addFile(appFile);
    	
    }
    
    public void createAppFile(String filename, Directory currentDirectory, User currentUser, String content)/*TODO: throws*/{
    	this.accessCheckerToCreate(filename, currentDirectory, currentUser);
		AppFile appFile = new AppFile(this.generateUniqueId(), filename, currentUser.getUmask(), 
    			currentUser, content);
    	currentDirectory.addFile(appFile);

    }
    
    public void accessCheckerToCreate(String filename, Directory currentDirectory, User currentUser) /* TODO: throws*/{
    	if(currentDirectory.hasFile(filename)){
    		// TODO : throw exception
    		// WE also need to check if the user can write here
    		
    	}
    }
    
    /* Uniques Ids */
    
    private int getAndIncrementuniqueId(){
    	Integer idSeed = super.getIdSeed();
    	super.setIdSeed(new Integer(idSeed.intValue() + 1));
    	return idSeed.intValue();
    }
    
    private int generateUniqueId(){
    	return this.getAndIncrementuniqueId();
    }
    
    @Override
    public void setSlash(Directory slash){
    	super.setSlash(slash);
    }
 
    
    /* Fenixframework binary relations setters */
    
    @Override
    public void setMyDriveManager(MyDriveManager mngr){
    	if(mngr == null){
    		super.setMyDriveManager(null);
    	}else
    		mngr.setFilesystem(this);
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
