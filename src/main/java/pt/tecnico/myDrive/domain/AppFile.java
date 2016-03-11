package pt.tecnico.myDrive.domain;

import org.jdom2.Element;

public class AppFile extends AppFile_Base {
    
	
    public AppFile() {
        super();
    }
    
    
    public AppFile(int id, String filename, String userMask, User owner) /* TODO: throws*/{
    	super.init(id, filename, userMask, owner);
    }
    
    public AppFile(int id, String filename, String userMask, User owner, String content) /* TODO: throws*/{
    	super.init(id, filename, userMask, owner, content);

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
    
    public Element xmlExport(){
    	Element app_el = new Element("app");
    	
    	app_el.setAttribute("id", getId().toString());
    	app_el.addContent("<name>" + getFilename() + "</name>");
    	app_el.addContent("<owner>" + getOwner() + "</owner>");
    	app_el.addContent("<path>" + getPath() + "</path>");
    	app_el.addContent("<perm>" + getPermissions() + "</perm>");
    	
    	// Check if there is content on the file, none found print nothing
    	app_el.addContent("<method>" + getContent() + "</method>");
    	
    	return app_el;
    }
}
