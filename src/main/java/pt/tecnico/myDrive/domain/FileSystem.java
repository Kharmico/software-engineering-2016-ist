package pt.tecnico.myDrive.domain;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jdom2.Element;
import pt.tecnico.myDrive.exception.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

public class FileSystem extends FileSystem_Base {

	static final Logger log = LogManager.getRootLogger();

	private static final String ROOT_USER = Root.ROOT_USERNAME;
	private static final String HOME_DIR = "home";
	public static final int MAX_PATH_SIZE = 1024;

	public FileSystem(MyDriveManager mdm) {
		super();
		setMyDriveManager(mdm);

		super.setIdSeed(new Integer(0));

		Root root = new Root();
		this.addUsers(root);

		try{
			super.setFsRoot(new Directory(this.generateUniqueId(),
					"/", root.getUmask(), root, this));

		}catch(InvalidFileNameException |InvalidMaskException e){
    		/* This exception should not occur it only exists to protect the method against
    		* bad programming
    		*/
			log.trace(e.getMessage());
			e.printStackTrace();
		}

		Directory home = new Directory(this.generateUniqueId(), HOME_DIR, root.getUmask(), root, getSlash(), this);

		addToSlash(home);

		Directory rootHomeDirectory = new Directory(this.generateUniqueId(),
				root.getUsername(), root.getUmask(), root, home, this);
		home.addFile(rootHomeDirectory);
		root.setHomeDirectory(rootHomeDirectory);
	}


	protected Directory getSlash(){
		return super.getFsRoot();
	}

	
	protected void addToSlash(File file){
		super.getFsRoot().addFile(file);
	}

	
	/* Fenixframework binary relations setters */

	@Override
	public void setMyDriveManager(MyDriveManager mngr){
		if(mngr == null){
			super.setMyDriveManager(null);
		}else
			mngr.setFilesystem(this);
	}
	

	/* Users */

	public void addUsers(String username) throws UserAlreadyExistsException , InvalidUsernameException{
		try {
			hasUser(username);
		}catch (UserUnknownException e){
			User toCreate = new User(username, this);
			Directory homeDirectory = new Directory(this.generateUniqueId(),
					username, toCreate.getUmask(), toCreate, getHomeDirectory(),this);
			this.addDirectoryToHome(homeDirectory);
			toCreate.setHomeDirectory(homeDirectory);
			super.addUsers(toCreate);
			return;
		}
		throw new UserAlreadyExistsException(username);
	}

	@Override
	public void addUsers(User user) throws UserAlreadyExistsException {
		
		try {
			hasUser(user.getUsername());
		}catch (UserUnknownException e) {
			super.addUsers(user);
			return;
		}

		throw new UserAlreadyExistsException(user.getUsername());
	}

	@Override
	public void removeUsers(User user){
		if(user.isRoot()){
			throw new IllegalRemovalException("You can't remove root user.");
		}else {
			super.removeUsers(user);
		}
	}
	

	protected void removeUsers(String username) throws IllegalRemovalException, UserUnknownException {
		hasUser(username);
		if(username.equals(ROOT_USER))
			throw new IllegalRemovalException(username);
		else{
			// Should we remove the user home dir?  If not, new owner = root?! - check next sprint
			User toRemove = getUserByUsername(username);
			toRemove.remove();
			super.removeUsers(toRemove);
		}
	}
	
	
	protected String getUserMask(String username) throws UserUnknownException {
		return getUserByUsername(username).getUmask();
	}

	
	private User getUserByUsername(String username) throws UserUnknownException {
		for (User user: super.getUsersSet()){
			if (user.getUsername().equals(username))
				return user;
		}
		throw new UserUnknownException(username);
	}

	
	private void hasUser(String username) throws UserUnknownException {
		this.getUserByUsername(username);
	}


	protected User getRoot() throws UserUnknownException {
		return getUserByUsername(ROOT_USER);
	}

    
    /* Directory */

	public void createDirectory(String path, Directory currentDirectory, User currentUser) throws InvalidFileNameException, FileAlreadyExistsException, InvalidMaskException, FileUnknownException {
		Directory beforeLast = absolutePath(path, currentUser, currentDirectory);
		beforeLast.addFile(new Directory(this.generateUniqueId(), path.substring(path.lastIndexOf("/")+1), currentUser.getUmask(),
				currentUser, beforeLast, this));
	}

	
	protected Directory changeDirectory(String directoryName, Directory currentDirectory, User currentUser) throws FileUnknownException{
		if(!currentDirectory.hasFile(directoryName))
			throw new FileUnknownException(directoryName);
		return currentDirectory.changeDirectory(directoryName, currentUser);
	}

