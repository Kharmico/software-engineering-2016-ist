package pt.tecnico.myDrive.exception;

/**
 * Thrown when a password has less than 8 characters.
 */
public class PasswordIsTooWeakException extends MyDriveException {

    private static final long serialVersionUID = 1L;

    public PasswordIsTooWeakException() {}

    @Override
    public String getMessage() {
        return "Password needs to be at least 8 characters long.";
    }
}
