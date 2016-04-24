package pt.tecnico.myDrive.exception;

/**
 * Thrown when an attempt is made to remove "." or ".."
 */
public class IllegalRemovalException extends MyDriveException {
	
	private static final long serialVersionUID = 1L;

	private final String removal;
	
	public IllegalRemovalException(String removalName){
		removal = removalName;
	}
	
	@Override
	public String getMessage() {
		return "You do not have permissions to remove '" + removal;
	}
}