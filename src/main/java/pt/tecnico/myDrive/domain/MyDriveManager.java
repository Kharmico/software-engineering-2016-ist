package pt.tecnico.myDrive.domain;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jdom2.Document;
import org.jdom2.Element;
import org.joda.time.DateTime;
import pt.ist.fenixframework.FenixFramework;
import pt.tecnico.myDrive.exception.*;

// import pt.tecnico.myDrive.exception.InvalidContentException;

import java.math.BigInteger;
import java.util.Random;

public class MyDriveManager extends MyDriveManager_Base {
	
	static final Logger log = LogManager.getRootLogger();
    private Session currentSession;
    
    private MyDriveManager() {
        FenixFramework.getDomainRoot().setMyDriveManager(this);
        super.setFilesystem(new FileSystem(this));
        currentSession = new Session(generateToken(),getFilesystem().getRoot(),getFilesystem().getSlash());
    }
    
    public static MyDriveManager getInstance(){
    	MyDriveManager mngr = FenixFramework.getDomainRoot().getMyDriveManager();
        if (mngr != null)
        	return mngr;

        log.trace("MyDriveManager.getInstance: new MyDriveManager");
        return new MyDriveManager();
    }

    @Override
    public void setFilesystem(FileSystem filesystem){
    	super.setFilesystem(filesystem);
    }
    
    public void remove(){
    	this.setFilesystem(null);
        for(Session s : getSessionSet())
            s.remove();
    	deleteDomainObject();
    }

    /* --- Users --- */
    
    public void addUser(String username){
    	
    	try {
    		super.getFilesystem().addUsers(username);
    	} catch (UserAlreadyExistsException ex) {
    		log.trace(ex.getMessage());
    	}
    }
    
    public void removeUser(String username){
    	
    	try {
    		super.getFilesystem().removeUsers(username);
    	} catch (UserUnknownException ex1) {
			log.trace(ex1.getMessage());
    	} catch (IllegalRemovalException ex2) {
    		log.trace(ex2.getMessage());
    	}
    }
    
    
    /* --- Directory --- */
    
    public void createDirectory(String filename){
    	//try {
    		super.getFilesystem().createDirectory(filename,
    			currentSession.getCurrentDir(), currentSession.getCurrentUser());
    	/*} catch (InvalidFileNameException ex1) {
    		log.trace(ex1.getMessage());
    	} catch (FileAlreadyExistsException ex2) {
    		log.trace(ex2.getMessage());
    	} catch (InvalidMaskException ex3) {
    		log.trace(ex3.getMessage());
    	} catch (FileUnknownException ex4) {
    		log.trace(ex4.getMessage());
    	}*/
    }
    
    
    public void changeDirectory(String directoryname){ //TODO delete this and rename AbsolutePath to this name?
    	try {
    		currentSession.setCurrentDir(getFilesystem().changeDirectory(directoryname,
    				currentSession.getCurrentDir(), currentSession.getCurrentUser()));
    	} catch (FileUnknownException ex) {
    		log.trace(ex.getMessage());
    	}
    }
    
    
    public void AbsolutePath(String path){
    	try {
    		currentSession.setCurrentDir(getFilesystem().absolutePath(path, currentSession.getCurrentUser(), currentSession.getCurrentDir()));
    	} catch (FileUnknownException ex) {
    		log.trace(ex.getMessage());
    	}catch (PathIsTooBigException ex) {
			log.trace(ex.getMessage());
		}
    }

    
    public void getDirectoryFilesName(String path) {
    	try {
    		System.out.println(getFilesystem().getDirectoryFilesName(path, currentSession.getCurrentUser()));
    	} catch (FileUnknownException ex) {
    		log.trace(ex.getMessage());
    	}
    }

    
    public void removeFile(String path){
    	try {
    		super.getFilesystem().removeFile(path, getCurrentSession().getCurrentUser());
    	} catch (FileUnknownException ex1) {
    		log.trace(ex1.getMessage());
    	} catch (IllegalRemovalException ex2) {
    		log.trace(ex2.getMessage());
    	}
    }
    
    
    /* --- Files --- */ 
    
