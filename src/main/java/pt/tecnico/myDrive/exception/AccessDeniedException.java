package pt.tecnico.myDrive.exception;

import pt.tecnico.myDrive.domain.User;

/**
 * Thrown when a restricted operation is attempted.
 */
public class AccessDeniedException extends MyDriveException {

	private static final long serialVersionUID = 1L;

	private final String conflictingUser;

	public AccessDeniedException(User user) {
		conflictingUser = user.getUsername();
	}

	@Override
	public String getMessage() {
		return "User '" + conflictingUser + "' does not have permission to do this operation";
	}
}