package pt.tecnico.myDrive.exception;

/**
 * Thrown when an invalid attempt is made to use an entry.
 */
public class IsNotLinkFileException extends MyDriveException {
	private static final long serialVersionUID = 1L;

	private final String conflictingLinkFileName;

	public IsNotLinkFileException(String name) {
		conflictingLinkFileName = name;
	}

	@Override
	public String getMessage() {
		return conflictingLinkFileName + " is not a link file.";
	}
}