    public void createPlainFile(String filename){
    	//try {
    		super.getFilesystem().createPlainFile(filename,
    				currentSession.getCurrentDir(), currentSession.getCurrentUser());
    	/*} catch (FileUnknownException ex1) {
    		log.trace(ex1.getMessage());
    	} catch (FileAlreadyExistsException ex2) {
    		log.trace(ex2.getMessage());
    	} catch (InvalidFileNameException ex3) {
    		log.trace(ex3.getMessage());
    	} catch (InvalidMaskException ex4) {
    		log.trace(ex4.getMessage());
    	}*/
    }
    
    
    public void createPlainFile(String filename, String content){
    	//try {
    		super.getFilesystem().createPlainFile(filename,
    				currentSession.getCurrentDir(), currentSession.getCurrentUser(), content);
    	/*} catch (FileUnknownException ex1) {
    		log.trace(ex1.getMessage());
		} catch (FileAlreadyExistsException ex2) {
			log.trace(ex2.getMessage());
		} catch (InvalidFileNameException ex3) {
    		log.trace(ex3.getMessage());
    	} catch (InvalidMaskException ex4) {
    		log.trace(ex4.getMessage());
    	}*/

    }

    
    public void createLinkFile(String filename, String content){
    	// TODO: InvalidContentException
		//try {
    		super.getFilesystem().createLinkFile(filename,
    			currentSession.getCurrentDir(), currentSession.getCurrentUser(), content);
    	/*} catch (FileUnknownException ex1) {
    		log.trace(ex1.getMessage());
    	} catch (FileAlreadyExistsException ex2) {
    		log.trace(ex2.getMessage());
    	} catch (InvalidFileNameException ex3) {
    		log.trace(ex3.getMessage());
    	} catch (InvalidMaskException ex4) {
    		log.trace(ex4.getMessage());
    	}*/
    }
 
    
    public void createAppFile(String filename){
    	//try {
    		super.getFilesystem().createAppFile(filename, 
    			currentSession.getCurrentDir(), currentSession.getCurrentUser());
    	/*} catch (FileUnknownException ex1) {
    		log.trace(ex1.getMessage());
    	} catch (FileAlreadyExistsException ex2) {
    		log.trace(ex2.getMessage());
    	} catch (InvalidFileNameException ex3) {
    		log.trace(ex3.getMessage());
    	} catch (InvalidMaskException ex4) {
    		log.trace(ex4.getMessage());
    	}*/
    }

    
    public void createAppFile(String filename, String content){
    	// TODO: InvalidContentException
		//try {
    		super.getFilesystem().createAppFile(filename,
    				currentSession.getCurrentDir(), currentSession.getCurrentUser(), content);
    	/*} catch (FileUnknownException ex1) {
    		log.trace(ex1.getMessage());
    	} catch (FileAlreadyExistsException ex2) {
    		log.trace(ex2.getMessage());
    	} catch (InvalidFileNameException ex3) {
    		log.trace(ex3.getMessage());
    	} catch (InvalidMaskException ex4) {
    		log.trace(ex4.getMessage());
    	}*/
    }

	public void readFile(String filename) throws FileUnknownException, IsNotPlainFileException, AccessDeniedException{
		try {
			System.out.println(super.getFilesystem().readFile(filename,currentSession.getCurrentDir()));
		} catch (FileUnknownException ex1) {
			log.trace(ex1.getMessage());
		} catch (IsNotPlainFileException ex2) {
			log.trace(ex2.getMessage());
		}
	}

    public void printPlainFile(String path) throws FileUnknownException, IsNotPlainFileException, AccessDeniedException{
    	try {
    		System.out.println(super.getFilesystem().printPlainFile(path, currentSession.getCurrentUser()));
    	} catch (FileUnknownException ex1) {
    		log.trace(ex1.getMessage());
    	} catch (IsNotPlainFileException ex2) {
    		log.trace(ex2.getMessage());
    	}
    }

    
    /* --- ImportXML --- */
    
    public void xmlImport(Element element) throws IllegalStateException {
    	super.getFilesystem().xmlImport(element);
    }
    
    
    /* --- ExportXML --- */
    
    public Document xmlExport(){
    	Element element = new Element("mydrivemanager");
    	Document doc = new Document(element);
    	super.getFilesystem().xmlExport(element);
        return doc;
    }

    /* --- Login --- */
    // log.warn() in usage of invalid token
    
    public Long login(String username, String password) throws UserUnknownException, WrongPasswordException {
    	User user = getFilesystem().checkUser(username,password);
    	
    	
    	Session currentSession = null;
    	long token;
    	
    	
    	removeOldSessions();
    	
    	do{ token = generateToken(); } while (token != 0);
    	
        currentSession = new Session(generateToken(),user,user.getHomeDirectory());
        return currentSession.getToken();
    }

    public boolean checkForSession(long token){
    	boolean activeSession = false;
        //Session output = null;
        
        for(Session s : getSessionSet()){
            if(s.getToken() == token){
                if(isTokenValid(token)){
                	if(dateTime + 2 == currentDateTime){
                		activeSession = true;
                		s.setLastAccess(currentDateTime);
                		break;
                	}
                }
            }
        }
        
      /*  if(output != null)
            output.setLastAccess(new DateTime());
        else output = new Session(generateToken(),user,user.getHomeDirectory());
       */
        return activeSession;
    }

    /* Make sure it's unique */
    /* FIXME */
    public long generateToken(){
        return new BigInteger(64, new Random()).longValue();
    }

    private void removeOldSessions(){
    	for(Session s : getSessionSet()){
    		
    	}
        /* FIXME */
    	
    	//DateTime - session.DateTime < 0;
    }

	public boolean isTokenValid(long token){
		// FIXME This method is returning always true while is not properly  implemented
		// 0 = not found, 1 = exists (valid), -1 = exists (not-valid)
		return true;
	}

    public Session getCurrentSession(){
        return currentSession;
    }


}
