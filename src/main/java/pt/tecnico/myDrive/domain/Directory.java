package pt.tecnico.myDrive.domain;

public class Directory extends Directory_Base {
    
    public Directory() {
        super();

    }
    
    public Directory(int id, String filename, String userMask, User owner) /* TODO: throws*/{
    	if(!filename.equals("/")){
    		// TODO : throw execption
    	}
    	super.init(id, filename, userMask, owner);
    	addFile(this);
    	setIdParentDir(this.getId());
        
    }
    
    


	public Directory(int id, String filename, String userMask, User owner, Directory father) /* TODO: throws*/{
    	super.init(id, filename, userMask, owner);
    	addFile(father);
    	setIdParentDir(father.getId());
    	
    	/*addFile(new Directory(father.getId(), "..", father.getPermissions(), father.getOwner()
    			, father.getFather()));*/
    }
    
    
    public void addFile(File file) /* TODO: throws*/{
    	if(hasFile(file.getFilename())){
    		// TODO : throw
    	}
    	super.addFiles(file);
    }
    
    public Directory getFather() {
		// TODO Auto-generated method stub
    	if(getFileById(super.getIdParentDir()).getClass() == Directory.class){
    		// Minor verification just to be sure that file .. is actually a directory
    		return (Directory) getFileById(super.getIdParentDir());
    		
    	}else {
    		return null;
    	}
	}
    
    /*private void setFilesystemRoot(Directory root){
    	super.setFilesSet(root.getFilesSet());
    }*/
    
   
    public void removeFile(String filename) /* TODO: throws*/{
    	if(!hasFile(filename)){
    		// TODO : throw
    	}
    	super.removeFiles(getFileByName(filename));
    }
    

    public File getFileByName(String name) {
    	// TODO : throw exception instead of returning null
        for (File file: super.getFilesSet())
            if (file.getFilename().equals(name))
                return file;
        return null;
    }
    
    public File getFileById(Integer id) {
    	// TODO : throw exception instead of returning null
        for (File file: super.getFilesSet())
            if (file.getId().equals(id))
                return file;
        return null;
    }

    public boolean hasFile(String filename) {
        return getFileByName(filename) != null;
    }

    @Override
    public void setIdParentDir(Integer idParentDir){
    	super.setIdParentDir(idParentDir);
    }
    
    @Override
    public void remove() {
        for (File f: getFilesSet())
            f.remove();
        setIdParentDir(null);
		setFilesystem(null);
        super.remove();
        deleteDomainObject();
    }
    
    
    
    

	@Override
	public Directory changeDirectory() {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public String printContent() {
		// TODO throw exception and remove return
		return null;
	}

	@Override
	public void executeApp() {
		// TODO throw exception and remove return
		
	}
    
}
