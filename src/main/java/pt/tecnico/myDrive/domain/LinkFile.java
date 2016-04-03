package pt.tecnico.myDrive.domain;

import org.jdom2.Element;
import pt.tecnico.myDrive.exception.InvalidFileNameException;
import pt.tecnico.myDrive.exception.InvalidMaskException;
import pt.tecnico.myDrive.exception.LinkFileWithoutContentException;

public class LinkFile extends LinkFile_Base {

    protected LinkFile() {
        super();
    }


    protected LinkFile(int id, String filename, String userMask, User owner) throws InvalidFileNameException, InvalidMaskException {
    	super.init(id, filename, userMask, owner);
    }


    protected LinkFile(int id, String filename, String userMask, User owner, String content) throws InvalidFileNameException, InvalidMaskException, LinkFileWithoutContentException {
    	if(content == null || content.isEmpty()){
            throw new LinkFileWithoutContentException(filename + " link doesn't have content.");
        }
        super.init(id, filename, userMask, owner, content);

    }
    
    @Override
	public String printContent(){
		
    	// TODO: Returns the file's content that is linked to this entity
    	return "<content>";
    }
    
    
    @Override
    public String toString(){
    	return toString() + " -> " + super.getContent();
    }

    @Override
    public Element xmlExport(){
        Element pf_el = new Element("link");

        super.generalFileExport(pf_el);

        if(!getContent().isEmpty())
            pf_el.addContent(new Element("value").setText(getContent()));

        return pf_el;
    }
    
}
