package pt.tecnico.myDrive.domain;

import org.jdom2.Element;

import pt.tecnico.myDrive.exception.FileAlreadyExistsException;
import pt.tecnico.myDrive.exception.InvalidContentException;
import pt.tecnico.myDrive.exception.InvalidFileNameException;
import pt.tecnico.myDrive.exception.InvalidMaskException;
import pt.tecnico.myDrive.exception.IsNotAppFileException;
import pt.tecnico.myDrive.exception.IsNotDirectoryException;
import pt.tecnico.myDrive.exception.IsNotFileException;

public class PlainFile extends PlainFile_Base {
    
	public PlainFile(){
		super();
	}
	
    public PlainFile(int id, String filename, String userMask, User owner) throws InvalidFileNameException, InvalidMaskException, FileAlreadyExistsException{
    	this.init(id, filename, userMask, owner);
    }
    
    
    public PlainFile(int id, String filename, String userMask, User owner, String content) throws InvalidFileNameException, InvalidMaskException, FileAlreadyExistsException, InvalidContentException {
    	this.init(id, filename, userMask, owner, content);

    }
    
    
    @Override
    protected void init(int id, String filename, String userMask, User owner) throws InvalidFileNameException, InvalidMaskException{
    	super.init(id, filename, userMask, owner);
    }
    
    
    protected void init(int id, String filename, String userMask, User owner, String content) throws InvalidFileNameException, InvalidMaskException, InvalidContentException{
    	init(id, filename, userMask, owner);
    	this.setContent(content);
    }

    
    @Override
    public void setContent(String content) throws InvalidContentException{
    	super.setContent(content);
    }
    
    public String getContent(){
    	return super.getContent();
    }
    
    @Override
    protected void isCdAble() throws IsNotDirectoryException {
    	throw new IsNotDirectoryException(this.getFilename()); 
    }
    
    @Override
    public boolean isDirectory(){
    	return false;
    }
    
    @Override
    protected String printContent(){
    	return this.getContent();

    }
    
    @Override
    protected void executeApp() throws IsNotAppFileException{
    	throw new IsNotAppFileException(this.getFilename());
    }

	@Override
	protected void addFile(File toAdd) throws FileAlreadyExistsException{
		throw new FileAlreadyExistsException(toAdd.getFilename());
	}

	@Override
	protected void removeFile(String toRemove) throws IsNotFileException{
		throw new IsNotFileException(toRemove);
		
	}
	
	protected Directory changeDirectory(String dirname, User currentUser) throws UnsupportedOperationException{
		throw new UnsupportedOperationException();
	}
	
	protected String getDirectoryFilesName() throws UnsupportedOperationException{
		throw new UnsupportedOperationException();
	}
	
    
	@Override
    public Element xmlExport(){ //Supposedly done, probably needs some changing tweaks!!!
    	Element pf_el = new Element("plain");
    	
    	pf_el.setAttribute("id", getId().toString());
    	pf_el.addContent("<name>" + getFilename() + "</name>");
    	pf_el.addContent("<owner>" + getOwner() + "</owner>");
    	pf_el.addContent("<path>" + getPath() + "</path>");
    	pf_el.addContent("<perm>" + getPermissions() + "</perm>");
    	
    	if(getContent() != null)// Check if there is content on the file, none found print nothing
    		pf_el.addContent("<contents>" + getContent() + "</contents>");
    	
    	return pf_el;
    }
    
}
