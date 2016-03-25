package pt.tecnico.myDrive.service;


import pt.tecnico.myDrive.exception.MyDriveException;

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
    protected void dispatch() throws MyDriveException{
        // TO DO
    }

}
