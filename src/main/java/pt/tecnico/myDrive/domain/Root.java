package pt.tecnico.myDrive.domain;

public class Root extends Root_Base {
    
	private static final String ROOT_UMASK = "rwxdr-x-";
	private static final String ROOT_NAME = "Super User";
	private static final String ROOT_PASSWORD = "***";
	protected static final String ROOT_USERNAME = "root";

	public Root() {
		// TODO : REFACTOR
        super();
        super.setUsername(ROOT_USERNAME);
        super.setPassword(ROOT_PASSWORD);
        super.setName(ROOT_NAME);
        super.setUmask(ROOT_UMASK);
    }
    
    @Override
    public boolean isRoot(){
    	return true;
    }
    
}
