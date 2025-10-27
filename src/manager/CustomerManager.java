package manager;

import model.Customer;
import model.Reservation;
import strategy.LoyaltyPointsStrategy;
import util.DatabaseManager;
import util.LanguageManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.Scanner;

public class CustomerManager
{
    private LoyaltyPointsStrategy pointsStrategy;
    private final Scanner scanner;
    private final Connection conn;

    public CustomerManager(Scanner scanner)
    {
        this.scanner = scanner;
        this.conn = DatabaseManager.getInstance().getConnection();
    }

    public void createCustomer()
    {
        System.out.println(LanguageManager.INSTANCE.getMessage("customer.name"));
        String name = scanner.nextLine();
        System.out.println(LanguageManager.INSTANCE.getMessage("customer.email"));
        String email = scanner.nextLine();

        String sql = "INSERT INTO customers (email, name) VALUES (?, ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql))
        {
            pstmt.setString(1, email);
            pstmt.setString(2, name);

            pstmt.executeUpdate();

            System.out.println(LanguageManager.INSTANCE.getMessage("customer.created"));

        }
        catch (SQLException e)
        {
            if (e.getErrorCode() == 19 && e.getMessage().contains("UNIQUE constraint failed"))
            {
                System.out.println(LanguageManager.INSTANCE.getMessage("customer.exists"));
            } else
            {
                System.err.println(MessageFormat.format(
                        LanguageManager.INSTANCE.getMessage("error.customer_create"),
                        e.getMessage()
                ));
            }
        }
    }

    public Customer getCustomerByEmail(String email)
    {
        String sql = "SELECT email, name, loyaltyPoints FROM customers WHERE LOWER(email) = LOWER(?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql))
        {
            pstmt.setString(1, email);

            try (ResultSet rs = pstmt.executeQuery())
            {
                if (rs.next())
                {
                    Customer customer = new Customer(
                            rs.getString("name"),
                            rs.getString("email")
                    );
                    customer.addPoints(rs.getInt("loyaltyPoints"));
                    return customer;
                }
            }
        }
        catch (SQLException e)
        {
            System.err.println(MessageFormat.format(
                    LanguageManager.INSTANCE.getMessage("error.customer_get"),
                    e.getMessage()
            ));
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
        String sql = "UPDATE customers SET loyaltyPoints = loyaltyPoints + ? WHERE LOWER(email) = LOWER(?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql))
        {
            pstmt.setInt(1, points);
            pstmt.setString(2, email);
            pstmt.executeUpdate();

        }
        catch (SQLException e)
        {
            System.err.println(MessageFormat.format(
                    LanguageManager.INSTANCE.getMessage("error.customer_add_points"),
                    e.getMessage()
            ));
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
            System.out.println(MessageFormat.format(
                    LanguageManager.INSTANCE.getMessage("customer.loyalty_points.result"),
                    email,
                    customer.getLoyaltyPoints()
            ));
        }
        else
        {
            System.out.println(LanguageManager.INSTANCE.getMessage("customer.not_found"));
        }
    }
}