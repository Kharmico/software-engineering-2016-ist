package pt.tecnico.myDrive.integration;

import org.junit.Test;
import org.junit.runner.RunWith;
import mockit.Mock;
import mockit.MockUp;
import mockit.integration.junit4.JMockit;
import pt.tecnico.myDrive.domain.EnvironmentVariable;
import pt.tecnico.myDrive.domain.MyDriveManager;
import pt.tecnico.myDrive.service.*;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(JMockit.class)
public class IntegrationTest extends AbstractServiceTest{

    @Override
    protected void populate() {
        MyDriveManager mg = MyDriveManager.getInstance();
        mg.addUser("Ricardino");
    }

    @Test
    public void sucess() throws Exception{
        LoginUserService lus = new LoginUserService("Ricardino","Ricardino");
        lus.dispatch();
        assertTrue(MyDriveManager.getInstance().getCurrentSession().getToken() == lus.result());

        new CreateFileService(lus.result(),"Nova Pasta","directory").dispatch();

        ChangeDirectoryService cd = new ChangeDirectoryService(lus.result(),"Nova Pasta");
        cd.dispatch();
        assertEquals("/home/Ricardino/Nova Pasta",cd.result());

        new CreateFileService(lus.result(),"AMS","directory","").dispatch();
        new CreateFileService(lus.result(),"Nova App","app","pt.tecnico.myDrive.domain.Directory.getPath()").dispatch();
        new CreateFileService(lus.result(),"Nova App 2","app","pt.tecnico.myDrive.domain.Directory").dispatch();
        new CreateFileService(lus.result(),"Novo Link","link","Nova App").dispatch();
        new CreateFileService(lus.result(),"Novo Plain","plain","IST").dispatch();

        ListDirectoryService ls = new ListDirectoryService(lus.result());
        ls.dispatch();
        String[] lsresult = parseLs(ls.result());
        assertTrue(lsresult.length == 7);
        assertEquals(lsresult[0],"rwxd---- Ricardino .");
        assertEquals(lsresult[1],"rwxd---- Ricardino ..");
        assertEquals(lsresult[2],"rwxd---- Ricardino Novo Plain");
        assertEquals(lsresult[3],"rwxd---- Ricardino Novo Link -> Nova App");
        assertEquals(lsresult[4],"rwxd---- Ricardino Nova App 2");
        assertEquals(lsresult[5],"rwxd---- Ricardino Nova App");
        assertEquals(lsresult[6],"rwxd---- Ricardino AMS");

        new WriteFileService(lus.result(),"Nova App","pt.tecnico.myDrive.domain.Directory.getFilename()").dispatch();

        ReadFileService rs = new ReadFileService(lus.result(),"Nova App");
        rs.dispatch();
        assertEquals("pt.tecnico.myDrive.domain.Directory.getFilename()",rs.result());

        new WriteFileService(lus.result(),"Novo Plain","IST ALAMEDA").dispatch();

        rs = new ReadFileService(lus.result(),"Novo Plain");
        rs.dispatch();
        assertEquals("IST ALAMEDA",rs.result());

        rs = new ReadFileService(lus.result(),"Novo Link");
        rs.dispatch();
        assertEquals("pt.tecnico.myDrive.domain.Directory.getFilename()",rs.result());


        cd = new ChangeDirectoryService(lus.result(),"/home/Ricardino");
        cd.dispatch();
        assertEquals("/home/Ricardino",cd.result());

        new DeleteFileService(lus.result(),"/home/Ricardino/Nova Pasta").dispatch();

        ls = new ListDirectoryService(lus.result());
        ls.dispatch();
        lsresult = parseLs(ls.result());
        assertTrue(lsresult.length == 2);
        assertEquals(lsresult[0],"rwxd---- Ricardino .");
        assertEquals(lsresult[1],"rwxdr-x- root ..");

        lus = new LoginUserService("nobody","");
        lus.dispatch();
        assertTrue(MyDriveManager.getInstance().getCurrentSession().getToken() == lus.result());

        lus = new LoginUserService("root","***");
        lus.dispatch();
        assertTrue(MyDriveManager.getInstance().getCurrentSession().getToken() == lus.result());

        // TO TEST EXECUTE APP
        /*new CreateFileService(lus.result(),"Tryme","app","pt.tecnico.myDrive.MyDriveApplication.test()").dispatch();

        ExecuteFileService es =  new ExecuteFileService(lus.result(),"Tryme");
        es.dispatch();
        assertEquals("tested",es.result());*/

        /*
        new MockUp<ExecuteFileService>() {
            @Mock
            void dispatch() {}
            String result() { return "pt.tecnico.myDrive.domain.TxtFile.execute(coisa)"; }
        };
        ExecuteFileService es = new ExecuteFileService(lus.result(),"/home/root/readme.txt");
        es.dispatch();
        */

        new AddEnvironmentVariableService(lus.result(),"$HOME","/home").dispatch();
        AddEnvironmentVariableService ev = new AddEnvironmentVariableService(lus.result(),"$ROOT","/home/root");
        ev.dispatch();
        assertTrue(MyDriveManager.getInstance().getCurrentSession().getVarSet().size() == 2);
        assertEquals("/home",ev.result().get("$HOME"));
        assertEquals("/home/root",ev.result().get("$ROOT"));


        cd = new ChangeDirectoryService(lus.result(),".");
        cd.dispatch();
        assertEquals("/home/root",cd.result());

        cd = new ChangeDirectoryService(lus.result(),"..");
        cd.dispatch();
        assertEquals("/home",cd.result());

        cd = new ChangeDirectoryService(lus.result(),"/");
        cd.dispatch();
        assertEquals("/",cd.result());

        ls = new ListDirectoryService(lus.result());
        ls.dispatch();
        lsresult = parseLs(ls.result());
        assertTrue(lsresult.length == 3);
        assertEquals(lsresult[0],"rwxdr-x- root .");
        assertEquals(lsresult[1],"rwxdr-x- root ..");
        assertEquals(lsresult[2],"rwxdr-x- root home");



    }

    public String[] parseLs(String result){
        String[] tokens = result.split("\\n");
        String[] output = new String[tokens.length];
        // 9 it's the permissions + space
        // 31 it's the date + space
        for(int i = 0; i < tokens.length; i++)
            output[i] = tokens[i].substring(0, 9 + tokens[i].split(" ")[1].length()) + " " + tokens[i].substring(9 + tokens[i].split(" ")[1].length() + 31);
        return output;
    }

    /* CD - Absolutos e relativos, . .. /
       Login - User, root e guest
       CreateFile - app, dir, link, plain
       DeleteFile - app, dir, link, plain -> corresponde a tentar apagar uma directoria completa com todos estes tipos
       ListDirectory - ver todos os tipos de ficheiros
       ReadFile - plainfile && app && link
       WriteFile - plainfile && app
       AddEnvVar - apenas com duas vars de ambiente...

       Falta ver:
       Execute Service - app
       Execute Service e AddEnvVar - mocks são meus?
     */



}
