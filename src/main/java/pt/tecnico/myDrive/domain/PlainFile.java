package pt.tecnico.myDrive.domain;

import org.jdom2.Element;
import pt.tecnico.myDrive.exception.*;

public class PlainFile extends PlainFile_Base {
    
	protected PlainFile(){
		super();
	}

	protected PlainFile(int id, String filename, String userMask, User owner) throws InvalidFileNameException, InvalidMaskException {
    	this.init(id, filename, userMask, owner);
    }


	protected PlainFile(int id, String filename, String userMask, User owner, String content) throws InvalidFileNameException, InvalidMaskException, InvalidContentException {
    	this.init(id, filename, userMask, owner, content);

    }


    @Override
    protected void init(int id, String filename, String userMask, User owner) throws InvalidFileNameException, InvalidMaskException{
    	super.init(id, filename, userMask, owner);
    }


    protected void init(int id, String filename, String userMask, User owner, String content) throws InvalidFileNameException, InvalidMaskException {
    	init(id, filename, userMask, owner);
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
    protected void executeApp(User logged) throws IsNotAppFileException{
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


	protected Element generalFileExport(Element el){
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
