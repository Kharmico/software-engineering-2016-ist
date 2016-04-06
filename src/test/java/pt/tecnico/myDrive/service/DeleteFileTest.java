package pt.tecnico.myDrive.service;

import static org.junit.Assert.assertNotSame;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import pt.tecnico.myDrive.domain.MyDriveManager;


public class DeleteFileTest extends AbstractServiceTest{

    private static final Logger log = LogManager.getRootLogger();

    protected void populate() {
        MyDriveManager manager = MyDriveManager.getInstance();
    }
}
