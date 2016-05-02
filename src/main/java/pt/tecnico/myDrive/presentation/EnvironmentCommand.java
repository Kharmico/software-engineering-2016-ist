package pt.tecnico.myDrive.presentation;


import pt.tecnico.myDrive.service.AddEnvironmentVariableService;

import java.util.HashMap;
import java.util.LinkedHashMap;

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
            case 0: AddEnvironmentVariableService envVarUse =
                        new AddEnvironmentVariableService(((MdShell) shell()).getCurrentToken());
                    envVarUse.execute();
                    LinkedHashMap<String, String> envVarList = envVarUse.result();
                    for(String key: envVarList.keySet())
                        shell().println(key + " = " + envVarList.get(key));
                    break;
            case 1: AddEnvironmentVariableService envVarName =
                        new AddEnvironmentVariableService(((MdShell) shell()).getCurrentToken());
                    envVarName.execute();
                    LinkedHashMap<String, String> envList = envVarName.result();
                    boolean printed = false;
                    for(String key: envList.keySet()){
                        if (args[0].equals(key)) {
                            shell().println(key + " = " + envList.get(key));
                            printed = true;
                            break;
                        }
                    }
                    if (!printed)
                        shell().println(args[0] + " is not, currently, an environment variable.");
                    break;
            case 2: AddEnvironmentVariableService envVar = new AddEnvironmentVariableService(((MdShell) shell()).getCurrentToken(),
                                                                args[0], args[1]);
                    envVar.execute();
                    break;
            default: throw new RuntimeException("USAGE: " + name() + " <name <value>>");
        }
    }

}
