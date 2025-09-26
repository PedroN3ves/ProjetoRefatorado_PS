package model;

public class Review
{
    private String email;
    private String hotelName;
    private int rating;
    private String comment;

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
        return email + " rated " + rating + "/5: " + comment;
    }
}
