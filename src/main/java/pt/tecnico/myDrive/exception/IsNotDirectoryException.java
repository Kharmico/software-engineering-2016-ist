package pt.tecnico.myDrive.exception;

/**
 * Thrown when an invalid attempt is made to use an entry.
 */
public class IsNotDirectoryException extends MyDriveException {
	private static final long serialVersionUID = 1L;

	private final String conflictingEntryName;

	public IsNotDirectoryException(String entryName) {
		conflictingEntryName = entryName;
	}

	public String getEntryName(){
		return conflictingEntryName;
	}
	
	@Override
	public String getMessage() {
		return "'" + conflictingEntryName + "' is not a directory.";
	}
}