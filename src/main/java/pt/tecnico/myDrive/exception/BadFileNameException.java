package pt.tecnico.myDrive.exception;


public class BadFileNameException extends MyDriveException{

    private String _filename;

    public BadFileNameException(String filename){
        _filename = filename;
    }

    public String getFilename(){
        return _filename;
    }

    @Override
    public String getMessage() {
        return "'" + _filename + "' is not a valid filename.";
    }

}
