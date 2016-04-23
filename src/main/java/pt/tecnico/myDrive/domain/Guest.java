package pt.tecnico.myDrive.domain;

public class Guest extends Guest_Base {
    private static final String GUEST_UMASK = "rwxdr-x-";
    private static final String GUEST_NAME = "Super User";
    private static final String GUEST_PASSWORD = "***";
    private static final String GUEST_USERNAME = "root";

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
