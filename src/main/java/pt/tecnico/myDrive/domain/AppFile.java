package pt.tecnico.myDrive.domain;

public class AppFile extends AppFile_Base {
    
	
    public AppFile() {
        super();
    }
    
    
    public AppFile(int id, String filename, String userMask, User owner) /* TODO: throws*/{
    	init(id, filename, userMask, owner);
    }
    
    public AppFile(int id, String filename, String userMask, User owner, String content) /* TODO: throws*/{
    	init(id, filename, userMask, owner, content);

    }
    
    @Override
    public void setContent(String content){
    	if(content.contains(" ") || content.contains("\n")){
    		// TODO: throw exception
    	}else{
    		super.setContent(content);
    	}
    	
    }
    
    
    @Override
    protected void executeApp(){
    	// TODO
    }
}
