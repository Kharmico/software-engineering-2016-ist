package pt.tecnico.myDrive.domain;

import org.jdom2.Element;
import pt.tecnico.myDrive.exception.*;

public class PlainFile extends PlainFile_Base {
    
	protected PlainFile(){
		super();
	}


	PlainFile(int id, String filename, String userMask, User owner, Directory parentDirectory) throws InvalidFileNameException, InvalidMaskException {
		this.init(id, filename, userMask, owner, parentDirectory);
	}


	PlainFile(int id, String filename, String userMask, User owner, String content, Directory parentDirectory) throws InvalidFileNameException, InvalidMaskException, InvalidContentException {
		this.init(id, filename, userMask, owner, content, parentDirectory);

	}


	void init(int id, String filename, String userMask, User owner, String content, Directory parentDirectory) throws InvalidFileNameException, InvalidMaskException {
		init(id, filename, userMask, owner, parentDirectory);
		this.setContent(content);
	}


	@Override
    protected void isCdAble() throws IsNotDirectoryException {
    	throw new IsNotDirectoryException(this.getFilename()); 
    }

    @Override
	public String printContent(User logged){
    	return this.getContent();

    }
    
    @Override
    protected void executeFile(User logged, String[] args) throws FileUnknownException, IsNotAppFileException{
    	String realContent = this.getContent();
    	String appArgs = "";
    	
    	while(realContent != ""){
	    	appArgs = realContent.substring(0,realContent.indexOf("\n"));
	    	String[] arrayApp = appArgs.split(" ");
	    	
	    	getFilesystem().absolutePath(arrayApp[0], logged, getFather()).
				getFileByName(arrayApp[0].substring(arrayApp[0].lastIndexOf("/") + 1)).executeFile(logged, arrayApp);

	    	realContent = realContent.substring(realContent.indexOf("\n"));
    	}
    }
   
    private FileSystem getFilesystem(){
        Directory dir = getFather();
        while(!dir.getFather().equals(dir)){
            dir = dir.getFather();
        }
        return dir.getFilesystem();
    }

    @Override
    protected void writeContentFromPlainFile(User logged, String[] arrayContent) throws IsNotAppFileException {
    	throw new IsNotAppFileException(this.getFilename());
    }
    
	@Override
	protected void addFile(File toAdd) throws IsNotDirectoryException{
		throw new IsNotDirectoryException(toAdd.getFilename());
	}

	@Override
	protected void removeFile(String toRemove) throws IsNotDirectoryException{
		throw new IsNotDirectoryException(toRemove);
		
	}

	@Override
	protected boolean isEmpty() throws IsNotDirectoryException{
		throw new IsNotDirectoryException(this.getFilename());
	}

	@Override
	protected String getDirectoryFilesName() throws IsNotDirectoryException{
		throw new IsNotDirectoryException(getFilename());
	}

	protected Directory changeDirectory(String dirname, User currentUser) throws UnsupportedOperationException{
		throw new UnsupportedOperationException();
	}


	@Override
	protected void remove(){
		super.removeObject();
		setContent(null);
		deleteDomainObject();

	}

	@Override
	protected Element xmlExport(){
		Element pf_el = new Element("plain");

		generalFileExport(pf_el);

		if(!getContent().isEmpty())
			pf_el.addContent(new Element("contents").setText(getContent()));

		return pf_el;
	}


	Element generalFileExport(Element el){
		el.setAttribute("id", getId().toString());
		el.addContent(new Element("name").setText(getFilename()));
		el.addContent(new Element("owner").setText(getOwner().getUsername()));
		el.addContent(new Element("path").setText(getPath()));
		el.addContent(new Element("perm").setText(getPermissions()));

		return el;
	}

	@Override
	public void writeContent(String content, User logged) throws IsNotPlainFileException{
		setContent(content);
	}
    
}
