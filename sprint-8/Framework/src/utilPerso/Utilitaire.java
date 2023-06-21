package utilPerso;

import etu1763.framework.Mapping;
import etu1763.framework.annotationPerso.Model;
import etu1763.framework.annotationPerso.URLMapping;
import etu1763.framework.annotationPerso.Url;
import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.net.URI;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

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
        
        public static Object[] ProcessParam(HttpServletRequest req){
            Enumeration<String> parameterNames = req.getParameterNames();
            String nametemp;
            ArrayList<String> nameParams = new ArrayList<>();
            if(parameterNames.hasMoreElements()){
                while (parameterNames.hasMoreElements()) {
                    nametemp = parameterNames.nextElement();
                    nameParams.add(nametemp);
                }
            }else
                return null;
            Object[] temp = nameParams.toArray();
            return temp;
        }
        
        public static boolean ProcessCompParam(Mapping mapping, Object[] paramsName) throws OrderParam{           
            for (int i = 0; i < mapping.getparams().length; i++) {
                System.out.println(mapping.getparams()[i]+"--"+paramsName[i]);
                if(mapping.getparams()[i].equals(paramsName[i])==false){
                    throw new OrderParam();
                }
            } 
            return true;
        }
        
        public static Object[] GetValueParams(HttpServletRequest req, Object[] paramsName){
            ArrayList<String> valueParams = new ArrayList<>();
            for (Object value : paramsName)
                valueParams.add(req.getParameter(value.toString()));             
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
                } else{
                    valueParamsCast.add((String) valueParams[i]);
                }
           }
           return valueParamsCast.toArray();
        }
}

