package pt.tecnico.myDrive.exception;

/**
 * Thrown when invocated class (by executing an App File) does not exist.
 */
public class ClassDoesNotExistException extends MyDriveException {

    private static final long serialVersionUID = 1L;

    private final String classNotExecuted;

    public ClassDoesNotExistException(String classTriedToExecute){
        classNotExecuted = classTriedToExecute;
    }

    @Override
    public String getMessage() {
        return "Class '" + classNotExecuted + "' that was tried to execute does not exist.";
    }
}
