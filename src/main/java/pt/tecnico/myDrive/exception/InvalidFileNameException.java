package pt.tecnico.myDrive.exception;

/**
 * Thrown when an invalid name is used to a file.
 */

public class InvalidFileNameException extends MyDriveException {
	
	private static final long serialVersionUID = 1L;
	
	private final String invalidFileName;
	
	public InvalidFileNameException(String filename){
		invalidFileName = filename;
	}
	
	public String getInvalidFileName(){
		return invalidFileName;
	}
	
	@Override
	public String getMessage(){
		return "'" + invalidFileName + "' is not a valid file name.";
	}
}