package pt.tecnico.myDrive.service;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import pt.tecnico.myDrive.domain.Directory;
import pt.tecnico.myDrive.domain.MyDriveManager;
import pt.tecnico.myDrive.exception.AccessDeniedException;
import pt.tecnico.myDrive.exception.InvalidContentException;


public class ExecuteFileTest extends AbstractServiceTest {

    private static final Logger log = LogManager.getRootLogger();

    @Override
    public void populate() {
        MyDriveManager manager = MyDriveManager.getInstance();
        manager.addUser("Josefina");
        manager.addUser("Pedrocas");

        manager.login("Josefina","Josefina");
        manager.createAppFile("noperm", "pt.tecnico.myDrive.MyDriveApplication.testAppfile");

        manager.login("Pedrocas","Pedrocas");
        manager.createAppFile("sufix", "pt.tecnico.myDrive.MyDriveApplication.testAppfile");
        manager.createAppFile("prefix", "pt.tecnico.myDrive.MyDriveApplication.testAppfile");
        manager.createAppFile("noMethod");
        manager.createPlainFile("noPath");
        manager.createPlainFile("SufixPath","/home/Pedrocas/sufix home Francisco Pedro");
        manager.createPlainFile("SPPath","/home/Pedrocas/sufix\n/home/Pedrocas/sufix");
    }


    //////////////////////////////////////////
    //           App File                   //
    //////////////////////////////////////////

    @Test
    public void success(){
        String[] t = {"cd","ls"};
        ExecuteFileService fl = new ExecuteFileService(MyDriveManager.getInstance().getCurrentSession().getCurrentDir().getFileByName("sufix").getPath(),
                t, MyDriveManager.getInstance().getCurrentSession().getToken());
        fl.execute();
     //   assertEquals("App file isnt working","AppFileRunning");
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

    
    //Text PlainFile with 2 paths
    @Test
    public void SucessPlainFileWith2Paths() {
        String[] t = {"cd","ls"};
        ExecuteFileService fl = new ExecuteFileService(MyDriveManager.getInstance().getCurrentSession().getCurrentDir().getFileByName("SPPath").getPath(),
                t, MyDriveManager.getInstance().getCurrentSession().getToken());
        fl.execute();
    }

    @Test
    public void SucessPlainFile(){
        String[] t = {"cd","ls"};
        ExecuteFileService fl = new ExecuteFileService(MyDriveManager.getInstance().getCurrentSession().getCurrentDir().getFileByName("SufixPath").getPath(),
                t, MyDriveManager.getInstance().getCurrentSession().getToken());
        fl.execute();
    }

    // When method does not exist
    @Test(expected = InvalidContentException.class)
    public void noContetPlainFile(){
        String[] t = {"cd","ls"};
        ExecuteFileService fl = new ExecuteFileService(MyDriveManager.getInstance().getCurrentSession().getCurrentDir().getFileByName("noMethod").getPath(),
                t, MyDriveManager.getInstance().getCurrentSession().getToken());
        fl.execute();
    }


}
