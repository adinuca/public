package annotations.typeone;

public class ObjectFactory {
    public static ObjectInterface getObject(ObjectType type) {
        ObjectInterface obj = null;
        if (type == ObjectType.OBJECT_ONE) {
            obj = new ObjectOne();
        }
        if (type == ObjectType.OBJECT_TWO) {
            obj = new ObjectTwo();
        }
        if (type == ObjectType.OBJECT_THREE) {
            obj = new ObjectThree();
        }
        if (obj != null) {
            return new ObjectProxy(obj);
        }
        return null;
    }
}
