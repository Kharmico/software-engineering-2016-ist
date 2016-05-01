package pt.tecnico.myDrive.domain;

import org.jdom2.Element;
import pt.tecnico.myDrive.exception.InvalidMaskException;
import pt.tecnico.myDrive.exception.InvalidUsernameException;
import pt.tecnico.myDrive.exception.PasswordIsTooWeakException;

import java.util.LinkedHashMap;

public class User extends User_Base {

	private static final String DEFAULT_UMASK = "rwxd----";
	private static final String INVALID_USERNAME_REGEX = "^[a-zA-Z0-9]*$";
	private static final int MIN_USERNAME_SIZE = 3;


	public User() {
        super();
    }
    
    public User(String username, FileSystem fs) throws  InvalidUsernameException, PasswordIsTooWeakException{
		this.checkUsername(username);
		this.setUsername(username);
		this.setPassword(username);
		this.setName(username);
		this.setUmask(DEFAULT_UMASK);
		super.setFilesystem(fs);
	}
    

    @Override
	public void setUsername(String username){
    	super.setUsername(username);
    }
    
    @Override
	public void setPassword(String password) throws PasswordIsTooWeakException {
		checkPasswordStrength(password);
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

    protected void remove(){
    	this.setHomeDirectory(null);
    	deleteDomainObject();
    }

	//FIXME REMOVE FROM COMMENT AFTER EXCEPTION IS IMPLEMENTED
    private void checkPasswordStrength(String password) /*throws PasswordIsTooWeakException*/{
		if( password.length() < 8 || !password.equals("***") || !password.equals("")){
			//throw new PasswordIsTooWeakException;
		}
	}

	protected boolean isRoot() {
		return false;
	}

	protected boolean isGuest(){
		return false;
	}
	
	protected void checkUsername(String username) throws InvalidUsernameException{
        if(username.length() < MIN_USERNAME_SIZE || !username.matches(INVALID_USERNAME_REGEX) || username.equals(Root.ROOT_USERNAME)){
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

	/* THE LIST ITSELF ALREADY EXISTS, IF WE GET THE SET WE ALREADY GET ALL EXTENSIONS ITERATING */
	public LinkedHashMap<File,String> getUserExtensions(){
		LinkedHashMap<File,String> output = new LinkedHashMap<File,String>();
		for(File f : getFileSet()){
			output.put(f,f.getExtension());
		}
		return output;
	}


}
