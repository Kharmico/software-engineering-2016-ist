package pt.tecnico.myDrive.service;

import org.junit.Test;
import org.junit.runners.model.MultipleFailureException;
import pt.tecnico.myDrive.domain.Directory;
import pt.tecnico.myDrive.domain.File;
import pt.tecnico.myDrive.domain.MyDriveManager;
import pt.tecnico.myDrive.exception.AccessDeniedException;
import pt.tecnico.myDrive.exception.FileUnknownException;
import pt.tecnico.myDrive.exception.IllegalAddContentException;
import pt.tecnico.myDrive.exception.InvalidContentException;
import pt.tecnico.myDrive.exception.InvalidTokenException;
import pt.tecnico.myDrive.presentation.Sys;

import static org.junit.Assert.assertEquals;


public class WriteFileTest extends AbstractServiceTest {

	@Override
    protected void populate() {
        MyDriveManager mdm = MyDriveManager.getInstance();

        mdm.getFilesystem().addUsers("Josefina");
        mdm.getFilesystem().addUsers("Josefo");

        mdm.login("root","***");

        Directory d = (Directory) mdm.getCurrentSession().getCurrentDir().getFather().getFileByName("Josefina");
        mdm.getCurrentSession().setCurrentDir(d);

        mdm.createPlainFile("IDoWell.txt", "I'm a plain file");
        mdm.createLinkFile("MeToo.txt", "/home/Josefina/IDoWell.txt");

        mdm.login("Josefina", "Josefina");
        mdm.createAppFile("MeThree.txt", "I'mAnAppFile");

    }

    private String getContent(String filename, Directory dir) {
    	File file = dir.getFileByName(filename); 
        return file.printContent(MyDriveManager.getInstance().getCurrentSession().getCurrentUser());
    }

    @Test
    public void successWithFileOwners(){
    	WriteFileService service = 
    			new WriteFileService(MyDriveManager.getInstance().getCurrentSession().getToken(), "MeThree.txt", "I'm.An.APPle.File!");
    	service.execute();
    	
    	assertEquals(getContent("MeThree.txt", MyDriveManager.getInstance().getCurrentSession().getCurrentDir()), "I'm.An.APPle.File!");
    }

    @Test
    public void rootCanWriteAnywhere() {
        final String content = "I am gROOT!";
        MyDriveManager.getInstance().login("root","***");
        WriteFileService service =
        		new WriteFileService(MyDriveManager.getInstance().getCurrentSession().getToken(), "/home/Josefina/IDoWell.txt", content);
        service.execute();

        String cntt = getContent("IDoWell.txt",(Directory) MyDriveManager.getInstance().getCurrentSession().getCurrentDir().getFather().getFileByName("Josefina"));
        assertEquals("Content was not written", content, cntt);
    }

    @Test(expected = FileUnknownException.class)
    public void writeOnNonExistingFile() {
    	final String NonExistingFile = "iNeedException.txt";
    	WriteFileService service = 
    			new WriteFileService(MyDriveManager.getInstance().getCurrentSession().getToken(), NonExistingFile, "I Do not exist :(");
    	service.execute();
    }

    @Test(expected = IllegalAddContentException.class)
    public void writeContentOnLinkFile(){
        String content = "Exception, please";
        MyDriveManager.getInstance().login("root","***");
        WriteFileService service = 
        		new WriteFileService(MyDriveManager.getInstance().getCurrentSession().getToken(), "/home/Josefina/MeToo.txt", content);
    	service.execute();
    }
    
    @Test(expected = AccessDeniedException.class)
    public void noPermissions(){
    	MyDriveManager mdm = MyDriveManager.getInstance();
    	mdm.login("Josefo","Josefo");
    	Directory dir = (Directory) mdm.getFilesystem().getHomeDirectory().getFileByName("Josefina");
    	mdm.getCurrentSession().setCurrentDir(dir);
    	
    	WriteFileService service = 
    			new WriteFileService(MyDriveManager.getInstance().getCurrentSession().getToken(),"MeThree.txt","Exception!!!!");
    	service.execute();
    }

    @Test(expected = InvalidTokenException.class)
    public void invalidToken(){
    	WriteFileService service = new WriteFileService(-1, "IDoWell.txt", "lol");
    	service.execute();
    }
    
    @Test(expected = InvalidContentException.class)
    public void invalidContentAppFile() {
    	WriteFileService service =
    			new WriteFileService(MyDriveManager.getInstance().getCurrentSession().getToken(),"MeThree.txt", "I am gRoot!");
    	service.execute();
    }

}
