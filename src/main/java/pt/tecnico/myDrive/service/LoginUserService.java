package pt.tecnico.myDrive.service;

import pt.tecnico.myDrive.domain.MyDriveManager;
import pt.tecnico.myDrive.exception.MyDriveException;

/**
 * Created by xxlxpto on 03-04-2016.
 */
public class LoginUserService extends MyDriveService {

    private String mUsername;
    private String mPassword;
    private long resultToken;

    public LoginUserService(String username, String password){
        mUsername = username;
        mPassword = password;
    }
    @Override
    public void dispatch() throws MyDriveException {
        MyDriveService.getMyDriveManager().login(mUsername, mPassword);
        resultToken = MyDriveService.getMyDriveManager().getCurrentSession().getToken();
    }

    public final long result(){
        return resultToken;
    }
}
