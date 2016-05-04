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
        String s = "home/Francisco/Pedro";
        String[] t = s.split("/");
        ExecuteFileService fl = new ExecuteFileService(MyDriveManager.getInstance().getCurrentSession().getCurrentDir().getFileByName("sufix").getPath(),
                t, MyDriveManager.getInstance().getCurrentSession().getToken());
        fl.execute();
     //   assertEquals("App file isnt working","AppFileRunning");
    }

    // Teste para o caso de o metodo nao existir
    @Test(expected = InvalidContentException.class)
    public void noMethodAppFile(){
        String s = "home/Francisco/Pedro";
        String[] t = s.split("/");
        ExecuteFileService fl = new ExecuteFileService(MyDriveManager.getInstance().getCurrentSession().getCurrentDir().getFileByName("noMethod").getPath(),
                t, MyDriveManager.getInstance().getCurrentSession().getToken());
        fl.execute();
    }

    // Teste para o caso de o utilizador nao ter permissao
    @Test(expected = AccessDeniedException.class)
    public void noPermissionAppFile(){
        String s = "home/Francisco/Pedro";
        String[] t = s.split("/");
        Directory d = (Directory) MyDriveManager.getInstance().getCurrentSession().getCurrentDir().getFather().getFileByName("Josefina");
        ExecuteFileService fl = new ExecuteFileService(d.getFileByName("noperm").getPath(),t, MyDriveManager.getInstance().getCurrentSession().getToken());
        fl.execute();
    }

    //////////////////////////////////////////
    //         Plain File                   //
    //////////////////////////////////////////

    /*
    //Text PlainFIle with 2 paths
    @Test
    public void SucessPlainFile() {
        String s = "home/Francisco/Pedro";
        String[] t = s.split("/");
        ExecuteFileService fl = new ExecuteFileService(MyDriveManager.getInstance().getCurrentSession().getCurrentDir().getFileByName("SPPath").getPath(),
                t, MyDriveManager.getInstance().getCurrentSession().getToken());
        fl.execute();
    }*/

    /*@Test
    public void SucessPlainFile(){
        String s = "home/Francisco/Pedro";
        String[] t = s.split("/");
        ExecuteFileService fl = new ExecuteFileService(MyDriveManager.getInstance().getCurrentSession().getCurrentDir().getFileByName("SufixPath").getPath(),
                t, MyDriveManager.getInstance().getCurrentSession().getToken());
        fl.execute();
    }*/

    // Teste para o caso de o metodo nao existir
    @Test(expected = InvalidContentException.class)
    public void noContetPlainFile(){
        String s = "home/Francisco/Pedro";
        String[] t = s.split("/");
        ExecuteFileService fl = new ExecuteFileService(MyDriveManager.getInstance().getCurrentSession().getCurrentDir().getFileByName("noMethod").getPath(),
                t, MyDriveManager.getInstance().getCurrentSession().getToken());
        fl.execute();
    }


}
