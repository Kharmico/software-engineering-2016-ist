package pt.tecnico.myDrive.domain;

import org.jdom2.Element;
import pt.tecnico.myDrive.exception.*;

import javax.naming.OperationNotSupportedException;
import java.util.ArrayList;

public class Directory extends Directory_Base {
    
    protected Directory() {
        super();

    }

    
	protected Directory(int id, String filename, String userMask, User owner, FileSystem fs) throws InvalidFileNameException, InvalidMaskException{
		super.init(id, filename, userMask, owner);
		this.setParentDirectory(this);
		this.setFilesystem(fs);

	}

	
	protected Directory(int id, String filename, String userMask, User owner, Directory father, FileSystem fs) throws InvalidFileNameException, InvalidMaskException{
		super.init(id, filename, userMask, owner);
		this.setParentDirectory(father);
		this.setFilesystem(fs);
	}

	
	@Override
	protected boolean isEmpty() {
		return getFilesSet().size() == 0;
	}
    
	
    @Override
    public void addFile(File file){
		super.addFiles(file);
    }

   
    protected Directory changeDirectory(String dirname, User currentUser) throws AccessDeniedException, IsNotDirectoryException{
    	File file = getFileByName(dirname);
    	file.checkAccess(currentUser);
    	file.isCdAble();
    	return (Directory) file;
    }
    
    protected void removeFile(String filename) throws FileUnknownException{
    	if(!hasFile(filename)){
    		throw new FileUnknownException(filename);
    	}
    	File toRemove = getFileByName(filename);
    	toRemove.remove();
    	super.removeFiles(toRemove);
    }


	public String getDirectoryFilesName() {
		String ls = this.getSelfLs() + "\n" +
				this.getFatherLs() + "\n";
		for (File file: super.getFilesSet()){
			ls = ls + file.toString() + "\n";
		}
		return ls;
	}

	private String getSelfLs(){
		return this.getPermissions() + " " + super.getOwner()
				+ " " + super.getLastModified() + " .";
	}

	private String getFatherLs(){
		return this.getPermissions() + " " + super.getOwner()
				+ " " + super.getLastModified() + " ..";
	}

	public File getFileByName(String name) throws FileUnknownException {
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
		try{
			getFileByName(filename);
		}catch (FileUnknownException e){
			return false;
		}
		return true;
	}


	@Override
	protected void remove() {
		super.removeObject();
		setFilesystem(null);
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
		if(fs == null)
			super.setFilesystem(fs);
	}


	@Override
	protected void isCdAble() {}

	@Override
	public String printContent() throws IsNotPlainFileException {
		throw new IsNotPlainFileException(this.getFilename());
	}

	@Override
	public void executeApp() throws IsNotAppFileException {
		throw new IsNotAppFileException(this.getFilename());

	}

	protected ArrayList<File> getAllFiles(){ // Auxiliary function to get all existing files (includes directories)
		ArrayList<File> allfiles = new ArrayList<File>();

		if(noDirectories()) {
			allfiles.addAll(getFilesSet());
			return allfiles;
		}

		// Getting all files present in slash (includes directories)
		for(File file : getFilesSet()){
			try{
				/* Slash has itself in the set */
				if(!file.equals(this)){
					file.isCdAble();
					Directory next = (Directory) file;
					allfiles.addAll(next.getAllFiles());}
			}
			catch (IsNotDirectoryException e) {}
			finally { allfiles.add(file); }
		}

		return allfiles;

	}

	private boolean noDirectories(){
		for (File f : getFilesSet()) {
			try {
				f.isCdAble();
				return false;
			} catch (IsNotDirectoryException e) {}
		}
		return true;
	}


	protected Element xmlExport(){
		Element dir_el = new Element("dir");

		dir_el.setAttribute("id", getId().toString());

		dir_el.addContent(new Element("name").setText(getFilename()));
		dir_el.addContent(new Element("owner").setText(getOwner().getUsername()));
		dir_el.addContent(new Element("path").setText(getPath()));
		dir_el.addContent(new Element("perm").setText(getPermissions()));

		return dir_el;
	}

	@Override
	public void writeContent(String content) throws IsNotPlainFileException{
		throw new IsNotPlainFileException(getFilename());
	}

}
