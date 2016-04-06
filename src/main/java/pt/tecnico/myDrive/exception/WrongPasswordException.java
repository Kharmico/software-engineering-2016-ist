package pt.tecnico.myDrive.exception;


public class WrongPasswordException extends MyDriveException{

    private static final long serialVersionUID = 1L;

    private final String wrongPassword;

    public WrongPasswordException(String password) {
        wrongPassword = password;
    }

    public String getOverSizeLimitPath(){
        return wrongPassword;
    }

    @Override
    public String getMessage() {
        return "Wrong Password.";
    }
}
