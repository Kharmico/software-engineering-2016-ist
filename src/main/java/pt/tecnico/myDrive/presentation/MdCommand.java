package pt.tecnico.myDrive.presentation;

/**
 * Created by xxlxpto on 23-04-2016.
 */
abstract class MdCommand extends Command {

    public MdCommand(MdShell sh, String n) { super(sh, n); }
    MdCommand(MdShell sh, String n, String h) { super(sh, n, h); }

}
