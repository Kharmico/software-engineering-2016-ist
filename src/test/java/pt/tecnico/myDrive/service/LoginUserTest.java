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
        mg.addUser("Obi-Wan");
        mg.addUser("R2D2");
    }

    @Test
    public void successLogin(){
        long token;
        LoginUserService service = new LoginUserService("R2D2", "R2D2");
        service.execute();
        token = service.result();
        assertNotNull("Session was not created", MyDriveManager.getInstance().getCurrentSession());
        assertEquals("Invalid Token", true, MyDriveManager.getInstance().isTokenValid(token));
    }

    @Test(expected = UserUnknownException.class)
    public void nonExistingUser(){
        LoginUserService service = new LoginUserService("Vader", "Vader");
        service.execute();
    }

    @Test(expected = WrongPasswordException.class)
    public void wrongPassword(){
        LoginUserService service = new LoginUserService("Obi-Wan", "Kenobi");
        service.execute();
    }

    /*
    Test Cases
    - Sucessfull login
    - NonExisting Username
    - Wrong Password
    */
}
