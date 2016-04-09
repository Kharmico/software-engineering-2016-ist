package pt.tecnico.myDrive.service;

import pt.tecnico.myDrive.domain.Session;
import pt.tecnico.myDrive.exception.FileAlreadyExistsException;
import pt.tecnico.myDrive.exception.FileUnknownException;
import pt.tecnico.myDrive.exception.IsNotPlainFileException;

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
        Session currSes = getMyDriveManager().getCurrentSession();
    	if(_token == currSes.getToken()) { //FIXME Use the new method -- ??
            try{
            	currSes.getCurrentDir().getFileByName(_filename);
            }catch (FileUnknownException e){
            	switch(_tipo.toLowerCase()){
                    case "app":
                        getMyDriveManager().createAppFile(_filename, _content);
                        break;
                    case "link":
                        getMyDriveManager().createLinkFile(_filename, _content);
                        break;
                    case "plain":
                    	getMyDriveManager().createPlainFile(_filename, _content);
                        break;
                    case "directory":
                        if(!(_content.equals("")))
                        	throw new IsNotPlainFileException(_filename);
                        getMyDriveManager().createDirectory(_filename);
                        break;
            	}
                return;
            }
            throw new FileAlreadyExistsException(_filename);
    	}
    }
}