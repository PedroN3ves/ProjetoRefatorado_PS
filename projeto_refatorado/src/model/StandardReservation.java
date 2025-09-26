package model;

public class StandardReservation extends  Reservation
{
    public StandardReservation(Customer customer, Hotel hotel,Room room, int days)
    {
        super(customer, hotel, room, days);
    }

    @Override
    public double getTotalCost()
    {
        return super.getTotalCost(); // sem desconto
    }
}
