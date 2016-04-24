package pt.tecnico.myDrive.service;


public class ExecuteFileService extends MyDriveService {

    private String _token;
    private String _path;

    public ExecuteFileService(String token, String path) {
        _token = token;
        _path = path;
    }

    @Override
    public void dispatch() {}

}
