package annotations;

import java.lang.annotation.Annotation;

public class ObjectProxy implements ObjectInterface {

    private ObjectInterface object;

    public ObjectProxy(ObjectInterface object) {
        this.object = object;
    }

    @Override
    public void printMessage(String message) {
        //check if annotation is on the object => printObject Info
        printObjectInfo(message);
        // Check if annotation is on the method => log method info
        object.printMessage(message);

    }

    @Override
    public int returnSum(int a, int b) {
        //check if annotation is on the object => printObject Info
        // Check if annotation is on the method => log method info
        printObjectInfo(a+" "+b);
        return object.returnSum(a,b);
    }

    private void printObjectInfo(String parameters) {
        Class c = object.getClass();
        Annotation[] annotationsOnClass = c.getAnnotations();
        for(Annotation annot : annotationsOnClass){
            if (annot.getClass().equals(Logged.class)){
                System.out.println("printMessage method, "+ object.getClass().toString()+", "+object.toString()+", and parameters: "+parameters);
            }
        }
    }
}
