package model;

public abstract class Reservation
{
    protected Customer customer;
    protected Hotel hotel;
    protected Room room;
    protected int days;

    public Reservation(Customer customer, Hotel hotel,Room room, int days)
    {
        this.customer = customer;
        this.hotel = hotel;
        this.room = room;
        this.days = days;
    }

    public double getTotalCost()
    {
        return room.getPrice() * days;
    }
    public Hotel getHotel()
    {
        return hotel;
    }

    public Room getRoom()
    {
        return room;
    }

    public int getDays()
    {
        return days;
    }

    public Customer getCustomer()
    {
        return customer;
    }
}
