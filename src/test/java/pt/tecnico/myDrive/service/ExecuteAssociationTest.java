package pt.tecnico.myDrive.service;

import mockit.Mock;
import mockit.MockUp;
import mockit.integration.junit4.JMockit;
import org.junit.*;
import org.junit.runner.RunWith;
import pt.tecnico.myDrive.domain.Directory;
import pt.tecnico.myDrive.domain.MyDriveManager;
import pt.tecnico.myDrive.exception.InvalidTokenException;


/* This test involves the usage of mock-ups
 * to test the association between files
 * and their extensions.
 */
@RunWith(JMockit.class)
public class ExecuteAssociationTest extends AbstractServiceTest {
    private static final String USER_LOGGED = "Evangelion";
    private static final String SECONDARY_USER = "Chiquitita";


    @Override
    public void populate() {
        MyDriveManager manager = MyDriveManager.getInstance();
        manager.addUser(SECONDARY_USER);
        manager.addUser(USER_LOGGED);

        manager.login(SECONDARY_USER, SECONDARY_USER);
        manager.createDirectory("Testing");
        manager.changeDirectory("Testing", manager.getCurrentSession().getToken());
        manager.createLinkFile("Linktotest", "home/Chiquitita");
        ((Directory) manager.getFilesystem().getHomeDirectory().getFileByName("Chiquitita")).
                getFileByName("Testing").setPermissions("rwxd----");

        manager.login(USER_LOGGED, USER_LOGGED);
        manager.createDirectory("Testnodir");

        /* --- AppFile and PlainFile with permissions ---*/
        manager.createAppFile("Shinji", "pt.tecnico.myDrive.domain.AppFile");
        manager.createPlainFile("Ikari", "pt.tecnico.myDrive.domain.AppFile cenas\n" +
                "pt.tecnico.myDrive.domain.AppFile cenas");

        /* --- AppFile and PlainFile without permissions --- */
        manager.createAppFile("Noname", "pt.tecnico.myDrive.domain.AppFile coisas");
        ((Directory) manager.getFilesystem().getHomeDirectory().getFileByName("Evangelion")).
                getFileByName("Noname").setPermissions("rwxd----");
        manager.createPlainFile("Ilikeithere", "pt.tecnico.myDrive.domain.AppFile coisas\n" +
                "pt.tecnico.myDrive.domain.AppFile coisas");
        ((Directory) manager.getFilesystem().getHomeDirectory().getFileByName("Evangelion")).
                getFileByName("Ilikeithere").setPermissions("rwxd----");

        manager.createLinkFile("RefToPermed", "home/Evangelion/");
        manager.createLinkFile("RefToNotPermed", "home/Evangelion/");
        //Create users and stuff...
        //Create dirs+files to test, with and without permissions, including directories
    }


    @Test(expected = InvalidTokenException.class)
    public void success() {
        String[] stuffToPass = {"cenas", "coisas"};

        /* No need to mock, I believe
        new MockUp<MyDriveManager>() {
            @Mock
            void executePlainFile(String _path, String[] _args, long _token) {
                throw new InvalidTokenException(_token);
            }
        }; */


        new MockUp<ExecuteFileService>() {

            String _pathToFile;
            String[] _argsToPass;
            long _tok;

            @Mock(invocations = 1)
            void $init(String pathToFile, String[] argsToPass, long tok) {
                _pathToFile = pathToFile;
                _argsToPass = argsToPass;
                _tok = tok;
            }

            @Mock(invocations = 1)
            void dispatch() {
                MyDriveManager mg = MyDriveManager.getInstance();
                mg.executePlainFile(_pathToFile, _argsToPass, _tok);
            }
        };
        
        

        ExecuteFileService execFile = new ExecuteFileService("cenas", stuffToPass, -1);
       execFile.execute();

    }
    
    
}

