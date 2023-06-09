package etu1763.framework.annotationPerso;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface URLMapping {
    String valeur() default "/";
}
