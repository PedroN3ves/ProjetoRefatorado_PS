package model;

public class Customer extends User
{
    private int loyaltyPoints;

    public Customer(String name, String email)
    {
        super(name, email);
        this.loyaltyPoints = 0;
    }

    public int getLoyaltyPoints()
    {
        return loyaltyPoints;
    }

    public void addPoints(int points)
    {
        this.loyaltyPoints += points;
    }

}