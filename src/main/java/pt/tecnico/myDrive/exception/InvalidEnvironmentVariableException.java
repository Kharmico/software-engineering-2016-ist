package pt.tecnico.myDrive.exception;

public class InvalidEnvironmentVariableException extends MyDriveException {

    private static final long serialVersionUID = 1L;

    public InvalidEnvironmentVariableException() {
    }

    @Override
    public String getMessage() {
        return "Given String is not a valid Environment Variable";
    }
}
