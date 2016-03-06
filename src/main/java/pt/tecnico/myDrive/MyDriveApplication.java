package pt.tecnico.myDrive;

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
		
		
		//remover o ficheiro /home/README
		//TODO manager.goToSlash();
		manager.changeDirectory("home");
		//TODO manager.removeFile("README");
		
		//imprimir a listagem simples da diretoria /home
		//TODO manager.goToSlash();
		manager.changeDirectory("home");
		//TODO manager.listDirectory();
	}
}
