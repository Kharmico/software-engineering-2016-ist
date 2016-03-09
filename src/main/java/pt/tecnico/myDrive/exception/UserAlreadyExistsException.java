package pt.tecnico.myDrive.exception;

/**
 * Thrown when an attempt is made to create a duplicate user.
 */
public class UserAlreadyExistsException extends MyDriveException {

	private static final long serialVersionUID = 1L;

	private final String conflictingUsername;

	public UserAlreadyExistsException(String username) {
		conflictingUsername = username;
	}
	
	public String getConflictingUsername(){
		return conflictingUsername;
	}

	@Override
	public String getMessage() {
		return "User'" + conflictingUsername + "' already exists.";
	}
}