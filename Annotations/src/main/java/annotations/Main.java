package annotations;

public class Main {
    public static void main(String[] args){
        ObjectInterface obj1 = new ObjectProxy(new ObjectOne());
        ObjectInterface obj2 = new ObjectProxy(new ObjectTwo());
        ObjectInterface obj3 = new ObjectProxy(new ObjectThree());

        obj1.printMessage("Message1");
        obj2.returnSum(1,3);
        obj3.printMessage("Message3");
        obj3.returnSum(1,3);
    }
}
