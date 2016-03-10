package pt.tecnico.myDrive.domain;

import pt.tecnico.myDrive.exception.InvalidFileNameException;
import pt.tecnico.myDrive.exception.InvalidMaskException;
import pt.tecnico.myDrive.exception.IsNotAppFileException;
import pt.tecnico.myDrive.exception.IsNotDirectoryException;

public class PlainFile extends PlainFile_Base {
    
	public PlainFile(){
		super();
	}
	
    public PlainFile(int id, String filename, String userMask, User owner) throws InvalidFileNameException, InvalidMaskException{
    	this.init(id, filename, userMask, owner);
    }
    
    
    public PlainFile(int id, String filename, String userMask, User owner, String content) throws InvalidFileNameException, InvalidMaskException {
    	this.init(id, filename, userMask, owner, content);

    }
    
    
    @Override
    protected void init(int id, String filename, String userMask, User owner) throws InvalidFileNameException, InvalidMaskException{
    	super.init(id, filename, userMask, owner);
    }
    
    
    protected void init(int id, String filename, String userMask, User owner, String content) throws InvalidFileNameException, InvalidMaskException{
    	init(id, filename, userMask, owner);
    	this.setContent(content);
    }

    
    @Override
    public void setContent(String content){
    	super.setContent(content);
    }
    
    public String getContent(){
    	return super.getContent();
    }
    
    @Override
    protected void isCdAble() throws IsNotDirectoryException {
    	throw new IsNotDirectoryException("Plain File"); //how to do this
    }
    
    @Override
    protected String printContent(){
    	return this.getContent();

    }
    
    @Override
    protected void executeApp() throws IsNotAppFileException{
    	throw new IsNotAppFileException("Plain File"); //same as above
    }
    
    protected Directory getFather() throws UnsupportedOperationException{
    	
    	throw new UnsupportedOperationException();
	}

	@Override
	public void addFile(File toAdd) throws UnsupportedOperationException{
		throw new UnsupportedOperationException();
	}

	@Override
	public void removeFile(String toRemove) throws UnsupportedOperationException{
		throw new UnsupportedOperationException();
		
	}
    
    
    
}
