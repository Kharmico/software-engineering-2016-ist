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
    	Element app_el = new Element("app");
    	
    	app_el.setAttribute("id", getId().toString());
    	app_el.addContent("<name>" + getFilename() + "</name>");
    	app_el.addContent("<owner>" + getOwner() + "</owner>");
    	app_el.addContent("<path>" + getPath() + "</path>");
    	app_el.addContent("<perm>" + getPermissions() + "</perm>");
    	
    	if(getContent() != null)// Check if there is content on the file, none found print nothing
    		app_el.addContent("<method>" + getContent() + "</method>");
    	
    	return app_el;
    }
}
