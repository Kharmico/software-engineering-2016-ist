package pt.tecnico.myDrive.service;

import org.junit.Test;
import pt.tecnico.myDrive.domain.Directory;
import pt.tecnico.myDrive.domain.File;
import pt.tecnico.myDrive.domain.Guest;
import pt.tecnico.myDrive.domain.MyDriveManager;
import pt.tecnico.myDrive.exception.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class CreateFileTest extends AbstractServiceTest {

    private static final String USER_LOGGED = "Charizard";

    @Override
    protected void populate(){
        MyDriveManager mg = MyDriveManager.getInstance();
        mg.addUser(USER_LOGGED);
        mg.login("root", "***");
        Directory d = (Directory) mg.getFilesystem().getHomeDirectory().getFileByName("Charizard");
        mg.getCurrentSession().setCurrentDir(d);
        mg.createDirectory("gary");

        // Setting gary directory mask to block reading writing from another user
        ((Directory) mg.getFilesystem().getHomeDirectory().getFileByName("Charizard"))
                .getFileByName("gary").setPermissions("rwxd----");

        mg.login("Charizard","Charizard");

        mg.createPlainFile("ash.txt");

    }

    private File getFile(String filename) {
        Directory dir = MyDriveManager.getInstance().getCurrentSession().getCurrentDir();
        return dir.getFileByName(filename);
    }

    private Directory getDirectory(String filename) {
        Directory dir = MyDriveManager.getInstance().getCurrentSession().getCurrentDir();
        return (Directory) dir.getFileByName(filename); // We know that that filename is a diretory because we created it
    }

    @Test
    public void successFile(){
        String filename = "seismic_toss.txt";
        CreateFileService service =
            new CreateFileService(MyDriveManager.getInstance().getCurrentSession().getToken(), filename, "plain");
        service.execute();
        File file = getFile(filename);
        assertNotNull("File was not created", file);
        assertEquals("Invalid filename", filename, file.getFilename());
    }

    @Test
    public void successFileWithContent(){
        String filename = "thundershock.txt";
        CreateFileService service =
                new CreateFileService(MyDriveManager.getInstance().getCurrentSession().getToken(), filename,
                        "plain", "CharizardUUUU");
        service.execute();
        File file = getFile(filename);
        assertNotNull("File was not created", file);
        assertEquals("Invalid content", "CharizardUUUU",
                file.printContent(MyDriveManager.getInstance().getCurrentSession().getCurrentUser()));
    }

    @Test
    public void successAppFileWithContent(){
        String filename = "quick_attack.exe";
        String content = "Pokedex.Charizard.beCute";
        CreateFileService service =
                new CreateFileService(MyDriveManager.getInstance().getCurrentSession().getToken(), filename,
                        "app", content);
        service.execute();
        File file = getFile(filename);
        assertNotNull("File was not created", file);
        assertEquals("Invalid content", content,
                file.printContent(MyDriveManager.getInstance().getCurrentSession().getCurrentUser()));
    }

    @Test
    public void successLinkWithContent(){
        CreateFileService service =
                new CreateFileService(MyDriveManager.getInstance().getCurrentSession().getToken(), "trainer.link", "link"
                , "/home/Charizard/ash.txt");
        service.execute();
    }

    @Test
    public void successFileByGuest(){
        MyDriveManager.getInstance().login("nobody","");
        MyDriveManager.getInstance().getCurrentSession().setCurrentDir((Directory)MyDriveManager.getInstance().getFilesystem().getHomeDirectory().getFileByName("nobody"));
        String filename = "GameShark.txt";
        CreateFileService service =
                new CreateFileService(MyDriveManager.getInstance().getCurrentSession().getToken(), filename,
                        "plain", "Infinite MasterBalls: 958D8046A7151D708BB602F78CEB681A");
        service.execute();
    }

    @Test(expected = PathIsTooBigException.class)
    public void fileWithBigPath(){
        CreateFileService service =
                new CreateFileService(MyDriveManager.getInstance().getCurrentSession().getToken(),
                        new String(new char[1024]).replace('\0', '€'), "plain");
        service.execute();
    }

    @Test(expected = LinkFileWithoutContentException.class)
    public void linkWithoutContent(){
        CreateFileService service =
                new CreateFileService(MyDriveManager.getInstance().getCurrentSession().getToken(), "thunder.txt", "link");
        service.execute();
    }

    @Test(expected = LoopLinkFileException.class)
    public void linkWithLoop(){
        CreateFileService service =
                new CreateFileService(MyDriveManager.getInstance().getCurrentSession().getToken(), "yellow.link",
                        "link", "/home/Charizard/yellow.link");
        service.execute();
    }

    @Test(expected = AccessDeniedException.class)
    public void createFileInADirectoryWithoutPermission(){
        MyDriveManager mg = MyDriveManager.getInstance();
        Directory d = (Directory) mg.getCurrentSession().getCurrentDir().getFileByName("gary");
        mg.getCurrentSession().setCurrentDir(d);
        CreateFileService service =
                new CreateFileService(MyDriveManager.getInstance().getCurrentSession().getToken(), "megapunch.txt", "plain");
        service.execute();
    }

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

    @Test(expected = UnknownTypeException.class)
    public void createWrongFileType(){
        CreateFileService service =
                new CreateFileService(MyDriveManager.getInstance().getCurrentSession().getToken(), "pokedex-entry",
                        "pokemon", "#025\nElectric\nMouse Pokemon");
        service.execute();
    }

    @Test(expected = InvalidTokenException.class)
    public void createFileWithInvalidUserToken(){
        CreateFileService service =
                new CreateFileService(-1, "pokedex-entry",
                        "directory", "#025\nElectric\nMouse Pokemon");
        service.execute();
    }

    @Test(expected = FileAlreadyExistsException.class)
    public void createFileThatAlreadyExists(){
        CreateFileService service =
                new CreateFileService(MyDriveManager.getInstance().getCurrentSession().getToken(), "ash.txt",
                        "plain");
        service.execute();
    }

}
