package pt.tecnico.myDrive.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import pt.tecnico.myDrive.domain.Directory;
import pt.tecnico.myDrive.domain.MyDriveManager;
import pt.tecnico.myDrive.exception.AccessDeniedException;
import pt.tecnico.myDrive.exception.FileUnknownException;
import pt.tecnico.myDrive.exception.InvalidTokenException;
import pt.tecnico.myDrive.exception.IsNotPlainFileException;

import static org.junit.Assert.assertEquals;

public class ReadFileTest extends AbstractServiceTest {
	private static final String USER_LOGGED = "charmander";
	protected static final Logger log = LogManager.getRootLogger();
	private static MyDriveManager mg;
	

	@Override
	protected void populate() {
		mg = MyDriveManager.getInstance();
		
		mg.addUser(USER_LOGGED);
		mg.addUser("charizard");
		
		/* --- Non permitted Files for User charmander --- */
		mg.login("root", "***");
		
		Directory diraux = (Directory) mg.getFilesystem().getHomeDirectory().getFileByName("charizard");
		mg.getFilesystem().getHomeDirectory().getFileByName("charizard").setPermissions("rwxd----");
		mg.getCurrentSession().setCurrentDir(diraux);

		mg.createDirectory("tackle");
		((Directory) mg.getFilesystem().getHomeDirectory().getFileByName("charizard"))
        .getFileByName("tackle").setPermissions("rwxdrwxd");

		mg.createPlainFile("takedown.txt");
		((Directory) mg.getFilesystem().getHomeDirectory().getFileByName("charizard"))
        .getFileByName("takedown.txt").setPermissions("rwxdrwxd");
		
		mg.createAppFile("flamethrower.txt");
		((Directory) mg.getFilesystem().getHomeDirectory().getFileByName("charizard"))
        .getFileByName("flamethrower.txt").setPermissions("rwxd----");
		
		mg.createLinkFile("catering.txt", "/home/charizard");
		((Directory) mg.getFilesystem().getHomeDirectory().getFileByName("charizard"))
        .getFileByName("catering.txt").setPermissions("rwxd----");
		
		/* --- User charmander Files --- */
		mg.login(USER_LOGGED, USER_LOGGED);
		
		mg.createDirectory("splash");
		mg.createPlainFile("ember.txt");
		mg.createPlainFile("burn.txt", "in hell");
		mg.createPlainFile("takedownv2.txt");
		((Directory) mg.getFilesystem().getHomeDirectory().getFileByName("charmander"))
				.getFileByName("takedownv2.txt").setPermissions("-wxd----");
		mg.createAppFile("firespin.txt");
		mg.createAppFile("inferno.txt", "destroy.world");
		mg.createLinkFile("firefly.txt", "/home/charmander/cenas");
		mg.createLinkFile("fly.txt", "/home/charmander/burn.txt");
		mg.createLinkFile("heatwave.txt", "/home/charmander/takedownv2.txt");
		mg.createLinkFile("firepump.txt", "/home/charizard/flamethrower.txt");
		mg.createLinkFile("airslash.txt", "/home/charmander/inferno.txt");
		mg.createLinkFile("blitz.txt", "/home/charmander/fly.txt");
		mg.createLinkFile("flying.txt", "/home/charizard/catering.txt");
		mg.createLinkFile("mewtwo.txt", "/home/charmander/splash");
		mg.createLinkFile("mew.txt", "/home/charizard/tackle");
		
	}
	
	
    /* ----- TESTS ----- */
    
    @Test(expected = IsNotPlainFileException.class)
    public void notPlainFile() {
    	
    	ReadFileService service = new ReadFileService(MyDriveManager.getInstance().getCurrentSession().getToken(), "splash");
    	service.execute();
    }
    
    @Test(expected = AccessDeniedException.class)
    public void notPermittedDirectory() {

    	ReadFileService service = new ReadFileService(MyDriveManager.getInstance().getCurrentSession().getToken(), "/home/charizard/tackle");
    	service.execute();
    }  
    
