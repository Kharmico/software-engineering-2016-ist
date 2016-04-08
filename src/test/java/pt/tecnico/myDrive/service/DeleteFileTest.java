package pt.tecnico.myDrive.service;

import static org.junit.Assert.assertNotSame;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import pt.tecnico.myDrive.domain.MyDriveManager;
import pt.tecnico.myDrive.domain.Session;
import pt.tecnico.myDrive.exception.FileUnknownException;


public class DeleteFileTest extends AbstractServiceTest{

    private static final Logger log = LogManager.getRootLogger();

    protected void populate() {
        MyDriveManager manager = MyDriveManager.getInstance();
        manager.addUser("Joao");
        manager.login("Joao","Joao");
        Session currentSession = manager.getCurrentSession();
        manager.createDirectory("teste");
        manager.createPlainFile("Exame","Isto e apenas um teste");
    }

    /*
   @Test(expected = FileUnknownException.class)
    public void success(){
        long token;
        DeleteFileService service = new  DeleteFileService(MyDriveManager.getInstance().getCurrentSession().getToken(),"Exame");
        service.execute();
        token = service.result();
    }

    @Test(expected = FileUnknownException.class)
    public void nonExistingFile(){
        DeleteFileService service =
                new DeleteFileService(MyDriveManager.getInstance().getCurrentSession().getToken(),"ProvaIntermedia");
        service.execute();
    }

    @Test(expected = FileUnknownException.class)
    public void nonExistingFile(){
        DeleteFileService service =
                new DeleteFileService(MyDriveManager.getInstance().getCurrentSession().getToken(),"teste");
        service.execute();
    }*/

    /* Test Cases */
    /* 1 - Delete inexistente Directory*/
    /* 2 - Delete Directory as another User with out beeing root*/
    /* 3 - Delete inexistente File*/
    /* 4 - Delete File as another User with out beeing root*/

}
