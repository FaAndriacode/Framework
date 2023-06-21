# Etape 1:

Dans votre projet importer le fichier jar:

    import framework.jar

# Etape 2:

### Web.xml :
- votre web.xml doit contenir les ligne suivant

           <servlet>
                  <servlet-name>FrontServlet</servlet-name>
                  <servlet-class>etu1763.framework.servlet.Front_Servlet</servlet-class>
                  <init-param>
                      <param-name>packClass</param-name>
                      <param-value>Package à scanner</param-value>
                  </init-param>
            </servlet>
            <servlet-mapping>
                <servlet-name>FrontServlet</servlet-name>
                <url-pattern>*.run</url-pattern>
            </servlet-mapping>

- #### N.B
  + chaque url dois contenir un ".run" a la fin pour etre valider par le " Frontservlet "
    >Exemple : example.run

# Etape 3
### classe:

  - `la classe` annoter @Model

        // classe annoter
        @Model
        public class Truc {
            ## code
        }
        
  - `URL` est associer a une anotation annoter à une methode
  
          @URLMapping(valeur="/exemple.run")
          public void test(){
            #...code
          }
  - `ModelView`
    > import etu1763.framework.ModelView;
      - si vous voulier retourner une **Modelview** 

              @URLMapping(valeur="/exemple.run")
              public ModelView class test(){
                  ModelView mv = new ModelView();
                  mv.setView("exemple.jsp");
                  return mv;
              }
      - Si vous voulez envoie quelque chose à la view utiliser cette fonction qui dois contenir les parametres suivants

            AddItems(nom,liste)
            nom : de type String
            liste : de type Arrayliste<T>liste

      - Exemple de code:

            @URLMapping(valeur="/exemple.run")
                public ModelView class test(){
                    ModelView mv = new ModelView();
                    Emp emp1 = new Emp();
                    emp1.setId(1);
                    emp1.setNom("Huhu");
                    ArrayList<Emp> liste = new ArrayList<>();
                    liste.add(emp1);
                    mv.AddItems("liste", liste);
                    mv.setView("exemple.jsp");
                    return mv;
                }
  - #### N.B

    + le framework support 3 types
    + vous devez generer des getters et des setters

          int , String , Date "java.util.date"
          getter et setters

