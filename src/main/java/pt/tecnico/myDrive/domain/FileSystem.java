package pt.tecnico.myDrive.domain;

import org.jdom2.Element;

import java.util.List;

public class FileSystem extends FileSystem_Base {
    
    private static final String ROOT_USER = Root.ROOT_USERNAME;
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
    
    protected void addUsers(String username)/*TODO: throws*/{
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
   
    protected void removeUsers(String username)/*TODO: throws*/{
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
    
    protected void createDirectory(String filename, Directory currentDirectory, User currentUser) /*TODO: throws*/{
    	this.accessCheckerToCreate(filename, currentDirectory, currentUser);
    	Directory newDir = new Directory(this.generateUniqueId(), filename, currentUser.getUmask(), 
    			currentUser, currentDirectory);
    	currentDirectory.addFile(newDir);
    }
     ///////////////////////////////// Em construção ///////////////////////////////////
    protected Directory changeDirectory(String dirname, Directory currentDirectory, User currentUser){
    	return currentDirectory.changeDirectory (dirname, currentUser); 	
    }
    
    protected Directory AbsolutPath(String path, Directory currentDirectory, User currentUser){
    	return null;
    }
    ///////////////////////////////////////////////////////////////////////////////////
    
    
    public String getDirectoryFilesName(Directory currentDirectory) {
    	return currentDirectory.getDirectoryFilesName();
    }
    
    // FIXME: ls - JP and cd - JP
    
    /* Files */
    
    // FIXME: rm - Carina
    
    protected String printTextFile(String path, User logged) /*TODO throws FileUnknownException, IsNotTextFileException, AccessDeniedException*/{
    	//String FileLocation = path.substring(0,path.lastIndexOf("/"));
    	//Directory d = cd(FileLocation);
    	//String filename = path.substring(path.lastIndexOf("/")+1);
    	//fileExists(filename, d);
    	//accessChecker(filename, d, logged);
    	//File f = d.getFileByName(filename);
    	//return f.printContent();
    	return "work in progress";
    }
    
    protected void createPlainFile(String filename, Directory currentDirectory, User currentUser)/*TODO: throws*/{
    	this.accessCheckerToCreate(filename, currentDirectory, currentUser);
		PlainFile plainFile = new PlainFile(this.generateUniqueId(), filename, currentUser.getUmask(), 
    			currentUser);
    	currentDirectory.addFile(plainFile);
	
    }
    
    protected void createPlainFile(String filename, Directory currentDirectory, User currentUser, String content)/*TODO: throws*/{
    	this.accessCheckerToCreate(filename, currentDirectory, currentUser);
		PlainFile plainFile = new PlainFile(this.generateUniqueId(), filename, currentUser.getUmask(), 
    			currentUser, content);
    	currentDirectory.addFile(plainFile);
	
    }
    
    protected void createLinkFile(String filename, Directory currentDirectory, User currentUser)/*TODO: throws*/{
    	this.accessCheckerToCreate(filename, currentDirectory, currentUser);
		LinkFile linkFile = new LinkFile(this.generateUniqueId(), filename, currentUser.getUmask(), 
    			currentUser);
    	currentDirectory.addFile(linkFile);
    	
    }
    
    protected void createLinkFile(String filename, Directory currentDirectory, User currentUser, String content)/*TODO: throws*/{
    	this.accessCheckerToCreate(filename, currentDirectory, currentUser);
		LinkFile linkFile = new LinkFile(this.generateUniqueId(), filename, currentUser.getUmask(), 
    			currentUser, content);
    	currentDirectory.addFile(linkFile);
  
    }
    
    protected void createAppFile(String filename, Directory currentDirectory, User currentUser)/*TODO: throws*/{
    	this.accessCheckerToCreate(filename, currentDirectory, currentUser);
		AppFile appFile = new AppFile(this.generateUniqueId(), filename, currentUser.getUmask(), 
    			currentUser);
    	currentDirectory.addFile(appFile);
    	
    }
    
    protected void createAppFile(String filename, Directory currentDirectory, User currentUser, String content)/*TODO: throws*/{
    	this.accessCheckerToCreate(filename, currentDirectory, currentUser);
		AppFile appFile = new AppFile(this.generateUniqueId(), filename, currentUser.getUmask(), 
    			currentUser, content);
    	currentDirectory.addFile(appFile);

    }
    
    protected void accessCheckerToCreate(String filename, Directory currentDirectory, User currentUser) /* TODO: throws*/{
    	if(currentDirectory.hasFile(filename)){
    		// TODO : throw exception
    		// WE also need to check if the user can write here
    		
    	}
    }
    
    /* Uniques Ids */
    
    private int generateUniqueId(){
    	Integer idSeed = super.getIdSeed();
    	super.setIdSeed(new Integer(idSeed.intValue() + 1));
    	return idSeed.intValue();
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
    
	protected User getRoot() {
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
    
    /* ImportXML */
	
    protected void xmlImport(Element element){
            this.xmlImportUser(element.getChildren("user"));
            this.xmlImportDir(element.getChildren("directory"));
            this.xmlImportFile(element.getChildren("linkfile"));	
    }
    
    private Directory createDirectories(Directory current, String name, User user) /* TODO: throws*/{
            Directory nextDir = new Directory(this.generateUniqueId(), name, 
                    user.getUmask(), user, current);
            current.addFile(nextDir);
            return nextDir;
    }
    
    private void createPath(String path, User user) /* TODO: throws*/{
            String delims = "[/]";
            String[] tokens = path.split(delims);
            
            /* Path only a slash */
            if (tokens.length == 0)
                    return;
            else{
                    Directory currentDir = super.getSlash();
                    /* Creating after slash */
                    if (tokens.length == 1)
                            currentDir = this.createDirectories(currentDir, tokens[0], user);
                    else{
                    /* Creating folder path with root */
                    for (int i = 0; i < (tokens.length - 1); i++)
                            currentDir = this.createDirectories(currentDir, tokens[i], this.getRoot());
                    /* Last folder from user */
                    currentDir = this.createDirectories(currentDir, tokens[tokens.length - 1], user);
                    }
                    user.setHomeDirectory(currentDir);
                    super.addUsers(user);
            }
    }
    
    private void xmlImportUser(List<Element> user){
            for (Element node : user) {
                    String username = node.getAttributeValue("username");
                    User toInsert = this.getUserByUsername(username);
                    if (toInsert == null){
                            toInsert = new User(username);
                            
                            if (node.getChild("password").getValue() != null)
                                    toInsert.setPassword(node.getChild("password").getValue());
                            
                            if (node.getChild("name").getValue() != null)
                                    toInsert.setName(node.getChild("name").getValue());
                            
                            if (node.getChild("mask").getValue() != null)
                                    toInsert.setUmask(node.getChild("mask").getValue());
                            
                            if (node.getChild("home").getValue() != null)
                                    createPath(node.getChild("home").getValue(), toInsert);
                            else{
                                    Directory homeDirectory = new Directory(this.generateUniqueId(), username, 
                                                    toInsert.getUmask(), toInsert);
                                    toInsert.setHomeDirectory(homeDirectory);
                                    this.addDirectorytoHome(homeDirectory);
                                    super.addUsers(toInsert);
                            }
                    }
            }
    }
    
    private void xmlImportDir(List<Element> dir){
    }
    
    private void xmlImportFile(List<Element> file){
    }
}
