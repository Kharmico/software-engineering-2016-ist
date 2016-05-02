package pt.tecnico.myDrive.exception;

public abstract class MyDriveException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    MyDriveException() {
    }

    MyDriveException(String msg) {
        super(msg);
    }
}