package pt.tecnico.myDrive.domain;

import org.jdom2.Element;

import pt.tecnico.myDrive.exception.FileAlreadyExistsException;
import pt.tecnico.myDrive.exception.InvalidFileNameException;
import pt.tecnico.myDrive.exception.InvalidMaskException;

public class LinkFile extends LinkFile_Base {
    
    public LinkFile() {
        super();
    }
    
    public LinkFile(int id, String filename, String userMask, User owner) throws InvalidFileNameException, InvalidMaskException, FileAlreadyExistsException{
    	super.init(id, filename, userMask, owner);
    }
    
    
    public LinkFile(int id, String filename, String userMask, User owner, String content) throws InvalidFileNameException, InvalidMaskException, FileAlreadyExistsException {
    	super.init(id, filename, userMask, owner, content);

    }
    
    @Override
    protected String printContent(){
		
    	// TODO: Print the file's content that is linked to this entity
    	return null;    	
    }
    
    
    @Override
    public String toString(){
    	return toString() + " -> " + super.getContent();
    }
    
    @Override
    public Element xmlExport(){ //Supposedly done, probably needs some changing tweaks!!!
    	Element link_el = new Element("link");
    	
    	link_el.setAttribute("id", getId().toString());
    	link_el.addContent("<name>" + getFilename() + "</name>");
    	link_el.addContent("<owner>" + getOwner() + "</owner>");
    	link_el.addContent("<path>" + getPath() + "</path>");
    	link_el.addContent("<perm>" + getPermissions() + "</perm>");
    	
    	if(getContent() != null)// Check if there is content on the file, none found print nothing
    		link_el.addContent("<value>" + getContent() + "</value>");
    	
    	return link_el;
    }
    
}
