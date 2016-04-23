package pt.tecnico.myDrive.presentation;

/**
 * Created by xxlxpto on 23-04-2016.
 */
public abstract class MdCommand extends Command {
    public MdCommand(Shell sh, String n) { super(sh, n); }
    public MdCommand(Shell sh, String n, String h) { super(sh, n, h); }
}
