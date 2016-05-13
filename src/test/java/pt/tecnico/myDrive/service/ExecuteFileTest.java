package pt.tecnico.myDrive.service;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pt.tecnico.myDrive.domain.Directory;
import pt.tecnico.myDrive.domain.MyDriveManager;
import pt.tecnico.myDrive.exception.AccessDeniedException;
import pt.tecnico.myDrive.exception.InvalidContentException;
import pt.tecnico.myDrive.exception.IsNotPlainFileException;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertEquals;


public class ExecuteFileTest extends AbstractServiceTest {

    private static final Logger log = LogManager.getRootLogger();
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private PrintStream ps;

    @Override
    public void populate() {
        MyDriveManager manager = MyDriveManager.getInstance();
        manager.addUser("Josefina");
        manager.addUser("Pedrocas");

        manager.login("Josefina","Josefina");
        manager.createAppFile("noperm", "pt.tecnico.myDrive.MyDriveApplication.testAppfile");
        manager.createPlainFile("noperm1","/home/Josefina/noperm home Francisco Pedro");
        manager.createLinkFile("JosApp","/home/Josefina/noperm1");


        manager.login("Pedrocas","Pedrocas");
        manager.createAppFile("sufix", "pt.tecnico.myDrive.MyDriveApplication.testAppfile");
        manager.createAppFile("prefix", "pt.tecnico.myDrive.MyDriveApplication.testAppfile");
        manager.createAppFile("noMethod");
        manager.createPlainFile("noPath");
        manager.createPlainFile("SufixPath","/home/Pedrocas/sufix home Francisco Pedro");
        manager.createPlainFile("SPPath","/home/Pedrocas/sufix\n/home/Pedrocas/sufix\n/home/Pedrocas/sufix");
        manager.createPlainFile("NoPermJosefina","/home/Josefina/noperm home Francisco Pedro");

        manager.createLinkFile("SLink","/home/Pedrocas/SPPath");
        manager.createLinkFile("WrongLink","/home/Pedrocas");
        manager.createLinkFile("NoLinkPermissionToJosPlainF","/home/Josefina/noperm1");
        manager.createLinkFile("NoLinkPermissionToJosApp","/home/Josefina/noperm");
    }

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
        ps  =  System.out;
    }

    @After
    public void cleanUpStreams() {
        System.out.flush();
        System.setOut(ps);
    }


    // When method does not exist
    @Test(expected = IsNotPlainFileException.class)
    public void ExecuteDir(){
        String[] t = {"cd","ls"};
        ExecuteFileService fl = new ExecuteFileService(MyDriveManager.getInstance().getCurrentSession().getCurrentDir().getPath(),
                t, MyDriveManager.getInstance().getCurrentSession().getToken());
        fl.execute();
    }

    //////////////////////////////////////////
    //           App File                   //
    //////////////////////////////////////////

    @Test
    public void success() {
        String[] t = {"cd", "ls"};

        ExecuteFileService fl = new ExecuteFileService(MyDriveManager.getInstance().getCurrentSession().getCurrentDir().getFileByName("sufix").getPath(),
                t, MyDriveManager.getInstance().getCurrentSession().getToken());
        fl.execute();

        String[] output = outContent.toString().split("\n");

        assertEquals("App file isnt working", "AppFileRunning", output[1]);
    }


    // When method does not exist
    @Test(expected = InvalidContentException.class)
    public void noMethodAppFile(){
        String[] t = {"cd","ls"};
        ExecuteFileService fl = new ExecuteFileService(MyDriveManager.getInstance().getCurrentSession().getCurrentDir().getFileByName("noMethod").getPath(),
                t, MyDriveManager.getInstance().getCurrentSession().getToken());
        fl.execute();
    }

    // User does not have permission
    @Test(expected = AccessDeniedException.class)
    public void noPermissionAppFile(){
        String[] t = {"cd","ls"};
        Directory d = (Directory) MyDriveManager.getInstance().getCurrentSession().getCurrentDir().getFather().getFileByName("Josefina");
        ExecuteFileService fl = new ExecuteFileService(d.getFileByName("noperm").getPath(),t, MyDriveManager.getInstance().getCurrentSession().getToken());
        fl.execute();
    }

    //////////////////////////////////////////
    //         Plain File                   //
    //////////////////////////////////////////



    //Text PlainFile with 3 paths
    @Test
    public void SucessPlainFileWith3Path() {

        String[] t = {"cd","ls"};
        ExecuteFileService fl = new ExecuteFileService(MyDriveManager.getInstance().getCurrentSession().getCurrentDir().getFileByName("SPPath").getPath(),
                t, MyDriveManager.getInstance().getCurrentSession().getToken());
        fl.execute();

        String[] output = outContent.toString().split("\n");

        assertEquals("App file isnt working", "AppFileRunningAppFileRunningAppFileRunning", output[1]);
    }


    //Text PlainFile with 1 paths
    @Test
    public void SucessPlainFile1aths(){

        String[] t = {"cd","ls"};
        ExecuteFileService fl = new ExecuteFileService(MyDriveManager.getInstance().getCurrentSession().getCurrentDir().getFileByName("SufixPath").getPath(),
                t, MyDriveManager.getInstance().getCurrentSession().getToken());
        fl.execute();

        String[] output = outContent.toString().split("\n");
        log.debug(output[1]);
        assertEquals("App file isnt working", "AppFileRunning", output[1]);
    }


    // When method does not exist
    @Test(expected = InvalidContentException.class)
    public void noContentPlainFile(){
        String[] t = {"cd","ls"};
        ExecuteFileService fl = new ExecuteFileService(MyDriveManager.getInstance().getCurrentSession().getCurrentDir().getFileByName("noPath").getPath(),
                t, MyDriveManager.getInstance().getCurrentSession().getToken());
        fl.execute();
    }

    // User does not have permission to execute josefina app through plain content
    @Test(expected = AccessDeniedException.class)
    public void noPermissionPlainFileToApp(){
        String[] t = {"cd","ls"};

        ExecuteFileService fl = new ExecuteFileService(MyDriveManager.getInstance().getCurrentSession().getCurrentDir().getFileByName("NoPermJosefina").getPath(),
                t, MyDriveManager.getInstance().getCurrentSession().getToken());
        fl.execute();
    }

    // User does not have permission to execute josefina plain file
    @Test(expected = AccessDeniedException.class)
    public void noPermissionPlainFile(){
        String[] t = {"cd","ls"};
        Directory d = (Directory) MyDriveManager.getInstance().getCurrentSession().getCurrentDir().getFather().getFileByName("Josefina");
        ExecuteFileService fl = new ExecuteFileService(d.getFileByName("noperm1").getPath(),t, MyDriveManager.getInstance().getCurrentSession().getToken());
        fl.execute();
    }

    //////////////////////////////////////////
    //           Link File                  //
    //////////////////////////////////////////



    //Text PlainFile with 3 paths
    @Test
    public void SucesLinkFile() {

        String[] t = {"cd","ls"};
        ExecuteFileService fl = new ExecuteFileService(MyDriveManager.getInstance().getCurrentSession().getCurrentDir().getFileByName("SLink").getPath(),
                t, MyDriveManager.getInstance().getCurrentSession().getToken());
        fl.execute();

        String[] output = outContent.toString().split("\n");

        assertEquals("App file isnt working", "AppFileRunningAppFileRunningAppFileRunning", output[1]);
    }

    // When method does not exist
    @Test(expected = IsNotPlainFileException.class)
    public void wrongContetLinkFile(){
        String[] t = {"cd","ls"};
        ExecuteFileService fl = new ExecuteFileService(MyDriveManager.getInstance().getCurrentSession().getCurrentDir().getFileByName("WrongLink").getPath(),
                t, MyDriveManager.getInstance().getCurrentSession().getToken());
        fl.execute();
    }

    // User does not have permission to execute josefina plain file through link
    @Test(expected = AccessDeniedException.class)
    public void noPermissionLinkToPlainFile(){
        String[] t = {"cd","ls"};

        ExecuteFileService fl = new ExecuteFileService(MyDriveManager.getInstance().getCurrentSession().getCurrentDir().getFileByName("NoLinkPermissionToJosPlainF").getPath(),
                t, MyDriveManager.getInstance().getCurrentSession().getToken());
        fl.execute();
    }

    // User does not have permission to execute josefina app file through link
    @Test(expected = AccessDeniedException.class)
    public void noPermissionLinkToAppFile(){
        String[] t = {"cd","ls"};

        ExecuteFileService fl = new ExecuteFileService(MyDriveManager.getInstance().getCurrentSession().getCurrentDir().getFileByName("NoLinkPermissionToJosApp").getPath(),
                t, MyDriveManager.getInstance().getCurrentSession().getToken());
        fl.execute();
    }

    // User does not have permission to execute josefina link file
    @Test(expected = AccessDeniedException.class)
    public void noPermissionAnotherUSerLinkFile(){
        String[] t = {"cd","ls"};
        Directory d = (Directory) MyDriveManager.getInstance().getCurrentSession().getCurrentDir().getFather().getFileByName("Josefina");
        ExecuteFileService fl = new ExecuteFileService(d.getFileByName("JosApp").getPath(),t, MyDriveManager.getInstance().getCurrentSession().getToken());
        fl.execute();
    }

}
