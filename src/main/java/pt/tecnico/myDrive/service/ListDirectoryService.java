package pt.tecnico.myDrive.service;

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
    public void dispatch() {
    	_out = getMyDriveManager().getDirectoryFilesName(_path, _token);
    }

    public final String result(){
        return _out;
    }
}

/*List Directory
Efetua a listagem completa da diretoria corrente.  Este serviço recebe o token.
O serviço devolve as carateristicas relevantes de cada ficheiro na diretoria corrente*/