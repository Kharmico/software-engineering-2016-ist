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

	private static final Logger log = LogManager.getRootLogger();

	private static final String ROOT_USER = Root.ROOT_USERNAME;
	private static final String GUEST_USER = Guest.GUEST_USERNAME;
	private static final String HOME_DIR = "home";
	private static final int MAX_PATH_SIZE = 1024;
	private static final String PATH_DELIM = "/";

	public FileSystem(MyDriveManager mdm) {
		super();
		setMyDriveManager(mdm);

		super.setIdSeed(0);

		Root root = new Root();
		this.addUsers(root);

		try{
			super.setFsRoot(new Directory(this.generateUniqueId(),
					PATH_DELIM, root.getUmask(), root, this));

		}catch(InvalidFileNameException | InvalidMaskException e){
    		/* This exception should not occur it only exists to protect the method against
    		* bad programming
    		*/
			log.trace(e.getMessage());
			e.printStackTrace();
		}

		System.out.println(getFsRoot().getFilesSet().size());
		System.out.println(getSlash().getFilesSet().size());

		Directory home = new Directory(generateUniqueId(), HOME_DIR, root.getUmask(), root, getSlash());

		addToSlash(home);

		Directory rootHomeDirectory = new Directory(generateUniqueId(),
				root.getUsername(), root.getUmask(), root, home);
		home.addFile(rootHomeDirectory);
		//root.setHomeDirectory(rootHomeDirectory);
		root.setHomeDirectory((Directory) home.getFileByName(ROOT_USER));

		Guest guest = new Guest();
		Directory guestHomeDirectory = new Directory(generateUniqueId(),
				guest.getUsername(), guest.getUmask(), guest, home);
		home.addFile(guestHomeDirectory);
		guest.setHomeDirectory((Directory) home.getFileByName(GUEST_USER));
		addUsers(guest);
	}


	Directory getSlash(){
		return super.getFsRoot();
	}

	
	private void addToSlash(File file){
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
					username, toCreate.getUmask(), toCreate, getHomeDirectory());
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
		if(user.isRoot() || user.isGuest()){
			throw new IllegalRemovalException("You can't remove " + user.getName());
		}else {
			super.removeUsers(user);
		}
	}
	

	protected void removeUsers(String username) throws IllegalRemovalException, UserUnknownException {
		hasUser(username);
		if(username.equals(ROOT_USER))
			throw new IllegalRemovalException(username);
		else{
			User toRemove = getUserByUsername(username);
			toRemove.remove();
			super.removeUsers(toRemove);
		}
	}

	protected User getUserByUsername(String username) throws UserUnknownException {
		for (User user: super.getUsersSet()){
			if (user.getUsername().equals(username))
				return user;
		}
		throw new UserUnknownException(username);
	}

	
	protected void hasUser(String username) throws UserUnknownException {
		this.getUserByUsername(username);
	}


	protected User getRoot() throws UserUnknownException {
		return getUserByUsername(ROOT_USER);
	}

	protected User getGuest() throws UserUnknownException {
		return getUserByUsername(GUEST_USER);
	}


    /* Directory */

	void createDirectory(String path, Directory currentDirectory, User currentUser)
			throws InvalidFileNameException, FileAlreadyExistsException, InvalidMaskException, FileUnknownException {

		Directory beforeLast = absolutePath(path, currentUser, currentDirectory);
		beforeLast.addFile(new Directory(this.generateUniqueId(), getLastPathToken(path),
				currentUser.getUmask(),	currentUser, beforeLast));
	}
	
	private Directory changeDirectory(String directoryName, Directory currentDirectory, User currentUser)
			throws FileUnknownException{

		currentDirectory.getFileByName(directoryName).checkAccessRead(currentUser);
		return currentDirectory.changeDirectory(directoryName, currentUser);
	}

	Directory getLastDirectory(String path, Directory currentDir, User currentUser) throws FileUnknownException, PathIsTooBigException, AccessDeniedException {
		if(path.equals("/"))
			return getSlash();

		if(path.equals("."))
			return currentDir;
		else if(path.equals(".."))
			return currentDir.getFather();

		Directory beforeLast = absolutePath(path, currentUser, currentDir);

		String[] tokens = path.split(PATH_DELIM);

		String name = tokens.length == 0 ? path : tokens[tokens.length - 1];

		beforeLast.changeDirectory(name,currentUser);

		return (Directory) beforeLast.getFileByName(name);

	}

	Directory absolutePath(String path, User currentUser, Directory currentDirectory) throws FileUnknownException, PathIsTooBigException{
		String resultantPath;

		if(path.equals(PATH_DELIM)){
			return getFsRoot();
		} else if(path.startsWith(PATH_DELIM)){
			resultantPath = path;
		} else{
			resultantPath = currentDirectory.getPath().equals(PATH_DELIM) ? currentDirectory.getPath() + path : currentDirectory.getPath() + PATH_DELIM + path;
		}
		if((resultantPath.length() > MAX_PATH_SIZE)){
			throw new PathIsTooBigException(path);
		}
		Directory directory = getSlash();
		String[] fileLocation = resultantPath.split(PATH_DELIM);
		for(int i = 1; i < (fileLocation.length - 1) ; i++)
			directory = changeDirectory(fileLocation[i], directory, currentUser);
		return directory;
	}

	String getDirectoryFilesName(String path, User currentUser, Directory currentDir)
			throws FileUnknownException, AccessDeniedException{
		if(path.equals("."))
			return currentDir.getDirectoryFilesName();
		else if(path.equals(".."))
			return currentDir.getFather().getDirectoryFilesName();
		log.debug(absolutePath(path, getRoot(), currentDir).getPath());
		File target = absolutePath(path, currentUser, currentDir).getFileByName(getLastPathToken(path));
		target.checkAccessRead(currentUser);
		return target.getDirectoryFilesName();
	}

	
	private void addDirectoryToHome(Directory toAdd){
		getSlash().getFileByName(HOME_DIR).addFile(toAdd);
	}


    /* Files */


	String readFile(String path, Directory currentDirectory, User currentUser)
			throws FileUnknownException, IsNotPlainFileException {

		Directory directory = absolutePath(path, currentUser, currentDirectory);
		String filename = getLastPathToken(path);
		File f = directory.getFileByName(filename);
		f.checkAccessRead(currentUser);
		return f.printContent(currentUser);
	}
	
	void createPlainFile(String path, Directory currentDirectory, User currentUser)
			throws InvalidFileNameException, InvalidMaskException, FileAlreadyExistsException {

		Directory directory = absolutePath(path, currentUser, currentDirectory);
		String filename = getLastPathToken(path);
		directory.checkAccessWrite(currentUser);

		for(File f : directory.getFilesSet()){
			if(f.getFilename().equals(filename))
				throw new FileAlreadyExistsException(filename);
		}
		directory.addFile(new PlainFile(this.generateUniqueId(), filename, currentUser.getUmask(),
				currentUser, directory));
	}

	
	void createPlainFile(String path, Directory currentDirectory, User currentUser, String content)
			throws InvalidFileNameException, InvalidMaskException, FileAlreadyExistsException {

		Directory directory = absolutePath(path, currentUser, currentDirectory);
		String filename = getLastPathToken(path);
		directory.checkAccessWrite(currentUser);

		for(File f : directory.getFilesSet()){
			if(f.getFilename().equals(filename))
				throw new FileAlreadyExistsException(filename);
		}
		
		directory.addFile(new PlainFile(this.generateUniqueId(), filename, currentUser.getUmask(),
				currentUser, content, directory));
	}	

	
	void createLinkFile(String path, Directory currentDirectory, User currentUser, String content)
			throws LinkFileWithoutContentException, InvalidFileNameException, InvalidMaskException, FileAlreadyExistsException{

		Directory directory = absolutePath(path, currentUser, currentDirectory);
		String filename = getLastPathToken(path);
		directory.checkAccessWrite(currentUser);

		for(File f : directory.getFilesSet()){
			if(f.getFilename().equals(filename))
				throw new FileAlreadyExistsException(filename);
		}
		
		directory.addFile(new LinkFile(this.generateUniqueId(), filename, currentUser.getUmask(),
				currentUser, content, directory));
	}

	
	void createAppFile(String path, Directory currentDirectory, User currentUser)
			throws InvalidFileNameException, InvalidMaskException, FileAlreadyExistsException{

		Directory directory = absolutePath(path, currentUser, currentDirectory);
		String filename = getLastPathToken(path);
		directory.checkAccessWrite(currentUser);

		for(File f : directory.getFilesSet()){
			if(f.getFilename().equals(filename))
				throw new FileAlreadyExistsException(filename);
		}
		
		directory.addFile(new AppFile(this.generateUniqueId(), filename, currentUser.getUmask(),
				currentUser, directory));
	}

	
	void createAppFile(String path, Directory currentDirectory, User currentUser, String content)
			throws InvalidFileNameException, InvalidMaskException, FileAlreadyExistsException, InvalidContentException{

		Directory directory = absolutePath(path, currentUser, currentDirectory);
		String filename = getLastPathToken(path);
		directory.checkAccessWrite(currentUser);

		for(File f : directory.getFilesSet()){
			if(f.getFilename().equals(filename))
				throw new FileAlreadyExistsException(filename);
		}
		
		directory.addFile(new AppFile(this.generateUniqueId(), filename, currentUser.getUmask(),
				currentUser, content, directory));
	}


	void removeFile(String path, User currentUser, Directory currentDir)
			throws FileUnknownException, AccessDeniedException {

		File fileToRemove = absolutePath(path, currentUser, currentDir).getFileByName(getLastPathToken(path));

		fileToRemove.checkAccessDelete(currentUser);

		try {
			fileToRemove.isEmpty();
			for (File f : getRecursiveRemovalContent((Directory) fileToRemove)) {
				f.checkAccessDelete(currentUser);
				f.remove();
			}
		}
		catch(IsNotDirectoryException e){
			//
		} finally {
			fileToRemove.remove();
		}
	}

	private String getLastPathToken(String path) {
		if(path.equals(PATH_DELIM))
			return ".";
		else
			return path.substring(path.lastIndexOf(PATH_DELIM) + 1);
	}

	private ArrayList<File> getRecursiveRemovalContent(Directory currentDir){
		ArrayList<File> toBeRemoved = new ArrayList<>();

		for(File f : currentDir.getFilesSet()){
			if(!f.equals(currentDir.getFather()) && !f.equals(currentDir)) {
				try {
					f.isCdAble();
					toBeRemoved.addAll(getRecursiveRemovalContent((Directory) f));
				} catch (IsNotDirectoryException e) {
					// 	in case of delete plainfiles
				} finally {
					toBeRemoved.add(f);
				}
			}
		}
		return toBeRemoved;
	}

	void writeContent(String path, User currentUser, Directory currentDirectory, String content){
		Directory d = absolutePath(path, currentUser, currentDirectory);
		String filename = getLastPathToken(path);
		d.getFileByName(filename).checkAccessWrite(currentUser);
		d.getFileByName(filename).writeContent(content, currentUser);
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

	void xmlImport(Element element) throws ImportDocumentException {
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
		catch (FileUnknownException e) {
			/* This exception should not occur it only exists to protect the method against
			* bad programming
			*/
			log.trace(e.getMessage());
			e.printStackTrace();
		} catch (IsNotDirectoryException e){
			throw new ImportDocumentException();
		}
		finally {
			if(next == null){
				next = new Directory(this.generateUniqueId(), name, user.getUmask(), user, current);
				current.addFile(next);
			}

		}
		return next;
	}

	
	/*  Creates the path until last token */
	private Directory createPath(String path) throws ImportDocumentException {
		String delims = PATH_DELIM;
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
			User toInsert;

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
					String[] tokens = node.getChild("home").getValue().split(PATH_DELIM);
					toInsert.setHomeDirectory(createDir(createPath(node.getChild("home").getValue()), tokens[tokens.length - 1], toInsert));
				} else {
					Directory homeDirectory = new Directory(this.generateUniqueId(), username,
							toInsert.getUmask(), toInsert, getHomeDirectory());
					toInsert.setHomeDirectory(homeDirectory);
					this.addDirectoryToHome(homeDirectory);
				}
				super.addUsers(toInsert);
			}
		}
	}


	private Vector<String> xmlImportFile(Element node) throws ImportDocumentException {
		Vector<String> output = new Vector<>();

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
				Directory beforeLast = createPath(node.getChild("path").getValue() + PATH_DELIM + input.get(1));
				if (node.getChild("contents") != null){
					this.createPlainFile(node.getChild("path").getValue() + PATH_DELIM + input.get(1), beforeLast, this.getUserByUsername(input.get(2)), node.getChild("contents").getValue());
				}
				else
					this.createPlainFile(node.getChild("path").getValue() + PATH_DELIM + input.get(1), beforeLast, this.getUserByUsername(input.get(2)));
			}
			else
				throw new ImportDocumentException();
		}
	}

	
	private void xmlImportDir(List<Element> dir) throws ImportDocumentException {
		for (Element node : dir) {
			Vector<String> input = xmlImportFile(node);
			if (node.getChild("path") != null)
				createDir(createPath(node.getChild("path").getValue() + PATH_DELIM + input.get(1)), input.get(1), this.getUserByUsername(input.get(2)));
			else
				throw new ImportDocumentException();
		}
	}

	
	private void xmlImportLink(List<Element> link) throws ImportDocumentException {
		for (Element node : link) {
			Vector<String> input = xmlImportFile(node);
			if (node.getChild("path") != null) {
				Directory beforeLast = createPath(node.getChild("path").getValue() + PATH_DELIM + input.get(1));
				if (node.getChild("value") != null){
					this.createLinkFile(node.getChild("path").getValue() + PATH_DELIM + input.get(1), beforeLast, this.getUserByUsername(input.get(2)), node.getChild("value").getValue());
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
				Directory beforeLast = createPath(node.getChild("path").getValue() + PATH_DELIM + input.get(1));
				if (node.getChild("method") != null)
					this.createAppFile(node.getChild("path").getValue() + PATH_DELIM + input.get(1), beforeLast, this.getUserByUsername(input.get(2)), node.getChild("method").getValue());
				else
					this.createAppFile(node.getChild("path").getValue() + PATH_DELIM + input.get(1), beforeLast, this.getUserByUsername(input.get(2)));
			}
			else
				throw new ImportDocumentException();
		}
	}

	
	/* Export XML */

	Element xmlExport(Element el){

		for(User usr : getUsersSet()) {
			if (!usr.isRoot())
				el.addContent(usr.xmlExport());
		}
		ArrayList<File> allfiles = getSlash().getAllFiles();

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

	User checkUser(String username, String password) throws UserUnknownException, WrongPasswordException{
		User toFind = getUserByUsername(username);
		//System.out.println("procurando user: " + toFind.getUsername());
		checkUserPass(toFind, password);
		return toFind;
	}

	private void checkUserPass(User user, String password) throws WrongPasswordException {
		if(!user.getPassword().equals(password))
			throw new WrongPasswordException(user.getPassword());
	}

}
