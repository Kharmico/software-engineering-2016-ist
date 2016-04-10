package pt.tecnico.myDrive.service;

import pt.tecnico.myDrive.domain.Session;
import pt.tecnico.myDrive.exception.FileAlreadyExistsException;

public class CreateFileService extends MyDriveService{

    private long _token;
    private String _filename;
    private String _tipo;
    private String _content = "";
    

    public CreateFileService(long token, String filename, String tipo){
    	_token = token;
    	_filename = filename;
    	_tipo = tipo;
    }
    public CreateFileService(long token, String filename, String tipo, String content){
        _token = token;
        _filename = filename;
        _tipo = tipo;
        _content = content;
    }

    @Override
    public void dispatch() throws UnsupportedOperationException, FileAlreadyExistsException {
        if(_content.equals(""))
            getMyDriveManager().createFile(_tipo, _filename, _token);
        else { getMyDriveManager().createFile(_tipo,_filename, _content, _token); }
    }
}