package pt.tecnico.myDrive.service;

import pt.tecnico.myDrive.exception.AccessDeniedException;
import pt.tecnico.myDrive.exception.InvalidTokenException;


/*List Directory
Lists the current directory. This service receives a token.
It returns the relevant characteristics of each file in the current directory*/
public class ListDirectoryService extends MyDriveService{

    private long _token;
    private String _out = "";
    private String _path = ".";
   
    public ListDirectoryService(long token){
        _token = token;
    }
    
    public ListDirectoryService(long token, String path){
    	_token = token;
    	_path = path;
    }
    
    @Override
    public void dispatch()  throws InvalidTokenException, AccessDeniedException {
        _out = getMyDriveManager().getDirectoryFilesName(_path, _token);
    }

    public final String result(){
        return _out;
    }
}

