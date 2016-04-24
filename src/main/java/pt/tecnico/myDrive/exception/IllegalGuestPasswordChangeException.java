package pt.tecnico.myDrive.exception;

/**
 * Thrown when trying to change the Guest's password.
 */
public class IllegalGuestPasswordChangeException extends MyDriveException {

    private static final long serialVersionUID = 1L;

    @Override
    public String getMessage() {
     return "Changing password of Guest is not permitted.";
    }

}
