package receipes;

import products.Products;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;

public class BiscuitRecipe implements GeneralRecipe {
    private final Map<Products, Integer> listOfIngredients;
    private static final int STANDARD_QUANTITY = 40;

    public BiscuitRecipe() {
        listOfIngredients = new ConcurrentSkipListMap<Products, Integer>();
        listOfIngredients.put(Products.SUGAR, 100);
        listOfIngredients.put(Products.FLOUR, 300);
        listOfIngredients.put(Products.CHOCOLATE, 100);
        listOfIngredients.put(Products.BUTTER, 100);
        listOfIngredients.put(Products.VANILLA, 5);
    }

    @Override
    public Map<Products, Integer> getListOfIngredients() {
        return Collections.unmodifiableMap(listOfIngredients);
    }

    @Override
    public int getStandardQuantity() {
        return STANDARD_QUANTITY;
    }


    @Override
    public Products getFinalProduct() {
        return Products.BISCUITS;
    }
}
