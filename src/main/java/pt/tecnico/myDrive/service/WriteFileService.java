package pt.tecnico.myDrive.service;

import pt.tecnico.myDrive.domain.File;
import pt.tecnico.myDrive.exception.FileUnknownException;

public class WriteFileService extends MyDriveService{

    private long _token;
    private String _filename;
    private String _content;

    public WriteFileService(long token, String filename, String content){
        _token = token;
        _filename = filename;
        _content = content;
    }

    @Override
    public void dispatch() throws FileUnknownException, UnsupportedOperationException {
        if(_token == getMyDriveManager().getCurrentSession().getToken()) {
            File toBeWritten = getMyDriveManager().getCurrentSession().getCurrentDir().getFileByName(_filename);
            toBeWritten.writeContent(_content);
        }
    }
}
