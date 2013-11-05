package cookiestore;

import products.Products;
import receipes.BiscuitRecipe;
import receipes.ChocolateCakeRecipe;
import receipes.GeneralRecipe;
import receipes.WrappedBiscuitesReceipe;
import workers.KitchenWorker;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ExecutorService;

public class ProducerThread implements Runnable {
    private final Map<Products, Integer> allProducts;
    private final ExecutorService service;
    private final List<GeneralRecipe> recipes;

    public ProducerThread(Map<Products, Integer> allProducts, ExecutorService service) {
        this.allProducts = allProducts;
        this.service = service;
        recipes = new ArrayList<GeneralRecipe>();
        recipes.add(new BiscuitRecipe());
        recipes.add(new ChocolateCakeRecipe());
        recipes.add(new WrappedBiscuitesReceipe());

    }

    @Override
    public void run() {
        long time = 100;
        while (time-- > 0) {
            service.submit(new KitchenWorker(getNewTask(), allProducts));
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
            }
        }
    }

    private GeneralRecipe getNewTask() {
        int n = Math.abs(new Random().nextInt(recipes.size()));
        return recipes.get(n);
    }

}
