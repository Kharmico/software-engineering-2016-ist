package pt.tecnico.myDrive.service;

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
        System.out.println("DISPATCH    ");
        getMyDriveManager().writeContent(_filename, _content, _token);
    }
}