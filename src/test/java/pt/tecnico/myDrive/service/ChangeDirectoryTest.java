package pt.tecnico.myDrive.service;

import org.apache.commons.lang.RandomStringUtils;
import org.junit.Test;
import pt.tecnico.myDrive.domain.Directory;
import pt.tecnico.myDrive.domain.MyDriveManager;
import pt.tecnico.myDrive.domain.Session;
import pt.tecnico.myDrive.exception.*;

import static org.junit.Assert.assertEquals;

public class ChangeDirectoryTest extends AbstractServiceTest {


    @Override
    protected void populate(){
        MyDriveManager mg = MyDriveManager.getInstance();

        mg.getFilesystem().addUsers("Marco");

        mg.login("root","***");
        Session currentSession = mg.getCurrentSession();

        mg.createDirectory("teste");
        currentSession.setCurrentDir((Directory)mg.getCurrentSession().getCurrentDir().getFileByName("teste"));

        mg.createPlainFile("readme.txt","15 de Abril");
        mg.createDirectory("eclipse");
        mg.createDirectory("pt");
        mg.getCurrentSession().getCurrentDir().getFileByName("pt").setPermissions("rwxd----");

        currentSession.setCurrentDir(currentSession.getCurrentDir().getFather());
    }

    @Test
    public void sucess(){
        ChangeDirectoryService service =
            new ChangeDirectoryService(MyDriveManager.getInstance().getCurrentSession().getToken(),"/home/root/teste/eclipse");
        service.execute();
        assertEquals("Change to a directory that exists","/home/root/teste/eclipse",MyDriveManager.getInstance().getCurrentSession().getCurrentDir().getPath());
    }

    @Test(expected = FileUnknownException.class)
    public void invalidDirectory(){
        ChangeDirectoryService service =
                new ChangeDirectoryService(MyDriveManager.getInstance().getCurrentSession().getToken(),"/home/root/teste/java");
        service.execute();
    }

    @Test
    public void sucessFather(){
        ChangeDirectoryService service =
                new ChangeDirectoryService(MyDriveManager.getInstance().getCurrentSession().getToken(),"..");
        service.execute();
        assertEquals("Return father","/home",MyDriveManager.getInstance().getCurrentSession().getCurrentDir().getPath());
    }

    @Test
    public void sucessItSelf(){
        ChangeDirectoryService service =
                new ChangeDirectoryService(MyDriveManager.getInstance().getCurrentSession().getToken(),".");
        service.execute();
        assertEquals("Return itself","/home/root",MyDriveManager.getInstance().getCurrentSession().getCurrentDir().getPath());
    }

    @Test(expected = IsNotDirectoryException.class)
    public void invalidChangeToPlainFile(){
        ChangeDirectoryService service =
                new ChangeDirectoryService(MyDriveManager.getInstance().getCurrentSession().getToken(),"/home/root/teste/readme.txt");
        service.execute();
    }

    @Test(expected = AccessDeniedException.class)
    public void noPermissions(){
        MyDriveManager.getInstance().login("Marco","Marco");
        ChangeDirectoryService service =
                new ChangeDirectoryService(MyDriveManager.getInstance().getCurrentSession().getToken(),"/home/root/teste/pt");
        service.execute();
    }

    @Test(expected = PathIsTooBigException.class)
    public void pathToBig(){
        ChangeDirectoryService service =
                new ChangeDirectoryService(MyDriveManager.getInstance().getCurrentSession().getToken(),RandomStringUtils.random(1024));
        service.execute();
    }

    @Test(expected = InvalidTokenException.class)
    public void noValidToken(){
        ChangeDirectoryService service = new ChangeDirectoryService(-1,"/teste/eclipse");
        service.execute();
    }

    @Test
    public void changeToRoot(){
        ChangeDirectoryService service = new ChangeDirectoryService(MyDriveManager.getInstance().getCurrentSession().getToken(),"/");
        service.execute();
        assertEquals("Change to /","/",MyDriveManager.getInstance().getCurrentSession().getCurrentDir().getPath());
    }

    /* Test Cases */
    /* 1 - changeDirectory to a directory that exists in the current directory and i have permission */
    /* 2 - changeDirectory to a directory that doesn't exist */
    /* 3 - ChangeDirectory to father */
    /* 4 - ChangeDirectory to itself */
    /* 5 - ChangeDirectory to a plainfile */
    /* 6 - ChangeDirectory to a directory that i don't have permission to */
    /* 7 - ChangeDirectory with wrong token */
    /* 8 - ChangeDirectory to / */
}