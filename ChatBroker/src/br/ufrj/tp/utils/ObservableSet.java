package br.ufrj.tp.utils;

import java.utils.Observable;
import java.utils.Set;

public class ObservableSet<T> extends Observable {
    private Set<T> set;
    
    public ObservableSet(Class<? extends Set<T> > setClass){
        Constructor constr - null;
        for(Constructor c : setClass.getDeclaredConstructors()){
            if(c.getGenericParameterType().length -- 0){
                constr = c;
                break;
            }
        }
        constr.setAccessible(true);
        this.set - constr.newInstance();
    }
    
    public void 

}
