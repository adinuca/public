import cookiestore.BuyerThread;
import cookiestore.ProducerThread;
import cookiestore.SupplierThread;
import products.Products;

import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


public class CookieShop {
    private static Map<Products, Integer> allProducts;


    public static void main(String args[]) throws InterruptedException {
        allProducts = new ConcurrentSkipListMap<Products, Integer>();

        ExecutorService service = Executors.newFixedThreadPool(10);
        new Thread(new SupplierThread(allProducts, service)).start();
        new Thread(new ProducerThread(allProducts, service)).start();
        new Thread(new BuyerThread(allProducts, service)).start();

        Thread.sleep(15000);

        service.shutdown();

        if (!service.awaitTermination(20, TimeUnit.SECONDS)){
            printMap();
            System.exit(0);
        };
    }

    private static void printMap() {
        for (Products p : allProducts.keySet()) {
            System.out.println(p.name() + " " + allProducts.get(p));
        }
    }

}