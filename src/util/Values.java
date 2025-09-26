package util;

import java.util.MissingResourceException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

public class Values
{
    private static ResourceBundle bundle;
    private static final Map<String, Double> PRICES = new HashMap<>();

    static
    {
        PRICES.put("single", 150.00);
        PRICES.put("couple", 250.00);
        PRICES.put("premium", 350.00);
    }

    public static void init(Locale locale)
    {
        try
        {
            bundle = ResourceBundle.getBundle("values", locale);
        }
        catch(MissingResourceException e)
        {
            bundle = ResourceBundle.getBundle("values", Locale.ENGLISH);
            System.err.println("Warning: Using default values (English) for missing bundle: " + locale);
        }
    }

    public static Double getPrice(String translatedType)
    {
        for (String key : PRICES.keySet())
        {
            if (bundle.getString("room.type." + key).equalsIgnoreCase(translatedType))
            {
                return PRICES.get(key);
            }
        }
        return null;
    }

    // Retorna os tipos traduzidos (para validação/exibição)
    public static String[] getTranslatedTypes()
    {
        return new String[]{
                bundle.getString("room.type.single"),
                bundle.getString("room.type.couple"),
                bundle.getString("room.type.premium")};
    }
}