package pt.tecnico.myDrive.domain;

import org.jdom2.Element;

import pt.tecnico.myDrive.exception.InvalidFileNameException;
import pt.tecnico.myDrive.exception.InvalidMaskException;
import pt.tecnico.myDrive.exception.IsNotAppFileException;
import pt.tecnico.myDrive.exception.IsNotDirectoryException;

public class PlainFile extends PlainFile_Base {
    
	public PlainFile(){
		super();
	}
	
    public PlainFile(int id, String filename, String userMask, User owner) throws InvalidFileNameException, InvalidMaskException{
    	this.init(id, filename, userMask, owner);
    }
    
    
    public PlainFile(int id, String filename, String userMask, User owner, String content) throws InvalidFileNameException, InvalidMaskException {
    	this.init(id, filename, userMask, owner, content);

    }
    
    
    @Override
    protected void init(int id, String filename, String userMask, User owner) throws InvalidFileNameException, InvalidMaskException{
    	super.init(id, filename, userMask, owner);
    }
    
    
    protected void init(int id, String filename, String userMask, User owner, String content) throws InvalidFileNameException, InvalidMaskException{
    	init(id, filename, userMask, owner);
    	this.setContent(content);
    }

    
    @Override
    public void setContent(String content){
    	super.setContent(content);
    }
    
    public String getContent(){
    	return super.getContent();
    }
    
    @Override
    protected void isCdAble() throws IsNotDirectoryException {
    	throw new IsNotDirectoryException("Plain File"); //how to do this
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
    	throw new IsNotAppFileException("Plain File"); //same as above
    }
    
    protected Directory getFather() throws UnsupportedOperationException{
    	
    	throw new UnsupportedOperationException();
	}

	@Override
	protected void addFile(File toAdd) throws UnsupportedOperationException{
		throw new UnsupportedOperationException();
	}

	@Override
	protected void removeFile(String toRemove) throws UnsupportedOperationException{
		throw new UnsupportedOperationException();
		
	}
	
	protected Directory changeDirectory(String dirname, User currentUser) throws UnsupportedOperationException{
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
    	
    	// Check if there is content on the file, none found print nothing
    	pf_el.addContent("<contents>" + getContent() + "</contents>");
    	
    	return pf_el;
    }
    
}
