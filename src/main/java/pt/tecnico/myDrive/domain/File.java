package pt.tecnico.myDrive.domain;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jdom2.Element;
import org.joda.time.DateTime;
import pt.tecnico.myDrive.exception.*;

public abstract class File extends File_Base {

	static final Logger log = LogManager.getRootLogger();
	public static final String INVALID_FILENAME_REGEX = "[a-zA-Z0-9]*/[a-zA-Z0-9]+";

	protected File(){
		super();     
    }
	
	protected void init(int id, String filename, String userMask, User owner) throws InvalidFileNameException, InvalidMaskException{
        setId(id);		
        setFilename(filename);
		setPermissions(userMask);
		setOwner(owner);
        setLastModified(new DateTime());	

	}
	
	
	@Override
	public void setId(Integer id){
		super.setId(id);
	}
	
	
	@Override
	public void setFilename(String filename) throws InvalidFileNameException {
		if(filename != null && filename.matches(INVALID_FILENAME_REGEX) ){
			throw new InvalidFileNameException(filename);
		}
		
		super.setFilename(filename);
	}
	
	
	@Override
	public void setOwner(User owner){
		super.setOwner(owner);
	}
	

	protected void setPermissions(String umask) throws InvalidMaskException{
		if(umask.length() != 8){
			throw new InvalidMaskException(umask);
		}
		super.setOwnerPermissions(umask.substring(0, 4));
        super.setOthersPermissions(umask.substring(4, 8));
	}
	
	
	@Override
	public void setLastModified(DateTime date){
		super.setLastModified(date);
	}
	
	
	@Override
    public void setParentDirectory(Directory parentDirectory){
		if(parentDirectory == null){
			super.setParentDirectory(null);
		}else
			parentDirectory.addFile(this);
    }
	
	
	protected Directory getFather(){
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
	
	
	protected String getPermissions(){
		return super.getOwnerPermissions() + super.getOthersPermissions(); 
	}

	
	protected void checkOwner(User u) throws AccessDeniedException{
		if(!u.equals(super.getOwner()) || !u.isRoot()){
			throw new AccessDeniedException(u);
		}
	}
	
	
	protected void checkAccess(User u){
		// Implement to the next Sprint
		// checkOwner(u);
		// TODO : implement permissions
	}

	protected void checkAccessRead(User u){
		if(u.getUsername().equals(getOwner().getUsername()))
			if(getPermissions().charAt(0) == '-')
				throw new AccessDeniedException(u);
		if(!(u.getUsername().equals(getOwner().getUsername()) && !(u.getUsername().equals("root"))))
			if(getPermissions().charAt(4) == '-')
				throw new AccessDeniedException(u);
	}

	protected void checkAccessWrite(User u){
		if(u.getUsername().equals(getOwner().getUsername()))
			if(getPermissions().charAt(1) == '-')
				throw new AccessDeniedException(u);
		if(!(u.getUsername().equals(getOwner().getUsername()) && !(u.getUsername().equals("root"))))
			if(getPermissions().charAt(5) == '-')
				throw new AccessDeniedException(u);
	}

	protected void checkAccessEx(User u){
		if(u.getUsername().equals(getOwner().getUsername()))
			if(getPermissions().charAt(2) == '-')
				throw new AccessDeniedException(u);
		if(!(u.getUsername().equals(getOwner().getUsername()) && !(u.getUsername().equals("root"))))
			if(getPermissions().charAt(6) == '-')
				throw new AccessDeniedException(u);
	}

	protected void checkAccessDelete(User u){
		if(u.getUsername().equals(getOwner().getUsername()))
			if(getPermissions().charAt(3) == '-')
				throw new AccessDeniedException(u);
		if(!(u.getUsername().equals(getOwner().getUsername()) && !(u.getUsername().equals("root"))))
			if(getPermissions().charAt(7) == '-')
				throw new AccessDeniedException(u);
	}
	
	
	protected abstract void isCdAble() throws IsNotDirectoryException;
	
	public abstract String printContent() throws IsNotPlainFileException;
	
	protected abstract void executeApp() throws IsNotAppFileException;
	
	protected abstract void addFile(File toAdd) throws UnsupportedOperationException;
	
	protected abstract void removeFile(String toRemove) throws IsNotFileException;
	
	protected abstract String getDirectoryFilesName();
	
	protected abstract Directory changeDirectory(String dirname, User currentUser);

	protected abstract boolean isEmpty() throws IsNotDirectoryException;
	
	protected abstract Element xmlExport();

	
	public String getPath(){

		String path = getFilename();
		File file = getFather();
		while(!file.getFather().equals(file)){
			path = file.getFilename() + "/" + path;
			file = file.getFather();
			System.out.println(path);
		}
		path = file.getFilename() + path;

		return path;
	}



	@Override
	public String toString(){
		return this.getPermissions() + " " + super.getOwner()
				+ " " + super.getLastModified() + " " + super.getFilename();
	}


	public abstract void writeContent(String content) throws IsNotPlainFileException;


}
