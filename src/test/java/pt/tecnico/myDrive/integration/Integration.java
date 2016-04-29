package pt.tecnico.myDrive.integration;

import org.junit.Test;
import pt.tecnico.myDrive.domain.MyDriveManager;
import pt.tecnico.myDrive.service.AbstractServiceTest;
import pt.tecnico.myDrive.service.ChangeDirectoryService;
import pt.tecnico.myDrive.service.ChangeDirectoryTest;
import pt.tecnico.myDrive.service.LoginUserService;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by diogo on 29-04-2016.
 */
public class Integration extends AbstractServiceTest{
    @Override
    protected void populate() {
        //MyDriveManager mg = MyDriveManager.getInstance();

        //mg.addUser("Ricardo");


    }

    /*
    WAITING FOR PROFESSOR TO ANSWER QUESTION
    @Test
    public void sucessLoginChange(){
        LoginUserService lus = new LoginUserService("Ricardo","Ricardo");
        assertTrue(MyDriveManager.getInstance().getCurrentSession().getToken() == lus.result());
        ChangeDirectoryService cd = new ChangeDirectoryService(lus.result(),"..");
        assertEquals("/home",cd.result());
    }


   */


}
