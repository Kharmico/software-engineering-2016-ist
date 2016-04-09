package pt.tecnico.myDrive.service;

public class ReadFileService extends MyDriveService{

    private long _token;
    private String _filename;
    private String _content;

    public ReadFileService(long token, String filename){
        _token = token;
        _filename = filename;
    }

    public void dispatch(){
        if(_token == getMyDriveManager().getCurrentSession().getToken()){
            String filecontent = getMyDriveManager().getCurrentSession().getCurrentDir().getFileByName(_filename).printContent();
            _content = filecontent;
        }
    }

    public String contente(){return _content;}

}
