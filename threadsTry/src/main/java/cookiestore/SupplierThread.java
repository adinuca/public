package cookiestore;

import products.Products;
import workers.SupplierWorker;

import java.util.Map;
import java.util.concurrent.ExecutorService;

public class SupplierThread implements Runnable {
    private final Map<Products, Integer> allProducts;
    private final ExecutorService service;
    private final SupplierWorker supplierWorker;

    public SupplierThread(Map<Products, Integer> allProducts, ExecutorService service) {
        this.allProducts = allProducts;
        this.supplierWorker = new SupplierWorker(allProducts);
        this.service = service;
    }

    public void run() {
        long time = 50;
        while (time-- > 0) {
            service.submit(supplierWorker);
            try {
                Thread.sleep(150);
            } catch (InterruptedException e) {
            }
        }
    }


}
