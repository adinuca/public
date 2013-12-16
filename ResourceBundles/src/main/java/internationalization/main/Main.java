package internationalization.main;

import java.text.MessageFormat;
import java.util.*;

public class Main {

    private static final String MESSAGES = "Messages";
    private static final String COOKIE = "internationalization.resourcebundles.CookieResourceBundle";
    private static final String COMPOUND_MESSAGES = "Compound";

    public static void main(String[] args) {
        printMessagesBundles();
        printCookieBundles();
        printCompoundMessages();
    }

    private static void printCompoundMessages() {
        printCompoundMessage(new Locale("ro"));
        printCompoundMessage(Locale.ENGLISH);

    }

    private static void printCompoundMessage(Locale locale) {
        ResourceBundle messages =
                ResourceBundle.getBundle("Compound", locale);
        Object[] messageArguments = {
                new Date()
        };
        MessageFormat formatter = new MessageFormat("");
        formatter.setLocale(locale);

        formatter.applyPattern(messages.getString("message"));
        String output = formatter.format(messageArguments);

        System.out.println(output);
    }

    // properties resource bundle
    private static void printMessagesBundles() {
        Locale locale = Locale.getDefault();
        printObjectsFromBundles(MESSAGES, locale);

        //return messages from Messages_en
        printObjectsFromBundles(MESSAGES, Locale.FRANCE);

        //return messages from Messages_ja
        printObjectsFromBundles(MESSAGES, Locale.JAPANESE);
    }

    // List resource bundle
    private static void printCookieBundles() {
        Locale[] supportedLocales = {
                Locale.FRANCE,
                Locale.CANADA,
                Locale.JAPAN,
        };
        //for Locale.France return default values
        for (Locale locale : supportedLocales) {
            printObjectsFromBundles(COOKIE, locale);
        }
    }

    private static void printObjectsFromBundles(String bundleName, Locale locale) {
        System.out.println(locale.toString());
        try {
            ResourceBundle bundle = ResourceBundle.getBundle(bundleName, locale);

            Enumeration<String> keys = bundle.getKeys();
            while (keys.hasMoreElements()) {
                String key = keys.nextElement();
                System.out.println(key + " : " + bundle.getObject(key));
            }
        } catch (MissingResourceException e) {
            System.out.println("ERRORR!!!" + e.getMessage());
        }
    }
}
