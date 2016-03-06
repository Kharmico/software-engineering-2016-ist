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
    	this.setParentDirectory(this);
        
    }
    
    


	public Directory(int id, String filename, String userMask, User owner, Directory father) /* TODO: throws*/{
    	super.init(id, filename, userMask, owner);
    	this.setParentDirectory(father);

    }
    
    
    public void addFile(File file) /* TODO: throws*/{
    	if(hasFile(file.getFilename())){
    		// TODO : throw
    	}
    	super.addFiles(file);
    }
    
    public Directory getFather() {  
    	return super.getParentDirectory();
	}
   
   
    public void removeFile(String filename) /* TODO: throws*/{
    	if(!hasFile(filename)){
    		// TODO : throw
    	}
    	File toRemove = getFileByName(filename);
    	toRemove.remove();
    	super.removeFiles(toRemove);
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
    public void remove() {
        for (File f: getFilesSet())
            f.remove();
        super.setParentDirectory(null);
		this.setFilesystem(null);
        super.remove();
        deleteDomainObject();
    }
    
    
    
    @Override
    public void setParentDirectory(Directory parentDirectory){
    	super.setParentDirectory(parentDirectory);
    }

	@Override
	public Directory changeDirectory(String dirName) {
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
