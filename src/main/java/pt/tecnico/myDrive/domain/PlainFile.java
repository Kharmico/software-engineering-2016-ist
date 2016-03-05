package pt.tecnico.myDrive.domain;


public class PlainFile extends PlainFile_Base {
    
	public PlainFile(){
		super();
	}
	
    public PlainFile(int id, String filename, String userMask, User owner) /* TODO: throws*/{
    	this.init(id, filename, userMask, owner);
    }
    
    
    public PlainFile(int id, String filename, String userMask, User owner, String content) /* TODO: throws*/{
    	this.init(id, filename, userMask, owner, content);

    }
    
    
    @Override
    protected void init(int id, String filename, String userMask, User owner) /* TODO: throws*/{
    	super.init(id, filename, userMask, owner);
    }
    
    
    public void init(int id, String filename, String userMask, User owner, String content) /* TODO: throws*/{
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
    public Directory changeDirectory(){
    	/* TODO: throw exception and remove return*/
    	return new Directory();
    }
    
    @Override
    public String printContent(){
    	return this.getContent();
 

    }
    
    @Override
    public void executeApp(){
    	/* TODO: throw exception */
    }
    
    public Directory getFather() throws UnsupportedOperationException{
		// TODO Auto-generated method stub
    	
    	throw new UnsupportedOperationException();
	}
    
}
