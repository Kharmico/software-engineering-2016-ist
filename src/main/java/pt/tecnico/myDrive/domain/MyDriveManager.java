package pt.tecnico.myDrive.domain;

import org.jdom2.Element;
import org.jdom2.Document;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import java.io.IOException;
import java.io.PrintStream;

import pt.ist.fenixframework.FenixFramework;
import pt.tecnico.myDrive.exception.AccessDeniedException;
import pt.tecnico.myDrive.exception.FileAlreadyExistsException;
import pt.tecnico.myDrive.exception.FileUnknownException;
import pt.tecnico.myDrive.exception.IllegalRemovalException;
import pt.tecnico.myDrive.exception.InvalidContentException;
import pt.tecnico.myDrive.exception.InvalidFileNameException;
import pt.tecnico.myDrive.exception.InvalidMaskException;
import pt.tecnico.myDrive.exception.IsNotDirectoryException;
import pt.tecnico.myDrive.exception.IsNotPlainFileException;
import pt.tecnico.myDrive.exception.UserAlreadyExistsException;
import pt.tecnico.myDrive.exception.UserUnknownException;


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
    
   
    
    /* --- Users --- */
    
    public void addUser(String username){
    	try{
    		super.getFilesystem().addUsers(username);
    	}catch(UserAlreadyExistsException e){System.out.println(e);}  
    }
    
    public void removeUser(String username){
    	try{
    		super.getFilesystem().removeUsers(username);
    	}catch(IllegalRemovalException | UserUnknownException e){System.out.println(e);} 	
    }
    
    /* --- Directory --- */
    
    public void createDirectory(String filename){
    	try{
    	super.getFilesystem().createDirectory(filename, 
    			getCurrentDirectory(), getCurrentUser());
    	}catch(InvalidFileNameException | FileAlreadyExistsException e){System.out.println(e);}
    }
    
    public void changeDirectory(String dirname){
    	try{
    		super.setCurrentDirectory(getFilesystem().changeDirectory(dirname, 
    			getCurrentDirectory(),getCurrentUser()));
    	}catch(IsNotDirectoryException e){System.out.println(e);}
    }
    
    public void AbsolutePath(String path){
    	try{
    		super.setCurrentDirectory(getFilesystem().AbsolutePath(path, getCurrentUser()));
    	}catch(AccessDeniedException | IsNotDirectoryException e){System.out.println(e);}
    }    
    
    public void getDirectoryFilesName(String filename) {
    	System.out.println(super.getFilesystem().getDirectoryFilesName(filename, getCurrentDirectory(), getCurrentUser()));
    }
    
    
    /* --- Files --- */ 
    
    public void createPlainFile(String filename){
    	try{
    		super.getFilesystem().createPlainFile(filename, 
    			getCurrentDirectory(), getCurrentUser());
    	}catch(InvalidFileNameException | InvalidMaskException | FileAlreadyExistsException e){System.out.println(e);}
    }
    
    public void createPlainFile(String filename, String content){
    	try{
    		super.getFilesystem().createPlainFile(filename, 
    			getCurrentDirectory(), getCurrentUser(), content);
    	}catch(InvalidFileNameException | InvalidMaskException | FileAlreadyExistsException e){System.out.println(e);}
    }
    public void createLinkFile(String filename){
    	try{
    		super.getFilesystem().createLinkFile(filename, 
    			getCurrentDirectory(), getCurrentUser());
    	}catch(InvalidFileNameException | InvalidMaskException | FileAlreadyExistsException e){System.out.println(e);}
    }
    
    public void createLinkFile(String filename, String content){
    	try{
    		super.getFilesystem().createLinkFile(filename, 
    			getCurrentDirectory(), getCurrentUser(), content);
    	}catch(InvalidFileNameException | InvalidMaskException | FileAlreadyExistsException e){System.out.println(e);}
    }
    
    public void createAppFile(String filename){
    	try{
    		super.getFilesystem().createAppFile(filename, 
    			getCurrentDirectory(), getCurrentUser());
    	}catch(InvalidFileNameException | InvalidMaskException | FileAlreadyExistsException e){System.out.println(e);}
    }
    
    public void createAppFile(String filename, String content){
    	try{
    	super.getFilesystem().createAppFile(filename, 
    			getCurrentDirectory(), getCurrentUser(), content);
    	}catch(InvalidFileNameException | InvalidMaskException | FileAlreadyExistsException e){System.out.println(e);}
    }
    
    public void printTextFile(String filename){
    	try{
    		System.out.println(super.getFilesystem().printTextFile(filename, getCurrentDirectory(), getCurrentUser()));
    	}catch(FileUnknownException | IsNotPlainFileException | AccessDeniedException e){System.out.println(e);}
    }
    
    public void removeEntries(String filename){
    	super.getFilesystem().removeEntries(filename, getCurrentUser());
    }
    
    /* --- ImportXML --- */
    
    public void xmlImport(Element element) throws IllegalStateException {
    	super.getFilesystem().xmlImport(element);
    }
    
    
    /* --- ExportXML --- */
    
    public void xmlExport(){ // TODO: Unfinished business!!!
    	Element element = new Element("mydrivemanager");
    	Document doc = new Document(element);
    	XMLOutputter xmloutput = new XMLOutputter(Format.getPrettyFormat());
    	
    	element.addContent(super.getFilesystem().xmlExport());
    	try {
    		xmloutput.output(doc, new PrintStream(System.out));
    	} catch (IOException e) { System.out.println(e); }
    }
}
