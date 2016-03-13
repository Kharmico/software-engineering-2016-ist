package pt.tecnico.myDrive.domain;

import org.jdom2.Element;
import pt.tecnico.myDrive.exception.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class FileSystem extends FileSystem_Base {

	private static final String ROOT_USER = Root.ROOT_USERNAME;
	private static final String HOME_DIR = "home";

	protected FileSystem(MyDriveManager mdm) {
		super();
		setMyDriveManager(mdm);

		super.setIdSeed(new Integer(0));

		Root root = new Root();
		this.addUsers(root);
		try{
			super.setFsRoot(new Directory(this.generateUniqueId(),
					"/", root.getUmask(), root, getMyDriveManager(), this));

		}catch(IllegalStateException e){
    		/* This exception should not occur it only exists to protect the method against
    		* bad programming
    		*/
			e.printStackTrace();
		}

		Directory home = new Directory(this.generateUniqueId(), HOME_DIR, root.getUmask(), root, getSlash(),getMyDriveManager(),this);
		addToSlash(home);


		Directory rootHomeDirectory = new Directory(this.generateUniqueId(),
				root.getUsername(), root.getUmask(), root, home,getMyDriveManager(),this);
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

	protected void addUsers(String username) throws UserAlreadyExistsException {
		if(hasUser(username)){
			throw new UserAlreadyExistsException(username);
		}else{
			User toCreate = new User(username, getMyDriveManager(), this);
			Directory homeDirectory = new Directory(this.generateUniqueId(),
					username, toCreate.getUmask(), toCreate, getMyDriveManager(),this);
			toCreate.setHomeDirectory(homeDirectory);
			this.addDirectoryToHome(homeDirectory);
			super.addUsers(toCreate);
		}
	}

	public void addUsers(User user) throws UserAlreadyExistsException {
		try {
			hasUser(user.getUsername());
		}catch (UserUnknownException e) {
			super.addUsers(user);
			return;
		}

		throw new UserAlreadyExistsException(user.getUsername());

	}

	protected void removeUsers(String username) throws IllegalRemovalException, UserUnknownException {
		if(!hasUser(username))
			throw new UserUnknownException(username);
		if(username.equals(ROOT_USER))
			throw new IllegalRemovalException(username);
		else{
			// Should we remove the user home dir?  If not, new owner = root?!
			User toRemove = getUserByUsername(username);
			toRemove.remove();
			super.removeUsers(toRemove);
		}
	}
	private String getUserMask(String username) throws UserUnknownException {
		String mask = getUserByUsername(username).getUmask();

		return getUserByUsername(username).getUmask();
	}

	private User getUserByUsername(String name) throws UserUnknownException {
		for (User user: super.getUsersSet()){
			if (user.getUsername().equals(name))
				return user;
		}
		throw new UserUnknownException(name);
	}

	private boolean hasUser(String username) throws UserUnknownException {
		return this.getUserByUsername(username) != null;
	}


	protected User getRoot() throws UserUnknownException {
		return getUserByUsername(ROOT_USER);
	}

    
    /* Directory */

	protected void createDirectory(String path, Directory currentDirectory, User currentUser) throws InvalidFileNameException, FileAlreadyExistsException{
		Directory beforeLast = createPath(path);
		// FIXME : Sometimes this returns null
		//System.out.println("Direcc: " + beforeLast +"User: " + currentUser);
		//beforeLast.checkAccess(currentUser);
		Directory newDir = new Directory(this.generateUniqueId(), path.substring(path.lastIndexOf("/")+1), currentUser.getUmask(),
				currentUser, beforeLast, getMyDriveManager(),this);
		beforeLast.addFile(newDir);
	}

	protected Directory changeDirectory(String dirName, Directory currentDirectory, User currentUser) throws IsNotDirectoryException{
		if(!currentDirectory.hasFile(dirName))
			throw new IsNotDirectoryException(dirName);
		return currentDirectory.changeDirectory(dirName, currentUser);
	}

	protected Directory absolutePath(String path, User currentUser){
		Directory directory = getSlash();
		String[] FileLocation = path.split("/");
		for(int i = 1; i < (FileLocation.length-1) ; i++)
			directory = changeDirectory(FileLocation[i], directory, currentUser);
		return directory;
	}

	protected String getDirectoryFilesName(String path, User currentUser) {
		System.out.println(path.substring(path.lastIndexOf("/")+1));
		return absolutePath(path, getRoot()).getFileByName(path.substring(path.lastIndexOf("/")+1)).getDirectoryFilesName();
	}

	private void addDirectoryToHome(Directory toAdd){
		getSlash().getFileByName(HOME_DIR).addFile(toAdd);
	}


    /* Files */

	protected String printPlainFile(String path, User logged) throws FileUnknownException, IsNotPlainFileException, AccessDeniedException{
		Directory d = absolutePath(path, logged);
		String filename = path.substring(path.lastIndexOf("/") + 1);
		d.hasFile(filename);
		File f = d.getFileByName(filename);
		f.checkAccess(logged);
		return f.printContent();
	}

	protected void createPlainFile(String path, Directory currentDirectory, User currentUser) throws InvalidFileNameException, InvalidMaskException, FileAlreadyExistsException {
		Directory d = absolutePath(path, currentUser);
		d.checkAccess(currentUser);
		d.addFile(new PlainFile(this.generateUniqueId(), path.substring(path.lastIndexOf("/") + 1), currentUser.getUmask(),
				currentUser));

	}

	protected void createPlainFile(String path, Directory currentDirectory, User currentUser, String content) throws InvalidFileNameException, InvalidMaskException, FileAlreadyExistsException, InvalidContentException {
		Directory d = absolutePath(path, currentUser);
		d.checkAccess(currentUser);
		d.addFile(new PlainFile(this.generateUniqueId(), path.substring(path.lastIndexOf("/") + 1), currentUser.getUmask(),
				currentUser, content));

	}

	protected void createLinkFile(String path, Directory currentDirectory, User currentUser) throws InvalidFileNameException, InvalidMaskException, FileAlreadyExistsException{
		Directory d = absolutePath(path, currentUser);
		d.checkAccess(currentUser);
		d.addFile(new LinkFile(this.generateUniqueId(), path.substring(path.lastIndexOf("/") + 1), currentUser.getUmask(),
				currentUser));

	}

	protected void createLinkFile(String path, Directory currentDirectory, User currentUser, String content) throws InvalidFileNameException, InvalidMaskException, FileAlreadyExistsException{
		Directory d = absolutePath(path, currentUser);
		d.checkAccess(currentUser);
		d.addFile(new LinkFile(this.generateUniqueId(), path.substring(path.lastIndexOf("/") + 1), currentUser.getUmask(),
				currentUser, content));

	}

	protected void createAppFile(String path, Directory currentDirectory, User currentUser) throws InvalidFileNameException, InvalidMaskException, FileAlreadyExistsException{
		Directory d = absolutePath(path, currentUser);
		d.checkAccess(currentUser);
		d.addFile(new AppFile(this.generateUniqueId(), path.substring(path.lastIndexOf("/") + 1), currentUser.getUmask(),
				currentUser));

	}

	protected void createAppFile(String path, Directory currentDirectory, User currentUser, String content) throws InvalidFileNameException, InvalidMaskException, FileAlreadyExistsException, InvalidContentException{
		Directory d = absolutePath(path, currentUser);
		d.checkAccess(currentUser);
		d.addFile(new AppFile(this.generateUniqueId(), path.substring(path.lastIndexOf("/") + 1), currentUser.getUmask(),
				currentUser, content));

	}


	protected void removeFile(String path, User currentUser) throws IllegalRemovalException, FileUnknownException, AccessDeniedException, IsNotDirectoryException { //TODO: permissions and throws
		String toRemove = path.substring(path.lastIndexOf("/") + 1);
		Directory currentDirectory = absolutePath(path, currentUser);
		try{
			if(currentDirectory.getFileByName(toRemove).isEmpty()){
				currentDirectory.getFileByName(toRemove).remove();
			}else
				throw new IllegalRemovalException(toRemove);
		}catch (IsNotDirectoryException e){
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

	protected void xmlImport(Element element) throws IllegalStateException {
		this.xmlImportUser(element.getChildren("user"));
		this.xmlImportDir(element.getChildren("dir"));
		this.xmlImportPlain(element.getChildren("plain"));
		this.xmlImportLink(element.getChildren("link"));
		this.xmlImportApp(element.getChildren("app"));
	}

	protected Directory getHomeDirectory(){
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
			next = new Directory(this.generateUniqueId(), name, user.getUmask(), user, current.getFather(), getMyDriveManager(), this);
			current.addFile(next);
		}
		catch (IsNotDirectoryException e){
			throw new ImportDocumentException();
		}
		finally {
			return next;
		}
	}

	/*  Creates all the path until last token */
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
				toInsert = new User(username, getMyDriveManager(), this);

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
							toInsert.getUmask(), toInsert, getHomeDirectory(), getMyDriveManager(), this);
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
				else
					this.createLinkFile(node.getChild("path").getValue() + "/" + input.get(1), beforeLast, this.getUserByUsername(input.get(2)));
			}
			else
				throw new ImportDocumentException();
		}
	}

	private void xmlImportApp(List<Element> app) throws IllegalStateException, UserUnknownException {
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

	protected Element xmlExport(Element el){ // Supposedly done
		ArrayList<File> allfiles = new ArrayList<File>();

		for(User usr : getUsersSet()) {
			if (!usr.isRoot())
				el.addContent(usr.xmlExport());
		}

		allfiles = getSlash().getAllFiles();

		/* Remove files that were created by users */
		for(File f : getHomeDirectory().getFilesSet()){
			for(User usr : getUsersSet()) {
				if(f.getOwner().equals(usr)){
					allfiles.remove(f);
				}
			}
		}

		for(File file : allfiles)
			if(file.getId() >= 3)
				el.addContent(file.xmlExport());

		return el;

	}
    
}
