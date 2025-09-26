package manager;

import model.Customer;
import util.LanguageManager;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CustomerManager
{
    private List<Customer> customers = new ArrayList<>();
    private Scanner scanner;

    public CustomerManager(Scanner scanner)
    {
        this.scanner = scanner;
    }

    public void createCustomer()
    {
        System.out.println(LanguageManager.getMessage("customer.name"));
        String name = scanner.nextLine();
        System.out.println(LanguageManager.getMessage("customer.email"));
        String email = scanner.nextLine();

        for (Customer c : customers)
        {
            if (c.getEmail().equalsIgnoreCase(email))
            {
                System.out.println(LanguageManager.getMessage("customer.exists"));
                return;
            }
        }

        Customer newCustomer = new Customer(name, email);
        customers.add(newCustomer);
        System.out.println(LanguageManager.getMessage("customer.created"));
    }

    public Customer getCustomerByEmail(String email)
    {
        for (Customer c : customers)
        {
            if (c.getEmail().equalsIgnoreCase(email))
            {
                return c;
            }
        }
        return null;
    }

    public void addLoyaltyPoints(String email, int points)
    {
        Customer customer = getCustomerByEmail(email);
        if (customer != null)
        {
            customer.addPoints(points);
        }
    }

    public int getLoyaltyPoints(String email)
    {
        Customer customer = getCustomerByEmail(email);
        if (customer != null)
        {
            return customer.getLoyaltyPoints();
        }
        return 0;
    }

    public void showLoyaltyPoints()
    {
        System.out.println(LanguageManager.getMessage("customer.loyalty_points.show"));
        String email = scanner.nextLine();

        Customer customer = getCustomerByEmail(email);
        if (customer != null)
        {
            System.out.println(MessageFormat.format(LanguageManager.getMessage("customer.loyalty_points.result"), email, customer.getLoyaltyPoints()));
        }
        else
        {
            System.out.println(LanguageManager.getMessage("customer.not_found"));
        }
    }
}