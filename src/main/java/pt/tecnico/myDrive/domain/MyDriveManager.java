package pt.tecnico.myDrive.domain;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jdom2.Document;
import org.jdom2.Element;
import org.joda.time.DateTime;
import pt.ist.fenixframework.FenixFramework;
import pt.tecnico.myDrive.exception.*;

import java.math.BigInteger;
import java.util.Random;


public class MyDriveManager extends MyDriveManager_Base {

    private static final Logger log = LogManager.getRootLogger();
    private static final int TIMEOUT_SESSION_TIME = 7200000;     // Two hours
    private static final int TIMEOUT_ROOT_SESSION_TIME = 600000; // Ten minutes
    private Session currentSession;
    
    private MyDriveManager() {
        FenixFramework.getDomainRoot().setMyDriveManager(this);
        super.setFilesystem(new FileSystem(this));
        currentSession = new Session(generateToken(), getFilesystem().getGuest(),
                getFilesystem().getGuest().getHomeDirectory(),this);
        addSession(currentSession);
        System.out.println("Numero de sessoes criacao: " + getSessionSet().size());
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
    
    public void addUser(String username) throws UserAlreadyExistsException , InvalidUsernameException, PasswordIsTooWeakException{
    		super.getFilesystem().addUsers(username);
    }


    /* --- EnvironmentVariables --- */

    public void addEnvironmentVariable(String name, String value, long token) throws InvalidTokenException{
        checkForSession(token);
        for(EnvironmentVariable var : super.getVarSet()){
            if(var.getName().equals(name)){
                var.setValue(value);
                return;
            }
        }
        super.getVarSet().add(new EnvironmentVariable(name, value, this));
    }

    public String listEnvironmentVariables(long token) throws InvalidTokenException{
        checkForSession(token);
        String list = "";
        for(EnvironmentVariable var : super.getVarSet()){
            list += var.toString() + "\n";
        }
        return list;
    }
    
    /* --- Directory --- */
    
    public void createDirectory(String filename){
    	super.getFilesystem().createDirectory(filename,
    		currentSession.getCurrentDir(), currentSession.getCurrentUser());
    }
    
    
    public String changeDirectory(String directoryName, long token) throws FileUnknownException, PathIsTooBigException, AccessDeniedException{
        checkForSession(token);
        log.debug("TARGET DIR: " + directoryName);
        Directory toChange;
        if(directoryName.equals(getCurrentSession().getCurrentUser().getHomeDirectory().getPath()))
            toChange = getCurrentSession().getCurrentUser().getHomeDirectory();
        else
            toChange = getFilesystem().getLastDirectory(directoryName, currentSession.getCurrentDir(), currentSession.getCurrentUser());
		currentSession.setCurrentDir(toChange);
        return toChange.getPath();
    }
    



    public String getDirectoryFilesName(String path, long token) throws InvalidTokenException, AccessDeniedException{
        checkForSession(token);
        //log.debug("Current Dir: " + currentSession.getCurrentDir().getPath());
        return getFilesystem().getDirectoryFilesName(path, currentSession.getCurrentUser(), currentSession.getCurrentDir());
    }

    public void removeFile(String path, long token) throws InvalidTokenException, AccessDeniedException, FileUnknownException{
    	checkForSession(token);
        super.getFilesystem().removeFile(path, getCurrentSession().getCurrentUser(), getCurrentSession().getCurrentDir());
    }

    public void writeContent(String path, String content, long token){
        checkForSession(token);
        //log.debug("WRITE", path);
        getFilesystem().writeContent(path, currentSession.getCurrentUser(), currentSession.getCurrentDir(), content);
    }
   
    
    /* --- Files --- */ 
    
    public void createFile(String type, String filename, long token) throws InvalidTokenException, UnknownTypeException, LinkFileWithoutContentException, FileAlreadyExistsException {
    	checkForSession(token);
        switch(type.toLowerCase()){
        	case "app":
        		createAppFile(filename);
        		break;
        	case "link":
        		throw new LinkFileWithoutContentException(filename);
        	case "plain":
        		createPlainFile(filename);
        		break;
        	case "directory":
        		createDirectory(filename);
        		break;
        	default:
        		throw new UnknownTypeException(type);
    	}
    }
    
    public void createFile(String type, String filename, String content, long token) throws InvalidTokenException, UnknownTypeException, IsNotPlainFileException, FileAlreadyExistsException {
        checkForSession(token);
        switch(type.toLowerCase()){
        	case "app":
        		createAppFile(filename, content);
        		break;
        	case "link":
        		createLinkFile(filename, content);
        		break;
        	case "plain":
        		createPlainFile(filename, content);
        		break;
        	case "directory":
        		throw new IsNotPlainFileException(filename);
        	default:
        		throw new UnknownTypeException(type);
    	}
    }
    
    /* CREATEs */
    
    public void createPlainFile(String filename) throws FileAlreadyExistsException{
		super.getFilesystem().createPlainFile(filename,
    		currentSession.getCurrentDir(), currentSession.getCurrentUser());
    }
    
    
    public void createPlainFile(String filename, String content) throws FileAlreadyExistsException{
    	super.getFilesystem().createPlainFile(filename,
    		currentSession.getCurrentDir(), currentSession.getCurrentUser(), content);
    }

    
    public void createLinkFile(String filename, String content) throws FileAlreadyExistsException{
    	// TODO: InvalidContentException
    	super.getFilesystem().createLinkFile(filename,
    		currentSession.getCurrentDir(), currentSession.getCurrentUser(), content);
    }
 
    
    public void createAppFile(String filename) throws FileAlreadyExistsException{
    	super.getFilesystem().createAppFile(filename,
    		currentSession.getCurrentDir(), currentSession.getCurrentUser());
    }

    
    public void createAppFile(String filename, String content) throws FileAlreadyExistsException{
    	// TODO: InvalidContentException
		super.getFilesystem().createAppFile(filename,
    			currentSession.getCurrentDir(), currentSession.getCurrentUser(), content);
    }

	public String readFile(String filename, long token) throws FileUnknownException, IsNotPlainFileException, AccessDeniedException{
		checkForSession(token);
        return super.getFilesystem().readFile(filename,currentSession.getCurrentDir(), currentSession.getCurrentUser());
	}


    /* --- ImportXML --- */
    
    public void xmlImport(Element element) throws IllegalStateException, PasswordIsTooWeakException {
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
    
    public long login(String username, String password) throws UserUnknownException, WrongPasswordException {
    	User user = getFilesystem().checkUser(username, password);
        removeOldSessions();
        for(Session s : getSessionSet()){
            if(s.getCurrentUser().equals(user)) {
                s.setLastAccess(new DateTime());
                currentSession = s;
                return currentSession.getToken();
            }
        }
        currentSession = new Session(generateToken(), user, user.getHomeDirectory(),this);
        addSession(currentSession);
        return currentSession.getToken();
    }

    private void checkForSession(long token) throws InvalidTokenException{
    	boolean activeSession = false;
        for(Session s : getSessionSet()){
            if(s.getToken() == token && !(s.getCurrentUser().isRoot())){
                if((new DateTime().getMillis() - s.getLastAccess().getMillis()) < TIMEOUT_SESSION_TIME) {
                    activeSession = true;
                    s.setLastAccess(new DateTime());
                    break;
                }
            }
            else if(s.getToken() == token && s.getCurrentUser().isRoot()){
            	if((new DateTime().getMillis() - s.getLastAccess().getMillis()) < TIMEOUT_ROOT_SESSION_TIME){
            		activeSession = true;
            		s.setLastAccess(new DateTime());
            		break;
            	}
            	else {
            		login(s.getCurrentUser().getName(), s.getCurrentUser().getPassword());
            		activeSession = true;
            		break;
            	}
            }
        }
        if(!activeSession) {
            log.warn("Invalid Token",
                    "An invalid token was used. If this message appears too much, maybe someone is trying to hack the filesystem.");
            throw new InvalidTokenException(token);
        }
    }

    private long generateToken(){
        boolean notFound = true;
        long token = 0;
        while(notFound) {
            // Integers can be negative so to assure unicity for tests we only consider the positive ones
            long randomLong = new BigInteger(64, new Random()).longValue();
            token = randomLong < 0 ? -randomLong : randomLong;
            notFound = false;
            for(Session s : getSessionSet()) {
                if(s.getToken() == token && !notFound)
                    notFound = true;
            }
        }
        return token;
    }

    private void removeOldSessions(){
        for(Session s : getSessionSet()){
            if(!s.getCurrentUser().equals(getFilesystem().getGuest()) && ((new DateTime().getMillis() - s.getLastAccess().getMillis()) >= TIMEOUT_SESSION_TIME)){
               removeSession(s);
                s.remove();
            }
    	}
    }

    public Session getCurrentSession(){
        return currentSession;
    }
}