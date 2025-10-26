package payment;

import payment.adapters.BoletoAdapter;
import payment.adapters.CreditCardAdapter;
import payment.adapters.PixAdapter;

import java.util.Scanner;

public class PaymentGatewayFactory
{

    public static IPaymentGateway getGateway(int choice, Scanner scanner)
    {
        switch (choice)
        {
            case 1:
                return new CreditCardAdapter(scanner);
            case 2:
                return new PixAdapter(scanner);
            case 3:
                return new BoletoAdapter(scanner);
            default:
                return null;
        }
    }
}