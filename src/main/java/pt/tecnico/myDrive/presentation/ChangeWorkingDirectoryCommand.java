package pt.tecnico.myDrive.presentation;

import pt.tecnico.myDrive.domain.MyDriveManager;
import pt.tecnico.myDrive.service.ChangeDirectoryService;

/**
 * Created by xxlxpto on 23-04-2016.
 */
public class ChangeWorkingDirectoryCommand extends MdCommand {

    public ChangeWorkingDirectoryCommand(Shell sh) { super(sh, "cwd", "it changes the working directory"); }
    @Override
    void execute(String[] args) {
        if (args.length < 1)
            throw new RuntimeException("USAGE: " + name() + " <path>");
        // FIXME: We should get the token elsewhere, in this case we are breaking the layers to get it.
        System.out.println(MyDriveManager.getInstance().getCurrentSession().getToken());
        new ChangeDirectoryService(MyDriveManager.getInstance().getCurrentSession().getToken(), args[0]).execute();
    }
}
