package pt.tecnico.myDrive.exception;

public class ExportDocumentException extends MyDriveException {

    private static final long serialVersionUID = 1L;

    public ExportDocumentException() {
        super("Error in exporting into XML");
    }
}