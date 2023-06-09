package utilPerso;

import etu1763.framework.Mapping;
import etu1763.framework.annotationPerso.Model;
import etu1763.framework.annotationPerso.URLMapping;
import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class Utilitaire {
        public static List<Class> loadClassesInProject(String projectDirectory,String pkg) throws Exception {
            List<Class> loadedClasses = new ArrayList<>();
            File directoryPath = new File(projectDirectory);
            File[] listFiles = directoryPath.listFiles();
            for (File file : listFiles) {
                if (file.isDirectory()) {
                    loadedClasses.addAll(loadClassesInProject(file.getAbsolutePath(), pkg + file.getName() + "."));
                } else if (file.getName().endsWith(".class")) {
                    String className =file.getName().replace(".class", "");
                    //System.out.println(pkg+className);
                    Class clazz = Class.forName(pkg + className);
                    //System.out.println(clazz.getSimpleName());
                    loadedClasses.add(clazz);
                }
            }
            return loadedClasses;
        }
        
        public static HashMap<String, Mapping> getClass_annotation(List<Class> classe) throws Exception{
            
            Method[] meths;
            Annotation annot;
            Method annotMeth;
            String valueUrl = null;
            Mapping mapping;
            HashMap<String, Mapping> mappingUrls = new HashMap<>();
            
            for (Class c : classe) {
                if (c.isAnnotationPresent(Model.class)) {
                    meths = c.getDeclaredMethods();
                    for (Method m : meths) {
                        if (m.isAnnotationPresent(URLMapping.class)) {

                            annot = m.getAnnotation(URLMapping.class);
                            
                            annotMeth = annot.annotationType().getDeclaredMethod("valeur");
                            
                            valueUrl = annotMeth.invoke(annot).toString();

                            mapping = new Mapping(c.getName(), m.getName());
                            
                            mappingUrls.put(valueUrl, mapping);

                        }
                    }
                }
            }
            
            return mappingUrls;
        }
 
        public static HashMap<String, Mapping> whoClass(String projectDirectory,String pkg) throws Exception{
            
            List<Class> classe = loadClassesInProject(projectDirectory, pkg);
            
            HashMap<String, Mapping> temp = getClass_annotation(classe);
            
            return temp;
        }
        
        public static String Uperword(String chaine) {
            if (Character.isLowerCase(chaine.charAt(0))) {
                return chaine.substring(0, 1).toUpperCase() + chaine.substring(1);
            }else{
                return chaine;
            }
        }
        
}

