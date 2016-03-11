package pt.tecnico.myDrive;

import java.io.File;
import java.io.IOException;

import org.jdom2.Document;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import jvstm.Atomic;
import pt.ist.fenixframework.FenixFramework;
import pt.tecnico.myDrive.domain.MyDriveManager;


public class MyDriveApplication{

	public static void main(String[] args){
		System.out.println("Hello World!");
		
		MyDriveManager manager = new MyDriveManager();
		
		
		//0 args -> criar fs com root e associados
		
		//1 arg -> importar xml
		
		
		//criar o ficheiro de texto /home/README com o conteudo lista de utilizadores
		//TODO fazer cd ate home ou dar path absoluta?
		//TODO manager.goToSlash();
		manager.changeDirectory("home");
		manager.createPlainFile("README", "work in progress" /*manager.listUsers()*/);
		
		//criar a diretoria /usr/local/bin
		//TODO manager.goToSlash();
		manager.createDirectory("usr");
		manager.changeDirectory("usr");
		manager.createDirectory("local");
		manager.changeDirectory("local");
		manager.createDirectory("bin");
		
		//imprimir o conteudo do ficheiro /home/README
		//TODO manager.goToSlash();
		manager.changeDirectory("home");
		manager.printTextFile("README");
		
		//remover a diretoria /usr/local/bin
		//TODO manager.goToSlash();
		manager.changeDirectory("usr");
		manager.changeDirectory("local");
		//TODO manager.removeFile("bin");
		
		//imprimir a exportacao em XML do sistema de ficheiros
		manager.xmlExport();
		
		//remover o ficheiro /home/README
		//TODO manager.goToSlash();
		manager.changeDirectory("home");
		//TODO manager.removeFile("README");
		
		//imprimir a listagem simples da diretoria /home
		//TODO manager.goToSlash();
		manager.changeDirectory("home");
		//TODO manager.listDirectory();
	}
	
	/*@Atomic
	public static void xmlScan(File file) {
		//log.trace("xmlScan: " + FenixFramework.getDomainRoot());
		MyDriveManager manager = MyDriveManager.getInstance();
		SAXBuilder builder = new SAXBuilder();
		try {
		    Document document = (Document)builder.build(file);
		    manager.xmlImport(document.getRootElement());
		} catch (JDOMException | IOException e) {
		    e.printStackTrace();
		}
	}*/
	
	
}
	
