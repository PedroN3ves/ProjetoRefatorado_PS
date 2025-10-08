package observer;

import model.Reservation;
import util.LanguageManager;
import java.text.MessageFormat;

public class EmailNotificationObserver implements ReservationObserver
{
    @Override
    public void onReservationCreated(Reservation reservation)
    {
        System.out.println("📧 " + MessageFormat.format(
                LanguageManager.INSTANCE.getMessage("email.reservation_created"),
                reservation.getCustomer().getName(),
                reservation.getHotel().getName(),
                reservation.getRoom().getNumber()
        ));
    }

    @Override
    public void onReservationCancelled(Reservation reservation)
    {
        System.out.println("📧 " + MessageFormat.format(
                LanguageManager.INSTANCE.getMessage("email.reservation_cancelled"),
                reservation.getCustomer().getName(),
                reservation.getHotel().getName()
        ));
    }
}