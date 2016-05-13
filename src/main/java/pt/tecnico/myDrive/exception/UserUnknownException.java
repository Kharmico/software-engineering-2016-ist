package pt.tecnico.myDrive.exception;

/**
 * Thrown when an attempt is made to use an unknown username.
 */
public class UserUnknownException extends MyDriveException {
	private static final long serialVersionUID = 1L;

	private final String missingUsername;

	public UserUnknownException(String username) {
		missingUsername = username;
	}

	@Override
	public String getMessage() {
		return "User '" + missingUsername + "' does not exist.";
	}
}