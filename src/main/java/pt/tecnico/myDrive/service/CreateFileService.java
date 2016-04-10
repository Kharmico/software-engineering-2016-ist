package pt.tecnico.myDrive.service;

import pt.tecnico.myDrive.exception.FileAlreadyExistsException;

public class CreateFileService extends MyDriveService{

    private long _token;
    private String _filename;
    private String _type;
    private String _content = "";
    

    public CreateFileService(long token, String filename, String type){
    	_token = token;
    	_filename = filename;
    	_type = type;
    }
    public CreateFileService(long token, String filename, String type, String content){
        _token = token;
        _filename = filename;
        _type = type;
        _content = content;
    }

    @Override
    public void dispatch() throws UnsupportedOperationException, FileAlreadyExistsException {
        if(_content.equals(""))
            getMyDriveManager().createFile(_type, _filename, _token);
        else { getMyDriveManager().createFile(_type,_filename, _content, _token); }
    }
}