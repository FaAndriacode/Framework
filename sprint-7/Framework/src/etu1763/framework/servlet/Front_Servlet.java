package etu1763.framework.servlet;

import etu1763.framework.Mapping;
import etu1763.framework.ModelView;
import utilPerso.Utilitaire;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.RequestDispatcher;

public class Front_Servlet extends HttpServlet {
    HashMap<String, Mapping> mappingUrls;
    
    @Override
    public void init() throws ServletException {
        try {
            String repertoire = this.getInitParameter("packClass");
            mappingUrls = Utilitaire.whoClass(repertoire, "");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

public void ProcessRequest(HttpServletRequest req, HttpServletResponse res) throws IOException, ClassNotFoundException, NoSuchMethodException, InstantiationException, ServletException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {        
        
        String url = req.getServletPath();
   
        //reduction
        
        if(this.mappingUrls.containsKey(url)) {
            Mapping mapping = this.mappingUrls.get(url);

            // Instanciation d'un objet de type classname du Mapping
            Class<?> classe = Class.forName(mapping.getClassName());
            Object object = classe.cast(classe.getDeclaredConstructor().newInstance());

            Method method = classe.getDeclaredMethod(mapping.getMethode());

            //cerification methode si return
            if(method.getReturnType().getSimpleName().equalsIgnoreCase("modelview")){
                // Prendre la view dans le ModelView retourné
                ModelView modelView = (ModelView) method.invoke(object);
                String view = modelView.getView();

                for (Map.Entry<String, Object> entry : modelView.getData().entrySet())
                    req.setAttribute(entry.getKey(),entry.getValue());
                
                //Dispatch vers la vue correspondante
                RequestDispatcher dispat = req.getRequestDispatcher(view);
                dispat.forward(req,res);
            }else{
                Enumeration<String> parameterNames = req.getParameterNames();
                String filed_name , name_parameter;
                while (parameterNames.hasMoreElements()) {
                    name_parameter = parameterNames.nextElement();
                    filed_name = Utilitaire.Uperword(name_parameter);
                    if(object.getClass().getDeclaredMethod("get"+filed_name).getReturnType().getSimpleName().equalsIgnoreCase("int"))
                        object.getClass().getDeclaredMethod("set" + filed_name, int.class).invoke(object, Integer.valueOf(req.getParameter(name_parameter)));
                    else      
                        object.getClass().getDeclaredMethod("set" + filed_name, String.class).invoke(object, req.getParameter(name_parameter));
                }
               method.invoke(object);
            }  
        }else
            res.sendRedirect("Error.jsp");
    }
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
       try {
            ProcessRequest(req, res);
        } catch (Exception e) {
           e.printStackTrace();
        }
    }
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
        try {
            ProcessRequest(req, res);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
}
