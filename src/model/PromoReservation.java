package model;

public class PromoReservation extends Reservation
{
    public PromoReservation(Customer customer, Hotel hotel, Room room, int days)
    {
        super(customer, hotel, room, days);
    }

    @Override
    public double getTotalCost()
    {
        return super.getTotalCost() * 0.85;
    }
}