package etu1763.framework;

import java.lang.reflect.Method;

public class Mapping {
    String className;
    Method method;
    Object[] params;

    public Mapping() {}
    
    public Mapping(String className, Method method, Object[] params) {
        this.className = className;
        this.method = method;
        this.params = params;
    }
    
    public Object[] getparams(){
        return this.params;
    }
    
    public String getClassName(){
        return this.className;
    }
    
    public Method getMethode(){
        return this.method;
    }
    
}
