package pt.tecnico.myDrive.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import pt.tecnico.myDrive.domain.Directory;
import pt.tecnico.myDrive.domain.File;
import pt.tecnico.myDrive.domain.MyDriveManager;
import pt.tecnico.myDrive.domain.Session;
import pt.tecnico.myDrive.exception.AccessDeniedException;
import pt.tecnico.myDrive.exception.FileUnknownException;
import pt.tecnico.myDrive.exception.InvalidTokenException;

import static org.junit.Assert.assertEquals;


public class DeleteFileTest extends AbstractServiceTest{

    private static final Logger log = LogManager.getRootLogger();

    protected void populate() {
        MyDriveManager manager = MyDriveManager.getInstance();
        manager.addUser("Joao");
        manager.addUser("Pedro");
        manager.login("Joao","Joao");

        manager.createDirectory("teste");
        manager.createPlainFile("sufix.txt","Engenharia dos sistemas de info");
        manager.getCurrentSession().setCurrentDir((Directory)manager.getCurrentSession().getCurrentDir().getFileByName("teste"));
        manager.createDirectory("sistemas");
        manager.createPlainFile("readme.txt","Engenharia de Software");

        manager.getCurrentSession().setCurrentDir(manager.getCurrentSession().getCurrentDir().getFather());

        manager.createDirectory("perm");
        manager.createDirectory("estrelas");

        manager.login("Pedro","Pedro");
        manager.createDirectory("noperm");

        manager.login("Joao","Joao");
    }


    private String getDirectoryFilesNames(Directory dir){
        String output = "";
        for(File f : dir.getFilesSet())
            output += f.getFilename() + " ";
        return dir.getFilesSet().size() == 0 ? output : output.substring(0,output.lastIndexOf(" "));
    }

    @Test
    public void sucess(){
        DeleteFileService service = new  DeleteFileService(MyDriveManager.getInstance().getCurrentSession().getToken(),"sufix.txt");
        service.execute();
        assertEquals("Remove file with permissions","perm estrelas teste",getDirectoryFilesNames(MyDriveManager.getInstance().getCurrentSession().getCurrentDir()));
    }

   @Test(expected = FileUnknownException.class)
    public void deleteUnknownFile(){
        DeleteFileService service = new  DeleteFileService(MyDriveManager.getInstance().getCurrentSession().getToken(),"inexistente");
        service.execute();
    }

    @Test(expected = AccessDeniedException.class)
    public void deleteFileWithoutPermissions(){
        DeleteFileService service = new  DeleteFileService(MyDriveManager.getInstance().getCurrentSession().getToken(),"/home/Pedro/noperm");
        service.execute();
    }


    @Test
    public void deleteAsRoot(){
        DeleteFileService service = new DeleteFileService(MyDriveManager.getInstance().login("root","***"),"/home/Pedro/noperm");
        service.execute();
        assertEquals("Remove file as root","",getDirectoryFilesNames((Directory)MyDriveManager.getInstance().getCurrentSession().getCurrentDir().getFather().getFileByName("Pedro")));
    }


    @Test
    public void deleteDirectoryWithContent() {
        DeleteFileService service = new DeleteFileService(MyDriveManager.getInstance().getCurrentSession().getToken(), "teste");
        service.execute();
        assertEquals("Remove directory with content","perm estrelas sufix.txt",getDirectoryFilesNames(MyDriveManager.getInstance().getCurrentSession().getCurrentDir()));
    }


    @Test
    public void deleteDirectoryWithoutContent() {
        DeleteFileService service = new  DeleteFileService(MyDriveManager.getInstance().getCurrentSession().getToken(),"perm");
        service.execute();
        assertEquals("Remove directory with no content","estrelas sufix.txt teste",getDirectoryFilesNames(MyDriveManager.getInstance().getCurrentSession().getCurrentDir()));
    }

    @Test(expected = InvalidTokenException.class)
    public void invalidTokenDeleteFile(){
         DeleteFileService service = new  DeleteFileService(-1,"sufix.txt");
         service.execute();
     }

    /* Test Cases */
    /* 1 - Delete a file that does exist and I have permission */
    /* 2 - Delete a file that does not exist */
    /* 3 - Delete a file that does exist but I don't have permission */
    /* 4 - Delete a file as root */
    /* 5 - Delete a directory with content */
    /* 6 - Delete a directory without content */
    /* 7 - Delete a file with wrong token */


}
