package pt.tecnico.myDrive.service;

public class ListDirectoryService extends MyDriveService{

    private long _token;
    private String _out;
   
    public ListDirectoryService(long token){
    	_token = token;
    }
    
    @Override
    public void dispatch() {
        _out = getMyDriveManager().getDirectoryFilesName(_token);
    }

    public final String result(){
        return _out;
    }
}

/*List Directory
Efetua a listagem completa da diretoria corrente.  Este serviço recebe o token.
O serviço devolve as carateristicas relevantes de cada ficheiro na diretoria corrente*/