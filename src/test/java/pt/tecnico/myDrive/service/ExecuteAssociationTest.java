package pt.tecnico.myDrive.service;

import mockit.*;
import mockit.integration.junit4.JMockit;
import org.junit.*;
import org.junit.runner.RunWith;
import pt.tecnico.myDrive.domain.MyDriveManager;
import pt.tecnico.myDrive.exception.InvalidTokenException;


/* This test involves the usage of mock-ups
 * to test the association between files
 * and their extensions.
 */
@RunWith(JMockit.class)
public class ExecuteAssociationTest extends AbstractServiceTest {

    @Override
    public void populate() {}


    @Test(expected = InvalidTokenException.class)
    public void success() {

    /*    System.out.println("CEEEEEEEENAAAAAAAAAAAAAAAAS!!!!!!!!!!!!!!!!!!!!!<<<<<<------------");
        System.out.println("CEEEEEEEENAAAAAAAAAAAAAAAAS!!!!!!!!!!!!!!!!!!!!!<<<<<<------------");
        System.out.println("CEEEEEEEENAAAAAAAAAAAAAAAAS!!!!!!!!!!!!!!!!!!!!!<<<<<<------------");
        System.out.println("CEEEEEEEENAAAAAAAAAAAAAAAAS!!!!!!!!!!!!!!!!!!!!!<<<<<<------------");
        System.out.println("CEEEEEEEENAAAAAAAAAAAAAAAAS!!!!!!!!!!!!!!!!!!!!!<<<<<<------------");
        System.out.println("CEEEEEEEENAAAAAAAAAAAAAAAAS!!!!!!!!!!!!!!!!!!!!!<<<<<<------------");
        System.out.println("CEEEEEEEENAAAAAAAAAAAAAAAAS!!!!!!!!!!!!!!!!!!!!!<<<<<<------------");
     */

        new MockUp<MyDriveManager>() {
            @Mock
            void executePlainFile(String _path, String _args, long _token) {
                throw new InvalidTokenException(_token);
            }
        };

        new MockUp<ExecuteFileService>() {

            String _pathToFile;
            String _argsToPass;
            long _tok;

            @Mock
            void $init(String pathToFile, String argsToPass, long tok) {
                _pathToFile = pathToFile;
                _argsToPass = argsToPass;
                _tok = tok;
            }

            @Mock
            void dispatch() {
                MyDriveManager mg = MyDriveManager.getInstance();
                mg.executePlainFile(_pathToFile, _argsToPass, _tok);
            }
        };

        ExecuteFileService execFile = new ExecuteFileService("cenas", "cenas", 123123123);
        execFile.execute();


    }

}
