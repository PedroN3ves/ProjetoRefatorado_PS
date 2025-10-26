package payment;

public interface IPaymentGateway
{
    boolean processPayment(String customerName, double amount);
}