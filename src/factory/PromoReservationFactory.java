package factory;

import model.*;

public class PromoReservationFactory implements ReservationFactory
{
    @Override
    public Reservation createReservation(Customer customer, Hotel hotel, Room room, int days)
    {
        validate(customer, hotel, room, days);
        return new PromoReservation(customer, hotel, room, days);
    }
}
