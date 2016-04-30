package pt.tecnico.myDrive.service;


public class ExecuteFileService extends MyDriveService {

    private long _token;
    private String _path;

    public ExecuteFileService(long token, String path) {
        _token = token;
        _path = path;
    }

    @Override
    public void dispatch() {}

    public final String result(){
        return "";
    }

}
