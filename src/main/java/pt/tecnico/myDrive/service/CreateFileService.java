package pt.tecnico.myDrive.service;

import pt.tecnico.myDrive.domain.FileSystem;
import pt.tecnico.myDrive.domain.Session;
import pt.tecnico.myDrive.exception.FileAlreadyExistsException;
import pt.tecnico.myDrive.exception.FileUnknownException;

public class CreateFileService extends MyDriveService{

    private long _token;
    private String _filename;
    private String _tipo;
    private String _content = "";
    

    public CreateFileService(long token, String filename, String tipo){
    	_token = token;
    	_filename = filename;
    	_tipo = tipo;
    }
    public CreateFileService(long token, String filename, String tipo, String content){
        _token = token;
        _filename = filename;
        _tipo = tipo;
        _content = content;
    }

    @Override
    public void dispatch() throws FileAlreadyExistsException, UnsupportedOperationException, FileUnknownException {
        FileSystem fs = getMyDriveManager().getFilesystem();
        Session currSes = getMyDriveManager().getCurrentSession();
    	if(_token == currSes.getToken()) { //FIXME Use the new method -- ??
            try{
            	currSes.getCurrentDir().getFileByName(_filename);
            }catch (FileAlreadyExistsException e){	
            	switch(_tipo.toLowerCase()){
            	case "app":
            		fs.createAppFile(_filename, currSes.getCurrentDir(), currSes.getCurrentUser(), _content);
            		break;
            	case "link":
            		fs.createLinkFile(_filename, currSes.getCurrentDir(), currSes.getCurrentUser(), _content);
            		break;
            	case "plain":
            		fs.createPlainFile(_filename, currSes.getCurrentDir(), currSes.getCurrentUser(), _content);
            		break;
            	case "directory":
            		fs.createDirectory(_filename, currSes.getCurrentDir(), currSes.getCurrentUser());
            		break;
            	}
            }
            throw new FileUnknownException(_filename);
    	}
    }
}

/*Cria um ficheiro na diretoria corrente. Este servico recebe o token, o nome a atribuir ao
ficheiro, o tipo de ficheiro a criar e, eventualmente, um conteudo. Notar que a criacao de
uma diretoria nao recebe conteudo, enquanto um ficheiro de ligacao (link)
recebe obrigatoriamente um caminho. No caso dos restantes tipos de ficheiros, o conteudo e opcional. 
O ficheiro e criado com as permissoes correspondentes a mascara do utilizador que o criou.*/