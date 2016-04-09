package pt.tecnico.myDrive.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ReadFileTest extends AbstractServiceTest {
	private static final String USER_LOGGED = "charmander";
	protected static final Logger log = LogManager.getRootLogger();
	
	
	@Override
	protected void populate() {
		MyDriveManager mg = MyDriveManager.getInstance();
		
		mg.addUser(USER_LOGGED);
		
		mg.login("root", "***");
	}
	
}