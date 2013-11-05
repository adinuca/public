package receipes;

import products.Products;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ChocolateCakeRecipe implements GeneralRecipe {
    private final Map<Products, Integer> listOfIngredients;
    private static final int STANDARD_QUANTITY = 1;

    public ChocolateCakeRecipe() {
        listOfIngredients = new HashMap<Products, Integer>();
        listOfIngredients.put(Products.SUGAR, 200);
        listOfIngredients.put(Products.FLOUR, 300);
        listOfIngredients.put(Products.NUTS, 100);
        listOfIngredients.put(Products.BUTTER, 200);
        listOfIngredients.put(Products.MILK, 100);
        listOfIngredients.put(Products.VANILLA, 5);
        listOfIngredients.put(Products.CHOCOLATE, 500);
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
        return Products.CHOCOLATE_CAKE;
    }
}
