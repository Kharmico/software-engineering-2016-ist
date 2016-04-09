package pt.tecnico.myDrive.service;

import pt.tecnico.myDrive.domain.Directory;
import pt.tecnico.myDrive.domain.FileSystem;
import pt.tecnico.myDrive.domain.Session;
import pt.tecnico.myDrive.exception.InvalidMaskException;
import pt.tecnico.myDrive.exception.InvalidTokenException;
import pt.tecnico.myDrive.exception.IsNotDirectoryException;
import pt.tecnico.myDrive.exception.MyDriveException;


public class ChangeDirectoryService extends  MyDriveService {

    private long _token;
    private String _path;

    public ChangeDirectoryService(long token, String path){
        _token = token;
        _path = path;
    }


    @Override
    public void dispatch() throws IsNotDirectoryException, InvalidMaskException, InvalidTokenException {
        if(_token == getMyDriveManager().getCurrentSession().getToken()) {
            getMyDriveManager().changeDirectory(_path);
        }
    }
}
