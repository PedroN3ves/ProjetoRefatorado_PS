package observer;

import model.Reservation;

public interface ReservationObserver
{
    void onReservationCreated(Reservation reservation);
    void onReservationCancelled(Reservation reservation);
}
