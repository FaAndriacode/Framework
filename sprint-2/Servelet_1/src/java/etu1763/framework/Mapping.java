package etu1763.framework;

public class Mapping {
    String className;
    String method;

    public Mapping() {}
    
    public Mapping(String className, String method) {
        this.className = className;
        this.method = method;
    }
    
    public String getClassName(){
        return this.className;
    }
    
    public String getMethode(){
        return this.method;
    }
}
