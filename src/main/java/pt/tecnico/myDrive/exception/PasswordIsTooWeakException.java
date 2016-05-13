package pt.tecnico.myDrive.exception;

/**
 * Thrown when a password has less than 8 characters.
 */
public class PasswordIsTooWeakException extends MyDriveException {

    private static final long serialVersionUID = 1L;
    private final String _password;

    public PasswordIsTooWeakException(String password){
        _password = password;
    }

    @Override
    public String getMessage() {
        return "Password " + _password + " needs to be at least 8 characters long.";
    }
}
