package pt.tecnico.myDrive.exception;

/**
 * Thrown when ID doesn't match to anything.
 */
public class IDUnknownException extends MyDriveException {
	private static final long serialVersionUID = 1L;

	private final int unmatchedID;

	public IDUnknownException(int id) {
		unmatchedID = id;
	}
	
	public int getUnmatchedID(){
		return unmatchedID;
	}

	@Override
	public String getMessage() {
		return "ID '" + unmatchedID + "' does not exist.";
	}
}