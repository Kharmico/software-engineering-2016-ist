package pt.tecnico.myDrive.domain;

import org.jdom2.Element;
import pt.tecnico.myDrive.exception.InvalidMaskException;
import pt.tecnico.myDrive.exception.InvalidUsernameException;
import pt.tecnico.myDrive.exception.UserAlreadyExistsException;

public class User extends User_Base {
    
    private static final String DEFAULT_UMASK = "rwxd----";


	protected User() {
        super();
    }
    
    protected User(String username, MyDriveManager mdm, FileSystem fs) throws  InvalidUsernameException{
		this.checkUsername(username);
		this.setUsername(username);
		this.setPassword(username);
		this.setName(username);
		this.setUmask(DEFAULT_UMASK);
		super.setMyDriveManager(mdm);
		super.setFilesystem(fs);;
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

    protected void remove(){
    	this.setHomeDirectory(null);
    	deleteDomainObject();
    }
    
    
	protected boolean isRoot() {
		return false;
	}
	
	protected void checkUsername(String username) throws InvalidUsernameException{
	    String pattern= "^[a-zA-Z0-9]*$";
        if(!username.matches(pattern) || username.equals(Root.ROOT_USERNAME)){
        	throw new InvalidUsernameException(username);
        }
	}

	protected Element xmlExport(){
		Element usr_el = new Element("user");
		usr_el.setAttribute("username", getUsername());

		usr_el.addContent(new Element("name").setText(getName()));
		usr_el.addContent(new Element("password").setText(getPassword()));
		usr_el.addContent(new Element("home").setText(getHomeDirectory().getPath() + "/" + getHomeDirectory().getFilename()));
		usr_el.addContent(new Element("mask").setText(getUmask()));

		return usr_el;
	}

	@Override
	public String toString(){
		return getUsername();
	}


}
