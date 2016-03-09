package pt.tecnico.myDrive.exception;

import pt.tecnico.myDrive.domain.File;

/**
 * Thrown when an attempt is made to create a duplicate file.
 */
public class FileAlreadyExistsException extends MyDriveException {
	
	private static final long serialVersionUID = 1L;

	private final File duplicateFile;

	public FileAlreadyExistsException(File file) {
		duplicateFile = file;
	}

	public File getDuplicateFile(){
		return duplicateFile;
	}
	
	@Override
	public String getMessage() {
		return duplicateFile + "' already exists.";
	}
}