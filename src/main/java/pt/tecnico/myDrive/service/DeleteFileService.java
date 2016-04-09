package pt.tecnico.myDrive.service;

import pt.tecnico.myDrive.domain.Session;
import pt.tecnico.myDrive.domain.MyDriveManager;
import pt.tecnico.myDrive.domain.File;
import pt.tecnico.myDrive.exception.AccessDeniedException;
import pt.tecnico.myDrive.exception.FileUnknownException;
import pt.tecnico.myDrive.exception.IllegalRemovalException;
import pt.tecnico.myDrive.exception.InvalidTokenException;

// removeFile in domain is to do the remove files+dirs
public class DeleteFileService extends MyDriveService {
	
	private long _token;
	private String _filename;
	
	public DeleteFileService(long token, String filename) {
		_token = token;
		_filename = filename;
	}
	
	@Override
	public void dispatch() /*throws*/ {
		Session currentSession = getMyDriveManager().getCurrentSession();
		
		if(_token == currentSession.getToken()){
			getMyDriveManager().removeFile(_filename);
		}
	}
	
}