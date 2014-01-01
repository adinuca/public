package workers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import products.Products;

import java.util.Map;

public class Buyer implements Runnable {

    private int quantity;
    private Products product;
    private final Map<Products, Integer> ingredientsAndProducts;
    private final static Logger logger = LogManager.getLogger("Buyer");

    public Buyer(Map<Products, Integer> allIngredientsAndProducts) {
        ingredientsAndProducts = allIngredientsAndProducts;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setProduct(Products product) {
        this.product = product;
    }

    @Override
    public void run() {
        synchronized (ingredientsAndProducts) {
            Integer existingQuantity = ingredientsAndProducts.get(product);
            int tries = 5;
            while (((existingQuantity == null) || (existingQuantity - quantity) < 0) && (tries-- > 0)) {
                try {
                    wait(200);
                } catch (InterruptedException e) {
                }
                existingQuantity = ingredientsAndProducts.get(product);
            }
            if (tries > 0) {
                int newQuantity = existingQuantity - quantity;
                ingredientsAndProducts.remove(product);
                ingredientsAndProducts.put(product, newQuantity);
                logger.info("Bought {} {}", product.name(), quantity);
            } else {
                logger.info("Product {} was not bought ", product);
            }
            notifyAll();
        }
    }
}
