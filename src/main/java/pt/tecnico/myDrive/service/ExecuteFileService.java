package pt.tecnico.myDrive.service;


public class ExecuteFileService extends MyDriveService {

    private long _token;
    private String _path;
    private String _args;

    public ExecuteFileService(String path, String args, long token) {
        _token = token;
        _path = path;
        _args = args;
    }

    @Override
    public void dispatch() {
        getMyDriveManager().executePlainFile(_path, _args, _token);
    }

}
