package pt.tecnico.myDrive.presentation;

import pt.tecnico.myDrive.service.LoginUserService;

/**
 * Created by xxlxpto on 23-04-2016.
 */
public abstract class MdCommand extends Command {

    public MdCommand(MdShell sh, String n) { super(sh, n); }
    public MdCommand(MdShell sh, String n, String h) { super(sh, n, h); }

}
