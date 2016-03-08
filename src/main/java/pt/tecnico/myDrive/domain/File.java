package pt.tecnico.myDrive.domain;


import org.joda.time.DateTime;

public abstract class File extends File_Base {
    
	public File(){	
		super();     
    }
		
	protected void init(int id, String filename, String userMask, User owner) /* TODO: throws*/{
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
	public void setFilename(String filename){
		if(filename.contains("/") || filename.contains("\0")){
			// TODO : throw exception
		}
		
		super.setFilename(filename);
	}
	
	@Override
	public void setOwner(User owner){
		super.setOwner(owner);
	}
	
	//@Override
	public void setPermissions(String umask){
		if(umask.length() != 8){
			// TODO : throw exception
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
	
	public void remove() {
		setLastModified(null);
		setId(null);
		setOwner(null);
        deleteDomainObject();
    }
	
	protected String getPermissions(){
		return super.getOwnerPermissions() + super.getOthersPermissions(); 
	}
	
	protected void checkOwner(User u){
		if(!u.equals(super.getOwner()) || !u.isRoot()){
			// TODO :throw Exception
		}
	}
	
	protected void checkAccess(User u){
		checkOwner(u);
		// TODO : implement permissions
	}
	
	protected abstract void isCdAble() throws UnsupportedOperationException;
	
	protected abstract String printContent()/* TODO: throws*/;
	
	protected abstract void executeApp()/* TODO: throws*/;
	
	protected abstract Directory getFather() throws UnsupportedOperationException;
	
	protected abstract void addFile(File toAdd) throws UnsupportedOperationException ;
	
	protected abstract void removeFile(String toRemove) throws UnsupportedOperationException ;
	
	@Override
	public String toString(){
		return this.getPermissions() + super.getOwner()
			+ super.getLastModified() + super.getFilename();
	}
		
    
    
    
}
