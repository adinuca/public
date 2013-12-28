package annotations.typeone;


import annotations.annotationprocessor.AnnotationProcessor;

public class ObjectProxy extends AnnotationProcessor implements ObjectInterface {

    public static final String PRINT_MESSAGE = "printMessage";
    public static final String RETURN_SUM = "returnSum";

    public ObjectProxy(ObjectInterface object) {
        super(object);
    }

    @Override
    public void printMessage(String message) {
        if(isAnnotationOnClass() || isAnnotationOnMethod(PRINT_MESSAGE)){
            printObjectInfo(PRINT_MESSAGE,message);
        }
        ((ObjectInterface)getObject()).printMessage(message);
    }

    @Override
    public int returnSum(int a, int b) {
        if(isAnnotationOnClass() || isAnnotationOnMethod(RETURN_SUM)){
            printObjectInfo(RETURN_SUM, a+" "+b);
        }
        return ((ObjectInterface)getObject()).returnSum(a,b);
    }
}
