package pt.tecnico.myDrive.exception;

/**
 * Thrown when an attempt is made to create a duplicate file.
 */
public class FileAlreadyExistsException extends MyDriveException {
	
	private static final long serialVersionUID = 1L;

	private final String duplicateFile;

	public FileAlreadyExistsException(String filename) {
		duplicateFile = filename;
	}

	@Override
	public String getMessage() {
		return duplicateFile + " already exists.";
	}
}