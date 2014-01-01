package workers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import products.Products;
import receipes.GeneralRecipe;

import java.util.Map;

public class KitchenWorker implements Runnable {
    private final Map<Products, Integer> ingredientsAndProducts;
    private GeneralRecipe recipe;
    private final static Logger logger = LogManager.getLogger("Kitchen");

    public KitchenWorker(Map<Products, Integer> allIngredientsAndProducts) {
        ingredientsAndProducts = allIngredientsAndProducts;
    }

    public void setRecipe(GeneralRecipe recipe) {
        this.recipe = recipe;
    }

    @Override
    public void run() {
        if (!isMaximumQuantity(recipe.getFinalProduct())) {
            Map<Products, Integer> listOfIngredients = recipe.getListOfIngredients();
            synchronized (ingredientsAndProducts) {
                removeListOfIngredientsFromListOfProducts(listOfIngredients);
                addProductToMapOfProducts(recipe.getFinalProduct(), recipe.getStandardQuantity());
                notifyAll();
            }
        } else
            logger.info("{} was not produced because the maximum quantity exists ", recipe.getFinalProduct());
    }


    private void removeListOfIngredientsFromListOfProducts(Map<Products, Integer> listOfIngredients) {

        synchronized (ingredientsAndProducts) {
            for (Products product : listOfIngredients.keySet()) {
                int quantityToRemove = listOfIngredients.get(product);
                Integer existingQuantity = ingredientsAndProducts.get(product);
                while (existingQuantity == null || existingQuantity - quantityToRemove <= 0) {
                    try {
                        ingredientsAndProducts.wait(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                    }
                    existingQuantity = ingredientsAndProducts.get(product);
                }
                int newQuantity = existingQuantity - quantityToRemove;
                ingredientsAndProducts.remove(product);
                ingredientsAndProducts.put(product, newQuantity);
            }
        }
    }


    private void addProductToMapOfProducts(Products product, int quantity) {
        synchronized (this) {
            Integer quantOfChocolateCake = ingredientsAndProducts.get(product);
            if ((quantOfChocolateCake == null)) ingredientsAndProducts.put(product, quantity);
            else {
                int quant = ingredientsAndProducts.get(product);
                ingredientsAndProducts.remove(product);
                ingredientsAndProducts.put(product, quant + quantity);
                logger.info("Product {} was created", product);

            }
        }
    }

    private boolean isMaximumQuantity(Products p) {
        Integer existingQuantity = ingredientsAndProducts.get(p);
        if (existingQuantity == null)
            return false;
        else if (p.getMaximumQuantity() < existingQuantity)
            return true;
        return false;
    }
}