    @Test
    public void readEmptyPlain() {

    	ReadFileService service = new ReadFileService(MyDriveManager.getInstance().getCurrentSession().getToken(), "ember.txt");
    	service.execute();
    	assertEquals(null, service.result());
    }
    
    @Test
    public void readPlainWithContent() {

    	ReadFileService service = new ReadFileService(MyDriveManager.getInstance().getCurrentSession().getToken(), "burn.txt");
    	service.execute();
    	assertEquals("in hell", service.result());
    }
    
    @Test(expected = AccessDeniedException.class)
    public void notPermittedPlainByNotPermittedDir() {

    	ReadFileService service = new ReadFileService(MyDriveManager.getInstance().getCurrentSession().getToken(),
				"/home/charizard/takedown.txt");
    	service.execute();
    }
    
    @Test
    public void readEmptyApp() {

    	ReadFileService service = new ReadFileService(MyDriveManager.getInstance().getCurrentSession().getToken(), "firespin.txt");
    	service.execute();
    	assertEquals(null, service.result());
    }
    
    @Test
    public void readAppWithContent() {

    	ReadFileService service = new ReadFileService(MyDriveManager.getInstance().getCurrentSession().getToken(), "inferno.txt");
    	service.execute();
    	assertEquals("destroy.world", service.result());
    }
    
    @Test(expected = AccessDeniedException.class)
    public void notPermittedAppByNotPermittedDir() {

    	ReadFileService service = new ReadFileService(MyDriveManager.getInstance().getCurrentSession().getToken(),
				"/home/charizard/flamethrower.txt");
    	service.execute();
    }
    
    @Test(expected = FileUnknownException.class)
    public void invalidLinkReference() {

    	ReadFileService service = new ReadFileService(MyDriveManager.getInstance().getCurrentSession().getToken(), "firefly.txt");
    	service.execute();
    }
    
    @Test
    public void linkReferenceToPlain() {

    	ReadFileService service = new ReadFileService(MyDriveManager.getInstance().getCurrentSession().getToken(), "fly.txt");
    	service.execute();
    	assertEquals("in hell", service.result());
    }
    
    @Test(expected = AccessDeniedException.class)
    public void notPermittedPlainByLinkReference() {

    	ReadFileService service = new ReadFileService(MyDriveManager.getInstance().getCurrentSession().getToken(), "heatwave.txt");
    	service.execute();
    }

	@Test(expected = AccessDeniedException.class)
	public void notPermittedAppByLinkReference() {

		ReadFileService service = new ReadFileService(MyDriveManager.getInstance().getCurrentSession().getToken(), "firepump.txt");
		service.execute();
	}

    @Test
    public void linkReferenceToApp() {

    	ReadFileService service = new ReadFileService(MyDriveManager.getInstance().getCurrentSession().getToken(), "airslash.txt");
    	service.execute();
    	assertEquals("destroy.world", service.result());
    }

    @Test
    public void linkReferenceToLink() {

    	ReadFileService service = new ReadFileService(MyDriveManager.getInstance().getCurrentSession().getToken(), "blitz.txt");
    	service.execute();
    	assertEquals("in hell", service.result());
    }
   
    @Test(expected = AccessDeniedException.class)
    public void notPermittedLinkByLinkReference() {

    	ReadFileService service = new ReadFileService(MyDriveManager.getInstance().getCurrentSession().getToken(), "flying.txt");
    	service.execute();
    }

    @Test(expected = IsNotPlainFileException.class)
    public void directoryNotReadableByLinkReference() {

    	ReadFileService service = new ReadFileService(MyDriveManager.getInstance().getCurrentSession().getToken(), "mewtwo.txt");
    	service.execute();
    }
    
    @Test(expected = AccessDeniedException.class)
    public void notPermittedDirectoryByLinkReference() {

    	ReadFileService service = new ReadFileService(MyDriveManager.getInstance().getCurrentSession().getToken(), "mew.txt");
    	service.execute();
    }

    @Test(expected = InvalidTokenException.class)
    public void readFileInactiveSession() {

		ReadFileService service = new ReadFileService(-1, "burn.txt");
		service.execute();
    }

}
