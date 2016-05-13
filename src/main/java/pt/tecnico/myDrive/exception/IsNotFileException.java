package pt.tecnico.myDrive.exception;

/**
 * Thrown when an invalid attempt is made to use an entry.
 */
public class IsNotFileException extends MyDriveException {
	private static final long serialVersionUID = 1L;

	private final String conflictingFileName;

	public IsNotFileException(String fileName) {
		conflictingFileName = fileName;
	}

	@Override
	public String getMessage() {
		return conflictingFileName + " is not a file.";
	}
}