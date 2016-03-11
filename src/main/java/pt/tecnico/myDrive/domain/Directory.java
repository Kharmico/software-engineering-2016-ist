package pt.tecnico.myDrive.domain;

import org.jdom2.Element;

import pt.tecnico.myDrive.exception.FileAlreadyExistsException;
import pt.tecnico.myDrive.exception.FileUnknownException;
import pt.tecnico.myDrive.exception.IDUnknownException;
import pt.tecnico.myDrive.exception.InvalidFileNameException;
import pt.tecnico.myDrive.exception.InvalidMaskException;
import pt.tecnico.myDrive.exception.IsNotAppFileException;
import pt.tecnico.myDrive.exception.IsNotPlainFileException;

public class Directory extends Directory_Base {
    
    public Directory() {
        super();

    }
    
    public Directory(int id, String filename, String userMask, User owner) throws IllegalStateException, InvalidFileNameException, InvalidMaskException{
    	if(!filename.equals("/")){
    		throw new IllegalStateException();
    	}
    	super.init(id, filename, userMask, owner);
    	this.setParentDirectory(this);
        
    }
    
	public Directory(int id, String filename, String userMask, User owner, Directory father) throws InvalidFileNameException, InvalidMaskException{
		super.init(id, filename, userMask, owner);
    	this.setParentDirectory(father);

    }
    
    @Override
    public void addFile(File file) throws FileAlreadyExistsException {
    	if(hasFile(file.getFilename())){
    		throw new FileAlreadyExistsException(file.getFilename());
    	}
    	super.addFiles(file);
    }
    
    @Override
    protected Directory getFather() {  
    	return super.getParentDirectory();
	}
   
    
    protected Directory changeDirectory(String dirname, User currentUser){
    	File file = getFileByName(dirname);
    	file.checkAccess(currentUser);
    	file.isCdAble();
    	return (Directory) file;
    }
    
    public void removeFile(String filename) throws FileUnknownException{
    	if(!hasFile(filename)){
    		throw new FileUnknownException(filename);
    	}
    	File toRemove = getFileByName(filename);
    	toRemove.remove();
    	super.removeFiles(toRemove);
    }
    
    

	protected String getDirectoryFilesName() {
    	String ls = null;
        for (File file: super.getFilesSet()){ 
        	ls = ls + file.getFilename() + "\n";
        }  
        return ls;
    }   
 
    
    protected File getFileByName(String name) throws FileUnknownException {
        for (File file: super.getFilesSet())
            if (file.getFilename().equals(name))
                return file;
        throw new FileUnknownException(name);
    }
    
    protected File getFileById(Integer id) throws IDUnknownException{
        for (File file: super.getFilesSet())
            if (file.getId().equals(id))
                return file;
        throw new IDUnknownException(id);
    }

    protected boolean hasFile(String filename) {
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
	public boolean isDirectory(){
		return true;
	}

	@Override
	public String printContent() throws IsNotPlainFileException {
		throw new IsNotPlainFileException("Directory"); //do empty constructor on exception?
	}

	@Override
	public void executeApp() throws IsNotAppFileException {
		throw new IsNotAppFileException("Directory"); //do empty constructor on exception?
		
	}
    
	public Element xmlExport(){
		Element dir_el = new Element("dir");
		
		/* isDir verification cycle, including
		 * addContent and Elements for XMLExport
		 * cannot include slash (/) nor /home dirs
		 */
		
		// if(file.isDirectory() == true) print dir info and enter it
		// else print file info
		return dir_el;
	}
	/*for(files)
	 * Att -> id
	 * Elements -> nome, username do owner, mask, path
	 */
}
