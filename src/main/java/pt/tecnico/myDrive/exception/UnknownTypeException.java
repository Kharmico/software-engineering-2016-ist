package pt.tecnico.myDrive.exception;

/**
 * Thrown when an attempt is made to create a file with unknown type.
 */
public class UnknownTypeException extends MyDriveException {
	
	private static final long serialVersionUID = 1L;

	private final String nonExistingType;

	public UnknownTypeException(String type) {
		nonExistingType = type;
	}

	@Override
	public String getMessage() {
		return "The type '" + nonExistingType + "' does not exist.\n" +
				"Types that exist: app, link, plain, directory";
	}
}