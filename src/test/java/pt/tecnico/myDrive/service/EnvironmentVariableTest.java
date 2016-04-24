package pt.tecnico.myDrive.service;

import org.junit.Test;

import pt.tecnico.myDrive.domain.MyDriveManager;
import pt.tecnico.myDrive.domain.Session;
import pt.tecnico.myDrive.exception.InvalidTokenException;

import static org.junit.Assert.assertTrue;


public class EnvironmentVariableTest extends AbstractServiceTest {

	@Override
	protected void populate() {
	    MyDriveManager mdm = MyDriveManager.getInstance();
	    mdm.login("root","***");
	    Session currentSession = mdm.getCurrentSession();
	    long token = currentSession.getToken();

	    currentSession.setCurrentDir(mdm.getFilesystem().getHomeDirectory());
	    mdm.addEnvironmentVariable("EnVarTest1", "SouO1", token);
	    mdm.addEnvironmentVariable("EnVarTest2", "SouO2", token);	  
	    mdm.addEnvironmentVariable("EnvVarTest3", "Falhei", token);	//FIXME: is there any exception? or error?
	    
	}
	
    private String getValue(long token) {
        return MyDriveManager.getInstance().listEnvironmentVariables(token);
    }

	/*
	@Test
	public void successNewEnvVar(){
		String value = "ItsMeMario";
		String writtenValue = "";
		EnvironmentVariableService service = 
				new EnvironmentVariableService(MyDriveManager.getInstance().getCurrentSession().getToken(), "IDoNotExistButIWill", value);
		service.execute();
		writtenValue = getValue(MyDriveManager.getInstance().getCurrentSession().getToken()).toLowerCase();
		
		assertTrue("Environment value was not created", writtenValue.contains("idonotexistbutiwill = " + value.toLowerCase()));	
	}

	@Test
	public void successExistingVar(){
		String value = "NewValue";
		String writtenValue = "";
		EnvironmentVariableService service = 
				new EnvironmentVariableService(MyDriveManager.getInstance().getCurrentSession().getToken(), "EnVarTest1", value);
		service.execute();
		writtenValue = getValue(MyDriveManager.getInstance().getCurrentSession().getToken()).toLowerCase();
		
		assertTrue("Environment value was not modified", writtenValue.contains("envartest1 = " + value.toLowerCase()));
	}
	
	
    @Test(expected = InvalidTokenException.class)
    public void invalidToken(){
    	EnvironmentVariableService service = new EnvironmentVariableService(-1, "EnVarTest3", "t0k3n");
    	service.execute();
    }
	
	*/
}