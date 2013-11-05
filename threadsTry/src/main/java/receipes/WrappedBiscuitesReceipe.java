package receipes;

import products.Products;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class WrappedBiscuitesReceipe implements GeneralRecipe {
    private final Map<Products, Integer> listOfIngredients;
    private static final int STANDARD_QUANTITY = 1;

    public WrappedBiscuitesReceipe() {
        listOfIngredients = new HashMap<Products, Integer>();
        listOfIngredients.put(Products.BISCUITS, 20);
        listOfIngredients.put(Products.WRAPPING_PAPER, 1);
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
        return Products.PACK_OF_BISCUITS;
    }
}
