package observer;

import model.Reservation;
import util.LanguageManager;
import java.text.MessageFormat;

public class SystemLogObserver implements ReservationObserver
{
    @Override
    public void onReservationCreated(Reservation reservation)
    {
        System.out.println("📝 " + MessageFormat.format(
                LanguageManager.INSTANCE.getMessage("log.reservation_created"),
                reservation.getHotel().getName(),
                reservation.getCustomer().getEmail()
        ));
    }

    @Override
    public void onReservationCancelled(Reservation reservation)
    {
        System.out.println("📝 " + MessageFormat.format(
                LanguageManager.INSTANCE.getMessage("log.reservation_cancelled"),
                reservation.getHotel().getName(),
                reservation.getCustomer().getEmail()
        ));
    }
}