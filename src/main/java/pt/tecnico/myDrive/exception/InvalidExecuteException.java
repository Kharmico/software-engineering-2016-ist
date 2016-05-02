package pt.tecnico.myDrive.exception;

public class InvalidExecuteException extends MyDriveException {
    private static final long serialVersionUID = 1L;

    private final String _msg;

    public InvalidExecuteException(String msg) {
        _msg = msg;
    }

    @Override
    public String getMessage() {
        return _msg;
    }
}
