package pt.tecnico.myDrive.service;


import java.util.LinkedHashMap;

public class AddEnvironmentVariableService extends MyDriveService {

    private long _token;
    private String _name;
    private String _value;
    private LinkedHashMap<String, String> a = new LinkedHashMap<>();

    public AddEnvironmentVariableService(long token, String name, String value){
        _token = token;
        _name = name;
        _value = value;
    }

    public AddEnvironmentVariableService(long token){
        _token = token;
        _value = null;
        _name = null;
    }

    @Override
    public void dispatch(){
        if(!_name.equals(null) && !_value.equals(null))
            getMyDriveManager().addEnvironmentVariable(_name, _value, _token);
        LinkedHashMap<String, String> result = getMyDriveManager().listEnvironmentVariables(_token);
        for(String key : result.keySet())
            a.put(new String(key), new String(result.get(key)));

    }

    public LinkedHashMap<String, String> result(){return a;}
}
