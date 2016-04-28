package pt.tecnico.myDrive.exception;

/**
 * Thrown when an invalid attempt is made to use an entry.
 */
public class LoopLinkFileException extends MyDriveException {
	private static final long serialVersionUID = 1L;

	private final String conflictingLinkFileName;

	public LoopLinkFileException(String name) {
		conflictingLinkFileName = name;
	}
	
	public String getConflictingLinkFileName(){
		return conflictingLinkFileName;
	}

	@Override
	public String getMessage() {
		return "'" + conflictingLinkFileName + "' is being linked to himself.";
	}
}