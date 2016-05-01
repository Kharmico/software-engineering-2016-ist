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

        //maybe use a switch-case, looks cleaner?!?, in which the default case is the "error" case
        switch (args.length) {
            case 0: //TODO: ((MdShell) shell()).getEnvVarList();
                    break;
            case 1: //TODO: ((MdShell) shell()).getEnvVarValue();  (error if it doesn't exist, let user know)
                    break;
            case 2: AddEnvironmentVariableService envVar = new AddEnvironmentVariableService(((MdShell) shell()).getCurrentToken(),
                                                                args[0], args[1]);
                    envVar.execute();
                    break;
            default: throw new RuntimeException("USAGE: " + name() + " <name <value>>");
        }
    }

}
