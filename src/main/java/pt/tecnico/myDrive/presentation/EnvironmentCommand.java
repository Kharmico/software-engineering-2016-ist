package pt.tecnico.myDrive.presentation;


import pt.tecnico.myDrive.service.AddEnvironmentVariableService;

public class EnvironmentCommand extends MdCommand {

    public EnvironmentCommand(MdShell sh) {
        super(sh, "env", "it changes an environment variable to the given value, or returns its value.\n" +
                "Both the environment variable's name and value are optional.\n" +
                "Can also list all environment variables.");
    }

    @Override
    public void execute(String args[]) {

        if(args.length < 1)
            throw new RuntimeException("USAGE: " + name() + " <name <value>>");
        else if(args.length == 1)
            return;
            //new AddEnvironmentVariableService();
        else
            return;
            //new AddEnvironmentVariableService();
    }
}
