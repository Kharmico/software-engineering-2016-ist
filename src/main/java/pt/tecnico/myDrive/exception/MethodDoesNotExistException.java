package pt.tecnico.myDrive.exception;

/**
 * Thrown when invocated method (by executing an App File) does not exist.
 */
public class MethodDoesNotExistException extends MyDriveException {

    private static final long serialVersionUID = 1L;

    private final String classTriedToExecute;
    private final String methodNotExecuted;

    public MethodDoesNotExistException(String classTried, String methodTried) {
        classTriedToExecute = classTried;
        methodNotExecuted = methodTried;
    }

    @Override
    public String getMessage() {
        return "Method '" + methodNotExecuted + "' of Class '" + classTriedToExecute + "' that was tried to execute was not found.";
    }

}
