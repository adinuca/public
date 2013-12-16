package internationalization.resourcebundles;

import java.util.ListResourceBundle;

public class CookieResourceBundle extends ListResourceBundle {
    private String name;
    private Double quantity;
    private Double price;


    private Object[][] contents = {
            { "name", "Cookies_US" },
            { "quantity", new Double(100) },
            { "price", new Double(0.99) },
    };
    @Override
    protected Object[][] getContents() {
        return contents;
    }
}
