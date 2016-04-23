package pt.tecnico.myDrive.domain;

import pt.tecnico.myDrive.exception.IllegalRemovalException;

public class Guest extends Guest_Base {
    private static final String GUEST_UMASK = "rxwdr-x-";
    private static final String GUEST_NAME = "Guest";
    private static final String GUEST_PASSWORD = "";
    private static final String GUEST_USERNAME = "nobody";

    public Guest() {
        super();
        super.setUsername(GUEST_USERNAME);
        super.setPassword(GUEST_PASSWORD);
        super.setName(GUEST_NAME);
        super.setUmask(GUEST_UMASK);
    }

    @Override
    public void setPassword(String password){
        //FIXME : when the exception is implemented uncomment this
        //throw new IllegalGuestPasswordChangeException(this);
    }

    @Override
    public void remove(){
        throw new IllegalRemovalException("You can't remove " + GUEST_NAME);
    }

    @Override
    protected boolean isGuest(){
        return true;
    }
    
}
