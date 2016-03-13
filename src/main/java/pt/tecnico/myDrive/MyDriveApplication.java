package pt.tecnico.myDrive;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jdom2.Document;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import pt.ist.fenixframework.Atomic;
import pt.ist.fenixframework.FenixFramework;
import pt.tecnico.myDrive.domain.MyDriveManager;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;


public class MyDriveApplication{

	private static MyDriveManager manager;
	static final Logger log = LogManager.getRootLogger();

	public static void main(String[] args){
		System.out.println("Hello World!");
		setup();

		if(args.length == 1) {
			File f = new File(args[0]);
			if(f != null)
				xmlScan(f);
		}

		func1();
		func2();
		func3();
		func4();
		xmlPrint();
		func6();
	}


	@Atomic
	public static void setup(){
		manager = MyDriveManager.getInstance();
	}

	@Atomic
	public static void func1(){
		manager.createPlainFile("/home/README", "lista de utilizadores");

	}

	@Atomic
	public static void func2(){
		manager.createDirectory("/usr/local/bin");
		/*Directory usr = (Directory) manager.getFilesystem().getSlash().getFileByName("usr");
		Directory local = (Directory) usr.getFileByName("local");

		System.out.println(local.getFileByName("bin").getPath());*/
		//System.out.println(manager.getDirectoryFilesName("/usr/local"));
	}

	@Atomic
	public static void func3(){
		System.out.println(manager.printTextFile("/home/README"));
	}

	@Atomic
	public static void func4(){
		manager.removeFile("/usr/local/bin");
		//System.out.println(manager.getDirectoryFilesName("/usr"));
	}

	@Atomic
	public static void func6(){
		manager.removeFile("/home/README");
	}

	@Atomic
	public static void func7(){
		System.out.println(manager.getDirectoryFilesName("/home"));
	}

	@Atomic
	public static void xmlPrint() {
		log.trace("xmlPrint: " + FenixFramework.getDomainRoot());
		Document doc = manager.xmlExport();
		XMLOutputter xmlOutput = new XMLOutputter(Format.getPrettyFormat());
		try { xmlOutput.output(doc, new PrintStream(System.out));
		} catch (IOException e) { System.out.println(e); }
	}


	@Atomic
	public static void xmlScan(File file) {
		//log.trace("xmlScan: " + FenixFramework.getDomainRoot());
		SAXBuilder builder = new SAXBuilder();
		try {
		    Document document = (Document)builder.build(file);
		    manager.xmlImport(document.getRootElement());
		} catch (JDOMException | IOException e) {
		    e.printStackTrace();
		}
	}

}
	
