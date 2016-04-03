package pt.tecnico.myDrive.service;

import static org.junit.Assert.assertNotSame;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

import pt.tecnico.myDrive.domain.Directory;
import pt.tecnico.myDrive.domain.File;
import pt.tecnico.myDrive.domain.FileSystem;
import pt.tecnico.myDrive.domain.MyDriveManager;
import pt.tecnico.myDrive.domain.Session;
import pt.tecnico.myDrive.domain.User;

public class WriteFileTest extends AbstractServiceTest {
	private static final Logger log = LogManager.getRootLogger();
	
	@Override
    protected void populate() {
        MyDriveManager mdm = MyDriveManager.getInstance();

        mdm.getFilesystem().addUsers("Josefina");

        mdm.login("root","***");
        Session currentSession = mdm.getCurrentSession();

        currentSession.setCurrentDir(mdm.getFilesystem().getFsRoot());

        mdm.createPlainFile("plainfile.txt","i'm a plain file");
    }

    private String getContent(String filename) {
    	Directory dir = MyDriveService.getMyDriveManager().getFilesystem().getHomeDirectory();
    	File file = dir.getFileByName(filename); 
        return file.printContent();
    }

    @Test
    public void success() {
       /* final String content = "hello, i'm a plain file";
        WriteFileService service = new WriteFileService(12563, "plainfile.txt", content); //TODO token (1st arg)
        service.execute();

        
        // check if content was written
        String cntt = getContent("plainfile.txt");
        assertNotSame("Content was not written", cntt, content);
    */}
}

/*
TEST CASES:

1. Write content on non existing plain file
2. see permissions
3. app/linkFile
*/