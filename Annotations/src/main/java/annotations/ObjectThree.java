package annotations;

public class ObjectThree implements ObjectInterface {
    @Override
    public void printMessage(String message) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    @Logged
    public int returnSum(int a, int b) {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
