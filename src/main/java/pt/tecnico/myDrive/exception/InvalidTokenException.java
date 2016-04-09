package pt.tecnico.myDrive.exception;

/**
 * Thrown when token is not valid
 */
public class InvalidTokenException extends MyDriveException{

    private static final long serialVersionUID = 1L;

    private final long invalidToken;

    public InvalidTokenException(long token) {
        invalidToken = token;
    }

    public long getInvalidToken(){
        return invalidToken;
    }

    @Override
    public String getMessage(){
        return invalidToken + " is not valid.";
    }
}