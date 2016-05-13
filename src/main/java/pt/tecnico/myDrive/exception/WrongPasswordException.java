package pt.tecnico.myDrive.exception;

/**
 * Thrown when a password is wrong
 */
public class WrongPasswordException extends MyDriveException{

    private static final long serialVersionUID = 1L;

    private final String wrongPassword;

    public WrongPasswordException(String password) {
        wrongPassword = password;
    }

    @Override
    public String getMessage() {
        return "Wrong Password.";
    }
}
