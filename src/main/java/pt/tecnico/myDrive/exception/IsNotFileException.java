package pt.tecnico.myDrive.exception;

/**
 * Thrown when an invalid attempt is made to use an entry.
 */
public class IsNotFileException extends MyDriveException {
	private static final long serialVersionUID = 1L;

	private final String conflictingEntryName;

	public IsNotFileException(String entryName) {
		conflictingEntryName = entryName;
	}
	
	public String getConflictingEntryName(){
		return conflictingEntryName;
	}

	@Override
	public String getMessage() {
		return "Entry'" + conflictingEntryName + "' is not a file.";
	}
}