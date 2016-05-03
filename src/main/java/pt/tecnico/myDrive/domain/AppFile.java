package pt.tecnico.myDrive.domain;


import java.lang.reflect.Method;

import org.apache.commons.lang.StringUtils;
import org.jdom2.Element;
import pt.tecnico.myDrive.exception.InvalidContentException;
import pt.tecnico.myDrive.exception.InvalidExecuteException;
import pt.tecnico.myDrive.exception.InvalidFileNameException;
import pt.tecnico.myDrive.exception.InvalidMaskException;

public class AppFile extends AppFile_Base {


    protected AppFile() {
        super();
    }


    AppFile(int id, String filename, String userMask, User owner, Directory parentDirectory) throws InvalidFileNameException, InvalidMaskException {
        super.init(id, filename, userMask, owner, parentDirectory);
    }

    AppFile(int id, String filename, String userMask, User owner, String content, Directory parentDirectory) throws InvalidFileNameException, InvalidMaskException, InvalidContentException{
        super.init(id, filename, userMask, owner, content, parentDirectory);

    }
    
    @Override
    public void writeContent(String content, User logged) throws InvalidContentException{
    	if(content.contains(" ") || content.contains("\n")){
    		throw new InvalidContentException(content);
    	}else{
    		super.setContent(content);
    	}
    	
    }
    
    @Override
    protected void executeFile(User logged, String[] args) throws InvalidExecuteException {
        String defPackageExt = "pt.tecnico.myDrive";
        try{
        String folder = this.getContent().substring(defPackageExt.length() + 1, getContent().length());

        if (StringUtils.countMatches(folder, ".") == 2) {
            defPackageExt += "." + folder.substring(0, folder.indexOf("."));
        }

        String[] tokens = this.getContent().substring(defPackageExt.length() + 1).split("\\.");

        String classname = tokens[0];
        String method = tokens.length == 2 ? tokens[1] : "main";

        try {
            Class<?> cls = Class.forName(defPackageExt + "." + classname);
            Method meth = cls.getMethod(method, String[].class);
            meth.invoke(null, (Object) args);

        } catch (Exception e) {
            throw new InvalidExecuteException(e.getMessage());}
        } catch (Exception e) {
            throw new InvalidContentException(e.getMessage());}
    }
    
    @Override
    public Element xmlExport(){
        Element pf_el = new Element("app");

        super.generalFileExport(pf_el);

        if(!getContent().isEmpty())
            pf_el.addContent(new Element("method").setText(getContent()));

        return pf_el;
    }

}