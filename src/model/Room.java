package model;

import util.LanguageManager;

import java.text.MessageFormat;

public class Room
{
    private String hotelName;
    private String number;
    private String type;
    private double price;
    private boolean available;

    public Room(String hotelName, String number, String type, double price)
    {
        this.hotelName = hotelName;
        this.number = number;
        this.type = type;
        this.price = price;
        this.available = true;
    }

    public String getHotelName()
    {
        return hotelName;
    }

    public String getNumber()
    {
        return number;
    }

    public String getType()
    {
        return type;
    }

    public double getPrice()
    {
        return price;
    }

    public boolean isAvailable()
    {
        return available;
    }

    public void setAvailable(boolean status)
    {
        this.available = status;
    }

    @Override
    public String toString()
    {
        String status = isAvailable() ?
                LanguageManager.getMessage("room.status.available") :
                LanguageManager.getMessage("room.status.occupied");

        return MessageFormat.format(
                LanguageManager.getMessage("room.display_format"),
                number,
                type,
                price,
                status
        );
    }
}
