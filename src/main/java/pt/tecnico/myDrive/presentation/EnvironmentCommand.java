package pt.tecnico.myDrive.presentation;


import pt.tecnico.myDrive.service.AddEnvironmentVariableService;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class EnvironmentCommand extends MdCommand {

    public EnvironmentCommand(MdShell sh) {
        super(sh, "env", "it changes an environment variable to the given value, or returns its value.\n" +
                "Both the environment variable's name and value are optional.\n" +
                "Can also list all environment variables.");
    }

    @Override
    public void execute(String args[]) {

        switch (args.length) {
            case 0: AddEnvironmentVariableService envVarUse = new AddEnvironmentVariableService(((MdShell) shell()).getCurrentToken());
                    LinkedHashMap<String, String> envVarList = envVarUse.result();
                    for(HashMap.Entry<String, String> var : envVarList.entrySet())
                        System.out.printf("%s = %s", var.getKey(), var.getValue());
                    break;
            case 1: /*AddEnvironmentVariableService envVarUse = new AddEnvironmentVariableService(((MdShell) shell()).getCurrentToken());
                    LinkedHashMap<String, String> envVarList = envVarUse.result(((MdShell) shell()).getCurrentToken());
                    for(String var : envVarList)
                      */
                    //TODO: ((MdShell) shell()).getEnvVarValue();  (error if it doesn't exist, let user know)
                    break;
            case 2: AddEnvironmentVariableService envVar = new AddEnvironmentVariableService(((MdShell) shell()).getCurrentToken(),
                                                                args[0], args[1]);
                    envVar.execute();
                    break;
            default: throw new RuntimeException("USAGE: " + name() + " <name <value>>");
        }
    }

}
