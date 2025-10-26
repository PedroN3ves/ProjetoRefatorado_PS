package payment.adapters;

import payment.IPaymentGateway;
import util.LanguageManager;
import java.util.Scanner;
import java.text.MessageFormat;

public class PixAdapter implements IPaymentGateway
{

    private Scanner scanner;

    public PixAdapter(Scanner scanner)
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

        System.out.println(LanguageManager.INSTANCE.getMessage("pix.key"));
        System.out.println(LanguageManager.INSTANCE.getMessage("pix.waiting"));
        try
        {
            Thread.sleep(1000);
        } catch (InterruptedException e) {}
        System.out.println(LanguageManager.INSTANCE.getMessage("pix.received"));
        return true;
    }
}