package pt.tecnico.myDrive.domain;

import org.jdom2.Element;
import pt.tecnico.myDrive.exception.InvalidContentException;
import pt.tecnico.myDrive.exception.InvalidFileNameException;
import pt.tecnico.myDrive.exception.InvalidMaskException;

public class AppFile extends AppFile_Base {


    protected AppFile() {
        super();
    }


    protected AppFile(int id, String filename, String userMask, User owner) throws InvalidFileNameException, InvalidMaskException {
    	super.init(id, filename, userMask, owner);
    }

    protected AppFile(int id, String filename, String userMask, User owner, String content) throws InvalidFileNameException, InvalidMaskException, InvalidContentException{
    	super.init(id, filename, userMask, owner, content);

    }
    
    @Override
    public void writeContent(String content, User logged) throws InvalidContentException{
    	if(content.contains(" ") || content.contains("\n")){
    		throw new InvalidContentException(content);
    	}else{
    		super.setContent(content);
    	}
    	
    }
    
    @Override
    protected void executeApp(User logged){
    	// TODO
    }

    @Override
    public Element xmlExport(){
        Element pf_el = new Element("app");

        super.generalFileExport(pf_el);

        if(!getContent().isEmpty())
            pf_el.addContent(new Element("method").setText(getContent()));

        return pf_el;
    }

    @Override
    public String toString(){
        return super.toString() + " -> " + getContent();
    }
}