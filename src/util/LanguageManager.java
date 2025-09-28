package util;

import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Scanner;

public enum LanguageManager
{
    INSTANCE;

    private ResourceBundle messages;
    private Locale currentLocale;
    private boolean initialized = false;

    public void initLanguage()
    {
        if(initialized)
        {
            return;
        }

        Scanner scanner = new Scanner(System.in);
        System.out.println("Choose your language / Escolha seu idioma / Elija su idioma:");
        System.out.println("1. English\n2. Português\n3. Español");
        System.out.print("Option: ");
        String option = scanner.nextLine();

        Locale locale;

        switch (option) {
            case "1":
                locale = new Locale("en");
                break;
            case "2":
                locale = new Locale("pt");
                break;
            case "3":
                locale = new Locale("es");
                break;
            default:
                System.out.println("Invalid option. Defaulting to English.");
                locale = new Locale("en");
                break;
        }

        messages = ResourceBundle.getBundle("messages", locale);
        currentLocale = locale;
        Values.init(locale);
        initialized = true;
    }

    public String getMessage(String key)
    {
        if(!initialized)
        {
            throw new IllegalStateException();
        }
        return messages.getString(key);
    }

    public boolean IsInitialized()
    {
        return initialized;
    }

    public Locale getCurrentLocale()
    {
        return currentLocale;
    }

    public void reinitLanguage(Locale locale)
    {
        messages = ResourceBundle.getBundle("messages", locale);
        currentLocale = locale;
        Values.init(locale);
        initialized = true;
    }

}
