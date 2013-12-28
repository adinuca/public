package annotations.typeone;

import annotations.annotationprocessor.Logged;

public class ObjectThree implements ObjectInterface {
    @Override
    public void printMessage(String message) {
        System.out.println(message + " object3");
    }

    @Override
    @Logged
    public int returnSum(int a, int b) {
        System.out.println(a + " " + b + " object3");
        return a+b;
    }
}
