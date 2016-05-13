package pt.tecnico.myDrive.exception;

/**
 * Thrown when there is invalid content for files.
 */

public class InvalidContentException extends MyDriveException {
	
	private static final long serialVersionUID = 1L;
	
	private final String invalidContent;
	
	public InvalidContentException(String content){
		invalidContent = content;
	}

	@Override
	public String getMessage(){
		return invalidContent + " is not a valid content.";
	}
}