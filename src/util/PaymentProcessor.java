package util;

import java.util.Scanner;
import java.text.MessageFormat;

public class PaymentProcessor
{
    private static Scanner scanner = new Scanner(System.in);

    public static boolean processPayment(String customerName, double amount)
    {
        System.out.println(LanguageManager.INSTANCE.getMessage("payment.title"));
        System.out.println(LanguageManager.INSTANCE.getMessage("payment.customer") + " " + customerName);
        System.out.println(MessageFormat.format(
                LanguageManager.INSTANCE.getMessage("payment.amount"),
                amount
        ));
        System.out.println(LanguageManager.INSTANCE.getMessage("payment.method"));
        System.out.println(LanguageManager.INSTANCE.getMessage("payment.option1"));
        System.out.println(LanguageManager.INSTANCE.getMessage("payment.option2"));
        System.out.println(LanguageManager.INSTANCE.getMessage("payment.option3"));

        System.out.print(LanguageManager.INSTANCE.getMessage("payment.choice") + " ");
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
                System.out.println(LanguageManager.INSTANCE.getMessage("payment.invalid"));
                return false;
        }
    }

    private static boolean processCreditCard()
    {
        System.out.print(LanguageManager.INSTANCE.getMessage("card.number") + " ");
        String cardNumber = scanner.nextLine();
        System.out.print(LanguageManager.INSTANCE.getMessage("card.expiry") + " ");
        String expiry = scanner.nextLine();
        System.out.print(LanguageManager.INSTANCE.getMessage("card.cvv") + " ");
        String cvv = scanner.nextLine();
        System.out.println(LanguageManager.INSTANCE.getMessage("card.processing"));
        System.out.println(LanguageManager.INSTANCE.getMessage("card.approved"));
        return true;
    }

    private static boolean processPIX()
    {
        System.out.println(LanguageManager.INSTANCE.getMessage("pix.key"));
        System.out.println(LanguageManager.INSTANCE.getMessage("pix.waiting"));
        System.out.println(LanguageManager.INSTANCE.getMessage("pix.received"));
        return true;
    }

    private static boolean processBoleto()
    {
        System.out.println(LanguageManager.INSTANCE.getMessage("boleto.generating"));
        System.out.println(LanguageManager.INSTANCE.getMessage("boleto.generated"));
        System.out.println(LanguageManager.INSTANCE.getMessage("boleto.confirmed"));
        return true;
    }
}