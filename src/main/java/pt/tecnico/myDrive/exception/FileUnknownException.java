package pt.tecnico.myDrive.exception;

import pt.tecnico.myDrive.domain.File;

/**
 * Thrown when an attempt is made to use an unknown file.
 */
public class FileUnknownException extends MyDriveException {
	
	private static final long serialVersionUID = 1L;

	private final File unknownFile;

	public FileUnknownException(File file) {
		unknownFile = file;
	}
	
	public File getUnknownFile(){
		return unknownFile;
	}

	@Override
	public String getMessage() {
		return unknownFile + "' does not exist.";
	}
}