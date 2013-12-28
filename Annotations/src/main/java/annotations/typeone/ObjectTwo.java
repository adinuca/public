package annotations.typeone;

import annotations.annotationprocessor.Logged;

public class ObjectTwo implements ObjectInterface {

    @Logged
    @Override
    public void printMessage(String message) {
        System.out.println(message + " object2");
    }
    @Override
    public int returnSum(int a, int b) {
        System.out.println(a + " " + b + " object2");
        return a+b;
    }
}
