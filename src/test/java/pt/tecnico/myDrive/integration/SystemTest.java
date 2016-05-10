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
        MyDriveManager.getInstance().createAppFile(APP_FILENAME,"pt.tecnico.myDrive.MyDriveApplication.testAppfile");
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
        new ExecuteCommand(sh).execute(new String[]{ "/home/root/main", "drive.xml" });

    }


}
