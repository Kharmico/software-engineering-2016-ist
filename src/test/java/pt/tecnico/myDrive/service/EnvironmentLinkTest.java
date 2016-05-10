package pt.tecnico.myDrive.service;


import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import pt.tecnico.myDrive.domain.Directory;
import pt.tecnico.myDrive.domain.LinkFile;
import pt.tecnico.myDrive.domain.MyDriveManager;
import pt.tecnico.myDrive.domain.User;
import pt.tecnico.myDrive.exception.AccessDeniedException;
import pt.tecnico.myDrive.exception.FileUnknownException;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertEquals;

@RunWith(JMockit.class)
public class EnvironmentLinkTest extends AbstractServiceTest {

    @Injectable
    private LinkFile fl;
    @Injectable
    private LinkFile file;
    @Injectable
    private LinkFile f2;
    @Injectable
    private  LinkFile f3;

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private PrintStream ps;

    @Override
    public void populate() {
        MyDriveManager manager = MyDriveManager.getInstance();
        manager.addUser("Francisco");
        manager.addUser("Alberto95");

        manager.login("Alberto95","Alberto95");
        manager.addEnvironmentVariable("Cenoura", "Exame", manager.getCurrentSession().getToken());
        manager.createDirectory("Exame");
        manager.getCurrentSession().setCurrentDir((Directory)manager.getCurrentSession().getCurrentDir().getFileByName("Exame"));
        manager.createPlainFile("prefix.txt","Engenharia dos sistemas");
        manager.createLinkFile("PrimeiroExame", "/home/Alberto95/$Cenoura/prefix.txt");
        file = (LinkFile) MyDriveManager.getInstance().getCurrentSession().getCurrentDir().getFileByName("PrimeiroExame");


        manager.login("Francisco","Francisco");
        manager.addEnvironmentVariable("Batata", "teste", manager.getCurrentSession().getToken());


        manager.createDirectory("teste");
        manager.getCurrentSession().setCurrentDir((Directory)manager.getCurrentSession().getCurrentDir().getFileByName("teste"));
        manager.createAppFile("App", "pt.tecnico.myDrive.MyDriveApplication.testAppfile");
        manager.createDirectory("TestesIntermedios");
        manager.createPlainFile("sufix.txt","Engenharia dos sistemas de info");
        manager.getCurrentSession().setCurrentDir(manager.getCurrentSession().getCurrentDir().getFather());
        manager.createLinkFile("PrimeiroTeste", "/home/Francisco/$Batata/sufix.txt");
        manager.createLinkFile("AppTest", "/home/Francisco/$Batata/App");
        manager.createLinkFile("Directorios", "/home/Francisco/$Batata");
        manager.createLinkFile("SegundoTeste", "/home/Francisco/$Alface/sufix.txt");
        fl = (LinkFile) MyDriveManager.getInstance().getCurrentSession().getCurrentDir().getFileByName("PrimeiroTeste");
        f2 = (LinkFile) MyDriveManager.getInstance().getCurrentSession().getCurrentDir().getFileByName("Directorios");
        f3 = (LinkFile) MyDriveManager.getInstance().getCurrentSession().getCurrentDir().getFileByName("AppTest");
    }

    private User getUser(){

        return MyDriveManager.getInstance().getCurrentSession().getCurrentUser();
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


    @Test
    public void PrintSucess(){
        new Expectations(LinkFile.class) {
            {
                fl.getContent();
                result = "/home/Francisco/teste/sufix.txt";
            }
        };
        assertEquals("Wrong content","Engenharia dos sistemas de info",fl.printContent(getUser()));
    }

    @Test
    public void ChangeDir(){
        new Expectations(LinkFile.class) {
            {
                f2.getContent();
                result = "/home/Francisco/teste";
            }
        };
        MyDriveManager.getInstance().getCurrentSession().setCurrentDir(
                MyDriveManager.getInstance().getFilesystem().getLastDirectory(f2.getContent(),
                        MyDriveManager.getInstance().getCurrentSession().getCurrentDir(),
                MyDriveManager.getInstance().getCurrentSession().getCurrentUser()));
        assertEquals("Wrong content", "teste", MyDriveManager.getInstance().getCurrentSession().getCurrentDir().getFilename());
    }

    @Test(expected = FileUnknownException.class)
    public void ChangeDirWrongEnv(){
         MyDriveManager.getInstance().getCurrentSession().setCurrentDir(MyDriveManager.getInstance().getFilesystem().getLastDirectory(f2.getContent(),
                 MyDriveManager.getInstance().getCurrentSession().getCurrentDir(),MyDriveManager.getInstance().getCurrentSession().getCurrentUser()));
    }

    //Directory Doesnt Exist
    @Test(expected = FileUnknownException.class)
    public void wrongPath(){
        new Expectations(LinkFile.class) {
            {
                fl.getContent();
                result = "/home/Alberto95/teste/sufix.txt";
            }
        };
        fl.printContent(getUser());
    }

    //Directory Doesnt Exist
    @Test(expected = FileUnknownException.class)
    public void nonExistingVarPath(){
        fl.printContent(getUser());
    }

    //Doenst have permission Exist
    @Test(expected = AccessDeniedException.class)
    public void permissionDeniedExist(){
        new Expectations(LinkFile.class) {
            {
                file.getContent();
                result = "/home/Alberto95/Exame/prefix.txt";
            }
        };
        file.printContent(getUser());
    }

    @Test
    public void SucesLinkFile() {
        new Expectations(LinkFile.class) {
            {
                f3.getContent();
                result = "/home/Francisco/teste/App";
            }
        };
        String[] t = {"cd","ls"};
        ExecuteFileService fl = new ExecuteFileService(MyDriveManager.getInstance().getCurrentSession().getCurrentDir().getFileByName("AppTest").getPath(),
                t, MyDriveManager.getInstance().getCurrentSession().getToken());
        fl.execute();

        String[] output = outContent.toString().split("\n");

        assertEquals("App file isnt working", "AppFileRunning", output[1]);
    }

    @Test(expected = FileUnknownException.class)
    public void NoVarAddLinkFile() {
        new Expectations(LinkFile.class) {
            {
                f3.getContent();
                result = "/home/Francisco/$Peixe/App";
            }
        };
        String[] t = {"cd","ls"};
        ExecuteFileService fl = new ExecuteFileService(MyDriveManager.getInstance().getCurrentSession().getCurrentDir().getFileByName("AppTest").getPath(),
                t, MyDriveManager.getInstance().getCurrentSession().getToken());
        fl.execute();
    }

    @Test(expected = FileUnknownException.class)
    public void wrongDirLinkFile() {
        new Expectations(LinkFile.class) {
            {
                f3.getContent();
                result = "/home/Francisco/Alface/App";
            }
        };
        String[] t = {"cd","ls"};
        ExecuteFileService fl = new ExecuteFileService(MyDriveManager.getInstance().getCurrentSession().getCurrentDir().getFileByName("AppTest").getPath(),
                t, MyDriveManager.getInstance().getCurrentSession().getToken());
        fl.execute();
    }


}
