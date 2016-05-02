package pt.tecnico.myDrive.integration;


import org.junit.Test;
import pt.tecnico.myDrive.domain.MyDriveManager;
import pt.tecnico.myDrive.presentation.*;
import pt.tecnico.myDrive.service.AbstractServiceTest;
import pt.tecnico.myDrive.service.ListDirectoryService;

/**
 * Created by xxlxpto on 28-04-2016.
 */
public class SystemTest extends AbstractServiceTest {
    private static final String[] ROOT_LOGIN_ARGS = new String[]{"root", "***"};
    private static final String[] NO_ARGS = new String[]{};
    private static final String AVO_CANTIGAS = "avoCantigas";
    private static final String PLAIN_FILENAME = "batatas";
    private static final String APP_FILENAME = "main";

    private MdShell sh;

    protected void populate() {
        sh = new MdShell();
        MyDriveManager.getInstance().login("root", "***");
        MyDriveManager.getInstance().createAppFile(APP_FILENAME);
        ListDirectoryService ls = new ListDirectoryService(MyDriveManager.getInstance().getCurrentSession().getToken(), "/home/root");
        ls.execute();
        System.out.println(ls.result());
        MyDriveManager.getInstance().login("nobody", "");
        MyDriveManager.getInstance().createPlainFile(PLAIN_FILENAME);
        MyDriveManager.getInstance().addUser(AVO_CANTIGAS);
    }

    @Test
    public void simpleSuccess() {
        new LoginCommand(sh).execute(ROOT_LOGIN_ARGS);
        new ListCommand(sh).execute(new String[] { "." });
    }
    @Test
    public void cwdSuccess() {
        new LoginCommand(sh).execute(ROOT_LOGIN_ARGS);
        new ChangeWorkingDirectoryCommand(sh).execute(new String[] { "/" });
        new ChangeWorkingDirectoryCommand(sh).execute(new String[] { "home" });
        new ChangeWorkingDirectoryCommand(sh).execute(new String[] { "root" });
        new ChangeWorkingDirectoryCommand(sh).execute(new String[] { ".." });
        new ChangeWorkingDirectoryCommand(sh).execute(new String[] { ".." });
        new ChangeWorkingDirectoryCommand(sh).execute(NO_ARGS);
    }


    @Test
    public void greatSuccess(){
        new ListCommand(sh).execute(NO_ARGS);
        new WriteCommand(sh).execute(new String[] {"batatas", "System.out.println bonecas\n" +
                "System.out.println batatas"});

        new LoginCommand(sh).execute(ROOT_LOGIN_ARGS);
        new ChangeWorkingDirectoryCommand(sh).execute(new String[] { ".." });
        new ChangeWorkingDirectoryCommand(sh).execute(new String[] { "nobody" });
        new ListCommand(sh).execute(NO_ARGS);

        //testing the root's power
        new ChangeWorkingDirectoryCommand(sh).execute(new String[] { "/" });
        new ChangeWorkingDirectoryCommand(sh).execute(new String[] { "home" });
        new ChangeWorkingDirectoryCommand(sh).execute(NO_ARGS);

        // Gimme some env vars
        new EnvironmentCommand(sh).execute(NO_ARGS);
        new EnvironmentCommand(sh).execute(new String[] {"ficheiro_nobody"});
        new EnvironmentCommand(sh).execute(new String[] {"ficheiro_nobody", "/home/nobody/batatas"});
        new EnvironmentCommand(sh).execute(NO_ARGS);

        // I believe this should work, however is not implemented yet.
        // new WriteCommand(sh).execute(new String[]{ "$ficheiro_nobody" , "cozidas" });
        new KeyCommand(sh).execute(new String[]{});
        new ChangeWorkingDirectoryCommand(sh).execute(new String[]{"/home/nobody"});
        new KeyCommand(sh).execute(new String[]{ "nobody" });
        new ListCommand(sh).execute(NO_ARGS);

        new KeyCommand(sh).execute(new String[]{"root"});

        // Let's login as another user
        new LoginCommand(sh).execute(new String[]{AVO_CANTIGAS, AVO_CANTIGAS});
        new ListCommand(sh).execute(NO_ARGS);
        new ChangeWorkingDirectoryCommand(sh).execute(new String[] { ".." });
        new ChangeWorkingDirectoryCommand(sh).execute(new String[] { "nobody" });
        new ChangeWorkingDirectoryCommand(sh).execute(new String[] {  });

        new LoginCommand(sh).execute(ROOT_LOGIN_ARGS);
        new ListCommand(sh).execute(NO_ARGS);
        new WriteCommand(sh).execute(new String[]{ "/home/root/main", "pt.tecnico.myDrive.MyDriveApplication.main"});
        new ExecuteCommand(sh).execute(new String[]{APP_FILENAME, "drive.xml" });

    }
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
