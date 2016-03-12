package pt.tecnico.myDrive.exception;

/**
 * Thrown when an invalid mask name is used.
 */

public class InvalidMaskException extends MyDriveException {
	
	private static final long serialVersionUID = 1L;
	
	private final String invalidMask;
	
	public InvalidMaskException(String mask){
		invalidMask = mask;
	}
	
	public String getInvalidMaskName(){
		return invalidMask;
	}
	
	@Override
	public String getMessage(){
		return "'" + invalidMask + "' is not a valid mask.";
	}
}