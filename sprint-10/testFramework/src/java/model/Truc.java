package model;

import etu1763.framework.FileUpload;
import etu1763.framework.ModelView;
import etu1763.framework.annotationPerso.Model;
import etu1763.framework.annotationPerso.Scope;
import etu1763.framework.annotationPerso.URLMapping;
import etu1763.framework.annotationPerso.Url;
import java.util.ArrayList;

@Model
@Scope(valeur="singleton")
public class Truc {
    private int id;
    private String nom;
    private FileUpload file;
    int n = 0;
     
    @URLMapping(valeur="/formulaire.run")
    public ModelView findAll() {
        ModelView mv = new ModelView();
        
        Emp emp1 = new Emp();
        Emp emp2 = new Emp();
        Emp emp3 = new Emp();
        Emp emp4 = new Emp();
        
        emp1.setId(1);
        emp1.setNom("Patron");
        emp2.setId(2);
        emp2.setNom("SousPatron");
        emp3.setId(3);
        emp3.setNom("Mpiasa");
        emp4.setId(4);
        emp4.setNom("Gardien");
        
        
        mv.setView("salut.jsp");
        
        ArrayList<Emp> liste = new ArrayList<>();
        liste.add(emp1);
        liste.add(emp2);
        liste.add(emp3);
        liste.add(emp4);
        
        mv.AddItems("liste", liste);
        
        return mv;
    }
    
    @URLMapping(valeur="/process.run")
    public ModelView process(@Url(url="nom") String nom , @Url(url="id") int id, @Url(url="file") FileUpload file){
        
        ModelView mv = new ModelView(); 
        Truc t = new Truc();
        t.setId(id);
        t.setNom(nom);
        t.setFile(file);
                
        System.out.println( id+" "+nom);
        mv.setView("succes.jsp");
        return mv;
    }
    
    @URLMapping(valeur="/find.run")
    public ModelView find(@Url(url="id_liens") String id){
        ModelView mv = new ModelView();
        System.out.println(id);
        mv.setView("index.jsp");
        return mv;
    }
    
    @URLMapping(valeur="/sing.run")
    public ModelView test(){
        ModelView mv = new ModelView();
        this.n = this.n + 1;
        System.out.println(n);
        mv.setView("succes.jsp");
        return mv;
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

    /**
     * @return the file
     */
    public FileUpload getFile() {
        return file;
    }

    /**
     * @param file the file to set
     */
    public void setFile(FileUpload file) {
        this.file = file;
    }
    
    

}
