package pt.tecnico.myDrive.domain;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jdom2.Element;
import pt.tecnico.myDrive.exception.*;

import java.util.ArrayList;

public class Directory extends Directory_Base {

	private static final Logger log = LogManager.getRootLogger();

    protected Directory() {
        super();

    }

    
	protected Directory(int id, String filename, String userMask, User owner, FileSystem fs) throws InvalidFileNameException, InvalidMaskException{
		super.init(id, filename, userMask, owner);
		this.setParentDirectory(this);
		this.setFilesystem(fs);

	}

	
	protected Directory(int id, String filename, String userMask, User owner, Directory father) throws InvalidFileNameException, InvalidMaskException{
		super.init(id, filename, userMask, owner);
		this.setParentDirectory(father);
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
    	file.checkAccessRead(currentUser);
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

	public String getFatherLs(){
		return this.getPermissions() + " " + super.getOwner()
				+ " " + super.getLastModified() + " ..";
	}

	public File getFileByName(String name) throws FileUnknownException {
		switch (name) {
			case ".":
				return this;
			case "..":
				return getFather();
			default:
				for (File file : super.getFilesSet())
					if (file.getFilename().equals(name))
						return file;
				throw new FileUnknownException(name);
		}
	}


	private boolean hasFile(String filename) {
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


	/* Fenix framework binary relations setters */

	@Override
	public void setUser(User user){
		if(user == null){
			super.setUser(null);
		}else
			user.setHomeDirectory(this);
	}


	@Override
	protected void isCdAble() {}

	@Override
	public String printContent(User logged) throws IsNotPlainFileException {
		throw new IsNotPlainFileException(this.getFilename());
	}

	@Override
	public void executeFile(User logged) throws IsNotAppFileException {
		throw new IsNotAppFileException(this.getFilename());
	}
	
    @Override
    protected void writeContentFromPlainFile(User logged, String[] arrayContent) throws IsNotAppFileException {
    	throw new IsNotAppFileException(this.getFilename());
    }

	ArrayList<File> getAllFiles(){ // Auxiliary function to get all existing files (includes directories)
		ArrayList<File> allFiles = new ArrayList<>();

		if(noDirectories()) {
			allFiles.addAll(getFilesSet());
			return allFiles;
		}

		// Getting all files present in slash (includes directories)
		for(File file : getFilesSet()){
			try{
				/* Slash has itself in the set */
				if(!file.equals(this)){
					file.isCdAble();
					Directory next = (Directory) file;
					allFiles.addAll(next.getAllFiles());}
			}
			catch (IsNotDirectoryException e) {
				/* This exception should not occur it only exists to protect the method against
    		* bad programming
    		*/
				log.trace(e.getMessage());
				e.printStackTrace();
			}
			finally { allFiles.add(file); }
		}

		return allFiles;

	}

	private boolean noDirectories(){
		for (File f : getFilesSet()) {
			try {
				f.isCdAble();
				return false;
			} catch (IsNotDirectoryException e) {
				/* This exception should not occur it only exists to protect the method against
    			* bad programming
    			*/
				log.trace(e.getMessage());
				e.printStackTrace();
			}
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
	public void writeContent(String content, User logged) throws IsNotPlainFileException{
		throw new IsNotPlainFileException(getFilename());
	}

}
