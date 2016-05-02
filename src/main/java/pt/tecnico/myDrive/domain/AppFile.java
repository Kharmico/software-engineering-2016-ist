package pt.tecnico.myDrive.domain;


import org.jdom2.Element;
import pt.tecnico.myDrive.exception.InvalidContentException;
import pt.tecnico.myDrive.exception.InvalidFileNameException;
import pt.tecnico.myDrive.exception.InvalidMaskException;

public class AppFile extends AppFile_Base {


    protected AppFile() {
        super();
    }


    AppFile(int id, String filename, String userMask, User owner, Directory parentDirectory) throws InvalidFileNameException, InvalidMaskException {
        super.init(id, filename, userMask, owner, parentDirectory);
    }

    AppFile(int id, String filename, String userMask, User owner, String content, Directory parentDirectory) throws InvalidFileNameException, InvalidMaskException, InvalidContentException{
        super.init(id, filename, userMask, owner, content, parentDirectory);

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
    protected void executeFile(User logged, String[] args){
    	
    	
    	
    	
    	
    	
    	
    	//FIXME: you can execute from its content or directly from service
    }

    @Override
    public Element xmlExport(){
        Element pf_el = new Element("app");

        super.generalFileExport(pf_el);

        if(!getContent().isEmpty())
            pf_el.addContent(new Element("method").setText(getContent()));

        return pf_el;
    }

}