package util;

import java.util.Scanner;
import java.text.MessageFormat;

public class CustomerSupport {
    private static Scanner scanner = new Scanner(System.in);

    public static void openSupportMenu() {
        System.out.println("\n" + LanguageManager.getMessage("support.menu_title"));
        System.out.println(LanguageManager.getMessage("support.welcome"));
        System.out.println(LanguageManager.getMessage("support.prompt"));
        System.out.println(LanguageManager.getMessage("support.option1"));
        System.out.println(LanguageManager.getMessage("support.option2"));
        System.out.println(LanguageManager.getMessage("support.option3"));
        System.out.println(LanguageManager.getMessage("support.option4"));
        System.out.println(LanguageManager.getMessage("support.option5"));
        System.out.print(LanguageManager.getMessage("support.choice") + " ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1:
                System.out.println(LanguageManager.getMessage("support.response1"));
                break;
            case 2:
                System.out.println(LanguageManager.getMessage("support.response2"));
                break;
            case 3:
                System.out.println(LanguageManager.getMessage("support.response3"));
                break;
            case 4:
                System.out.println(LanguageManager.getMessage("support.response4"));
                break;
            case 5:
                System.out.println(LanguageManager.getMessage("support.response5"));
                return;
            default:
                System.out.println(LanguageManager.getMessage("support.invalid"));
        }
    }
}