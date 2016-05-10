package pt.tecnico.myDrive.service;

import mockit.Mock;
import mockit.MockUp;
import mockit.integration.junit4.JMockit;
import org.junit.Test;
import org.junit.runner.RunWith;
import pt.tecnico.myDrive.domain.Directory;
import pt.tecnico.myDrive.domain.MyDriveManager;
import pt.tecnico.myDrive.domain.User;
import pt.tecnico.myDrive.exception.AccessDeniedException;
import pt.tecnico.myDrive.exception.InvalidTokenException;
import pt.tecnico.myDrive.exception.IsNotPlainFileException;


/* This test involves the usage of mock-ups
 * to test the association between files
 * and their extensions.
 */
@RunWith(JMockit.class)
public class ExecuteAssociationTest extends AbstractServiceTest {
    private static final String USER_LOGGED = "Evangelion";
    private static final String SECONDARY_USER = "Chiquitita";
    private static MyDriveManager _manager;


    @Override
    public void populate() {
        _manager = MyDriveManager.getInstance();
        _manager.addUser(SECONDARY_USER);
        _manager.addUser(USER_LOGGED);

        /* --- File and Directory to test when there is no permission to enter a Directory --- */
        _manager.login(SECONDARY_USER, SECONDARY_USER);
        _manager.createDirectory("Testing");
        _manager.changeDirectory("Testing", _manager.getCurrentSession().getToken());
        _manager.createLinkFile("Linktotest", "/home/Chiquitita");
        ((Directory) _manager.getFilesystem().getHomeDirectory().getFileByName("Chiquitita")).
                getFileByName("Testing").setPermissions("rwxdrwxd");

        _manager.login(USER_LOGGED, USER_LOGGED);
        _manager.createDirectory("Testnodir");

        /* --- AppFiles that execute PlainFile or AppFile when those do not have permission --- */
        _manager.createAppFile("ExecPlain", "pt.tecnico.myDrive.MyDriveApplication.execNotPermedFile");
        _manager.createAppFile("ExecApp", "pt.tecnico.myDrive.MyDriveApplication.execNotPermedFile");

        /* --- AppFile and PlainFile with permissions ---*/
        _manager.createAppFile("Shinji", "pt.tecnico.myDrive.MyDriveApplication.testAppfile");
        _manager.createPlainFile("Ikari", "/home/Evangelion/Shinji Justbecause\n" +
                "/home/Evangelion/Shinji Justbecause\n" +
                "/home/Evangelion/Shinji Ilikethis");

        /* --- AppFile and PlainFile without permissions --- */
        _manager.createAppFile("Noname", "pt.tecnico.myDrive.MyDriveApplication.testAppfile");
        ((Directory) _manager.getFilesystem().getHomeDirectory().getFileByName("Evangelion")).
                getFileByName("Noname").setPermissions("rw-d----");
        _manager.createPlainFile("Ilikeithere", "/home/Evangelion/Shinji Stuff\n" +
                "/home/Evangelion/Shinji Stuff");
        ((Directory) _manager.getFilesystem().getHomeDirectory().getFileByName("Evangelion")).
                getFileByName("Ilikeithere").setPermissions("rw-d----");

        _manager.createLinkFile("RefToPermed", "/home/Evangelion/Shinji");
        _manager.createLinkFile("RefToNotPermed", "/home/Evangelion/Ilikeithere");
        _manager.createLinkFile("RefToDiffDir", "/home/Chiquitita/Linktotest");
    }


    @Test
    public void successfullyExecAppFile() {
        String[] stuffToPass = {"Ithinknot", "Stuff"};

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

        ExecuteFileService execFile = new ExecuteFileService("/home/Evangelion/Shinji", stuffToPass, _manager.getCurrentSession().getToken());
        execFile.execute();
        //assertNotNull(_manager.getCurrentSession().getCurrentDir().getFileByName("Ithinknot"));
    }

    @Test
    public void successfullyExecPlainFile() {
        String[] stuffToPass = {"Justbecause"};

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

        ExecuteFileService execFile = new ExecuteFileService("/home/Evangelion/Ikari", stuffToPass, _manager.getCurrentSession().getToken());
        execFile.execute();
        //assertNotNull(((Directory)_manager.getCurrentSession().getCurrentDir().getFileByName("Justbecause")).getFileByName("Ilikethis"));
    }

