package utilPerso;

import etu1763.framework.FileUpload;
import etu1763.framework.Mapping;
import etu1763.framework.annotationPerso.Model;
import etu1763.framework.annotationPerso.URLMapping;
import etu1763.framework.annotationPerso.Url;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.net.URI;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

@SuppressWarnings("unchecked")
public class Utilitaire {
        public static List<Class> loadClassesInProject(File projectDirectory,String pkg) throws Exception {
            List<Class> loadedClasses = new ArrayList<>();
            File directoryPath = projectDirectory;
            File[] listFiles = directoryPath.listFiles();
            for (File file : listFiles) {
                if (file.isDirectory()) {
                    loadedClasses.addAll(loadClassesInProject(file, pkg + file.getName() + "."));
                } else if (file.getName().endsWith(".class")) {
                    String className =file.getName().replace(".class", "");                    
                    Class clazz = Class.forName(pkg + className);
                    loadedClasses.add(clazz);
                }
            }
            return loadedClasses;
        }
        
        public static List<Class> getClasses(String packageName) throws Exception {
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            String path = packageName.replace('.', '/');
            Enumeration<URL> resources = classLoader.getResources(path);
            List<File> dirs = new ArrayList<File>();

            URL resource;
            URI uri;

            while (resources.hasMoreElements()) {
                resource = resources.nextElement();
                uri = new URI(resource.toString());
                dirs.add(new File(uri.getPath()));
            }
            List<Class> classes = new ArrayList<Class>();
            for (File directory : dirs) {
                classes.addAll(loadClassesInProject(directory, packageName));
            }
            return classes;
        }
         
        public static HashMap<String, Mapping> whoClass(String pkg) throws Exception{           
            List<Class> classe = getClasses(pkg);            
            HashMap<String, Mapping> temp = getClass_annotation(classe);            
            return temp;
        }
        
        public static HashMap<String, Mapping> getClass_annotation(List<Class> c) throws Exception{           
            Method[] methods;
            
            Annotation annotM ;
            Annotation annotP;
            
            Method annotMeth;
            Method annotParams;
            
            Parameter[] params;
            String valueUrl = "";
            String valueParams = "";
            
            Mapping mapping;
            
            ArrayList<String> parametre = new ArrayList<>();
            HashMap<String, Mapping> mappingUrls = new HashMap<>();
            
            for (Class classe : c) {
                if (classe.isAnnotationPresent(Model.class)) {
                    methods=classe.getDeclaredMethods();
                    for(Method m:methods){
                        if(m.isAnnotationPresent(URLMapping.class)){
                            annotM = m.getAnnotation(URLMapping.class);          
                            annotMeth = annotM.annotationType().getDeclaredMethod("valeur");                    
                            params = m.getParameters();
                                   
                            valueUrl = annotMeth.invoke(annotM).toString();
                            
                            if(params.length > 0){
                                for (Parameter param : params) {
                                    annotP = param.getAnnotation(Url.class);   
                                    annotParams = annotP.annotationType().getDeclaredMethod("url");  
                                    valueParams = annotParams.invoke(annotP).toString();
                                    
                                    parametre.add(valueParams);                               
                                }
                            }                       
                            mapping = new Mapping(classe.getName(),m, parametre.toArray());   
                            parametre = new ArrayList<>();
                            mappingUrls.put(valueUrl, mapping);
                        }
                    }
                }
            }           
            return mappingUrls;
        }
           
        public static String Uperword(String chaine) {
            if (Character.isLowerCase(chaine.charAt(0))) {
                return chaine.substring(0, 1).toUpperCase() + chaine.substring(1);
            }else{
                return chaine;
            }
        } 
        
         public static boolean ProcessCompParam(Mapping mapping, Object[] paramsName) throws OrderParam{           
            for (int i = 0; i < mapping.getparams().length; i++) {
                System.out.println(mapping.getparams()[i]+" == "+paramsName[i]);
                if(mapping.getparams()[i].equals(paramsName[i])==false){
                    throw new OrderParam();
                }
            } 
            return true;
        }
         
        public static Object[] ProcessParam(HttpServletRequest req) throws IOException, ServletException {
            ArrayList<Object> params = new ArrayList<>();

            try {
                Collection<Part> parts = req.getParts();
                for (Part part : parts) {
                    part.getName();
                    String name = part.getName();
                    params.add(name);
                }
            } catch (IOException | ServletException e){
                //rien faire parceque je test test
            }
            

            return params.toArray();
        }
            
    public static Object[] GetValueParams(HttpServletRequest req) throws IOException, ServletException {
        ArrayList<Object> valueParams = new ArrayList<>();

        try {
            Collection<Part> parts = req.getParts();
            for (Part part : parts) {
                String name = part.getSubmittedFileName();
                if (name != null && !name.isEmpty()) {
                    // Si le paramètre est un fichier uploadé
                    InputStream fileContent = part.getInputStream();
                    byte[] bytes = fileContent.readAllBytes();
                    valueParams.add(new FileUpload(name, bytes));
                } else {
                    // Si le paramètre est un lien (URL)
                    String value = req.getParameter(part.getName());
                    valueParams.add(value);
                }
            }
        } catch (IOException | ServletException e) {
        //rien faire parceque je test test
        }
        return valueParams.toArray();
    }
        
    public static Object[] castParams(Method methode,Object[] valueParams) throws ParseException{
       Class[] type =  methode.getParameterTypes();
       ArrayList<Object> valueParamsCast = new ArrayList<>();
       int numberInt;
       double numberDouble;
       Date numberDate;
       for(int i = 0 ; i < type.length ; i++){
            if (type[i] == int.class || type[i] == Integer.class) {
                numberInt = Integer.parseInt((String) valueParams[i]);
                valueParamsCast.add(numberInt);
            } else if (type[i] == double.class || type[i] == Double.class) {
                numberDouble = Double.parseDouble((String) valueParams[i]);
                valueParamsCast.add(numberDouble);
            } else if(type[i] == Date.class){
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                numberDate = format.parse((String) valueParams[i]);
                valueParamsCast.add(numberDate);
            } else if(type[i] == String.class){
                valueParamsCast.add((String) valueParams[i]);
            } else if(type[i] == FileUpload.class){
                valueParamsCast.add((FileUpload) valueParams[i]);
            }
       }
       return valueParamsCast.toArray();
    }
}

