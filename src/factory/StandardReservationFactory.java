package factory;

import model.*;

public class StandardReservationFactory implements ReservationFactory
{
    @Override
    public Reservation createReservation(Customer customer, Hotel hotel, Room room, int days)
    {
        validate(customer, hotel, room, days);
        return new StandardReservation(customer,hotel, room, days);
    }
}
