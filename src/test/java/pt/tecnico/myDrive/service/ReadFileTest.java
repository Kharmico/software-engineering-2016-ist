package pt.tecnico.myDrive.service;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pt.tecnico.myDrive.domain.Directory;
import pt.tecnico.myDrive.domain.File;
import pt.tecnico.myDrive.domain.MyDriveManager;
import pt.tecnico.myDrive.domain.Session;
import pt.tecnico.myDrive.exception.*;

public class ReadFileTest extends AbstractServiceTest {
	private static final String USER_LOGGED = "charmander";
	protected static final Logger log = LogManager.getRootLogger();
	
	
	@Override
	protected void populate() {
		
	}
	
}