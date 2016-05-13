package pt.tecnico.myDrive.exception;

/**
 * Thrown when an invalid attempt is made to use an entry.
 */
public class IsNotPlainFileException extends MyDriveException {
	private static final long serialVersionUID = 1L;

	private final String conflictingPlainFileName;

	public IsNotPlainFileException(String name) {
		conflictingPlainFileName = name;
	}

	@Override
	public String getMessage() {
		return conflictingPlainFileName + " is not a plain file.";
	}
}