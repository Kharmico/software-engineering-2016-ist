package pt.tecnico.myDrive.domain;

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
    protected boolean isGuest(){
        return true;
    }
    
}
