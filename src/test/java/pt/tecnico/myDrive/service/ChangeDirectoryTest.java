package pt.tecnico.myDrive.service;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pt.tecnico.myDrive.domain.Directory;
import pt.tecnico.myDrive.domain.MyDriveManager;
import pt.tecnico.myDrive.domain.Session;
import pt.tecnico.myDrive.exception.FileUnknownException;
import pt.tecnico.myDrive.exception.InvalidMaskException;
import pt.tecnico.myDrive.exception.IsNotDirectoryException;

public class ChangeDirectoryTest extends AbstractServiceTest {
    protected static final Logger log = LogManager.getRootLogger();

    @Override
    protected void populate(){
        MyDriveManager mg = MyDriveManager.getInstance();

        mg.getFilesystem().addUsers("Marco");

        mg.login("root","***");
        Session currentSession = mg.getCurrentSession();

        currentSession.setCurrentDir(mg.getFilesystem().getFsRoot());
        mg.createDirectory("teste");
        currentSession.setCurrentDir((Directory)mg.getFilesystem().getFsRoot().getFileByName("teste"));

        mg.createPlainFile("readme.txt","15 de Abril");
        mg.createDirectory("eclipse");

    }

    /*
    @Test
    public void sucess(){

        ChangeDirectoryService service =
            new ChangeDirectoryService(MyDriveManager.getInstance().getCurrentSession().getToken(),"/teste/eclipse");
        service.execute();
        assertEquals("Change to a directory that exists","/teste/eclipse",MyDriveManager.getInstance().getCurrentSession().getCurrentDir().getPath());
    }
    */

    /*
    @Test(expected = FileUnknownException.class)
    public void invalidDirectory(){
        ChangeDirectoryService service =
                new ChangeDirectoryService(MyDriveManager.getInstance().getCurrentSession().getToken(),"/teste/java");
        service.execute();

    }
    */

    /*
    @Test
    public void sucessFather(){
        ChangeDirectoryService service =
                new ChangeDirectoryService(MyDriveManager.getInstance().getCurrentSession().getToken(),"..");
        assertEquals("Return father","/",MyDriveManager.getInstance().getCurrentSession().getCurrentDir().getPath());
    }
    */

    /*
    @Test
    public void sucessItSelf(){
        ChangeDirectoryService service =
                new ChangeDirectoryService(MyDriveManager.getInstance().getCurrentSession().getToken(),".");
        assertEquals("Return itself","/teste/",MyDriveManager.getInstance().getCurrentSession().getCurrentDir().getPath());
    }
    */

     /*
    @Test(expected = IsNotDirectoryException.class)
    public void invalidChangeToPlainFile(){
        ChangeDirectoryService service =
                new ChangeDirectoryService(MyDriveManager.getInstance().getCurrentSession().getToken(),"/teste/readme.txt");
        service.execute();
    }
    */

    /*
    @Test(expected = InvalidMaskException.class)
    public void noPermissions(){
        MyDriveManager.getInstance().login("Marco","Marco");
        ChangeDirectoryService service =
                new ChangeDirectoryService(MyDriveManager.getInstance().getCurrentSession().getToken(),"/teste/eclipse");
        service.execute();
    }
    */

    /*
    @Test(expected = InvalidTokenException.class)
    public void noValidToken(){
        MyDriveManager.getInstance().login("Marco","Marco");
        long token = MyDriveManager.getInstance().getCurrentSession().getToken() + 1;
        ChangeDirectoryService service =
                new ChangeDirectoryService(token,"/teste/eclipse");
        service.execute();
    }
    */


    /* Test Cases */
    /* 1 - changeDirectory to a directory that exists in the current directory and i have permission */
    /* 2 - changeDirectory to a directory that doesn't exist */
    /* 3 - ChangeDirectory to father */
    /* 4 - ChangeDirectory to itself */
    /* 5 - ChangeDirectory to a plainfile */
    /* 6 - ChangeDirectory to a directory that i don't have permission to */
    /* 7 - ChangeDirectory with wrong token */
}