package pt.tecnico.myDrive.domain;

public class Directory extends Directory_Base {
    
    public Directory() {
        super();

    }
    
    public Directory(int id, String filename, String userMask, User owner) throws IllegalStateException{
    	if(!filename.equals("/")){
    		throw new IllegalStateException();
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
   
    
    ////////////////Mudar Directorio em construçao /////////////////////////////////////////////////
    public Directory changeDirectory(String dirname, User currentUser){
    	getFileByName(dirname).isCdAble();
    	return (Directory) getFileByName(dirname);
    }
    /////////////////////////////////////////////////////////////////////////////////////////////////
    
    public void removeFile(String filename) /* TODO: throws*/{
    	if(!hasFile(filename)){
    		// TODO : throw
    	}
    	File toRemove = getFileByName(filename);
    	toRemove.remove();
    	super.removeFiles(toRemove);
    }
    
    //////////////// Funçao ls para testar//////////////////
    
    public void getDirectoryFilesName() {
        for (File file: super.getFilesSet()){
        	System.out.println(file.getFilename());
        }  
    }   
    
    /////////////////////////////////////////////////////
    
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
    
    /* Fenixframework binary relations setters */
    
    @Override
    public void setUser(User user){
    	if(user == null){
    		super.setUser(null);
    	}else
    		user.setHomeDirectory(this);
    }
    
    @Override
    public void setFilesystem(FileSystem fs){
    	if(fs == null){
    		super.setFilesystem(null);
    	}else
    		fs.setSlash(this);
    }
    @Override
    public void setMyDriveManager(MyDriveManager mngr){
    	if(mngr == null){
    		super.setMyDriveManager(null);
    	}else
    		mngr.setCurrentDirectory(this);
    }

	@Override
	public void isCdAble() {
		// TODO Auto-generated method stub
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
