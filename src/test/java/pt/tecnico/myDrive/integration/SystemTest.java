package pt.tecnico.myDrive.integration;


import static org.junit.Assert.assertEquals;
import org.junit.Test;
import pt.tecnico.myDrive.presentation.*;
import pt.tecnico.myDrive.service.AbstractServiceTest;

/**
 * Created by xxlxpto on 28-04-2016.
 */
public class SystemTest extends AbstractServiceTest {
    private MdShell sh;

    protected void populate() {
        sh = new MdShell();
    }

    @Test
    public void simpleSuccess() {
        new LoginCommand(sh).execute(new String[] { "root" , "***" });
        new ListCommand(sh).execute(new String[] { "." });
    }
//    @Test
//    public void cwdSuccess() {
//        new LoginCommand(sh).execute(new String[] { "root" , "***" });
//        new ChangeWorkingDirectoryCommand(sh).execute(new String[] { "/" });
//        new ChangeWorkingDirectoryCommand(sh).execute(new String[] { "home" });
//        new ChangeWorkingDirectoryCommand(sh).execute(new String[] { "root" });
//        new ChangeWorkingDirectoryCommand(sh).execute(new String[] { ".." });
//        new ChangeWorkingDirectoryCommand(sh).execute(new String[] { ".." });
//        new ChangeWorkingDirectoryCommand(sh).execute(new String[] {  });
//    }


    /*FRANCISCO - ve se queres aproveitar algo daqui eu enganei-me e pensei que tinha que fazer casos de teste à camada mas és tu*/
    /*
    @Override
    protected void populate() {
        MyDriveManager mg = MyDriveManager.getInstance();

        mg.addUser("Ricardo");
        mg.addUser("Leandro");
        mg.addUser("Quim");


    }


    @Test
    public void testLoginSucessUsernamePassword(){
        MdShell md = new MdShell();
        LoginCommand lc = new LoginCommand(md);
        String[] args = new String[2];
        args[0] = "root";
        args[1] = "***";
        lc.execute(args);
        assertEquals("Login with sucess username + password",args[0],md.getCurrentUser());
    }

    @Test
    public void testLoginSucessUsername(){
        MdShell md = new MdShell();
        LoginCommand lc = new LoginCommand(md);
        String[] args = new String[1];
        args[0] = "Ricardo";
        lc.execute(args);
        assertEquals("Login with sucess just username",args[0],md.getCurrentUser());
    }

    @Test
    public void testLoginInsucessZeroArgs(){
        MdShell md = new MdShell();
        LoginCommand lc = new LoginCommand(md);
        String[] args = new String[0];
        lc.execute(args);
        assertEquals("Login with zero args on execute","nobody",md.getCurrentUser());
    }

    @Test(expected = UserUnknownException.class)
    public void testLoginInsucessUser(){
        MdShell md = new MdShell();
        LoginCommand lc = new LoginCommand(md);
        String[] args = new String[1];
        args[0] = "Josefina";
        lc.execute(args);
    }

    @Test
    public void testLoginSame(){
        MdShell md = new MdShell();
        LoginCommand lc = new LoginCommand(md);
        String[] args = new String[2];
        args[0] = "nobody";
        args[1] = "";
        String currentUser = md.getCurrentUser();
        lc.execute(args);
        assertEquals("Login to the same user",currentUser,md.getCurrentUser());
        assertEquals("Login to the same user doesn't increase map size",1,md.getSessions().size());
    }

    Login:
    1) login com username + password
    2) login com username
    3) login sem argumentos
    4) login para user que não existe
    5) login para o mesmo user
     */

}
