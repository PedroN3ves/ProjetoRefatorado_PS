package factory;

import model.*;

public class CorporativeReservationFactory implements ReservationFactory
{
    @Override
    public Reservation createReservation(Customer customer, Hotel hotel, Room room, int days)
    {
        validate(customer, hotel, room, days);
        return new CorporativeReservation(customer, hotel, room, days);
    }
}
