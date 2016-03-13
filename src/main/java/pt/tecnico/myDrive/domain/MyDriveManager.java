package pt.tecnico.myDrive.domain;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jdom2.Document;
import org.jdom2.Element;
import pt.ist.fenixframework.FenixFramework;
import pt.tecnico.myDrive.exception.AccessDeniedException;
import pt.tecnico.myDrive.exception.FileUnknownException;
import pt.tecnico.myDrive.exception.IsNotPlainFileException;

public class MyDriveManager extends MyDriveManager_Base {
	
	static final Logger log = LogManager.getRootLogger();
    
    private MyDriveManager() {
        FenixFramework.getDomainRoot().setMyDriveManager(this);
        super.setFilesystem(new FileSystem(this));
        super.setCurrentUser(getFilesystem().getRoot());
        super.setCurrentDirectory(getCurrentUser().getHomeDirectory());
    }
    
    public static MyDriveManager getInstance(){
    	MyDriveManager mngr = FenixFramework.getDomainRoot().getMyDriveManager();
        if (mngr != null)
        	return mngr;

        log.trace("MyDriveManager.getInstance: new MyDriveManager");
        return new MyDriveManager();
    }
    
    @Override
    public void setCurrentDirectory(Directory currentDirectory){
    	super.setCurrentDirectory(currentDirectory);
    }
    
    @Override
    public void setCurrentUser(User currentUser){
    	super.setCurrentUser(currentUser);
    }
    
    
    @Override
    public void setFilesystem(FileSystem filesystem){
    	super.setFilesystem(filesystem);
    }
    
    public void remove(){
    	this.setCurrentDirectory(null);
    	this.setCurrentUser(null);
    	this.setFilesystem(null);
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
    			getCurrentDirectory(), getCurrentUser());
    }
    
    public void changeDirectory(String dirname){
    	super.setCurrentDirectory(getFilesystem().changeDirectory(dirname, 
    			getCurrentDirectory(),getCurrentUser()));
    }
    
    public void AbsolutePath(String path){
    	super.setCurrentDirectory(getFilesystem().AbsolutePath(path, getCurrentUser()));
    }

    public String getDirectoryFilesName(String path) {
        return getFilesystem().getDirectoryFilesName(path, getCurrentUser());
    }

    public void removeFile(String path){
        super.getFilesystem().removeFile(path, getCurrentUser());
    }
    
    
    /* --- Files --- */ 
    
    public void createPlainFile(String filename){
    	super.getFilesystem().createPlainFile(filename, 
    			getCurrentDirectory(), getCurrentUser());
    }
    
    public void createPlainFile(String filename, String content){
    	super.getFilesystem().createPlainFile(filename, 
    			getCurrentDirectory(), getCurrentUser(), content);
    }
    
    public void createLinkFile(String filename){
    	super.getFilesystem().createLinkFile(filename, 
    			getCurrentDirectory(), getCurrentUser());
    }
    
    public void createLinkFile(String filename, String content){
    	super.getFilesystem().createLinkFile(filename, 
    			getCurrentDirectory(), getCurrentUser(), content);
    }
    
    public void createAppFile(String filename){
    	super.getFilesystem().createAppFile(filename, 
    			getCurrentDirectory(), getCurrentUser());
    }
    
    public void createAppFile(String filename, String content){
    	super.getFilesystem().createAppFile(filename, 
    			getCurrentDirectory(), getCurrentUser(), content);
    }
    
    public String printPlainFile(String path) throws FileUnknownException, IsNotPlainFileException, AccessDeniedException{
    	return super.getFilesystem().printPlainFile(path, getCurrentUser());
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
}
