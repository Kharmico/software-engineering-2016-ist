package pt.tecnico.myDrive.domain;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jdom2.Element;
import org.joda.time.DateTime;
import pt.tecnico.myDrive.exception.*;

public abstract class File extends File_Base {

	private static final String INVALID_FILENAME_REGEX = "[a-zA-Z0-9]*/[a-zA-Z0-9]+";
	private static final Logger log = LogManager.getRootLogger();
	private String _extension;

	protected File(){
		super();     
    }

	@Deprecated
	protected void init(int id, String filename, String userMask, User owner) throws InvalidFileNameException, InvalidMaskException{
		setId(id);
		setFilename(filename);
		setPermissions(userMask);
		setOwner(owner);
		setLastModified(new DateTime());
		assignExtension(filename, getClass().getName());
	}

	protected void init(int id, String filename, String userMask, User owner, Directory parentDirectory) throws InvalidFileNameException, InvalidMaskException{
        setId(id);		
        setFilename(filename);
		setPermissions(userMask);
		setOwner(owner);
        setLastModified(new DateTime());
		setParentDirectory(parentDirectory);
		assignExtension(filename, getClass().getName());
	}

	/* Extensions */

	protected void assignExtension(String filename, String classname){
		String[] tokens = filename.split("\\.");
		if(tokens.length >= 2)
			_extension = tokens[tokens.length-1];
		else{
			tokens = classname.split("\\.");
			switch(tokens[tokens.length-1].toLowerCase()){
				case "appfile":
					setExtension("");
					break;
				case "linkfile":
					setExtension("link");
					break;
				case "plainfile":
					setExtension("plain");
					break;
				case "directory":
					setExtension("directory");
					break;
			}
		}
	}

	protected String getExtension(){
		return _extension;
	}

	protected void setExtension(String extension){
		_extension = extension;
	}

	/* END - EXTENSIONS */

	@Override
	public void setFilename(String filename) throws InvalidFileNameException {
		if(filename != null && filename.matches(INVALID_FILENAME_REGEX) ){
			throw new InvalidFileNameException(filename);
		}
		super.setFilename(filename);
	}


	public void setPermissions(String umask) throws InvalidMaskException{
		if(umask.length() != 8){
			throw new InvalidMaskException(umask);
		}
		super.setOwnerPermissions(umask.substring(0, 4));
        super.setOthersPermissions(umask.substring(4, 8));
	}


	@Override
    public void setParentDirectory(Directory parentDirectory){
//		if(parentDirectory == null){
//			super.setParentDirectory(null);
//		}else
//			parentDirectory.addFile(this);
		super.setParentDirectory(parentDirectory);
    }
	
	
	public Directory getFather(){
	    return super.getParentDirectory();
	}

	
	protected void remove() {
		removeObject();
		deleteDomainObject();
	}

	
	protected void removeObject(){
		setLastModified(null);
		setParentDirectory(null);
		setFilename(null);
		setId(null);
		setOwner(null);
	}
	
	
	String getPermissions(){
		return super.getOwnerPermissions() + super.getOthersPermissions(); 
	}


	void checkAccessRead(User u) throws AccessDeniedException {
		generalPermissionChecker(u, 0, 4);
	}

	void checkAccessWrite(User u) throws AccessDeniedException{
		generalPermissionChecker(u, 1, 5);
	}

	void checkAccessEx(User u) throws AccessDeniedException{
		generalPermissionChecker(u, 2, 6);
	}

	void checkAccessDelete(User u) throws AccessDeniedException{
		generalPermissionChecker(u, 3, 7);
	}
	
	private void generalPermissionChecker(User u, int ownPermIndex, int otherPermIndex)
			throws AccessDeniedException{
		/*log.debug("User: " + u.getUsername());
		log.debug("Directory Owner: " + getOwner().getUsername());
		log.debug("Home: " + u.getHomeDirectory().getPath());
		log.debug("Directory to be accessed: " + this.getPath());*/
		if(u.equals(getOwner())) {
			if (getPermissions().charAt(ownPermIndex) == '-')
				throw new AccessDeniedException(u);
		}
		else if(!u.isRoot()) {
			if (getPermissions().charAt(otherPermIndex) == '-')
				throw new AccessDeniedException(u);
			else if(u.isGuest())
				throw new AccessDeniedException(u);
		}
	}

	protected abstract void isCdAble() throws IsNotDirectoryException;
	
	public abstract String printContent(User logged) throws IsNotPlainFileException;

	public abstract void writeContent(String content, User logged) throws IsNotPlainFileException;

	protected abstract void executeFile(User logged, String args) throws IsNotPlainFileException;
	
	protected abstract void addFile(File toAdd) throws UnsupportedOperationException;
	
	protected abstract void removeFile(String toRemove) throws IsNotFileException;
	
	protected abstract String getDirectoryFilesName();
	
	protected abstract Directory changeDirectory(String dirname, User currentUser);

	protected abstract boolean isEmpty() throws IsNotDirectoryException;
	
	protected abstract void writeContentFromPlainFile(User logged, String[] array);
	
	protected abstract Element xmlExport();

	
	public String getPath(){
		if(getFilename().equals("/"))
			return getFilename();

		String path = getFilename();
		File file = getFather();
		while(!file.getFather().equals(file)){
			path = file.getFilename() + "/" + path;
			file = file.getFather();
		}
		path = file.getFilename() + path;

		return path;
	}



	@Override
	public String toString(){
		return this.getPermissions() + " " + super.getOwner()
				+ " " + super.getLastModified() + " " + super.getFilename();
	}

}
