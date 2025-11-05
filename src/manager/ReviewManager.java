package manager;

import model.Review;
import util.DatabaseManager;
import util.LanguageManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.Scanner;
import java.util.regex.Pattern;

public class ReviewManager
{
    private final Scanner scanner;
    private final Connection conn;

    public ReviewManager(Scanner scanner)
    {
        this.scanner = scanner;
        this.conn = DatabaseManager.getInstance().getConnection();
    }

    private boolean isValidEmail(String email)
    {
        if (email == null || email.isEmpty())
        {
            return false;
        }
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";

        Pattern pattern = Pattern.compile(emailRegex);
        return pattern.matcher(email).matches();
    }

    public void addReview()
    {
        String email;
        while (true)
        {
            System.out.println(LanguageManager.INSTANCE.getMessage("review.customer_email"));
            email = scanner.nextLine();

            if (isValidEmail(email))
            {
                break;
            }
            else
            {
                System.out.println(LanguageManager.INSTANCE.getMessage("error.email"));
            }
        }

        System.out.println(LanguageManager.INSTANCE.getMessage("review.hotel_name"));
        String hotelName = scanner.nextLine();

        int rating;
        while(true)
        {
            System.out.println(LanguageManager.INSTANCE.getMessage("review.rating"));
            String ratingString = scanner.nextLine();
            try
            {
                rating = Integer.parseInt(ratingString);
                if (rating >= 1 && rating <= 5)
                {
                    break;
                }
                else
                {
                    System.out.println(LanguageManager.INSTANCE.getMessage("error.rating"));
                }
            }
            catch (NumberFormatException e)
            {
                System.out.println(LanguageManager.INSTANCE.getMessage("error.rating2"));
            }
        }


        System.out.println(LanguageManager.INSTANCE.getMessage("review.comment"));
        String comment = scanner.nextLine();

        String sql = "INSERT INTO reviews (customerEmail, hotelName, rating, comment) VALUES (?, ?, ?, ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql))
        {
            pstmt.setString(1, email);
            pstmt.setString(2, hotelName);
            pstmt.setInt(3, rating);
            pstmt.setString(4, comment);

            pstmt.executeUpdate();

            System.out.println(LanguageManager.INSTANCE.getMessage("review.added"));

        }
        catch (SQLException e)
        {
            System.err.println(MessageFormat.format(
                    LanguageManager.INSTANCE.getMessage("error.review_add"),
                    e.getMessage()
            ));
        }
    }

    public void showReviews()
    {
        System.out.println(LanguageManager.INSTANCE.getMessage("review.show_reviews"));
        String hotelName = scanner.nextLine();
        boolean found = false;

        String sql = "SELECT customerEmail, hotelName, rating, comment FROM reviews WHERE LOWER(hotelName) = LOWER(?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql))
        {
            pstmt.setString(1, hotelName);

            try (ResultSet rs = pstmt.executeQuery())
            {
                while (rs.next())
                {
                    if (!found) found = true;
                    Review r = new Review(
                            rs.getString("customerEmail"),
                            rs.getString("hotelName"),
                            rs.getInt("rating"),
                            rs.getString("comment")
                    );

                    System.out.println(r);
                }
            }

            if (!found)
            {
                System.out.println(LanguageManager.INSTANCE.getMessage("review.not_found"));
            }
        }
        catch (SQLException e)
        {
            System.err.println(MessageFormat.format(
                    LanguageManager.INSTANCE.getMessage("error.review_show"),
                    e.getMessage()
            ));
        }
    }
}