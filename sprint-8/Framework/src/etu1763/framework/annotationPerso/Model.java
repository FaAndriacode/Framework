package etu1763.framework.annotationPerso;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Model {
    String name() default "classe modely";
    boolean isDAO() default false;
}