	@Deprecated
	protected Directory absolutePath(String path, User currentUser) throws FileUnknownException, PathIsTooBigException{ //TODO delete this?
		if(path.length() > MAX_PATH_SIZE){
			throw new PathIsTooBigException(path);
		}
		Directory directory = getSlash();
		String[] FileLocation = path.split("/");
		for(int i = 1; i < (FileLocation.length - 1) ; i++)
			directory = changeDirectory(FileLocation[i], directory, currentUser);
		return directory;
	}

	// TODO: New method that allows search using relativePaths, this method should be called in the place of the former one.
	protected Directory absolutePath(String path, User currentUser, Directory currentDirectory) throws FileUnknownException, PathIsTooBigException{
		String resultantPath;
		if(path.startsWith("/")){
			resultantPath = path;
		}else{
			resultantPath = currentDirectory.getPath() + "/" + path;

		}
		System.out.println(resultantPath);
		if((resultantPath.length() > MAX_PATH_SIZE)){
			throw new PathIsTooBigException(path);
		}
		Directory directory = getSlash();
		String[] FileLocation = resultantPath.split("/");
		for(int i = 1; i < (FileLocation.length - 1) ; i++)
			directory = changeDirectory(FileLocation[i], directory, currentUser);
		return directory;
	}

	
	protected String getDirectoryFilesName(String path, User currentUser) throws FileUnknownException{
		return absolutePath(path, getRoot()).getFileByName(path.substring(path.lastIndexOf("/")+1)).getDirectoryFilesName();
	}

	
	private void addDirectoryToHome(Directory toAdd){
		getSlash().getFileByName(HOME_DIR).addFile(toAdd);
	}


    /* Files */

	protected String printPlainFile(String path, User currentUser) throws FileUnknownException, IsNotPlainFileException {
		Directory d = absolutePath(path, currentUser);
		String filename = path.substring(path.lastIndexOf("/") + 1);
		
		//d.hasFile(filename); ---------> Why is this here??? Seems pointless, useless!!!
		File f = d.getFileByName(filename);
		// f.checkAccess(logged); ----> TODO:  For now, this is useless!  Uncomment when checkAccess is implemented
		return f.printContent();
	}

	
	protected void createPlainFile(String path, Directory currentDirectory, User currentUser) throws FileUnknownException, InvalidFileNameException, InvalidMaskException, FileAlreadyExistsException {
		Directory d = absolutePath(path, currentUser, currentDirectory);
		String filename = path.substring(path.lastIndexOf("/") + 1);
		//d.checkAccess(currentUser);  ----> TODO:  For now, this is useless!  Uncomment when checkAccess is implemented
		for(File f : d.getFilesSet()){
			if(f.getFilename().equals(filename))
				throw new FileAlreadyExistsException(filename);
		}
		
		d.addFile(new PlainFile(this.generateUniqueId(), filename, currentUser.getUmask(),
				currentUser));
	}

	
	public void createPlainFile(String path, Directory currentDirectory, User currentUser, String content) throws FileUnknownException, InvalidFileNameException, InvalidMaskException, FileAlreadyExistsException {
		Directory d = absolutePath(path, currentUser, currentDirectory);
		String filename = path.substring(path.lastIndexOf("/") + 1);
		// d.checkAccess(currentUser); ----> TODO:  For now, this is useless!  Uncomment when checkAccess is implemented
		for(File f : d.getFilesSet()){
			if(f.getFilename().equals(filename))
				throw new FileAlreadyExistsException(filename);
		}
		
		d.addFile(new PlainFile(this.generateUniqueId(), filename, currentUser.getUmask(),
				currentUser, content));
	}	

	
	public void createLinkFile(String path, Directory currentDirectory, User currentUser, String content) throws FileUnknownException, InvalidFileNameException, InvalidMaskException, FileAlreadyExistsException{
		Directory d = absolutePath(path, currentUser, currentDirectory);;
		String filename = path.substring(path.lastIndexOf("/") + 1);
		// d.checkAccess(currentUser); ----> TODO:  For now, this is useless!  Uncomment when checkAccess is implemented
		for(File f : d.getFilesSet()){
			if(f.getFilename().equals(filename))
				throw new FileAlreadyExistsException(filename);
		}
		
		d.addFile(new LinkFile(this.generateUniqueId(), filename, currentUser.getUmask(),
				currentUser, content));
	}

	
	public void createAppFile(String path, Directory currentDirectory, User currentUser) throws FileUnknownException, InvalidFileNameException, InvalidMaskException, FileAlreadyExistsException{
		Directory d = absolutePath(path, currentUser, currentDirectory);;
		String filename = path.substring(path.lastIndexOf("/") + 1);
		// d.checkAccess(currentUser); ----> TODO:  For now, this is useless!  Uncomment when checkAccess is implemented
		for(File f : d.getFilesSet()){
			if(f.getFilename().equals(filename))
				throw new FileAlreadyExistsException(filename);
		}
		
		d.addFile(new AppFile(this.generateUniqueId(), filename, currentUser.getUmask(),
				currentUser));
	}

	
	public void createAppFile(String path, Directory currentDirectory, User currentUser, String content) throws FileUnknownException, InvalidFileNameException, InvalidMaskException, FileAlreadyExistsException, InvalidContentException{
		Directory d = absolutePath(path, currentUser, currentDirectory);
		String filename = path.substring(path.lastIndexOf("/") + 1);
		// d.checkAccess(currentUser); ----> TODO:  For now, this is useless!  Uncomment when checkAccess is implemented
		for(File f : d.getFilesSet()){
			if(f.getFilename().equals(filename))
				throw new FileAlreadyExistsException(filename);
		}
		
		d.addFile(new AppFile(this.generateUniqueId(), filename, currentUser.getUmask(),
				currentUser, content));
	}


