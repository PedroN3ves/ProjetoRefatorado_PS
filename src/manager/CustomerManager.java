package manager;

import model.Customer;
import model.Reservation;
import strategy.LoyaltyPointsStrategy;
import util.LanguageManager;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CustomerManager
{
    private List<Customer> customers = new ArrayList<>();
    private LoyaltyPointsStrategy pointsStrategy;
    private Scanner scanner;

    public CustomerManager(Scanner scanner)
    {
        this.scanner = scanner;
    }

    public void createCustomer()
    {
        System.out.println(LanguageManager.INSTANCE.getMessage("customer.name"));
        String name = scanner.nextLine();
        System.out.println(LanguageManager.INSTANCE.getMessage("customer.email"));
        String email = scanner.nextLine();

        for (Customer c : customers)
        {
            if (c.getEmail().equalsIgnoreCase(email))
            {
                System.out.println(LanguageManager.INSTANCE.getMessage("customer.exists"));
                return;
            }
        }

        Customer newCustomer = new Customer(name, email);
        customers.add(newCustomer);
        System.out.println(LanguageManager.INSTANCE.getMessage("customer.created"));
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

    public void setPointsStrategy(LoyaltyPointsStrategy strategy)
    {
        this.pointsStrategy = strategy;
    }

    public void addReservationPoints(Reservation reservation)
    {
        if (pointsStrategy != null)
        {
            int points = pointsStrategy.calculatePoints(reservation);
            addLoyaltyPoints(reservation.getCustomer().getEmail(), points);
        }
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
        System.out.println(LanguageManager.INSTANCE.getMessage("customer.loyalty_points.show"));
        String email = scanner.nextLine();

        Customer customer = getCustomerByEmail(email);
        if (customer != null)
        {
            System.out.println(MessageFormat.format(LanguageManager.INSTANCE.getMessage("customer.loyalty_points.result"), email, customer.getLoyaltyPoints()));
        }
        else
        {
            System.out.println(LanguageManager.INSTANCE.getMessage("customer.not_found"));
        }
    }
}