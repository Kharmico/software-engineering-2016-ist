package pt.tecnico.myDrive.service;

import org.junit.*;
import mockit.*;
import pt.tecnico.myDrive.domain.MyDriveManager;
import pt.tecnico.myDrive.exception.*;

public class ExecuteFileTest extends AbstractServiceTest {

    @Override
    public void populate() {
        MyDriveManager manager = MyDriveManager.getInstance();
        manager.addUser("Joao");
        manager.addUser("Pedro");

        manager.login("Joao","Joao");
        manager.createAppFile("noperm");

        manager.login("Pedro","Pedro");
        manager.createAppFile("sufix");
    }

}

/*
1. Pedir para executar um  ficheiro sem especificar a path;
2. Pedir para executar um ficheiro sem argumentos;
3. Pedir para executar um ficheiro sem ter permissoes para tal;
4. Executar um ficheiro que nao é uma aplicacao;
 */