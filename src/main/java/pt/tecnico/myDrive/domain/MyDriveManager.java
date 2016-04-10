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

// import pt.tecnico.myDrive.exception.InvalidContentException;

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
    
    
    public void changeDirectory(String directoryName) throws FileUnknownException, PathIsTooBigException, AccessDeniedException{ //TODO delete this and rename AbsolutePath to this name?
		currentSession.setCurrentDir(getFilesystem().getLastDirectory(directoryName, currentSession.getCurrentDir(), currentSession.getCurrentUser()));
    }
    
    
    public void AbsolutePath(String path){
    	currentSession.setCurrentDir(getFilesystem().absolutePath(path, currentSession.getCurrentUser(), currentSession.getCurrentDir()));
    }

    @Deprecated
    public String getDirectoryFilesName(String path) {
		return getFilesystem().getDirectoryFilesName(path, currentSession.getCurrentUser(), currentSession.getCurrentDir());
    }

    public String getDirectoryFilesName() {
        return getFilesystem().getDirectoryFilesName(currentSession.getCurrentDir().getPath(), currentSession.getCurrentUser(), currentSession.getCurrentDir());
    }

    
    public void removeFile(String path){
    	super.getFilesystem().removeFile(path, getCurrentSession().getCurrentUser(), getCurrentSession().getCurrentDir());
    }

    public void writeContent(String path, String content){
        getFilesystem().writeContent(path, currentSession.getCurrentUser(), currentSession.getCurrentDir(), content);
    }
    
    /* --- Files --- */ 
    
    public void createFile(String tipo, String filename, String content) throws UnknownTypeException, IsNotPlainFileException, FileAlreadyExistsException {
    	switch(tipo.toLowerCase()){
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
        		if(!(content.equals("")))
        			throw new IsNotPlainFileException(filename);
        		createDirectory(filename);
        		break;
        	default:
        		throw new UnknownTypeException(tipo);
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
    
    public long login(String username, String password) throws UserUnknownException, WrongPasswordException {
    	User user = getFilesystem().checkUser(username,password);
        removeOldSessions();
        currentSession = new Session(generateToken(),user,user.getHomeDirectory());
        return currentSession.getToken();
    }

    /*public boolean checkForSession(long token){
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
        
       if(output != null)
            output.setLastAccess(new DateTime());
        else output = new Session(generateToken(),user,user.getHomeDirectory());

        return activeSession;
    }*/

    private long generateToken(){
        boolean notFound = true;
        long token = 0;
        while(notFound) {
            token = new BigInteger(64, new Random()).longValue();
            notFound = false;
            for(Session s : getSessionSet()) {
                if(s.getToken() == token && notFound == false)
                    notFound = true;
            }
        }
        return token;
    }

    private void removeOldSessions(){
        for(Session s : getSessionSet()){
            if((new DateTime().getMillis() - s.getLastAccess().getMillis()) >= 7200000)
                s.remove();
    	}
    }

    public Session getCurrentSession(){
        return currentSession;
    }
}