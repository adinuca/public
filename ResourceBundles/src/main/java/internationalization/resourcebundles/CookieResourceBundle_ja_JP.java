package internationalization.resourcebundles;

import java.util.ListResourceBundle;

public class CookieResourceBundle_ja_JP extends ListResourceBundle {
    private String name;
    private Double quantity;
    private Double price;


    private Object[][] contents = {
            { "name", "Cookies_JP" },
            { "quantity", new Double(100) },
            { "price", new Double(102) },
    };

    @Override
    protected Object[][] getContents() {
        return contents;
    }
}
