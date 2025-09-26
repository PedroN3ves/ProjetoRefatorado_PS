package model;

public class CorporativeReservation extends Reservation
{
    public CorporativeReservation(Customer customer, Hotel hotel, Room room, int days)
    {
        super(customer, hotel, room, days);
    }

    @Override
    public double getTotalCost()
    {
        return super.getTotalCost() * 0.70;
    }
}