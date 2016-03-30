package pt.tecnico.myDrive.exception;

/**
 * Thrown when a link has no content.
 */
public class LinkFileWithoutContentException extends MyDriveException {

    private static final long serialVersionUID = 1L;

    private final String linkWithoutContent;

    public LinkFileWithoutContentException(String name) {
        linkWithoutContent = name;
    }

    public String getLinkWithoutContent(){
        return linkWithoutContent;
    }

    @Override
    public String getMessage() {
        return "'" + linkWithoutContent + "' has an empty content.";
    }
}
