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
	private static final Logger log = LogManager.getRootLogger();

	public static void main(String[] args) {
		try {
			log.trace("MyDriveApplication.main: Starting application.");

			setup();

			if (args.length == 1) {
				File f = new File(args[0]);
				if (f != null)
					xmlScan(f);
			}
		}finally {
			FenixFramework.shutdown();
		}
	}
	

	@Atomic
	public static void setup(){
		log.trace("MyDriveApplication.setup: Setting up Manager." );
		manager = MyDriveManager.getInstance();
	}


	@Atomic
	public static void xmlPrint() {
		log.trace("MyDriveApplication.xmlPrint: " + FenixFramework.getDomainRoot());
		Document doc = manager.xmlExport();
		XMLOutputter xmlOutput = new XMLOutputter(Format.getPrettyFormat());
		try { xmlOutput.output(doc, new PrintStream(System.out));
		} catch (IOException e) { System.out.println(e); }
	}


	@Atomic
	public static void xmlScan(File file) {
		log.trace("MyDriveApplication.xmlScan: " + FenixFramework.getDomainRoot());
		SAXBuilder builder = new SAXBuilder();
		try {
		    Document document = (Document)builder.build(file);
		    manager.xmlImport(document.getRootElement());
		} catch (JDOMException | IOException e) {
		    e.printStackTrace();
		}
	}
	
}
	
