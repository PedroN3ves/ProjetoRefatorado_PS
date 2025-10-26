package payment.adapters;

import payment.IPaymentGateway;
import util.LanguageManager;
import java.util.Scanner;
import java.text.MessageFormat;

public class BoletoAdapter implements IPaymentGateway
{

    private Scanner scanner;

    public BoletoAdapter(Scanner scanner)
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

        System.out.println(LanguageManager.INSTANCE.getMessage("boleto.generating"));
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {}
        System.out.println(LanguageManager.INSTANCE.getMessage("boleto.generated"));
        System.out.println(LanguageManager.INSTANCE.getMessage("boleto.confirmed"));
        return true;
    }
}