package pt.tecnico.myDrive.domain;

import java.util.ArrayList;

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
    
    public Directory(int id, String filename, String userMask, User owner) throws InvalidFileNameException, InvalidMaskException{
    	if(!filename.equals("/")){
    		throw new InvalidFileNameException(filename);
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
		throw new IsNotPlainFileException(this.getFilename());
	}

	@Override
	public void executeApp() throws IsNotAppFileException {
		throw new IsNotAppFileException(this.getFilename()); 
		
	}
    
	public ArrayList<File> getAllFiles(){ //TODO: Unfinished business!!!
		int i;
		Directory dir = null;
		ArrayList<File> allfiles = new ArrayList<File>();
		
		// Getting all files present in slash (includes directories)
		for(File file : getFilesSet()){
			allfiles.add(file);
		}
		
		// Getting all files inside all directories (includes directories)
		for(i = 0; i < allfiles.size(); i++){
			if(allfiles.get(i).isDirectory()){
				dir = (Directory) allfiles.get(i);
				for(File filling : dir.getFilesSet()){
				allfiles.add(filling);
				}
			}
		}
		
		allfiles.remove(getFileByName("home"));
		
		return allfiles;
	}
	
	public Element xmlExport(){
		Element dir_el = new Element("dir");
		
    	dir_el.setAttribute("id", getId().toString());
    	dir_el.addContent("<name>" + getFilename() + "</name>");
    	dir_el.addContent("<owner>" + getOwner() + "</owner>");
    	dir_el.addContent("<path>" + getPath() + "</path>");
    	dir_el.addContent("<perm>" + getPermissions() + "</perm>");
		
    	return dir_el;
	}
		
		
		
/*
				dirorfile_el = new Element("dir");
				dirorfile_el.setAttribute("id", getId().toString());
				dirorfile_el.addContent("<name>" + getFilename() + "</name>");
				dirorfile_el.addContent("<owner>" + getOwner()+ "</owner>");
				dirorfile_el.addContent("<path>" + getPath()+ "</path>");
				dirorfile_el.addContent("<perm>" + getPermissions() + "</perm>");
*/

}
