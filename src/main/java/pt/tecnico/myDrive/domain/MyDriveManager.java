package pt.tecnico.myDrive.domain;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jdom2.Document;
import org.jdom2.Element;
import pt.ist.fenixframework.FenixFramework;
import pt.tecnico.myDrive.exception.AccessDeniedException;
import pt.tecnico.myDrive.exception.FileAlreadyExistsException;
import pt.tecnico.myDrive.exception.FileUnknownException;
import pt.tecnico.myDrive.exception.IllegalRemovalException;
import pt.tecnico.myDrive.exception.InvalidFileNameException;
import pt.tecnico.myDrive.exception.InvalidMaskException;
import pt.tecnico.myDrive.exception.IsNotPlainFileException;
import pt.tecnico.myDrive.exception.UserAlreadyExistsException;
import pt.tecnico.myDrive.exception.UserUnknownException;

// import pt.tecnico.myDrive.exception.InvalidContentException;

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
    	
    	try {
    		super.getFilesystem().createDirectory(filename, 
    			getCurrentDirectory(), getCurrentUser());
    	} catch (InvalidFileNameException ex1) {
    		log.trace(ex1.getMessage());
    	} catch (FileAlreadyExistsException ex2) {
    		log.trace(ex2.getMessage());
    	} catch (InvalidMaskException ex3) {
    		log.trace(ex3.getMessage());
    	} catch (FileUnknownException ex4) {
    		log.trace(ex4.getMessage());
    	}
    }
    
    
    public void changeDirectory(String directoryname){
    	
    	try {
    		super.setCurrentDirectory(getFilesystem().changeDirectory(directoryname, 
    				getCurrentDirectory(),getCurrentUser()));
    	} catch (FileUnknownException ex) {
    		log.trace(ex.getMessage());
    	}
    }
    
    
    public void AbsolutePath(String path){
    	
    	try {
    	super.setCurrentDirectory(getFilesystem().absolutePath(path, getCurrentUser()));
    	} catch (FileUnknownException ex) {
    		log.trace(ex.getMessage());
    	}
    }

    
    public void getDirectoryFilesName(String path) {
    	
    	try {
    		System.out.println(getFilesystem().getDirectoryFilesName(path, getCurrentUser()));
    	} catch (FileUnknownException ex) {
    		log.trace(ex.getMessage());
    	}
    }

    
    public void removeFile(String path){
    	
    	try {
    		super.getFilesystem().removeFile(path, getCurrentUser());
    	} catch (FileUnknownException ex1) {
    		log.trace(ex1.getMessage());
    	} catch (IllegalRemovalException ex2) {
    		log.trace(ex2.getMessage());
    	}
    }
    
    
    /* --- Files --- */ 
    
    public void createPlainFile(String filename){
    	
    	try {
    		super.getFilesystem().createPlainFile(filename, 
    				getCurrentDirectory(), getCurrentUser());
    	} catch (FileUnknownException ex1) {
    		log.trace(ex1.getMessage());
    	} catch (FileAlreadyExistsException ex2) {
    		log.trace(ex2.getMessage());
    	} catch (InvalidFileNameException ex3) {
    		log.trace(ex3.getMessage());
    	} catch (InvalidMaskException ex4) {
    		log.trace(ex4.getMessage());
    	}
    }
    
    
    public void createPlainFile(String filename, String content){
    	
    	try {
    		super.getFilesystem().createPlainFile(filename,
    				getCurrentDirectory(), getCurrentUser(), content);
    	} catch (FileUnknownException ex1) {
    		log.trace(ex1.getMessage());
		} catch (FileAlreadyExistsException ex2) {
			log.trace(ex2.getMessage());
		} catch (InvalidFileNameException ex3) {
    		log.trace(ex3.getMessage());
    	} catch (InvalidMaskException ex4) {
    		log.trace(ex4.getMessage());
    	}
    }
 
    
    public void createLinkFile(String filename){
    	
    	try {
    		super.getFilesystem().createLinkFile(filename, 
    			getCurrentDirectory(), getCurrentUser());
    	} catch (FileUnknownException ex1) {
    		log.trace(ex1.getMessage());
    	} catch (FileAlreadyExistsException ex2) {
    		log.trace(ex2.getMessage());
    	} catch (InvalidFileNameException ex3) {
    		log.trace(ex3.getMessage());
    	} catch (InvalidMaskException ex4) {
    		log.trace(ex4.getMessage());
    	}
    	
    }
  
    
    public void createLinkFile(String filename, String content){
    	// TODO: InvalidContentException
    	try {
    		super.getFilesystem().createLinkFile(filename, 
    			getCurrentDirectory(), getCurrentUser(), content);
    	} catch (FileUnknownException ex1) {
    		log.trace(ex1.getMessage());
    	} catch (FileAlreadyExistsException ex2) {
    		log.trace(ex2.getMessage());
    	} catch (InvalidFileNameException ex3) {
    		log.trace(ex3.getMessage());
    	} catch (InvalidMaskException ex4) {
    		log.trace(ex4.getMessage());
    	}
    	
    }
 
    
    public void createAppFile(String filename){
    	
    	try {
    		super.getFilesystem().createAppFile(filename, 
    			getCurrentDirectory(), getCurrentUser());
    	} catch (FileUnknownException ex1) {
    		log.trace(ex1.getMessage());
    	} catch (FileAlreadyExistsException ex2) {
    		log.trace(ex2.getMessage());
    	} catch (InvalidFileNameException ex3) {
    		log.trace(ex3.getMessage());
    	} catch (InvalidMaskException ex4) {
    		log.trace(ex4.getMessage());
    	}
    }

    
    public void createAppFile(String filename, String content){
    	// TODO: InvalidContentException
    	try {
    		super.getFilesystem().createAppFile(filename,
    				getCurrentDirectory(), getCurrentUser(), content);
    	} catch (FileUnknownException ex1) {
    		log.trace(ex1.getMessage());
    	} catch (FileAlreadyExistsException ex2) {
    		log.trace(ex2.getMessage());
    	} catch (InvalidFileNameException ex3) {
    		log.trace(ex3.getMessage());
    	} catch (InvalidMaskException ex4) {
    		log.trace(ex4.getMessage());
    	}
    }

    
    public void printPlainFile(String path) throws FileUnknownException, IsNotPlainFileException, AccessDeniedException{
    	
    	try {
    		System.out.println(super.getFilesystem().printPlainFile(path, getCurrentUser()));
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
}