	protected void removeFile(String path, User currentUser) throws FileUnknownException, IsNotDirectoryException {
		String toRemove = path.substring(path.lastIndexOf("/") + 1);
		Directory currentDirectory = absolutePath(path, currentUser);
		try{
			if(currentDirectory.getFileByName(toRemove).isEmpty()){
				currentDirectory.getFileByName(toRemove).remove();
			} //else    TODO: Permissions allowance!!!
				//throw new IllegalRemovalException(toRemove); AccessDeniedException
		} catch (IsNotDirectoryException e){
			currentDirectory.getFileByName(toRemove).remove();
		}
	}
	
	
    /* Uniques Ids */
    private int generateUniqueId(){
    	Integer idSeed = super.getIdSeed();
    	super.setIdSeed(++idSeed);
    	return idSeed;
    }
    

	protected void remove(){
		getSlash();
		for (User user: super.getUsersSet()){
			this.removeUsers(user.getUsername());
			user.remove();
		}
		deleteDomainObject();
	}
    
	
     /* ImportXML */

	protected void xmlImport(Element element) throws ImportDocumentException {
		this.xmlImportUser(element.getChildren("user"));
		this.xmlImportDir(element.getChildren("dir"));
		this.xmlImportPlain(element.getChildren("plain"));
		this.xmlImportLink(element.getChildren("link"));
		this.xmlImportApp(element.getChildren("app"));
	}

	
	public Directory getHomeDirectory(){
		return (Directory) this.getSlash().getFileByName(HOME_DIR);
	}

	
	private Directory createDir(Directory current, String name, User user) throws ImportDocumentException {
		Directory next = null;

		try{
			File file = current.getFileByName(name);
			file.isCdAble();
			next = (Directory) current.getFileByName(name);
		}
		catch (FileUnknownException e) {}
		catch (IsNotDirectoryException e){
			throw new ImportDocumentException();
		}
		finally {
			if(next == null){
				next = new Directory(this.generateUniqueId(), name, user.getUmask(), user, current, this);
				current.addFile(next);
			}
			return next;
		}
	}

	
	/*  Creates the path until last token */
	private Directory createPath(String path) throws ImportDocumentException {
		String delims = "/";
		String[] tokens = path.split(delims);
		Directory current = getSlash();

        /* Slash case */
		if (tokens.length == 0)
			throw new ImportDocumentException();

		for (int i = 1; i < (tokens.length - 1); i++)
			current = createDir(current, tokens[i], this.getRoot());

		return current;
	}

	
	private void xmlImportUser(List<Element> user) throws ImportDocumentException {
		for (Element node : user) {
			String username = node.getAttributeValue("username");
			User toInsert = null;

			try {
				toInsert = this.getUserByUsername(username);
			} catch (UserAlreadyExistsException e) {
				e.getMessage();
				throw new ImportDocumentException();
			} catch (UserUnknownException e) {
				toInsert = new User(username, this);

				if (node.getChild("password")!= null)
					toInsert.setPassword(node.getChild("password").getValue());

				if (node.getChild("name") != null)
					toInsert.setName(node.getChild("name").getValue());

				if (node.getChild("mask") != null)
					toInsert.setUmask(node.getChild("mask").getValue());

				if (node.getChild("home") != null) {
					String[] tokens = node.getChild("home").getValue().split("/");
					toInsert.setHomeDirectory(createDir(createPath(node.getChild("home").getValue()), tokens[tokens.length - 1], toInsert));
				} else {
					Directory homeDirectory = new Directory(this.generateUniqueId(), username,
							toInsert.getUmask(), toInsert, getHomeDirectory(), this);
					toInsert.setHomeDirectory(homeDirectory);
					this.addDirectoryToHome(homeDirectory);
				}
				super.addUsers(toInsert);
			}
		}
	}


