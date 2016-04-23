package pt.tecnico.myDrive.presentation;

/**
 * Created by xxlxpto on 23-04-2016.
 */
public class MdShell extends Shell {
    public static void main(String[] args) throws Exception {
        MdShell sh = new MdShell();
        sh.execute();
    }

    public MdShell() { // add commands here
        super("MyDrive");
        new ChangeWorkingDirectoryCommand(this);
//        new CreateContact(this);
//        new RemovePerson(this);
//        new RemoveContact(this);
//        new List(this);
//        new Import(this);
//        new Export(this);
    }
}
