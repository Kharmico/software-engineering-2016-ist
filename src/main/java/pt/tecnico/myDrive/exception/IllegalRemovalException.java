package pt.tecnico.myDrive.exception;

/**
 * Thrown when an attempt is made to remove "." or ".."
 */
public class IllegalRemovalException extends MyDriveException {
	
	private static final long serialVersionUID = 1L;

	@Override
	public String getMessage() {
		return "It is not possible to remove \".\" or \"..\".";
	}
}