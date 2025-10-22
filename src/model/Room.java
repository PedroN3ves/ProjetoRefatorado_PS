package model;

import model.state.AvailableState;
import model.state.RoomState;
import util.LanguageManager;

import java.text.MessageFormat;

public class Room
{
    private String hotelName;
    private String number;
    private String type;
    private double price;
    private RoomState state;

    public Room(String hotelName, String number, String type, double price)
    {
        this.hotelName = hotelName;
        this.number = number;
        this.type = type;
        this.price = price;
        this.state = new AvailableState();
    }

    public void book()
    {
        state.book(this);
    }

    public void makeAvailable()
    {
        state.makeAvailable(this);
    }

    public void putInMaintenance()
    {
        state.markForMaintenance(this);
    }

    public void setState(RoomState state)
    {
        this.state = state;
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
        return state.isAvailable();
    }

    @Override
    public String toString()
    {
        String statusKey = state.getStatusKey();

        String status = LanguageManager.INSTANCE.getMessage(statusKey);

        return MessageFormat.format(
                LanguageManager.INSTANCE.getMessage("room.display_format"),
                number,
                type,
                price,
                status
        );
    }
}