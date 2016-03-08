package pt.tecnico.myDrive.domain;

import org.jdom2.Element;

import pt.ist.fenixframework.FenixFramework;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MyDriveManager extends MyDriveManager_Base {
	
	static final Logger log = LogManager.getRootLogger();
    
    public MyDriveManager() {
        super();
        super.setRootFenix(FenixFramework.getDomainRoot());
        this.setCurrentUser(getFilesystem().getRoot());
        this.setCurrentDirectory(getCurrentUser().getHomeDirectory());
    }
    
    public static MyDriveManager getInstance(){
    	MyDriveManager mngr = FenixFramework.getDomainRoot().getMyDriveManager();
        if (mngr != null)
        	return mngr;

        log.trace("new MyDriveManager");
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
    
    /* Users */
    
    public void addUser(String username){
    	super.getFilesystem().addUsers(username);
    }
    
    public void removeUser(String username){
    	super.getFilesystem().removeUsers(username);
    }
    
    /* Directory */
    
    public void createDirectory(String filename){
    	super.getFilesystem().createDirectory(filename, 
    			getCurrentDirectory(), getCurrentUser());
    }
    
    ///////////////////////////Em Construção/////////////////////////////////////////////////////
    public void changeDirectory(String dirname){
    	super.setCurrentDirectory(getFilesystem().changeDirectory(dirname, 
    			getCurrentDirectory(),getCurrentUser()));
    }
    /////////////////////////////////////////////////////////////////////////////////////////////
    
    
    public void getDirectoryFilesName() {
    	System.out.println(super.getFilesystem().getDirectoryFilesName(getCurrentDirectory()));
    }
    
    /* Files */ 
    
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
    
    public void printTextFile(String path) /*TODO throws FileUnknownException, IsNotTextFileException, AccessDeniedException*/{
    	System.out.println(super.getFilesystem().printTextFile(path, getCurrentUser()));
    }
    
    /* ImportXML */
    
    public void xmlImport(Element element) throws IllegalStateException {
    	super.getFilesystem().xmlImport(element);
    }
    
}
