package pt.tecnico.myDrive.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

import pt.tecnico.myDrive.domain.Directory;
import pt.tecnico.myDrive.domain.File;
import pt.tecnico.myDrive.domain.MyDriveManager;
import pt.tecnico.myDrive.domain.Session;
import pt.tecnico.myDrive.exception.AccessDeniedException;
import pt.tecnico.myDrive.exception.FileUnknownException;

public class WriteFileTest extends AbstractServiceTest {
	private static final Logger log = LogManager.getRootLogger();
	
	@Override
    protected void populate() {
        MyDriveManager mdm = MyDriveManager.getInstance();

        mdm.getFilesystem().addUsers("Josefina");
        mdm.getFilesystem().addUsers("Josefo");

        mdm.login("root","***");
        Session currentSession = mdm.getCurrentSession();

        Directory d = (Directory) mdm.getFilesystem().getHomeDirectory().getFileByName("Josefina");
        currentSession.setCurrentDir(d);

        mdm.createPlainFile("IDoWell.txt", "I'm a plain file");
        mdm.createLinkFile("MeToo.txt", "/home/Josefina/IDoWell.txt");
        
        mdm.login("Josefina", "Josefina");
        mdm.createAppFile("MeThree.txt", "I'mAnAppFile");
    }

    private String getContent(String filename) {
    	Directory dir = MyDriveManager.getInstance().getCurrentSession().getCurrentDir();
    	File file = dir.getFileByName(filename); 
        return file.printContent(MyDriveManager.getInstance().getCurrentSession().getCurrentUser());
    }

    @Test
    public void success() {
        final String content = "Hello, I'm a plain file and I'm great";

        WriteFileService service = 
        		new WriteFileService(MyDriveManager.getInstance().getCurrentSession().getToken(), "IDoWell.txt", content);
        service.execute();
        
        String cntt = getContent("IDoWell.txt");
        assertSame("Content was written", content, cntt);
    }
    
    @Test(expected = FileUnknownException.class)
    public void writeOnNonExistingFile() {
    	final String NonExistingFile = "iNeedException.txt";
    	WriteFileService service = 
    			new WriteFileService(MyDriveManager.getInstance().getCurrentSession().getToken(), NonExistingFile, "I Do not exist :(");
    	service.execute();
    }
    
    
    @Test
    public void writeContentOnLinkFile(){
        String content = "Exception, please";
        WriteFileService service = 
        		new WriteFileService(MyDriveManager.getInstance().getCurrentSession().getToken(), "MeToo.txt", content);
    	service.execute();
        assertEquals("Link file is not pointing to the right file.", content, getContent("IDoWell.txt"));
    }
    
    @Test(expected = AccessDeniedException.class)
    public void noPermissions(){
    	MyDriveManager mdm = MyDriveManager.getInstance();
    	mdm.login("Josefo","Josefo");
    	Directory dir = (Directory) mdm.getFilesystem().getHomeDirectory().getFileByName("Josefina");
    	mdm.getCurrentSession().setCurrentDir(dir);
    	
    	WriteFileService service = 
    			new WriteFileService(MyDriveManager.getInstance().getCurrentSession().getToken(),"MeThree.txt","Exception!!!!");
    	service.execute();
    }
    
    /*
    
    @Test(expected = InvalidTokenException.class)
    public void invalidToken(){
    	WriteFileService service = new WriteFileService(-1, "IDoWell.txt", "lol");
    	service.execute;
    
    }
    */
    
    
}

/*
TEST CASES:

1. Write content on non existing plain file - DONE
2. Try to change content on link file - DONE, missing exception
3. See permissions
4. App file
5. well succeded writing - DONE
6. Invalid Token
7. Root
*/

