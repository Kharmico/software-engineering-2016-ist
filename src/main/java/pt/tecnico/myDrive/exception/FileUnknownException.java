package pt.tecnico.myDrive.exception;

/**
 * Thrown when an attempt is made to use an unknown file.
 */
public class FileUnknownException extends MyDriveException {
	
	private static final long serialVersionUID = 1L;

	private final String unknownFile;

	public FileUnknownException(String file) {
		unknownFile = file;
	}

	@Override
	public String getMessage() {
		return unknownFile + " does not exist.";
	}
}