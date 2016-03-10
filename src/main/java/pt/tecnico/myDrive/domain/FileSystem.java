package pt.tecnico.myDrive.domain;

import org.jdom2.Element;

import pt.tecnico.myDrive.exception.FileAlreadyExistsException;
import pt.tecnico.myDrive.exception.IllegalRemovalException;
import pt.tecnico.myDrive.exception.InvalidFileNameException;
import pt.tecnico.myDrive.exception.UserAlreadyExistsException;
import pt.tecnico.myDrive.exception.UserUnknownException;

import java.util.List;
import java.util.Vector;

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
    
    protected void addUsers(String username) throws UserAlreadyExistsException{
    	if(hasUser(username)){
    		throw new UserAlreadyExistsException(username);
    	}else{
    		User toCreate = new User(username);
    		Directory homeDirectory = new Directory(this.generateUniqueId(), 
    				username, toCreate.getUmask(), toCreate); 
    		toCreate.setHomeDirectory(homeDirectory);
    		this.addDirectorytoHome(homeDirectory);
    		super.addUsers(toCreate);
    	}
    }
    
    public void addUsers(User user) throws UserAlreadyExistsException{
    	if(hasUser(user.getUsername())){
    		throw new UserAlreadyExistsException(user.getUsername());
    	}else{
    		
    		super.addUsers(user);
    	}
    }
   
    protected void removeUsers(String username) throws IllegalRemovalException {
    	if(!hasUser(username) || username.equals(ROOT_USER)){
    		throw new IllegalRemovalException(username);
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
    
    private User getUserByUsername(String name) throws UserUnknownException {
        for (User user: super.getUsersSet()){
            if (user.getUsername().equals(name))
                return user;
        }
        throw new UserUnknownException(name);
    }
    
    private boolean hasUser(String username) {
        return this.getUserByUsername(username) != null;
    }
    
    // FIXME: Joao - Restrictions, we need ifs & stuff
    
    /* Directory */
    
    protected void createDirectory(String filename, Directory currentDirectory, User currentUser) throws InvalidFileNameException{
    	this.accessCheckerToCreate(filename, currentDirectory, currentUser);
    	Directory newDir = new Directory(this.generateUniqueId(), filename, currentUser.getUmask(), 
    			currentUser, currentDirectory);
    	currentDirectory.addFile(newDir);
    }
    
    protected Directory changeDirectory(String dirname, Directory currentDirectory, User currentUser){
    	return currentDirectory.changeDirectory (dirname, currentUser); 	
    }
    
    protected Directory AbsolutePath(String path, User currentUser){
    	Directory directory = getSlash();
    	String[] FileLocation = path.split("/");
    	for(int i = 1; i < (FileLocation.length-1) ; i++) 
    		directory = changeDirectory(FileLocation[i], directory, currentUser);
    	return directory;
    }
    
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
    
    protected void createPlainFile(String filename, Directory currentDirectory, User currentUser) throws InvalidFileNameException, FileAlreadyExistsException {
    	this.accessCheckerToCreate(filename, currentDirectory, currentUser);
		PlainFile plainFile = new PlainFile(this.generateUniqueId(), filename, currentUser.getUmask(), 
    			currentUser);
    	currentDirectory.addFile(plainFile);
	
    }
    
    protected void createPlainFile(String filename, Directory currentDirectory, User currentUser, String content) throws FileAlreadyExistsException{
    	this.accessCheckerToCreate(filename, currentDirectory, currentUser);
		PlainFile plainFile = new PlainFile(this.generateUniqueId(), filename, currentUser.getUmask(), 
    			currentUser, content);
    	currentDirectory.addFile(plainFile);
	
    }
    
    protected void createLinkFile(String filename, Directory currentDirectory, User currentUser) throws FileAlreadyExistsException{
    	this.accessCheckerToCreate(filename, currentDirectory, currentUser);
		LinkFile linkFile = new LinkFile(this.generateUniqueId(), filename, currentUser.getUmask(), 
    			currentUser);
    	currentDirectory.addFile(linkFile);
    	
    }
    
    protected void createLinkFile(String filename, Directory currentDirectory, User currentUser, String content) throws FileAlreadyExistsException{
    	this.accessCheckerToCreate(filename, currentDirectory, currentUser);
		LinkFile linkFile = new LinkFile(this.generateUniqueId(), filename, currentUser.getUmask(), 
    			currentUser, content);
    	currentDirectory.addFile(linkFile);
  
    }
    
    protected void createAppFile(String filename, Directory currentDirectory, User currentUser) throws FileAlreadyExistsException{
    	this.accessCheckerToCreate(filename, currentDirectory, currentUser);
		AppFile appFile = new AppFile(this.generateUniqueId(), filename, currentUser.getUmask(), 
    			currentUser);
    	currentDirectory.addFile(appFile);
    	
    }
    
    protected void createAppFile(String filename, Directory currentDirectory, User currentUser, String content)throws FileAlreadyExistsException{
    	this.accessCheckerToCreate(filename, currentDirectory, currentUser);
		AppFile appFile = new AppFile(this.generateUniqueId(), filename, currentUser.getUmask(), 
    			currentUser, content);
    	currentDirectory.addFile(appFile);

    }
    
    protected void accessCheckerToCreate(String filename, Directory currentDirectory, User currentUser)  throws FileAlreadyExistsException{
    	if(currentDirectory.hasFile(filename)){
    		throw new FileAlreadyExistsException(filename);
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
	
	/* SUBSTITUIR EXCEPTION POR UMA ADEQUADA */
    /* FALTA CASOS DE VERIFICAÇÃO */
    protected void xmlImport(Element element) throws IllegalStateException {
        this.xmlImportUser(element.getChildren("user"));
        this.xmlImportDir(element.getChildren("dir"));
        this.xmlImportLink(element.getChildren("link"));
        this.xmlImportApp(element.getChildren("app"));	
    }
    
    private Directory createDir(Directory current, String name, User user){
		Directory next = null;
		
    	if(!current.hasFile(name)){
    		current.checkAccess(user);
			next = new Directory (this.generateUniqueId(), name, this.getRoot().getUmask(), user, current);
			current.addFile(next);
		}
		else{ 
			try{
				current.getFileByName(name).isCdAble();
				next = (Directory) current.getFileByName(name);
				next.checkAccess(user);
			} catch (Exception e) {}
			/* TO DO */ 
		}
    	return next;
	}
    
    /*  Creates all the path until last token */
    private Directory createPath(String path) throws IllegalStateException {
        String delims = "[/]";
        String[] tokens = path.split(delims);
        Directory current = super.getSlash();
        
        /* Slash case */
        if (tokens.length == 0)
        	throw new IllegalStateException();
  
        for (int i = 0; i < (tokens.length - 1); i++)
        	current = createDir(current, tokens[i], this.getRoot());
        return current;
    }
    
    /* SUBSTITUIR EXCEPTION POR UMA ADEQUADA */
    /* FALTA CASOS DE VERIFICAÇÃO */
    private void xmlImportUser(List<Element> user) throws IllegalStateException {
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
           
                if (node.getChild("home").getValue() != null){
                	String[] tokens = node.getChild("home").getValue().split("[/]");
                	toInsert.setHomeDirectory(createDir(createPath(node.getChild("home").getValue()),tokens[tokens.length - 1],toInsert));
                }
                else{
                    Directory homeDirectory = new Directory(this.generateUniqueId(), username, 
                                    toInsert.getUmask(), toInsert);
                    toInsert.setHomeDirectory(homeDirectory);
                    this.addDirectorytoHome(homeDirectory);
                }
                super.addUsers(toInsert);
            }
        }
    }
    
    /* SUBSTITUIR EXCEPTION POR UMA ADEQUADA */
    /* FALTA CASOS DE VERIFICAÇÃO */
    private Vector<String> xmlImportFile(Element node) throws IllegalStateException {	
    	Vector<String> output = new Vector<String>();
    	
    	int id = node.getAttributeValue("id") != null ? Integer.parseInt(node.getAttributeValue("id")) : this.generateUniqueId();
 		if (!(id == (this.getIdSeed() + 1)))
 			throw new IllegalStateException();
 
 		String name = node.getChild("name").getValue();
 		if(name == null)
 			throw new IllegalStateException(); 
   
     	String ownerUsername = node.getChild("owner").getValue();
 		if(ownerUsername != null && this.hasUser(ownerUsername))
 			throw new IllegalStateException(); 
 		
 		String perm = node.getChild("perm").getValue();
 		if(perm == null)
 			perm = this.getUserByUsername(ownerUsername).getUmask();
 		
 		output.addElement(String.valueOf(id));
 		output.addElement(name);
 		output.addElement(ownerUsername);
 		output.addElement(perm);
 		
 		return output;
 		
    }
    
    /* SUBSTITUIR EXCEPTION POR UMA ADEQUADA */
    /* FALTA CASOS DE VERIFICAÇÃO */
    private void xmlImportDir(List<Element> dir) throws IllegalStateException {
    	for (Element node : dir) {
    		Vector<String> input = xmlImportFile(node);
			if (node.getChild("path").getValue() != null){
				String[] tokens = node.getChild("path").getValue().split("[/]");
				createDir(createPath(node.getChild("path").getValue()), tokens[tokens.length - 1], this.getUserByUsername(input.get(2)));
			}
			throw new IllegalStateException();
    	}
    }
    
    /* SUBSTITUIR EXCEPTION POR UMA ADEQUADA */
    /* FALTA CASOS DE VERIFICAÇÃO */
    private void xmlImportLink(List<Element> link) throws IllegalStateException {
    	for (Element node : link) {
    		Vector<String> input = xmlImportFile(node);
			if (node.getChild("path").getValue() != null)
				if (node.getChild("value").getValue() != null)
					this.createLinkFile(input.get(1), createPath(node.getChild("path").getValue()), this.getUserByUsername(input.get(2)), node.getChild("value").getValue());
				else
					this.createLinkFile(input.get(1), createPath(node.getChild("path").getValue()), this.getUserByUsername(input.get(2)));
    		}
    	throw new IllegalStateException();
    }
    
    /* SUBSTITUIR EXCEPTION POR UMA ADEQUADA */
    /* FALTA CASOS DE VERIFICAÇÃO */
    private void xmlImportApp(List<Element> app) throws IllegalStateException {
    	for (Element node : app) {
    		Vector<String> input = xmlImportFile(node);
			if (node.getChild("path").getValue() != null)
				if (node.getChild("method").getValue() != null)
					this.createAppFile(input.get(1), createPath(node.getChild("path").getValue()), this.getUserByUsername(input.get(2)), node.getChild("method").getValue());
				else
					this.createAppFile(input.get(1), createPath(node.getChild("path").getValue()), this.getUserByUsername(input.get(2)));
    		}
    	throw new IllegalStateException();
    }
   
}