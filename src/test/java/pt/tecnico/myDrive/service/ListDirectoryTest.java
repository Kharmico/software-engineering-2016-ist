package pt.tecnico.myDrive.service;

import org.junit.Test;
import pt.tecnico.myDrive.domain.Directory;
import pt.tecnico.myDrive.domain.File;
import pt.tecnico.myDrive.domain.MyDriveManager;
import pt.tecnico.myDrive.domain.Session;
import pt.tecnico.myDrive.exception.InvalidTokenException;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertEquals;


public class ListDirectoryTest extends AbstractServiceTest {

    private long token;
    private String expected;
    @Override
    protected void populate() {
        MyDriveManager mg = MyDriveManager.getInstance();
        mg.login("root","***");
        Session currentSession = mg.getCurrentSession();

        currentSession.setCurrentDir(mg.getFilesystem().getHomeDirectory());
        mg.createDirectory("DeathStar");
        Directory deathStar = (Directory) mg.getFilesystem().getHomeDirectory().getFileByName("DeathStar");
        currentSession.setCurrentDir((Directory)mg.getFilesystem().getHomeDirectory().getFileByName("DeathStar"));

        mg.createPlainFile("DarthVader.txt","The is strong in this one");
        mg.createPlainFile("Emperor.txt","Good, good, let the hate flow thrugh you");
        mg.createDirectory("TieFighters");
        mg.createDirectory("Stormtroopers");

        Directory tieFighters = (Directory) currentSession.getCurrentDir().getFileByName("TieFighters");
        Directory stormtroopers = (Directory) currentSession.getCurrentDir().getFileByName("Stormtroopers");
        File darthVader =  currentSession.getCurrentDir().getFileByName("DarthVader.txt");
        File emperor = currentSession.getCurrentDir().getFileByName("Emperor.txt");


        expected = "rwxdr-x- root " + deathStar.getLastModified() + " .\n" +
                deathStar.getFatherLs() + "\n" +
                "rwxdr-x- root " + emperor.getLastModified() + " Emperor.txt\n" +
                "rwxdr-x- root " + tieFighters.getLastModified() + " TieFighters\n" +
                "rwxdr-x- root " + stormtroopers.getLastModified() + " Stormtroopers\n" +
                "rwxdr-x- root " + darthVader.getLastModified() + " DarthVader.txt\n";

        token = mg.getInstance().getCurrentSession().getToken();
    }


    @Test
    public void successLogin(){
        ListDirectoryService service = new ListDirectoryService(token);
        service.execute();
        String out = service.result();
        assertNotNull("Nothing listed", out);
        assertEquals("Wrong output form", expected, out);
    }

    @Test(expected = InvalidTokenException.class)
    public void invalidToken(){
        ListDirectoryService service = new ListDirectoryService(token+1);
        service.execute();
    }

}