	private Vector<String> xmlImportFile(Element node) throws ImportDocumentException {
		Vector<String> output = new Vector<String>();

		int id = this.generateUniqueId();

		String name = node.getChild("name").getValue();
		if(node.getChild("name") == null)
			throw new ImportDocumentException();

		String ownerUsername = node.getChild("owner").getValue();
		if(node.getChild("owner") != null){
			try{
				this.hasUser(ownerUsername);
			}
			catch (UserUnknownException e){
				throw new ImportDocumentException();
			}
		}

		String perm = node.getChild("perm").getValue();
		if(node.getChild("perm") == null)
			perm = this.getUserByUsername(ownerUsername).getUmask();

		output.addElement(String.valueOf(id));
		output.addElement(name);
		output.addElement(ownerUsername);
		output.addElement(perm);

		return output;
	}

	
	private void xmlImportPlain(List<Element> plain) throws ImportDocumentException {
		for (Element node : plain) {
			Vector<String> input = xmlImportFile(node);
			if (node.getChild("path") != null) {
				Directory beforeLast = createPath(node.getChild("path").getValue() + "/" + input.get(1));
				if (node.getChild("contents") != null){
					this.createPlainFile(node.getChild("path").getValue() + "/" + input.get(1), beforeLast, this.getUserByUsername(input.get(2)), node.getChild("contents").getValue());
				}
				else
					this.createPlainFile(node.getChild("path").getValue() + "/" + input.get(1), beforeLast, this.getUserByUsername(input.get(2)));
			}
			else
				throw new ImportDocumentException();
		}
	}

	
	private void xmlImportDir(List<Element> dir) throws ImportDocumentException {
		for (Element node : dir) {
			Vector<String> input = xmlImportFile(node);
			if (node.getChild("path") != null)
				createDir(createPath(node.getChild("path").getValue() + "/" + input.get(1)), input.get(1), this.getUserByUsername(input.get(2)));
			else
				throw new ImportDocumentException();
		}
	}

	
	private void xmlImportLink(List<Element> link) throws ImportDocumentException {
		for (Element node : link) {
			Vector<String> input = xmlImportFile(node);
			if (node.getChild("path") != null) {
				Directory beforeLast = createPath(node.getChild("path").getValue() + "/" + input.get(1));
				if (node.getChild("value") != null){
					this.createLinkFile(node.getChild("path").getValue() + "/" + input.get(1), beforeLast, this.getUserByUsername(input.get(2)), node.getChild("value").getValue());
				}
			}
			else
				throw new ImportDocumentException();
		}
	}

	
	private void xmlImportApp(List<Element> app) throws ImportDocumentException {
		for (Element node : app) {
			Vector<String> input = xmlImportFile(node);
			if (node.getChild("path") != null) {
				Directory beforeLast = createPath(node.getChild("path").getValue() + "/" + input.get(1));
				if (node.getChild("method") != null)
					this.createAppFile(node.getChild("path").getValue() + "/" + input.get(1), beforeLast, this.getUserByUsername(input.get(2)), node.getChild("method").getValue());
				else
					this.createAppFile(node.getChild("path").getValue() + "/" + input.get(1), beforeLast, this.getUserByUsername(input.get(2)));
			}
			else
				throw new ImportDocumentException();
		}
	}

	
	/* Export XML */

	protected Element xmlExport(Element el){
		ArrayList<File> allfiles = new ArrayList<File>();

		for(User usr : getUsersSet()) {
			if (!usr.isRoot())
				el.addContent(usr.xmlExport());
		}
		allfiles = getSlash().getAllFiles();

		/* Remove files that were created by users */
		for(File f : getHomeDirectory().getFilesSet()){
			for(User usr : getUsersSet()) {
				if(f.getOwner().equals(usr))
					allfiles.remove(f);
			}
		}
		Collections.reverse(allfiles);
		for(File file : allfiles){
			if(file.getId() >= 3)
				el.addContent(file.xmlExport());
		}
		return el;
	}

	protected User checkUser(String username, String password){
		User toFind = null;
		try{
			toFind = getUserByUsername(username);
		}
		catch(UserUnknownException e){
		}
		finally {
			if(toFind != null)
				toFind = toFind.getPassword().equals(password) ? toFind : null;
			return toFind;
		}
	}



}
