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
}
