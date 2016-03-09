package pt.tecnico.myDrive.domain;

public class LinkFile extends LinkFile_Base {
    
    public LinkFile() {
        super();
    }
    
    public LinkFile(int id, String filename, String userMask, User owner) /* TODO: throws*/{
    	super.init(id, filename, userMask, owner);
    }
    
    
    public LinkFile(int id, String filename, String userMask, User owner, String content) /* TODO: throws*/{
    	super.init(id, filename, userMask, owner, content);

    }
    
    @Override
    public void setContent(String content) throws UnsupportedOperationException{
    	throw new UnsupportedOperationException();
    }
    
    @Override
    protected String printContent(){
		
    	// TODO: Print the file's content that is linked to this entity
    	return null;    	
    }
    
    
    @Override
    public String toString(){
    	return toString() + " -> " + super.getContent();
    }
    
}
