package pt.tecnico.myDrive.domain;


import org.jdom2.Element;
import org.joda.time.DateTime;
import pt.tecnico.myDrive.exception.*;

public abstract class File extends File_Base {
    
	protected File(){
		super();     
    }
	
	protected void init(int id, String filename, String userMask, User owner) throws InvalidFileNameException, InvalidMaskException{
        setId(id);		
		setOwner(owner);
        setFilename(filename);
		setPermissions(userMask);
        setLastModified(new DateTime());	

	}
	
	
	@Override
	public void setId(Integer id){
		super.setId(id);
	}
	
	
	@Override
	public void setFilename(String filename) throws InvalidFileNameException {
		// TODO: Restrictions remove during fenix frameweork rework
		/*if(filename.contains("/") || filename.contains("\0")){
			throw new InvalidFileNameException(filename);
		}*/
		
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

	
	protected void remove() {
		removeObject();
		deleteDomainObject();
	}

	
	protected void removeObject(){
		setLastModified(null);
		setParentDirectory(null);
		//setPermissions(null);
		setFilename(null);
		setId(null);
		setOwner(null);
	}
	
	
	protected String getPermissions(){
		return super.getOwnerPermissions() + super.getOthersPermissions(); 
	}

	
	protected void checkOwner(User u) throws AccessDeniedException{
		// FIXME : Plz implement this right
		if(!u.equals(super.getOwner()) || !u.isRoot()){
			throw new AccessDeniedException(u);
		}
	}
	
	
	protected void checkAccess(User u){

		// checkOwner(u);
		// TODO : implement permissions
	}
	
	
	protected abstract void isCdAble() throws IsNotDirectoryException;
	
	protected abstract String printContent() throws IsNotPlainFileException;
	
	protected abstract void executeApp() throws IsNotAppFileException;
	
	protected abstract void addFile(File toAdd) throws UnsupportedOperationException;
	
	protected abstract void removeFile(String toRemove) throws IsNotFileException;
	
	protected abstract String getDirectoryFilesName();
	
	protected abstract Directory changeDirectory(String dirname, User currentUser);

	protected abstract boolean isEmpty() throws IsNotDirectoryException;
	
	protected abstract Element xmlExport();

	
	protected String getPath(){
		String path = "";
		File file = getFather();
		while(!file.getFather().equals(file)){
			if(path.equals("")){
				path = file.getFilename();
			}else
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
