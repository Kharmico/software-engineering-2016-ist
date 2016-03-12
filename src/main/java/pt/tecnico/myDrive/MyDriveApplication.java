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
		
		MyDriveManager manager = MyDriveManager.getInstance();
		
		//0 args -> criar fs com root e associados
		if(args.length == 0){
			
		}
		
		//1 arg -> importar xml
		else if(args.length == 1){
			//manager.xmlImport(element);
			//manager.setFilesystem(filesystem);
		}
		
		//criar o ficheiro de texto /home/README com o conteudo lista de utilizadores
		manager.createPlainFile("/home/README", "lista de utilizadores");
		
		//criar a diretoria /usr/local/bin
		manager.createDirectory("/usr/local/bin");
		
		//imprimir o conteudo do ficheiro /home/README
		manager.printTextFile("/home/README");
		
		//remover a diretoria /usr/local/bin
		manager.removeEntries("/usr/local/bin");
		
		//imprimir a exportacao em XML do sistema de ficheiros
		manager.xmlExport();
		
		//remover o ficheiro /home/README
		manager.removeEntries("/home/README");
		
		//imprimir a listagem simples da diretoria /home
		manager.getDirectoryFilesName("/home");
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
	
