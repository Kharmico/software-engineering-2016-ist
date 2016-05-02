package pt.tecnico.myDrive.service;

import org.junit.Test;

import pt.tecnico.myDrive.domain.MyDriveManager;
import pt.tecnico.myDrive.domain.Session;
import pt.tecnico.myDrive.exception.InvalidTokenException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.LinkedHashMap;


public class AddEnvironmentVariableTest extends AbstractServiceTest {

	@Override
	protected void populate() {
	    MyDriveManager mdm = MyDriveManager.getInstance();
	    mdm.login("root","***");
	    Session currentSession = mdm.getCurrentSession();
	    long token = currentSession.getToken();

	    currentSession.setCurrentDir(mdm.getFilesystem().getHomeDirectory());
	    mdm.addEnvironmentVariable("EnVarTest1", "SouO1", token);
	    mdm.addEnvironmentVariable("EnVarTest2", "SouO2", token);	 
	    
	}
	
	@Test
	public void successNewEnvVar(){
		String value = "/home/ItsMeMario";
		LinkedHashMap<String, String> vars = new LinkedHashMap<String, String>();
		
		AddEnvironmentVariableService service = 
				new AddEnvironmentVariableService(MyDriveManager.getInstance().getCurrentSession().getToken(), "IDoNotExistButIWill", value);
		service.execute();
		vars = service.result();

		assertTrue("Wrong size of vars", vars.size() == 3);
		
		assertEquals("Environment value was not created", vars.get("IDoNotExistButIWill"), value);
	}

	@Test
	public void successExistingVar(){
		String value1 = "NewValue";
		String value2 = "hi";
		LinkedHashMap<String, String> vars = new LinkedHashMap<String, String>();

		AddEnvironmentVariableService service1 = 
				new AddEnvironmentVariableService(MyDriveManager.getInstance().getCurrentSession().getToken(), "EnVarTest1", value1);
		service1.execute();
		vars = service1.result();
		
		AddEnvironmentVariableService service2 = 
				new AddEnvironmentVariableService(MyDriveManager.getInstance().getCurrentSession().getToken(), "EnVarTest2", value2);
		service2.execute();
		vars.putAll(service2.result());
		
		assertTrue("Wrong size of vars", vars.size() == 2);
		assertEquals("Environment value 1 was not modified", vars.get("EnVarTest1"), value1);
		assertEquals("Environment value 2 was not modified", vars.get("EnVarTest2"), value2);
	}
	
	
    @Test(expected = InvalidTokenException.class)
    public void invalidToken(){
    	AddEnvironmentVariableService service = new AddEnvironmentVariableService(-1, "EnVarTest2", "t0k3n");
    	service.execute();
    }
	
	
}