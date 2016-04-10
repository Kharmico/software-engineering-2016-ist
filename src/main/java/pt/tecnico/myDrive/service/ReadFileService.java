package pt.tecnico.myDrive.service;

public class ReadFileService extends MyDriveService{

    private long _token;
    private String _filename;
    private String _content;

    public ReadFileService(long token, String filename){
        _token = token;
        _filename = filename;
    }

    @Override
    public void dispatch(){
        _content = getMyDriveManager().readFile(_filename,_token);
    }

    public String result(){return _content;}

}
