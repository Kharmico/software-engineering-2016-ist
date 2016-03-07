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
	
	public String getPermissions(){
		return super.getOwnerPermissions() + super.getOthersPermissions(); 
	}
	
	public void checkOwner(User u){
		if(!u.equals(super.getOwner()) || !u.isRoot()){
			// TODO :throw Exception
		}
	}
	
	public void checkAccess(User u){
		checkOwner(u);
		// TODO : implement permissions
	}
	
	public abstract void isCdAble(String dirName)/* TODO: throws*/;
	
	public abstract String printContent()/* TODO: throws*/;
	
	public abstract void executeApp()/* TODO: throws*/;
	
	public abstract Directory getFather() /* TODO: throws*/;
	
	public abstract void addFile(File toAdd) /* TODO: throws*/ ;
	
	public abstract void removeFile(String toRemove) /* TODO: throws*/ ;
	
	@Override
	public String toString(){
		return this.getPermissions() + super.getOwner()
			+ super.getLastModified() + super.getFilename();
	}
		
    
    
    
}
