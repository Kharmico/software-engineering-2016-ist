package pt.tecnico.myDrive.service;

import pt.tecnico.myDrive.domain.Session;

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