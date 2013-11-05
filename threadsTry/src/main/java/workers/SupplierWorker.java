package workers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import products.Products;

import java.util.HashMap;
import java.util.Map;

public class SupplierWorker implements Runnable {

    private final Map<Products, Integer> listOfIngredients;
    private final Map<Products, Integer> allIngredients;
    private final static Logger logger = LogManager.getLogger("Supplier");

    public SupplierWorker(Map<Products, Integer> allIngredients) {
        this.allIngredients = allIngredients;
        listOfIngredients = new HashMap<Products, Integer>();
        listOfIngredients.put(Products.SUGAR, 5000);
        listOfIngredients.put(Products.CHOCOLATE, 5000);
        listOfIngredients.put(Products.MILK, 5000);
        listOfIngredients.put(Products.FLOUR, 5000);
        listOfIngredients.put(Products.NUTS, 1000);
        listOfIngredients.put(Products.BUTTER, 5000);
        listOfIngredients.put(Products.VANILLA, 200);
        listOfIngredients.put(Products.WRAPPING_PAPER, 4);
    }

    @Override
    public void run() {
        synchronized (allIngredients) {

            for (Products ingrName : listOfIngredients.keySet()) {
                int quant = listOfIngredients.get(ingrName);
                Integer existingQuantity = allIngredients.get(ingrName);
                if ((existingQuantity == null) || (existingQuantity < ingrName.getMaximumQuantity())) {
                    if (allIngredients.get(ingrName) != null) {
                        int quantity = allIngredients.get(ingrName);
                        allIngredients.remove(ingrName);
                        allIngredients.put(ingrName, quantity + quant);
                    } else
                        allIngredients.put(ingrName, quant);
                }

            }
            logger.info("Added ingredients");
            notifyAll();

        }
    }
}
