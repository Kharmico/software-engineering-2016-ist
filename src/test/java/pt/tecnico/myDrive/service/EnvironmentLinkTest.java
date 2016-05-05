package pt.tecnico.myDrive.service;


import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import org.junit.Test;
import org.junit.runner.RunWith;
import pt.tecnico.myDrive.domain.Directory;
import pt.tecnico.myDrive.domain.LinkFile;
import pt.tecnico.myDrive.domain.MyDriveManager;
import pt.tecnico.myDrive.domain.User;
import pt.tecnico.myDrive.exception.AccessDeniedException;
import pt.tecnico.myDrive.exception.FileUnknownException;
import sun.awt.image.ImageWatched;

import static org.junit.Assert.assertEquals;

@RunWith(JMockit.class)
public class EnvironmentLinkTest extends AbstractServiceTest {

    @Injectable
    private LinkFile fl;
    private LinkFile file;

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
        manager.createPlainFile("sufix.txt","Engenharia dos sistemas de info");
        manager.getCurrentSession().setCurrentDir(manager.getCurrentSession().getCurrentDir().getFather());
        manager.createLinkFile("PrimeiroTeste", "/home/Francisco/$Batata/sufix.txt");
        manager.createLinkFile("SegundoTeste", "/home/Francisco/$Alface/sufix.txt");
        fl = (LinkFile) MyDriveManager.getInstance().getCurrentSession().getCurrentDir().getFileByName("PrimeiroTeste");
    }

    private User getUser(){

        return MyDriveManager.getInstance().getCurrentSession().getCurrentUser();
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



}