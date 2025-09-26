package util;

import java.util.Scanner;
import java.text.MessageFormat;

public class PaymentProcessor
{
    private static Scanner scanner = new Scanner(System.in);

    public static boolean processPayment(String customerName, double amount)
    {
        System.out.println(LanguageManager.getMessage("payment.title"));
        System.out.println(LanguageManager.getMessage("payment.customer") + " " + customerName);
        System.out.println(MessageFormat.format(
                LanguageManager.getMessage("payment.amount"),
                amount
        ));
        System.out.println(LanguageManager.getMessage("payment.method"));
        System.out.println(LanguageManager.getMessage("payment.option1"));
        System.out.println(LanguageManager.getMessage("payment.option2"));
        System.out.println(LanguageManager.getMessage("payment.option3"));

        System.out.print(LanguageManager.getMessage("payment.choice") + " ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice)
        {
            case 1:
                return processCreditCard();
            case 2:
                return processPIX();
            case 3:
                return processBoleto();
            default:
                System.out.println(LanguageManager.getMessage("payment.invalid"));
                return false;
        }
    }

    private static boolean processCreditCard()
    {
        System.out.print(LanguageManager.getMessage("card.number") + " ");
        String cardNumber = scanner.nextLine();
        System.out.print(LanguageManager.getMessage("card.expiry") + " ");
        String expiry = scanner.nextLine();
        System.out.print(LanguageManager.getMessage("card.cvv") + " ");
        String cvv = scanner.nextLine();
        System.out.println(LanguageManager.getMessage("card.processing"));
        System.out.println(LanguageManager.getMessage("card.approved"));
        return true;
    }

    private static boolean processPIX()
    {
        System.out.println(LanguageManager.getMessage("pix.key"));
        System.out.println(LanguageManager.getMessage("pix.waiting"));
        System.out.println(LanguageManager.getMessage("pix.received"));
        return true;
    }

    private static boolean processBoleto()
    {
        System.out.println(LanguageManager.getMessage("boleto.generating"));
        System.out.println(LanguageManager.getMessage("boleto.generated"));
        System.out.println(LanguageManager.getMessage("boleto.confirmed"));
        return true;
    }
}