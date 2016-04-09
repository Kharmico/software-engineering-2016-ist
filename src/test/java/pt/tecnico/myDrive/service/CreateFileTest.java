package pt.tecnico.myDrive.service;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pt.tecnico.myDrive.domain.Directory;
import pt.tecnico.myDrive.domain.File;
import pt.tecnico.myDrive.domain.MyDriveManager;
import pt.tecnico.myDrive.domain.Session;
import pt.tecnico.myDrive.exception.*;

public class CreateFileTest extends AbstractServiceTest {
    private static final Logger log = LogManager.getRootLogger();

    private static final String USER_LOGGED = "pikachu";

    @Override
    protected void populate(){
        //log.trace(this.getClass().getSimpleName() + ": Getting mydrive");
        MyDriveManager mg = MyDriveManager.getInstance();
        //log.trace(this.getClass().getSimpleName() + ": Add user " + USER_LOGGED);
        mg.addUser(USER_LOGGED);
        //log.trace(this.getClass().getSimpleName() + ": Logging in " + USER_LOGGED);
        mg.login("root", "***");
        Directory d = (Directory) mg.getFilesystem().getHomeDirectory().getFileByName("pikachu");
        mg.getCurrentSession().setCurrentDir(d);
        mg.createDirectory("gary");

        // Setting gary directory mask to block reading writing from another user
        ((Directory) mg.getFilesystem().getHomeDirectory().getFileByName("pikachu"))
                .getFileByName("gary").setPermissions("rwxd----");

        mg.login("pikachu","pikachu");

        mg.createPlainFile("ash.txt");

    }

    private File getFile(String filename) {
        Directory dir = MyDriveManager.getInstance().getCurrentSession().getCurrentDir();
        return dir.getFileByName(filename);
    }

    private Directory getDirectory(String filename) {
        Directory dir = MyDriveManager.getInstance().getCurrentSession().getCurrentDir();
        return (Directory) dir.getFileByName(filename); // We know that that filename is a diretory because we created;
    }

    @Test
    public void successFile(){
        //log.trace(this.getClass().getSimpleName() + ": Calling service");
        String filename = "seismic_toss.txt";
        CreateFileService service =
            new CreateFileService(MyDriveManager.getInstance().getCurrentSession().getToken(), filename, "plain");
       // log.trace(this.getClass().getSimpleName() + ": Running service");
        service.execute();
        //log.trace(this.getClass().getSimpleName() + ": Trying to get file");
        File file = getFile(filename);
        assertNotNull("File was not created", file);
        assertEquals("Invalid filename", filename, file.getFilename());
    }

    @Test
    public void successFileWithContent(){
        String filename = "thundershock.txt";
        CreateFileService service =
                new CreateFileService(MyDriveManager.getInstance().getCurrentSession().getToken(), filename,
                        "plain", "PIKACHUUUUU");
        service.execute();
        File file = getFile(filename);
        assertNotNull("File was not created", file);
        assertEquals("Invalid content", "PIKACHUUUUU", file.printContent());
    }

    @Test
    public void successAppFileWithContent(){
        String filename = "quick_attack.exe";
        String content = "Pokedex.Pikachu.beCute";
        CreateFileService service =
                new CreateFileService(MyDriveManager.getInstance().getCurrentSession().getToken(), filename,
                        "app", content);
        service.execute();
        File file = getFile(filename);
        assertNotNull("File was not created", file);
        assertEquals("Invalid content", content, file.printContent());
    }

    // This test is useless, since if there is a / in a filename, it will be consider as a part of a relative path
    /*@Test(expected = InvalidFileNameException.class)
    public void invalidFileName(){
        CreateFileService service =
                new CreateFileService(MyDriveManager.getInstance().getCurrentSession().getToken(), "thunder/wave.txt", "plain");
        service.execute();

    }*/

    @Test(expected = PathIsTooBigException.class)
    public void fileWithBigPath(){
        CreateFileService service =
                new CreateFileService(MyDriveManager.getInstance().getCurrentSession().getToken(), new String(new char[1024]).replace('\0', '€'), "plain");
        service.execute();
    }

    @Test(expected = LinkFileWithoutContentException.class)
    public void linkWithoutContent(){
        CreateFileService service =
                new CreateFileService(MyDriveManager.getInstance().getCurrentSession().getToken(), "thunder.txt", "link");
        service.execute();
    }

    /*@Test(expected = AccessDeniedException.class)
    public void createFileInADirectoryWithoutPermission(){
        MyDriveManager mg = MyDriveManager.getInstance();
        Directory d = (Directory) mg.getCurrentSession().getCurrentDir().getFileByName("gary");
        mg.getCurrentSession().setCurrentDir(d);
        CreateFileService service =
                new CreateFileService(MyDriveManager.getInstance().getCurrentSession().getToken(), "megapunch.txt", "plain");
        service.execute();
    }*/

    @Test(expected = FileAlreadyExistsException.class)
    public void sameFileTwice(){
        CreateFileService service =
                new CreateFileService(MyDriveManager.getInstance().getCurrentSession().getToken(), "ash.txt", "plain");
        service.execute();
    }

    @Test
    public void createDirectory(){
        CreateFileService service =
                new CreateFileService(MyDriveManager.getInstance().getCurrentSession().getToken(), "pokedex-entry",
                        "directory");
        service.execute();
        Directory directory = getDirectory("pokedex-entry");
        assertNotNull("Directory was not created", directory);
        assertEquals("Invalid filename",
                "pokedex-entry", directory.getFilename());
    }



    @Test(expected = IsNotPlainFileException.class)
    public void createDirectoryWithContent(){
        CreateFileService service =
                new CreateFileService(MyDriveManager.getInstance().getCurrentSession().getToken(), "pokedex-entry",
                        "directory", "#025\nElectric\nMouse Pokemon");
        service.execute();
    }

        /*
    @Test(expected = InvalidTokenException.class)
    public void createFileWithInvalidUserToken(){
        CreateFileService service =
                new CreateFileService(-1, "pokedex-entry",
                        "directory", "#025\nElectric\nMouse Pokemon");
        service.execute();
    }

    */

    /* Test Cases */
    /* 1 - Create a plain file in the current directory that the user has permission to write on it */
    /* 2 - Create a plain file in the current directory with an invalid filename ex: ola/adues */
    /* !!!!!3 - Create a plain file in the current directory that exists and I have permission to enter on it,
     *   but I don't have permission to write on it ||CAN I TEST DIS?|| I cant do this, i would need to change directory,
      *   and I can't test another service here   */
    /* 4 - Create a plain file with a filename bigger than 1024 characters */
    /* 5 - Create a link file without any content */
    /* 6 - Create a directory in the current directory that the user has permission to write on it */
    /* 7 - Create a directory with content in the current directory that the user has permission to write on it */
}