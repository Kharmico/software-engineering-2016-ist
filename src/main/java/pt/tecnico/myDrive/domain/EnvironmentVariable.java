package pt.tecnico.myDrive.domain;

public class EnvironmentVariable extends EnvironmentVariable_Base {
    
    public EnvironmentVariable() {
        super();
    }

    public EnvironmentVariable(String name, String value) {
        super.setName(name);
        super.setValue(value);
    }

    @Override
    public void setValue(String value){
        super.setValue(value);
    }

    @Override
    public String toString(){
        return this.getName() + " = " + super.getValue();
    }
}
