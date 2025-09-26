package util;

import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Scanner;

public class LanguageManager
{
    private static ResourceBundle messages;
    private static Locale currentLocale;

    public static void initLanguage()
    {
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
    }

    public static String getMessage(String key)
    {
        return messages.getString(key);
    }
}
