package cookiestore;

import products.Products;
import workers.Buyer;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.ExecutorService;

public class BuyerThread implements Runnable {

    private final Map<Products, Integer> allProducts;
    private final ExecutorService service;
    private final Products[] listAvailableProducts = Products.values();

    public BuyerThread(Map<Products, Integer> allProducts, ExecutorService service) {
        this.allProducts = allProducts;
        this.service = service;
    }


    @Override
    public void run() {
        long time = 200;
        while (time-- > 0) {
            Products p = getNewTask();
            service.submit(new Buyer(p.getMaximumQuantity() / 5, p, allProducts));
            try {
                Thread.sleep(70);
            } catch (InterruptedException e) {
            }
        }
    }

    private Products getNewTask() {
        int n = Math.abs(new Random().nextInt(listAvailableProducts.length));
        return listAvailableProducts[n];
    }
}
