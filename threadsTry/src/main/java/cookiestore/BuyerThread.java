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
    private final Buyer buyer;

    public BuyerThread(Map<Products, Integer> allProducts, ExecutorService service) {
        this.allProducts = allProducts;
        this.service = service;
        this.buyer = new Buyer(allProducts);
    }


    @Override
    public void run() {
        long time = 200;
        while (time-- > 0) {
            Products p = getNewTask();
            buyer.setProduct(p);
            buyer.setQuantity(getQuantity(p));
            service.submit(buyer);
            try {
                Thread.sleep(70);
            } catch (InterruptedException e) {
            }
        }
    }

    private int getQuantity(Products p) {
        return Math.abs(new Random().nextInt( p.getMaximumQuantity()));
    }

    private Products getNewTask() {
        int n = Math.abs(new Random().nextInt(listAvailableProducts.length));
        return listAvailableProducts[n];
    }
}
