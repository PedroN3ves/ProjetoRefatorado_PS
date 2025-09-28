package util;

import java.util.Scanner;
import java.text.MessageFormat;

public class CustomerSupport {
    private static Scanner scanner = new Scanner(System.in);

    public static void openSupportMenu() {
        System.out.println("\n" + LanguageManager.INSTANCE.getMessage("support.menu_title"));
        System.out.println(LanguageManager.INSTANCE.getMessage("support.welcome"));
        System.out.println(LanguageManager.INSTANCE.getMessage("support.prompt"));
        System.out.println(LanguageManager.INSTANCE.getMessage("support.option1"));
        System.out.println(LanguageManager.INSTANCE.getMessage("support.option2"));
        System.out.println(LanguageManager.INSTANCE.getMessage("support.option3"));
        System.out.println(LanguageManager.INSTANCE.getMessage("support.option4"));
        System.out.println(LanguageManager.INSTANCE.getMessage("support.option5"));
        System.out.print(LanguageManager.INSTANCE.getMessage("support.choice") + " ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1:
                System.out.println(LanguageManager.INSTANCE.getMessage("support.response1"));
                break;
            case 2:
                System.out.println(LanguageManager.INSTANCE.getMessage("support.response2"));
                break;
            case 3:
                System.out.println(LanguageManager.INSTANCE.getMessage("support.response3"));
                break;
            case 4:
                System.out.println(LanguageManager.INSTANCE.getMessage("support.response4"));
                break;
            case 5:
                System.out.println(LanguageManager.INSTANCE.getMessage("support.response5"));
                return;
            default:
                System.out.println(LanguageManager.INSTANCE.getMessage("support.invalid"));
        }
    }
}