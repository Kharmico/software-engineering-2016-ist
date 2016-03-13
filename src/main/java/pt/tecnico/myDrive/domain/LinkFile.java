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
        Element pf_el = new Element("link");

        super.generalFileExport(pf_el);

        // Check if there is content on the file, none found print nothing
        if(!getContent().isEmpty())
            pf_el.addContent(new Element("value").setText(getContent()));

        return pf_el;
    }
    
}
