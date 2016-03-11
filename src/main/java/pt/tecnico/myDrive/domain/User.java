package pt.tecnico.myDrive.domain;

import org.jdom2.Element;

import pt.tecnico.myDrive.exception.InvalidMaskException;
import pt.tecnico.myDrive.exception.UserAlreadyExistsException;

public class User extends User_Base {
    
    private static final String DEFAULT_UMASK = "rwxd----";


	public User() {
        super();
    }
    
    public User(String username) throws UserAlreadyExistsException {
    	if(username.equals(Root.ROOT_USERNAME)){
    		throw new UserAlreadyExistsException(username);
    	}
    	this.setUsername(username);
    	this.setPassword(username);
    	this.setName(username);
    	this.setUmask(DEFAULT_UMASK);
    }
    

    @Override
	public void setUsername(String username){
    	super.setUsername(username);
    }
    
    @Override
	public void setPassword(String password){
    	super.setPassword(password);
    }
    
    @Override
	public void setName(String name){
    	super.setName(name);
    }
    
    @Override
	public void setUmask(String umask) throws InvalidMaskException {
    	if(umask.length() != 8) {
    		throw new InvalidMaskException(umask);
    	}
    	super.setUmask(umask);
    }
    
    @Override 
    public void setHomeDirectory(Directory homeDirectory){
    	super.setHomeDirectory(homeDirectory);
    }
    
    /* FenixFramework binary relations setters */
    
    @Override
    public void setFilesystem(FileSystem fs) {
        if (fs == null)
            super.setFilesystem(null);
        else
            fs.addUsers(this);
    }
    
    @Override
    public void setMyDriveManager(MyDriveManager mngr) {
        if (mngr == null)
            super.setMyDriveManager(null);
        else
        	mngr.setCurrentUser(this);
    }
    
    @Override
    public void setFile(File file) {
        if (file == null)
            super.setFile(null);
        else
        	file.setOwner(this);
    }
    

    public void remove(){
    	this.setHomeDirectory(null);
    	deleteDomainObject();
    }
    
    
	public boolean isRoot() {
		return false;
	}
    
	protected Element xmlExport(){
		Element usr_el = new Element("user");
		usr_el.setAttribute("username", getUsername());
		usr_el.addContent("<password>" + getPassword() + "</password>");
		usr_el.addContent("<name>" + getName() + "</name>");
		usr_el.addContent("<home>" + getHomeDirectory() + "</home>");
		usr_el.addContent("<mask>" + getUmask() + "</mask>");

		return usr_el;
	}


}
