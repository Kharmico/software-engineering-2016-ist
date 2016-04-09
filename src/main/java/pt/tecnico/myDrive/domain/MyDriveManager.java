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
import java.nio.file.Path;
import java.util.ArrayList;
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
    		super.getFilesystem().addUsers(username);
    }
    
    public void removeUser(String username){
    	super.getFilesystem().removeUsers(username);
    }
    
    
    /* --- Directory --- */
    
    public void createDirectory(String filename){
    	super.getFilesystem().createDirectory(filename,
    		currentSession.getCurrentDir(), currentSession.getCurrentUser());
    }
    
    
    public void changeDirectory(String directoryname) throws FileUnknownException, PathIsTooBigException, AccessDeniedException{ //TODO delete this and rename AbsolutePath to this name?
		currentSession.setCurrentDir(getFilesystem().getLastDirectory(directoryname, currentSession.getCurrentDir(), currentSession.getCurrentUser()));
    }
    
    
    public void AbsolutePath(String path){
    	currentSession.setCurrentDir(getFilesystem().absolutePath(path, currentSession.getCurrentUser(), currentSession.getCurrentDir()));
    }

    
    public String getDirectoryFilesName(String path) {
		return getFilesystem().getDirectoryFilesName(path, currentSession.getCurrentUser(), currentSession.getCurrentDir());
    }

    
    public void removeFile(String path){
    	super.getFilesystem().removeFile(path, getCurrentSession().getCurrentUser(), getCurrentSession().getCurrentDir());
    }
    
    
    /* --- Files --- */ 
    
    public void createPlainFile(String filename){
		super.getFilesystem().createPlainFile(filename,
    		currentSession.getCurrentDir(), currentSession.getCurrentUser());
    }
    
    
    public void createPlainFile(String filename, String content){
    	super.getFilesystem().createPlainFile(filename,
    		currentSession.getCurrentDir(), currentSession.getCurrentUser(), content);
    }

    
    public void createLinkFile(String filename, String content){
    	// TODO: InvalidContentException
    	super.getFilesystem().createLinkFile(filename,
    		currentSession.getCurrentDir(), currentSession.getCurrentUser(), content);
    }
 
    
    public void createAppFile(String filename){
    	super.getFilesystem().createAppFile(filename,
    		currentSession.getCurrentDir(), currentSession.getCurrentUser());
    }

    
    public void createAppFile(String filename, String content){
    	// TODO: InvalidContentException
		super.getFilesystem().createAppFile(filename,
    			currentSession.getCurrentDir(), currentSession.getCurrentUser(), content);
    }

	public String readFile(String filename) throws FileUnknownException, IsNotPlainFileException, AccessDeniedException{
		return super.getFilesystem().readFile(filename,currentSession.getCurrentDir(), currentSession.getCurrentUser());
	}


    public String printPlainFile(String path) throws FileUnknownException, IsNotPlainFileException, AccessDeniedException{
  		return super.getFilesystem().printPlainFile(path, currentSession.getCurrentUser(), currentSession.getCurrentDir());
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
