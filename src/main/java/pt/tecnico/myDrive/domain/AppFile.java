package pt.tecnico.myDrive.domain;

import org.jdom2.Element;
import pt.tecnico.myDrive.exception.FileAlreadyExistsException;
import pt.tecnico.myDrive.exception.InvalidContentException;
import pt.tecnico.myDrive.exception.InvalidFileNameException;
import pt.tecnico.myDrive.exception.InvalidMaskException;

public class AppFile extends AppFile_Base {
    
	
    public AppFile() {
        super();
    }
    
    
    public AppFile(int id, String filename, String userMask, User owner) throws InvalidFileNameException, InvalidMaskException, FileAlreadyExistsException{
    	super.init(id, filename, userMask, owner);
    }
    
    public AppFile(int id, String filename, String userMask, User owner, String content) throws InvalidFileNameException, InvalidMaskException, FileAlreadyExistsException, InvalidContentException{
    	super.init(id, filename, userMask, owner, content);

    }
    
    @Override
    public void setContent(String content) throws InvalidContentException{
    	if(content.contains(" ") || content.contains("\n")){
    		throw new InvalidContentException(content);
    	}else{
    		super.setContent(content);
    	}
    	
    }
    
    @Override
    protected void executeApp(){
    	// TODO
    }

    @Override
    public Element xmlExport(){ //Supposedly done, probably needs some changing tweaks!!!
        Element pf_el = new Element("app");

        super.generalFileExport(pf_el);

        // Check if there is content on the file, none found print nothing
        if(!getContent().isEmpty())
            pf_el.addContent(new Element("method").setText(getContent()));

        return pf_el;
    }

    @Override
    public String toString(){
        return super.toString() + " -> " + getContent();
    }
}
