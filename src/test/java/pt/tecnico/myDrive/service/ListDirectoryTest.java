package pt.tecnico.myDrive.service;

import org.junit.Test;
import pt.tecnico.myDrive.domain.Directory;
import pt.tecnico.myDrive.domain.File;
import pt.tecnico.myDrive.domain.MyDriveManager;
import pt.tecnico.myDrive.domain.Session;
import pt.tecnico.myDrive.exception.AccessDeniedException;
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

        token = mg.getCurrentSession().getToken();
    }

    @Test
    public void successListDir(){
        ListDirectoryService service = new ListDirectoryService(token);
        service.execute();
        String out = service.result();
        assertNotNull("Nothing listed", out);
        assertEquals("Wrong output form", expected, out);
    }

    @Test(expected = InvalidTokenException.class)
    public void invalidTokenListDir(){
        ListDirectoryService service = new ListDirectoryService(-1);
        service.execute();
    }

    @Test(expected = AccessDeniedException.class)
    public void accessDeniedListDir(){
        Session currentSession = MyDriveManager.getInstance().getCurrentSession();
        currentSession.setCurrentDir(MyDriveManager.getInstance().getFilesystem().getHomeDirectory());
        MyDriveManager.getInstance().createDirectory("MillenniumFalcon");
        Directory millenniumFalcon = (Directory) MyDriveManager.getInstance().getCurrentSession().getCurrentDir().getFileByName("MillenniumFalcon");
        millenniumFalcon.setPermissions("--------");
        currentSession.setCurrentDir((Directory)MyDriveManager.getInstance().getFilesystem().getHomeDirectory().getFileByName("MillenniumFalcon"));
        MyDriveManager.getInstance().createDirectory("HanSolo");
        Directory hanSolo = (Directory) MyDriveManager.getInstance().getCurrentSession().getCurrentDir().getFileByName("HanSolo");
        hanSolo.setPermissions("--------");
        MyDriveManager.getInstance().createDirectory("Chewbacca");
        Directory chewbacca = (Directory) MyDriveManager.getInstance().getCurrentSession().getCurrentDir().getFileByName("Chewbacca");
        chewbacca.setPermissions("--------");
        MyDriveManager.getInstance().addUser("Emperorr");
        MyDriveManager.getInstance().login("Emperorr","Emperorr");
        ListDirectoryService service = new ListDirectoryService(token);
        service.execute();
    }

}