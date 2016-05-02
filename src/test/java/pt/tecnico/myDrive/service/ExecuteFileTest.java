package pt.tecnico.myDrive.service;

import pt.tecnico.myDrive.domain.MyDriveManager;

public class ExecuteFileTest extends AbstractServiceTest {

    @Override
    public void populate() {
        MyDriveManager manager = MyDriveManager.getInstance();
        manager.addUser("Josefina");
        manager.addUser("Pedrocas");

        manager.login("Josefina","Josefina");
        manager.createAppFile("noperm");

        manager.login("Pedrocas","Pedrocas");
        manager.createAppFile("sufix");
    }

}

/*
1. Pedir para executar um  ficheiro sem especificar a path;
2. Pedir para executar um ficheiro sem argumentos;
3. Pedir para executar um ficheiro sem ter permissoes para tal;
4. Executar um ficheiro que nao é uma aplicacao;
 */