    @Test
    public void successfullyExecAppFileThroughExt() {
        String[] stuffToPass = {"Beep"};

        new MockUp<User>() {

            @Mock
            String getFileAssocByExt(String path){
                return "/home/Evangelion/ExecApp";
            }
        };

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
                try {
                    mg.executePlainFile(_pathToFile, _argsToPass, _tok);
                } catch (AccessDeniedException ex) {
                    int count = 2;
                    String[] argsForExec = new String[_argsToPass.length + 2];
                    argsForExec[0] = "/home/Evangelion/Noname";
                    argsForExec[1] = String.valueOf(_manager.getCurrentSession().getToken());
                    argsForExec[2] = stuffToPass[0];
                    mg.executePlainFile(mg.getCurrentSession().getCurrentUser().getFileAssocByExt(argsForExec[0]), argsForExec, _tok);
                }
            }
        };

        ExecuteFileService execFile = new ExecuteFileService("/home/Evangelion/Noname", stuffToPass, _manager.getCurrentSession().getToken());
        execFile.execute();
        //assertNotNull(((Directory)_manager.getCurrentSession().getCurrentDir().getFileByName("Noname")).getFileByName("Noname"));
    }

    @Test
    public void successfullyExecPlainFileThroughExt() {
        String[] stuffToPass = {"Ithinknot"};

        new MockUp<User>() {

            @Mock
            String getFileAssocByExt(String path){
                return "/home/Evangelion/ExecPlain";
            }
        };

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
                try {
                    mg.executePlainFile(_pathToFile, _argsToPass, _tok);
                } catch (AccessDeniedException ex) {
                    int count = 2;
                    String[] argsForExec = new String[_argsToPass.length + 2];
                    argsForExec[0] = "/home/Evangelion/Ilikeithere";
                    argsForExec[1] = String.valueOf(_manager.getCurrentSession().getToken());
                    argsForExec[2] = stuffToPass[0];
                    mg.executePlainFile(mg.getCurrentSession().getCurrentUser().getFileAssocByExt(argsForExec[0]), argsForExec, _tok);
                }
            }
        };

        ExecuteFileService execFile = new ExecuteFileService("/home/Evangelion/Ilikeithere", stuffToPass, _manager.getCurrentSession().getToken());
        execFile.execute();
        //assertNotNull(_manager.getCurrentSession().getCurrentDir().getFileByName("Ithinknot"));
    }

    @Test
    public void successfullyExecAppFileThroughLink() {
        String[] stuffToPass = {"Ithinknot"};

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

        ExecuteFileService execFile = new ExecuteFileService("/home/Evangelion/RefToPermed", stuffToPass, _manager.getCurrentSession().getToken());
        execFile.execute();
        //assertNotNull(_manager.getCurrentSession().getCurrentDir().getFileByName("Ithinknot"));
    }

    @Test
    public void successfullyExecPlainFileThroughLinkAndExt() {
        String[] stuffToPass = {"Ithinknot"};

        new MockUp<User>() {

            @Mock
            String getFileAssocByExt(String path){
                return "/home/Evangelion/ExecPlain";
            }
        };

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
                try {
                    mg.executePlainFile(_pathToFile, _argsToPass, _tok);
                } catch (AccessDeniedException ex) {
                    int count = 2;
                    String[] argsForExec = new String[_argsToPass.length + 2];
                    argsForExec[0] = "/home/Evangelion/Ilikeithere";
                    argsForExec[1] = String.valueOf(_manager.getCurrentSession().getToken());
                    argsForExec[2] = stuffToPass[0];
                    mg.executePlainFile(mg.getCurrentSession().getCurrentUser().getFileAssocByExt(argsForExec[0]), argsForExec, _tok);
                }
            }
        };

        ExecuteFileService execFile = new ExecuteFileService("/home/Evangelion/RefToNotPermed", stuffToPass, _manager.getCurrentSession().getToken());
        execFile.execute();
        //assertNotNull(_manager.getCurrentSession().getCurrentDir().getFileByName("Ithinknot"));
    }


    @Test(expected = InvalidTokenException.class)
    public void invalidTokenTest() {
        String[] stuffToPass = {"stuff", "something"};


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

        ExecuteFileService execFile = new ExecuteFileService("stuff", stuffToPass, -1);
        execFile.execute();
    }

    @Test(expected = IsNotPlainFileException.class)
    public void cannotExecDirectory() {
        String[] stuffToPass = {"Ithinknot"};

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

        ExecuteFileService execFile = new ExecuteFileService("/home/Evangelion/Testnodir", stuffToPass, _manager.getCurrentSession().getToken());
        execFile.execute();
    }

    @Test(expected = AccessDeniedException.class)
    public void noPermToExecOnOtherDirectory() {
        String[] stuffToPass = {"Ithinknot"};

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

        ExecuteFileService execFile = new ExecuteFileService("/home/Evangelion/RefToDiffDir", stuffToPass, _manager.getCurrentSession().getToken());
        execFile.execute();
    }

}

