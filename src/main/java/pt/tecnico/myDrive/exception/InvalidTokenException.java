package pt.tecnico.myDrive.exception;

/**
 * Thrown when token is not valid
 */
public class InvalidTokenException extends MyDriveException{

    private static final long serialVersionUID = 1L;

    private final String invalidToken;

    public InvalidTokenException(String password) {
        invalidToken = password;
    }

    public String getInvalidToken(){
        return invalidToken;
    }

    @Override
    public String getMessage() {
        return "Session is not active.";
    }
}