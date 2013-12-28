package annotations;

import annotations.typeone.*;

public class Main {
    public static void main(String[] args){
        ObjectInterface obj1 = ObjectFactory.getObject(ObjectType.OBJECT_ONE);
        ObjectInterface obj2 = ObjectFactory.getObject(ObjectType.OBJECT_TWO);
        ObjectInterface obj3 = ObjectFactory.getObject(ObjectType.OBJECT_THREE);

        obj1.printMessage("Message1");
        obj1.returnSum(1,3);
        obj2.printMessage("Message2");
        obj2.returnSum(1,3);
        obj3.printMessage("Message3");
        obj3.returnSum(1,3);
    }
}
