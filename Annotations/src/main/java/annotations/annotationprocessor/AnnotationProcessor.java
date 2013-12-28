package annotations.annotationprocessor;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public abstract class AnnotationProcessor {
    private Object object;

    public AnnotationProcessor(Object object) {
        this.object = object;
    }

    public Object getObject() {
        return object;
    }

    protected boolean isAnnotationOnClass() {
        Class c = object.getClass();
        Annotation[] annotationsOnClass = c.getAnnotations();
        Annotation annotation = c.getAnnotation(Logged.class);
        if (annotation != null) {
            return true;
        }
        return false;
    }

    protected boolean isAnnotationOnMethod(String methodName) {
        Class c = object.getClass();
        Method[] methods = c.getMethods();
        for (Method m : methods) {
            if (m.getName().equals(methodName)) {
                Annotation annotation = m.getAnnotation(Logged.class);
                if (annotation != null) {
                    return true;
                }
                return false;
            }
        }
        return false;
    }


    protected void printObjectInfo(String methodName, String parameters) {
        System.out.println("Class: " + object.getClass() + " method name: " + methodName +" parameters: "+ parameters);
    }

}
