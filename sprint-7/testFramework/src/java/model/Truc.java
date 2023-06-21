package model;

import etu1763.framework.ModelView;
import etu1763.framework.annotationPerso.Model;
import etu1763.framework.annotationPerso.URLMapping;
import java.util.ArrayList;

@Model
public class Truc {
    private int id;
    private String nom;
     
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
    @URLMapping(valeur="/process.run")
    public void process(){
        System.out.println( getId());
        System.out.println( getNom());
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the nom
     */
    public String getNom() {
        return nom;
    }

    /**
     * @param nom the nom to set
     */
    public void setNom(String nom) {
        this.nom = nom;
    }
    
    

}
