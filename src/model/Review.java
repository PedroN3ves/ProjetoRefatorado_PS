package model;

import util.LanguageManager;
import java.text.MessageFormat;

public class Review
{
    private final String email;
    private final String hotelName;
    private final int rating;
    private final String comment;

    public Review(String email, String hotelName, int rating, String comment)
    {
        this.email = email;
        this.hotelName = hotelName;
        this.rating = rating;
        this.comment = comment;
    }

    public String getEmail()
    {
        return email;
    }

    public String getHotelName()
    {
        return hotelName;
    }

    public int getRating()
    {
        return rating;
    }

    public String getComment()
    {
        return comment;
    }

    @Override
    public String
    toString()
    {
        return MessageFormat.format(
                LanguageManager.INSTANCE.getMessage("review.display_format"),
                email,
                rating,
                comment
        );
    }
}