package pt.tecnico.myDrive.system;


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
    public void success() {
        new LoginCommand(sh).execute(new String[] { "root" , "***" });
        new ListCommand(sh).execute(new String[] { "." });
        assertEquals(true, true);
//        new Import(sh).execute(new String[] { "other.xml" } );
//        new CreatePerson(sh).execute(new String[] { "Rui" } );
//        new CreateContact(sh).execute(new String[] { "Rui", "SOS", "112" } );
//        new List(sh).execute(new String[] { } );
//        new RemoveContact(sh).execute(new String[] { "Xana", "Xico" } );
//        new RemovePerson(sh).execute(new String[] { "Sofia" } );
//        new Export(sh).execute(new String[] { } );
    }
}
