package pt.tecnico.myDrive.domain;

public class EnvironmentVariable extends EnvironmentVariable_Base {
    
    public EnvironmentVariable() {
        super();
    }

    public EnvironmentVariable(String name, String value, Session session) {
        super.setName(name);
        super.setValue(value);
        super.setSession(session);
    }

    @Override
    public String toString(){
        return this.getName() + " = " + super.getValue();
    }
}
