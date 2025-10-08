package manager;

import factory.*;
import model.*;
import strategy.CorporatePoinstsStrategy;
import strategy.LoyaltyPointsStrategy;
import strategy.PromoPointsStrategy;
import strategy.StandardPointsStrategy;
import util.PaymentProcessor;
import util.LanguageManager;
import java.text.MessageFormat;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class BookingManager
{
    private List<Reservation> bookings = new ArrayList<>();
    private Scanner scanner;
    private CustomerManager customerManager;
    private RoomManager roomManager;
    private HotelManager hotelManager;
    private int days;
    private double amount;

    public BookingManager(Scanner scanner, CustomerManager customerManager, RoomManager roomManager, HotelManager hotelManager)
    {
        this.scanner = scanner;
        this.customerManager = customerManager;
        this.roomManager = roomManager;
        this.hotelManager = hotelManager;
    }

    private ReservationFactory getTypeReservation(int type)
    {
        switch (type)
        {
            case 1:
                return new StandardReservationFactory();

            case 2:
                return new PromoReservationFactory();

            case 3:
                return new CorporativeReservationFactory();

            default:
                return new StandardReservationFactory();
        }
    }


    private Reservation createReservationFactory(int type,Customer customer,Hotel hotel,Room room, int days)
    {
        ReservationFactory factory = getTypeReservation(type);
        return factory.createReservation(customer, hotel, room, days);
    }

    public void bookRoom()
    {
        System.out.println(LanguageManager.INSTANCE.getMessage("booking.payment_failed"));
        String email = scanner.nextLine();
        Customer customer = customerManager.getCustomerByEmail(email);
        if (customer == null)
        {
            System.out.println(LanguageManager.INSTANCE.getMessage("booking.customer_not_found"));
            return;
        }

        System.out.println(LanguageManager.INSTANCE.getMessage("booking.hotel_name"));
        String hotelName = scanner.nextLine();
        System.out.println(LanguageManager.INSTANCE.getMessage("booking.room_number"));
        String roomNumber = scanner.nextLine();
        System.out.println(LanguageManager.INSTANCE.getMessage("booking.days"));
        String daysString = scanner.nextLine();

        days = Integer.parseInt(daysString);

        Room room = roomManager.getAvailableRoom(hotelName, roomNumber);
        if (room == null)
        {
            System.out.println(LanguageManager.INSTANCE.getMessage("booking.room_not_available"));
            return;
        }

        Hotel hotel = hotelManager.getHotelByName(room.getHotelName());

        System.out.println(LanguageManager.INSTANCE.getMessage("booking.type"));
        int typeChoice = Integer.parseInt(scanner.nextLine());

        Reservation reservation = createReservationFactory(typeChoice, customer, hotel, room, days);

        LoyaltyPointsStrategy strategy = switch (typeChoice) {
            case 1 ->
                    new StandardPointsStrategy();
            case 2 ->
                    new PromoPointsStrategy();
            case 3 ->
                    new CorporatePoinstsStrategy();
            default ->
                    new StandardPointsStrategy();
        };

        amount = reservation.getTotalCost();
        boolean paid = PaymentProcessor.processPayment(customer.getName(), amount);
        if (!paid)
        {
            System.out.println(LanguageManager.INSTANCE.getMessage("booking.payment_failed"));
            return;
        }

        room.setAvailable(false);
        bookings.add(reservation);
        customerManager.setPointsStrategy(strategy);
        customerManager.addReservationPoints(reservation);
        System.out.println(MessageFormat.format(LanguageManager.INSTANCE.getMessage("booking.success"), roomNumber, hotelName, customerManager.getLoyaltyPoints(email)));
    }

    public void cancelBooking()
    {
        System.out.println(LanguageManager.INSTANCE.getMessage("booking.cancel_hotel"));
        String hotelName = scanner.nextLine();
        System.out.println(LanguageManager.INSTANCE.getMessage("booking.cancel_room"));
        String roomNumber = scanner.nextLine();

        Iterator<Reservation> iterator = bookings.iterator();
        while (iterator.hasNext())
        {
            Reservation r = iterator.next();
            if (r.getHotel().getName().equalsIgnoreCase(hotelName) && r.getRoom().getNumber().equals(roomNumber))
            {
                Room room = roomManager.getRoom(hotelName, roomNumber);
                if (room != null)
                {
                    room.setAvailable(true);
                }
                iterator.remove();
                System.out.println(LanguageManager.INSTANCE.getMessage("booking.cancelled"));
                return;
            }
        }
        System.out.println(LanguageManager.INSTANCE.getMessage("booking.not_found"));
    }
}
