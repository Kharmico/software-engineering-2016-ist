package pt.tecnico.myDrive.service;

import org.junit.Test;
import pt.tecnico.myDrive.domain.Directory;
import pt.tecnico.myDrive.domain.File;
import pt.tecnico.myDrive.domain.MyDriveManager;
import pt.tecnico.myDrive.exception.AccessDeniedException;
import pt.tecnico.myDrive.exception.FileUnknownException;
import pt.tecnico.myDrive.exception.InvalidTokenException;

import static org.junit.Assert.assertEquals;


public class DeleteFileTest extends AbstractServiceTest{

    protected void populate() {
        MyDriveManager manager = MyDriveManager.getInstance();
        manager.addUser("Francisco");
        manager.addUser("Ferreira");
        manager.login("Francisco","Francisco");

        manager.createDirectory("teste");
        manager.createPlainFile("sufix.txt","Engenharia dos sistemas de info");
        manager.getCurrentSession().setCurrentDir((Directory)manager.getCurrentSession().getCurrentDir().getFileByName("teste"));
        manager.createDirectory("sistemas");
        manager.createPlainFile("readme.txt","Engenharia de Software");

        manager.getCurrentSession().setCurrentDir(manager.getCurrentSession().getCurrentDir().getFather());

        manager.createDirectory("perm");
        manager.createDirectory("estrelas");

        manager.login("Ferreira","Ferreira");
        manager.createDirectory("noperm");

        manager.login("Francisco","Francisco");
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
        DeleteFileService service = new  DeleteFileService(MyDriveManager.getInstance().getCurrentSession().getToken(),"/home/Ferreira/noperm");
        service.execute();
    }

    @Test
    public void deleteAsRoot(){
        DeleteFileService service = new DeleteFileService(MyDriveManager.getInstance().login("root","***"),"/home/Ferreira/noperm");
        service.execute();
        assertEquals("Remove file as root","",getDirectoryFilesNames((Directory)MyDriveManager.getInstance().getCurrentSession().getCurrentDir().getFather().getFileByName("Ferreira")));
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

}
