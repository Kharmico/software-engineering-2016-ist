package pt.tecnico.myDrive.exception;

/**
 * Thrown when a path exceeds the size of 1024 chars.
 */
public class PathIsTooBigException extends MyDriveException{

    private static final long serialVersionUID = 1L;

    private final String overSizeLimitPath;

    public PathIsTooBigException(String path) {
        overSizeLimitPath = path;
    }

    public String getOverSizeLimitPath(){
        return overSizeLimitPath;
    }

    @Override
    public String getMessage() {
        return "'" + overSizeLimitPath + "' exceeds the path size limit.";
    }
}
