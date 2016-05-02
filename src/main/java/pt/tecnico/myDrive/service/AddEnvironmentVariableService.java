package pt.tecnico.myDrive.service;


import java.util.LinkedHashMap;

public class AddEnvironmentVariableService extends MyDriveService {

    private long _token;
    private String _name;
    private String _value;

    public AddEnvironmentVariableService(long token, String name, String value){
        _token = token;
        _name = name;
        _value = value;
    }

    @Override
    public void dispatch(){ getMyDriveManager().addEnvironmentVariable(_name, _value, _token);
    }

    public LinkedHashMap<String, String> result(){return getMyDriveManager().listEnvironmentVariables(_token);}
}
