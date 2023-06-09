package model;

import etu1763.framework.ModelView;
import etu1763.framework.annotationPerso.Model;
import etu1763.framework.annotationPerso.URLMapping;
import java.util.ArrayList;

@Model
public class Truc {
    String attr;
    
    @URLMapping(valeur="/formulaire.run")
    public ModelView findAll() {
        ModelView mv = new ModelView();
        
        Emp emp = new Emp();
        
        emp.setId(0);
        emp.setNom("Patron");
        
        mv.setView("salut.jsp");
        
        ArrayList<Emp> liste = new ArrayList<>();
        liste.add(emp);
        
        mv.AddItems("liste", liste);
        
        return mv;
    }
}
