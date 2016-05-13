package pt.tecnico.myDrive.exception;

/**
 * Thrown when an invalid username is used.
 */

public class InvalidUsernameException extends MyDriveException {
	
	private static final long serialVersionUID = 1L;
	
	private final String invalidUsername;
	
	public InvalidUsernameException(String username){
		invalidUsername = username;
	}

	@Override
	public String getMessage(){
		return invalidUsername + " is not a valid username.\n" +
				"It should be only numbers or letters and at least 3 characters long.";
	}
}