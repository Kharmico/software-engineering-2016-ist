package pt.tecnico.myDrive.domain;


import org.joda.time.DateTime;

import org.jdom2.Element;

import pt.tecnico.myDrive.exception.AccessDeniedException;
import pt.tecnico.myDrive.exception.InvalidFileNameException;
import pt.tecnico.myDrive.exception.InvalidMaskException;
import pt.tecnico.myDrive.exception.IsNotAppFileException;
import pt.tecnico.myDrive.exception.IsNotDirectoryException;
import pt.tecnico.myDrive.exception.IsNotFileException;
import pt.tecnico.myDrive.exception.IsNotPlainFileException;

public abstract class File extends File_Base {
    
	public File(){	
		super();     
    }
		
	protected void init(int id, String filename, String userMask, User owner) throws InvalidFileNameException, InvalidMaskException{
		setPermissions(userMask);		
        setId(new Integer(id));
        setFilename(filename);
        setLastModified(new DateTime());	
		setOwner(owner);
	}
	
	@Override
	public void setId(Integer id){
		super.setId(id);
	}
	
	@Override
	public void setFilename(String filename) throws InvalidFileNameException {
		if(filename.contains("/") || filename.contains("\0")){
			throw new InvalidFileNameException(filename);
		}
		
		super.setFilename(filename);
	}
	
	@Override
	public void setOwner(User owner){
		super.setOwner(owner);
	}
	
	//@Override
	public void setPermissions(String umask) throws InvalidMaskException{
		if(umask.length() != 8){
			throw new InvalidMaskException(umask);
		}
		super.setOwnerPermissions(umask.substring(0, 3));
        super.setOthersPermissions(umask.substring(4, 7));
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
	
	public void remove() {
		setLastModified(null);
		setId(null);
		setOwner(null);
        deleteDomainObject();
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
		checkOwner(u);
		// TODO : implement permissions
	}
	
	protected abstract void isCdAble() throws IsNotDirectoryException;
	
	protected abstract String printContent() throws IsNotPlainFileException;
	
	protected abstract void executeApp() throws IsNotAppFileException;
	
	protected abstract void addFile(File toAdd) throws UnsupportedOperationException;
	
	protected abstract void removeFile(String toRemove) throws IsNotFileException;
	
	public abstract boolean isDirectory();
	
	protected abstract Directory changeDirectory(String dirname, User currentUser);
	
	public abstract Element xmlExport();
	
	// TODO: Implement a recursive path calculator
	public String getPath(){
		String path = null;
		File file = getFather();
		while(!file.getFilename().equals("/")){
			path = file.getFilename() + "/" + path;
			file = file.getFather();
		}
		path = file.getFilename() + path;
		return path;
	}
	
	@Override
	public String toString(){
		return this.getPermissions() + super.getOwner()
			+ super.getLastModified() + super.getFilename();
	}
}
