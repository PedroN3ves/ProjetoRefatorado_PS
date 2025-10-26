package payment.adapters;

import payment.IPaymentGateway;
import util.LanguageManager;
import java.util.Scanner;
import java.text.MessageFormat;

public class CreditCardAdapter implements IPaymentGateway
{
    private Scanner scanner;

    public CreditCardAdapter(Scanner scanner)
    {
        this.scanner = scanner;
    }

    @Override
    public boolean processPayment(String customerName, double amount)
    {
        System.out.println("\n" + LanguageManager.INSTANCE.getMessage("payment.title"));
        System.out.println(LanguageManager.INSTANCE.getMessage("payment.customer") + " " + customerName);
        System.out.println(MessageFormat.format(
                LanguageManager.INSTANCE.getMessage("payment.amount"),
                amount
        ));

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
}