package pt.tecnico.myDrive.exception;

/**
 * Thrown when a trying to add content to a link
 */
public class IllegalAddContentException extends MyDriveException{

    private static final long serialVersionUID = 1L;

    private final String illegalContent;

    public IllegalAddContentException(String content) {
        illegalContent = content;
    }

    public String getIllegalAddContent(){
        return illegalContent;
    }

    @Override
    public String getMessage() {
        return "Cannont add content to a link file.";
    }
}
