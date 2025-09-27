package factory;

import model.Customer;
import model.Hotel;
import model.Reservation;
import model.Room;

public interface ReservationFactory
{
    Reservation createReservation(Customer customer, Hotel hotel, Room room, int days);

    default void validate(Customer customer, Hotel hotel, Room room, int days)
    {
        if(customer == null)
        {
            throw new IllegalArgumentException();
        }
        else if(hotel == null)
        {
            throw new IllegalArgumentException();
        }
        else if(room == null)
        {
            throw new IllegalArgumentException();
        }
        else if(days <= 0)
        {
            throw new IllegalArgumentException();
        }
    }
}
