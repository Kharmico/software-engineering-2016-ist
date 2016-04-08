package pt.tecnico.myDrive.service;

import pt.tecnico.myDrive.domain.Session;

public class ListDirectoryService extends MyDriveService{

    private long _token;
   
    public ListDirectoryService(long token){
    	_token = token;
    }
    
    @Override
    public void dispatch() {
        Session currSes = getMyDriveManager().getCurrentSession();
     	if(_token == currSes.getToken()){   //FIXME
     		currSes.getCurrentDir().getDirectoryFilesName();
     	}
    }
}

/*List Directory
Efetua a listagem completa da diretoria corrente.  Este serviço recebe o token.
O serviço devolve as carateristicas relevantes de cada ficheiro na diretoria corrente*/