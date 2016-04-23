package pt.tecnico.myDrive.domain;

import pt.tecnico.myDrive.exception.IllegalRemovalException;

public class Root extends Root_Base {
    
	private static final String ROOT_UMASK = "rwxdr-x-";
	private static final String ROOT_NAME = "Super User";
	private static final String ROOT_PASSWORD = "***";
	static final String ROOT_USERNAME = "root";

	public Root() {
        super();
        super.setUsername(ROOT_USERNAME);
        super.setPassword(ROOT_PASSWORD);
        super.setName(ROOT_NAME);
        super.setUmask(ROOT_UMASK);
    }

    @Override
    public void remove(){
        throw new IllegalRemovalException("You can't remove " + ROOT_NAME);
    }
    
    @Override
    protected boolean isRoot(){
    	return true;
    }

}
