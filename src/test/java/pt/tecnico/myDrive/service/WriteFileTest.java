package pt.tecnico.myDrive.service;

import static org.junit.Assert.assertNotSame;

import org.junit.Test;

import pt.tecnico.myDrive.domain.Directory;
import pt.tecnico.myDrive.domain.File;
import pt.tecnico.myDrive.domain.FileSystem;
import pt.tecnico.myDrive.domain.MyDriveManager;
import pt.tecnico.myDrive.domain.User;

public class WriteFileTest extends AbstractServiceTest {

	//long token = fs.getMyDriveManager().getCurrentSession().getToken();
	
    protected void populate() {
        MyDriveManager mdm = MyDriveManager.getInstance();

        FileSystem fs = new FileSystem(mdm);
        User usr = new User("Josefina", fs);
        Directory dir = usr.getHomeDirectory();
        String path =  dir.getPath();
        
        fs.createPlainFile(path, dir, usr, "i'm a plain file");   //FIXME
    }

    private String getContent(String filename) {
    	Directory dir = MyDriveService.getMyDriveManager().getFilesystem().getHomeDirectory();
    	File file = dir.getFileByName(filename); 
        return file.printContent();
    }

    @Test
    public void success() {
        final String content = "hello, i'm a plain file";
        WriteFileService service = new WriteFileService(12563, "plainFileName", content); //TODO token (1st arg)
        service.execute();

        
        // check if content was written
        String cntt = getContent("plainFileName");
        assertNotSame("Content was not written", cntt, content);
    }
}