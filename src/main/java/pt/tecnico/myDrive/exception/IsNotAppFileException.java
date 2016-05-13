package pt.tecnico.myDrive.exception;

/**
 * Thrown when an invalid attempt is made to use an entry.
 */
public class IsNotAppFileException extends MyDriveException {
	private static final long serialVersionUID = 1L;

	private final String conflictingAppFileName;

	public IsNotAppFileException(String name) {
		conflictingAppFileName = name;
	}

	@Override
	public String getMessage() {
		return conflictingAppFileName + " is not an app file.";
	}
}