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
	public void setPassword(String password) throws PasswordIsTooWeakException {
		checkPasswordStrength(password);
    	super.setPassword(password);
    }

	@Override
	public void setUmask(String umask) throws InvalidMaskException {
    	if(umask.length() != 8) {
    		throw new InvalidMaskException(umask);
    	}
    	super.setUmask(umask);
    }
    
    /* FenixFramework binary relations setters */
    
    @Override
    public void setFilesystem(FileSystem fs) {
        if (fs == null)
            super.setFilesystem(null);
        else
            fs.addUsers(this);
    }

    void remove(){
    	this.setHomeDirectory(null);
    	deleteDomainObject();
    }

    private void checkPasswordStrength(String password) throws PasswordIsTooWeakException{
		if( password.length() < 8 && !isRoot() && !isGuest()){
			throw new PasswordIsTooWeakException();
		}
	}

	boolean isRoot() {
		return false;
	}

	boolean isGuest(){
		return false;
	}
	
	private void checkUsername(String username) throws InvalidUsernameException{
        if(username.length() < MIN_USERNAME_SIZE || !username.matches(INVALID_USERNAME_REGEX) || username.equals(Root.ROOT_USERNAME)){
        	throw new InvalidUsernameException(username);
        }
	}

	Element xmlExport(){
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
		LinkedHashMap<File,String> output = new LinkedHashMap<>();
		for(File f : getFileSet()){
			output.put(f,f.getExtension());
		}
		return output;
	}

	// Used for MockUp testing
	public String getFileAssocByExt(String path) {
		return "";
	}

}
