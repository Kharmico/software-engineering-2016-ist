package pt.tecnico.myDrive.service;

import pt.tecnico.myDrive.exception.AccessDeniedException;
import pt.tecnico.myDrive.exception.InvalidTokenException;

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

/*List Directory
Efetua a listagem completa da diretoria corrente.  Este serviço recebe o token.
O serviço devolve as carateristicas relevantes de cada ficheiro na diretoria corrente*/