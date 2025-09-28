package manager;

import model.Review;
import util.LanguageManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ReviewManager
{
    private List<Review> reviews = new ArrayList<>();
    private Scanner scanner;

    public ReviewManager(Scanner scanner)
    {
        this.scanner = scanner;
    }

    public void addReview()
    {
        System.out.println(LanguageManager.INSTANCE.getMessage("review.customer_email"));
        String email = scanner.nextLine();
        System.out.println(LanguageManager.INSTANCE.getMessage("review.hotel_name"));
        String hotelName = scanner.nextLine();
        System.out.println(LanguageManager.INSTANCE.getMessage("review.rating"));
        int rating = Integer.parseInt(scanner.nextLine());
        System.out.println(LanguageManager.INSTANCE.getMessage("review.comment"));
        String comment = scanner.nextLine();

        reviews.add(new Review(email, hotelName, rating, comment));
        System.out.println(LanguageManager.INSTANCE.getMessage("review.added"));
    }

    public void showReviews()
    {
        System.out.println(LanguageManager.INSTANCE.getMessage("review.show_reviews"));
        String hotelName = scanner.nextLine();
        boolean found = false;
        for (Review r : reviews)
        {
            if (r.getHotelName().equalsIgnoreCase(hotelName))
            {
                System.out.println(r);
                found = true;
            }
        }
        if (!found)
        {
            System.out.println(LanguageManager.INSTANCE.getMessage("review.not_found"));
        }
    }
}
