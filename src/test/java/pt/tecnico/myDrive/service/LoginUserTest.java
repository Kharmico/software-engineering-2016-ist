package pt.tecnico.myDrive.service;

import org.junit.Test;
import pt.tecnico.myDrive.domain.MyDriveManager;
import pt.tecnico.myDrive.exception.UserUnknownException;
import pt.tecnico.myDrive.exception.WrongPasswordException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


public class LoginUserTest extends AbstractServiceTest {

    @Override
    protected void populate() {
        MyDriveManager mg = MyDriveManager.getInstance();
        mg.addUser("ObiWannn");
        mg.addUser("R2D2R2D2");
    }

    @Test
    public void successLogin(){
        LoginUserService service = new LoginUserService("R2D2R2D2", "R2D2R2D2");
        service.execute();
        long token = service.result();
        assertNotNull("Session was not created", MyDriveManager.getInstance().getCurrentSession());
        long actualToken = MyDriveManager.getInstance().getCurrentSession().getToken();
        assertEquals(token, actualToken);
    }

    @Test(expected = UserUnknownException.class)
    public void nonExistingUser(){
        LoginUserService service = new LoginUserService("Vader", "Vader");
        service.execute();
    }

    @Test(expected = WrongPasswordException.class)
    public void wrongPassword(){
        LoginUserService service = new LoginUserService("ObiWannn", "Kenobi");
        service.execute();
    }

}
