package pt.tecnico.myDrive.service;

import pt.tecnico.myDrive.exception.InvalidMaskException;
import pt.tecnico.myDrive.exception.InvalidTokenException;
import pt.tecnico.myDrive.exception.IsNotDirectoryException;


public class ChangeDirectoryService extends  MyDriveService {

    private long _token;
    private String _path;

    public ChangeDirectoryService(long token, String path){
        _token = token;
        _path = path;
    }

    @Override
    public void dispatch() throws IsNotDirectoryException, InvalidMaskException, InvalidTokenException {
        getMyDriveManager().changeDirectory(_path,_token);
    }
}
