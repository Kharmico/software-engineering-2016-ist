package pt.tecnico.myDrive.domain;

import org.jdom2.Element;
import pt.tecnico.myDrive.exception.*;

public class LinkFile extends LinkFile_Base {

    protected LinkFile() {
        super();
    }

    public LinkFile(int id, String filename, String userMask, User owner, String content, Directory parentDirectory) throws
            InvalidFileNameException, InvalidMaskException, LinkFileWithoutContentException, LoopLinkFileException {
        if(invalidContent(content)){
            throw new LinkFileWithoutContentException(filename + " link has an invalid content.");
        }else if(loopContent(content, filename, parentDirectory)){
            throw new LoopLinkFileException(filename);
        }
        super.init(id, filename, userMask, owner, content, parentDirectory);

    }


    private boolean invalidContent(String content){
        return content == null || content.isEmpty();
    }

    private boolean loopContent(String content, String filename, Directory parentDirectory){
        return content.equals(parentDirectory.getPath() + "/" + filename);
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
    public void executeFile(User logged, String[] args) {
        getFilesystem().absolutePath(super.getContent(), logged, getFather())
                .getFileByName(getContent().substring(getContent().lastIndexOf("/") + 1)).executeFile(logged, args);
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
