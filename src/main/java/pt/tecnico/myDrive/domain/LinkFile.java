package pt.tecnico.myDrive.domain;

import org.jdom2.Element;
import pt.tecnico.myDrive.exception.*;

public class LinkFile extends LinkFile_Base {

    protected LinkFile() {
        super();
    }

    protected LinkFile(int id, String filename, String userMask, User owner, String content) throws
            InvalidFileNameException, InvalidMaskException, LinkFileWithoutContentException {
    	if(invalidContent(content)){
            throw new LinkFileWithoutContentException(filename + " link has an invalid content.");
        }
        super.init(id, filename, userMask, owner, content);

    }

    private boolean invalidContent(String content){
        return content == null || content.isEmpty();
    }

    @Override
	public String printContent(User logged){
    	return getFilesystem().absolutePath(super.getContent(), logged, getFather())
                .getFileByName(getContent().substring(getContent().lastIndexOf("/") + 1)).printContent(logged);
    }
    
    @Override
	public void writeContent(String content, User logged)throws IllegalAddContentException  {
    	throw new IllegalAddContentException(this.getFilename());
    }

    @Override
    public void executeFile(User logged) {
        getFilesystem().absolutePath(super.getContent(), logged, getFather())
                .getFileByName(getContent().substring(getContent().lastIndexOf("/") + 1)).executeFile(logged);
    }

    private FileSystem getFilesystem(){
        Directory dir = getFather();
        while(!dir.getFather().equals(dir)){
            dir = dir.getFather();
        }
        return dir.getFilesystem();
    }
    
    @Override
    protected void writeContentFromPlainFile(User logger, String[] array) throws IsNotAppFileException{
    	throw new IsNotAppFileException(this.getFilename());
    }
    
    
    @Override
    public String toString(){
    	return super.toString() + " -> " + super.getContent();
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
