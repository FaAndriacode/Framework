package model;

import etu1763.framework.ModelView;
import etu1763.framework.annotationPerso.Model;
import etu1763.framework.annotationPerso.URLMapping;

@Model
public class Truc {
    String attr;
    
    @URLMapping(valeur="/truc-salut.run")
    public ModelView bonjour() {
        //Traitement

        ModelView m = new ModelView();
        
        m.setView("salut.jsp");
        
        return m;
    }
}
