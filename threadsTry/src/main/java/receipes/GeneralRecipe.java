package receipes;

import products.Products;

import java.util.Map;

public interface GeneralRecipe {
    Map<Products, Integer> getListOfIngredients();

    int getStandardQuantity();

    Products getFinalProduct();
}
