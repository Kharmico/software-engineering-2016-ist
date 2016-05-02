package pt.tecnico.myDrive.domain;


import org.jdom2.Element;
import pt.tecnico.myDrive.exception.InvalidContentException;
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
    protected void executeFile(User logged, String[] args){
    	//package.class.method(args)
    	//package.class
    	/*String pack = "pt.tecnico.myDrive.domain";
    	
    	String[] tokens = this.getContent().substring(pack.length()+1).split("\\.");
    	
    	String classname;
    	String method;
    	if(tokens.length == 2){
    		classname = tokens[0];
    		method = tokens[1];
    	}
    	else{
    		classname = tokens[0];
    		method = "main";
    	}
    	Class cl = null;
    	try {
			cl = Class.forName(pack+"."+classname);
			
			
			  // get the constructor with one parameter
	          java.lang.reflect.Constructor constructor =
	             cl.getConstructor(new Class[] {String.class});

	          // create an instance
	          Object invoker=constructor.newInstance(new Object[]{"REAL'S HOWTO"});

			 // the method has no argument
	          Class  arguments[] = new Class[] { };

	          
	          java.lang.reflect.Method objMethod =
		             cl.getMethod(method, arguments);
	          
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			System.out.println("OOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO"+ cl);
		}*/
   
    	
    	
    	
    	//FIXME: you can execute from its content or directly from service
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