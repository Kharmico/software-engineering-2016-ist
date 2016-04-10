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
	
	// dirs = tackle splash
	// plain = ember burn takedown
	// app = firespin flamethrower inferno
	// link = firefly-n fly-n heatwave-p airslash-p blitz-a flying-a catering-l mewtwo-l harden-l mew-d signals-d
	@Override
	protected void populate() {
		MyDriveManager mg = MyDriveManager.getInstance();
		
		mg.addUser(USER_LOGGED);
		mg.addUser("charizard");
		
		/* --- Non permitted Files --- */
		mg.login("root", "***");
		
		Directory diraux = (Directory) mg.getFilesystem().getHomeDirectory().getFileByName("charizard");
		mg.getCurrentSession().setCurrentDir(diraux);

		mg.createDirectory("tackle");
		((Directory) mg.getFilesystem().getHomeDirectory().getFileByName("charizard"))
        .getFileByName("tackle").setPermissions("rwxd----");
		
		mg.createPlainFile("takedown.txt"); //no perm
		((Directory) mg.getFilesystem().getHomeDirectory().getFileByName("charizard"))
        .getFileByName("takedown.txt").setPermissions("rwxd----");
		
		mg.createAppFile("flamethrower.txt"); //no perm
		((Directory) mg.getFilesystem().getHomeDirectory().getFileByName("charizard"))
        .getFileByName("flamethrower.txt").setPermissions("rwxd----");
		
		mg.createLinkFile("catering.txt", "/home/charizard"); //no perm
		((Directory) mg.getFilesystem().getHomeDirectory().getFileByName("charizard"))
        .getFileByName("catering.txt").setPermissions("rwxd----");
		
		/* --- User Charmander Files --- */
		mg.login("charmander", "charmander");
		
		// dirs = --tackle-- splash
		// plain = ember burn --takedown--
		// app = firespin --flamethrower-- inferno
		// link = firefly-n fly heatwave-p airslash-p blitz-a flying-a --catering-l-- mewtwo-l harden-l mew-d signals-d
		
		// dir = notplain, no perm
		// plain = no content, content, no perm
		// app = no content, content, no perm
		// link = invalid, plain, plain no perm, app, link, link no perm, dir, dir no perm
		mg.createDirectory("splash");
		
		mg.createPlainFile("ember.txt"); //no content
		mg.createPlainFile("burn.txt", "in hell"); //with content
		
		mg.createAppFile("firespin.txt"); //no content
		mg.createAppFile("inferno.txt", "destroy.world"); //with content
		
		mg.createLinkFile("firefly.txt", "/home/charmander/cenas"); //invalid reference
		mg.createLinkFile("fly.txt", "/home/charmander/burn.txt"); //plain
		mg.createLinkFile("heatwave.txt", "/home/charizard/takedown"); //plain no perm
		mg.createLinkFile("airslash.txt", "/home/charmander/inferno.txt"); //app
		mg.createLinkFile("blitz.txt", "/home/charmander/fly.txt"); //link
		mg.createLinkFile("flying.txt", "/home/charizard/catering.txt"); //link no perm
		mg.createLinkFile("mewtwo.txt", "/home/charmander/splash"); //dir
		mg.createLinkFile("mew.txt", "/home/charizard/tackle"); //dir no perm
		
	}
	
	
    /* ----- TESTS ----- */
    
    @Test(expected = IsNotPlainFileException.class)
    public void notPlainFile() {
    	
    	ReadFileService service = new ReadFileService(MyDriveManager.getInstance().getCurrentSession().getToken(), "splash");
    	service.execute();
    }
    
    @Test(expected = AccessDeniedException.class)
    public void notPermittedDirectory() {
    	MyDriveManager mg = MyDriveManager.getInstance();
    	
    	mg.getCurrentSession().setCurrentDir((Directory) mg.getFilesystem().getHomeDirectory().getFileByName("charizard"));
    	ReadFileService service = new ReadFileService(MyDriveManager.getInstance().getCurrentSession().getToken(), "tackle");
    	service.execute();
    }  
    
    @Test
    public void readEmptyPlain() {
    	ReadFileService service = new ReadFileService(MyDriveManager.getInstance().getCurrentSession().getToken(), "ember.txt");
    	service.execute();
    	assertEquals(service.result(), null);
    }
    
    @Test
    public void readPlainWithContent() {
    	ReadFileService service = new ReadFileService(MyDriveManager.getInstance().getCurrentSession().getToken(), "burn.txt");
    	service.execute();
    	assertEquals(service.result(), "in hell");
    }
    
    @Test(expected = AccessDeniedException.class)
    public void notPermittedPlain() {
    	MyDriveManager mg = MyDriveManager.getInstance();
    	
    	mg.getCurrentSession().setCurrentDir((Directory) mg.getFilesystem().getHomeDirectory().getFileByName("charizard"));
    	ReadFileService service = new ReadFileService(MyDriveManager.getInstance().getCurrentSession().getToken(), "takedown.txt");
    	service.execute();
    }
    
    @Test
    public void readEmptyApp() {
    	ReadFileService service = new ReadFileService(MyDriveManager.getInstance().getCurrentSession().getToken(), "firespin.txt");
    	service.execute();
    	assertEquals(service.result(), null);
    }
    
    @Test
    public void readAppWithContent() {
    	ReadFileService service = new ReadFileService(MyDriveManager.getInstance().getCurrentSession().getToken(), "inferno.txt");
    	service.execute();
    	assertEquals(service.result(), "destroy.world");
    }
    
    @Test(expected = AccessDeniedException.class)
    public void notPermittedApp() {
    	MyDriveManager mg = MyDriveManager.getInstance();
    	
    	mg.getCurrentSession().setCurrentDir((Directory) mg.getFilesystem().getHomeDirectory().getFileByName("charizard"));
    	ReadFileService service = new ReadFileService(MyDriveManager.getInstance().getCurrentSession().getToken(), "flamethrower.txt");
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
    	assertEquals(service.result(), "in hell");
    }
    
    @Test(expected = AccessDeniedException.class)
    public void notPermittedPlainByLinkReference() {
    	ReadFileService service = new ReadFileService(MyDriveManager.getInstance().getCurrentSession().getToken(), "heatwave.txt");
    	service.execute();
    }
   
    @Test
    public void linkReferenceToApp() {
    	ReadFileService service = new ReadFileService(MyDriveManager.getInstance().getCurrentSession().getToken(), "airslash.txt");
    	service.execute();
    	assertEquals(service.result(), "destroy.world");
    }

    @Test
    public void linkReferenceToLink() {
    	ReadFileService service = new ReadFileService(MyDriveManager.getInstance().getCurrentSession().getToken(), "blitz.txt");
    	service.execute();
    	assertEquals(service.result(), "in hell");
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