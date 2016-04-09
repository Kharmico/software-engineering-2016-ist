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
    public void dispatch() throws UnsupportedOperationException, FileAlreadyExistsException {
        Session currSes = getMyDriveManager().getCurrentSession();
    	if(_token == currSes.getToken()) { //FIXME Use the new method -- ??
           /*try{
        	   currSes.getCurrentDir().getFileByName(_filename);
           } catch (FileUnknownException e){
        	 */  getMyDriveManager().createFile(_tipo,_filename, _content);
           //}
           //return;
    	}
    }
}