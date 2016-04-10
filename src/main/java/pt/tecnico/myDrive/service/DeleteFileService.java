package pt.tecnico.myDrive.service;

import pt.tecnico.myDrive.exception.AccessDeniedException;
import pt.tecnico.myDrive.exception.FileUnknownException;
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
	public void dispatch() throws InvalidTokenException, AccessDeniedException, FileUnknownException {
		getMyDriveManager().removeFile(_filename, _token);
	}
	
}