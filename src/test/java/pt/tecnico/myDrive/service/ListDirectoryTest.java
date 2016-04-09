package pt.tecnico.myDrive.service;

import org.junit.Test;
import pt.tecnico.myDrive.domain.Directory;
import pt.tecnico.myDrive.domain.MyDriveManager;
import pt.tecnico.myDrive.domain.Session;
import pt.tecnico.myDrive.exception.InvalidTokenException;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertEquals;


public class ListDirectoryTest extends AbstractServiceTest {

    long token;

    @Override
    protected void populate() {
        MyDriveManager mg = MyDriveManager.getInstance();
        mg.login("root","***");
        Session currentSession = mg.getCurrentSession();

        currentSession.setCurrentDir(mg.getFilesystem().getHomeDirectory());
        mg.createDirectory("DeathStar");
        currentSession.setCurrentDir((Directory)mg.getFilesystem().getHomeDirectory().getFileByName("DeathStar"));

        mg.createPlainFile("DarthVader.txt","The is strong in this one");
        mg.createPlainFile("Emperor.txt","Good, good, let the hate flow thrugh you");
        mg.createDirectory("TieFighters");
        mg.createDirectory("Stormtroopers");
        token = mg.getInstance().getCurrentSession().getToken();
    }


    @Test
    public void successLogin(){
        ListDirectoryService service = new ListDirectoryService(token);
        service.execute();
        String out = service.result();
        assertNotNull("Nothing listed", out);
        assertEquals("Wrong output form", "DarthVader.txt\nEmperor.txt\nTieFighters\nStormtroopers\n", out);
    }

    @Test(expected = InvalidTokenException.class)
    public void invalidToken(){
        ListDirectoryService service = new ListDirectoryService(token+1);
        service.execute();
    }

}