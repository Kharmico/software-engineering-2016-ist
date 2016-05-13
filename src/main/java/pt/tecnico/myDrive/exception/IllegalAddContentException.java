package pt.tecnico.myDrive.exception;

/**
 * Thrown when a trying to add content to a link
 */
public class IllegalAddContentException extends MyDriveException{

    private static final long serialVersionUID = 1L;

    public IllegalAddContentException() {}

    @Override
    public String getMessage() {
        return "Cannot add content to a link file.";
    }
}
