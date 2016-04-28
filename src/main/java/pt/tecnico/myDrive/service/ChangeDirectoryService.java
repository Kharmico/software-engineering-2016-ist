package pt.tecnico.myDrive.service;

import pt.tecnico.myDrive.exception.InvalidMaskException;
import pt.tecnico.myDrive.exception.InvalidTokenException;
import pt.tecnico.myDrive.exception.IsNotDirectoryException;


public class ChangeDirectoryService extends  MyDriveService {

    private long _token;
    private String _path;
    private String _finalPath;

    public ChangeDirectoryService(long token, String path){
        _token = token;
        _path = path;
    }

    public ChangeDirectoryService(long token){
        _token = token;
        _path = null;
    }

    @Override
    public void dispatch() throws IsNotDirectoryException, InvalidMaskException, InvalidTokenException {
        if(_path == null)
            _path = getMyDriveManager().getCurrentSession().getCurrentUser().getHomeDirectory().getPath();
        _finalPath = getMyDriveManager().changeDirectory(_path,_token);
    }

    public final String result(){
        return _finalPath;
    }
}
