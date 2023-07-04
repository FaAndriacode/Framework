package etu1763.framework.servlet;

import java.io.IOException;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import etu1763.framework.Mapping;
import etu1763.framework.ModelView;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.Map;
import javax.servlet.RequestDispatcher;
import utilPerso.OrderParam;
import utilPerso.PageNotFound;
import utilPerso.Utilitaire;

public class Front_Servlet extends HttpServlet {
    private HashMap<Class, Object> singleton ;
    private HashMap<String, Mapping> mappingUrls;
    Object[] paramsName;
    String url,view;
    Object retour;
    RequestDispatcher dispat;
            
    @Override
    public void init() throws ServletException {
        try {
            String repertoire = this.getInitParameter("packClass");
            Utilitaire.whoClass(repertoire,this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

public void ProcessRequest(HttpServletRequest req, HttpServletResponse res) 
        throws PageNotFound, OrderParam, ClassNotFoundException, NoSuchMethodException, ParseException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, ServletException, IOException {        
        url = req.getServletPath();
        if(this.getMappingUrls().containsKey(url)) {
            Mapping mapping = this.getMappingUrls().get(url);
            
            // Instanciation d'un objet de type classname du Mapping
            Class<?> classe = Class.forName(mapping.getClassName());
            
            //Parameter Traitement
            paramsName = Utilitaire.ProcessParam(req);     
            if(paramsName != null){
                if(Utilitaire.ProcessCompParam(mapping, paramsName)){
                    paramsName = Utilitaire.GetValueParams(req);
                    paramsName = Utilitaire.castParams(mapping.getMethode(),paramsName);
                }
            }
            
            //verification singletion
            Object object = null;
            if(singleton.containsKey(classe)){
                if(this.getSingleton().get(classe) == null){
                    object = classe.cast(classe.getDeclaredConstructor().newInstance());
                    this.getSingleton().put(classe,object);
                }else{
                    object = this.getSingleton().get(classe); 
                }             
            }else
                object = classe.cast(classe.getDeclaredConstructor().newInstance());
            
            retour = mapping.getMethode().invoke(object,paramsName);
            if(retour instanceof ModelView){
                // Prendre la view dans le ModelView retourné
                ModelView modelView = (ModelView) retour;
                view = modelView.getView();
                
                for (Map.Entry<String, Object> entry : modelView.getData().entrySet())
                    req.setAttribute(entry.getKey(),entry.getValue());
            }
        }else
            throw new PageNotFound();
    }
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
        try {
            ProcessRequest(req, res);           
        }catch(PageNotFound e){
            view = "Error.jsp";
            System.out.println( e.getMessage());  
        }catch(OrderParam e){
            view = "Error.jsp";
            System.out.println( e.getMessage());  
        }catch (IOException | ClassNotFoundException | IllegalAccessException | IllegalArgumentException | InstantiationException | NoSuchMethodException | InvocationTargetException | ParseException | ServletException e) {
            view = "Error.jsp";
            System.out.println( e.getMessage());   
        }finally{
            try {
                //Dispatch vers la vue correspondante
                dispat = req.getRequestDispatcher(view);
                dispat.forward(req,res);
            } catch (IOException | ServletException ex) {}
        }
    }
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res){
        try {
            ProcessRequest(req, res);           
        }catch(PageNotFound e){
            view = "Error.jsp";
            System.out.println( e.getMessage());  
        }catch(OrderParam e){
            view = "Error.jsp";
            System.out.println( e.getMessage());           
        }catch (IOException | ClassNotFoundException | IllegalAccessException | IllegalArgumentException | InstantiationException | NoSuchMethodException | InvocationTargetException | ParseException | ServletException e) {
            view = "Error.jsp";
            System.out.println( e.getMessage());   
        }finally{
            try {
                //Dispatch vers la vue correspondante
                dispat = req.getRequestDispatcher(view);
                dispat.forward(req,res);
            } catch (IOException | ServletException ex) {}
        }
    }

    /**
     * @return the singleton
     */
    public HashMap<Class, Object> getSingleton() {
        return singleton;
    }

    /**
     * @param singleton the singleton to set
     */
    public void setSingleton(HashMap<Class, Object> singleton) {
        this.singleton = singleton;
    }

    /**
     * @return the mappingUrls
     */
    public HashMap<String, Mapping> getMappingUrls() {
        return mappingUrls;
    }

    /**
     * @param mappingUrls the mappingUrls to set
     */
    public void setMappingUrls(HashMap<String, Mapping> mappingUrls) {
        this.mappingUrls = mappingUrls;
    }
    
}