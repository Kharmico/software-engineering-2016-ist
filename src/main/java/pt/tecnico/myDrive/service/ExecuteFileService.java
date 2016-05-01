package pt.tecnico.myDrive.service;


public class ExecuteFileService extends MyDriveService {

    private long _token;
    private String _path;
    private String _args[];

    public ExecuteFileService(long token, String path, String args[]) {
        _token = token;
        _path = path;
        _args = args;
    }

    @Override
    public void dispatch() {
        //getMyDriveManager().executeFile(_token, _path, _args);
    }

}
