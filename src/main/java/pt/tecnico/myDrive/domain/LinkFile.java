package pt.tecnico.myDrive.domain;

import org.jdom2.Element;

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
    
    public Element xmlExport(){
    	Element link_el = new Element("link");
    	
    	link_el.setAttribute("id", getId().toString());
    	link_el.addContent("<name>" + getFilename() + "</name>");
    	link_el.addContent("<owner>" + getOwner() + "</owner>");
    	link_el.addContent("<path>" + getPath() + "</path>");
    	link_el.addContent("<perm>" + getPermissions() + "</perm>");
    	
    	// Check if there is content on the file, none found print nothing
    	link_el.addContent("<value>" + getContent() + "</value>");
    	
    	return link_el;
    }
    
}
