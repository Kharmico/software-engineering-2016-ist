package pt.tecnico.myDrive.service;

import pt.tecnico.myDrive.exception.MyDriveException;

public class LoginUserService extends MyDriveService {

    private String mUsername;
    private String mPassword;
    private long mResultToken;

    public LoginUserService(String username, String password){
        mUsername = username;
        mPassword = password;
    }
    @Override
    public void dispatch() throws MyDriveException {
        MyDriveService.getMyDriveManager().login(mUsername, mPassword);
        mResultToken = MyDriveService.getMyDriveManager().getCurrentSession().getToken();
    }

    public final long result(){
        return mResultToken;
    }
}
