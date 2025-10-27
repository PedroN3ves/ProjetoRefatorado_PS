package manager;

import factory.*;
import model.*;
import observer.ReservationObserver;
import payment.IPaymentGateway;
import payment.PaymentGatewayFactory;
import strategy.CorporatePoinstsStrategy;
import strategy.LoyaltyPointsStrategy;
import strategy.PromoPointsStrategy;
import strategy.StandardPointsStrategy;
import util.DatabaseManager;
import util.LanguageManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BookingManager
{
    private final Scanner scanner;
    private final CustomerManager customerManager;
    private final RoomManager roomManager;
    private final HotelManager hotelManager;
    private int days;
    private double amount;
    private final List<ReservationObserver> observers = new ArrayList<>();
    private final Connection conn;
    public BookingManager(Scanner scanner, CustomerManager customerManager, RoomManager roomManager, HotelManager hotelManager)
    {
        this.scanner = scanner;
        this.customerManager = customerManager;
        this.roomManager = roomManager;
        this.hotelManager = hotelManager;
        this.conn = DatabaseManager.getInstance().getConnection();
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

    public void addObserver(ReservationObserver observer)
    {
        observers.add(observer);
    }

    public void removeObserver(ReservationObserver observer)
    {
        observers.remove(observer);
    }

    private void notifyReservationCreated(Reservation reservation)
    {
        for (ReservationObserver observer : observers)
        {
            observer.onReservationCreated(reservation);
        }
    }

    private void notifyReservationCancelled(Reservation reservation)
    {
        for (ReservationObserver observer : observers)
        {
            observer.onReservationCancelled(reservation);
        }
    }

    public void bookRoom()
    {
        System.out.println(LanguageManager.INSTANCE.getMessage("booking.customer_email"));
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

        LoyaltyPointsStrategy strategy = switch (typeChoice)
        {
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

        System.out.println(LanguageManager.INSTANCE.getMessage("payment.method"));
        System.out.println(LanguageManager.INSTANCE.getMessage("payment.option1"));
        System.out.println(LanguageManager.INSTANCE.getMessage("payment.option2"));
        System.out.println(LanguageManager.INSTANCE.getMessage("payment.option3"));
        System.out.print(LanguageManager.INSTANCE.getMessage("payment.choice") + " ");

        int paymentChoice;
        try
        {
            paymentChoice = Integer.parseInt(scanner.nextLine());
        }
        catch (NumberFormatException e)
        {
            paymentChoice = -1;
        }

        IPaymentGateway gateway = PaymentGatewayFactory.getGateway(paymentChoice, scanner);
        boolean paid;

        if (gateway == null)
        {
            System.out.println(LanguageManager.INSTANCE.getMessage("payment.invalid"));
            paid = false;
        }
        else
        {
            paid = gateway.processPayment(customer.getName(), amount);
        }

        if (!paid)
        {
            System.out.println(LanguageManager.INSTANCE.getMessage("booking.payment_failed"));
            return;
        }

        String sqlInsertReservation = "INSERT INTO reservations (customerEmail, hotelName, roomNumber, days, totalCost) VALUES (?, ?, ?, ?, ?)";
        String sqlUpdateRoom = "UPDATE rooms SET status = 'occupied' WHERE LOWER(hotelName) = LOWER(?) AND number = ?";

        try (PreparedStatement pstmtInsert = conn.prepareStatement(sqlInsertReservation);
             PreparedStatement pstmtUpdate = conn.prepareStatement(sqlUpdateRoom)) {

            pstmtInsert.setString(1, customer.getEmail());
            pstmtInsert.setString(2, hotel.getName());
            pstmtInsert.setString(3, room.getNumber());
            pstmtInsert.setInt(4, days);
            pstmtInsert.setDouble(5, amount);
            pstmtInsert.executeUpdate();

            pstmtUpdate.setString(1, hotel.getName());
            pstmtUpdate.setString(2, room.getNumber());
            pstmtUpdate.executeUpdate();
        }
        catch (SQLException e)
        {
            System.err.println(MessageFormat.format(
                    LanguageManager.INSTANCE.getMessage("error.booking_create"),
                    e.getMessage()
            ));
            room.makeAvailable();
            return;
        }
        customerManager.setPointsStrategy(strategy);
        customerManager.addReservationPoints(reservation);

        notifyReservationCreated(reservation);
        System.out.println(MessageFormat.format(LanguageManager.INSTANCE.getMessage("booking.success"), roomNumber, hotelName, customerManager.getLoyaltyPoints(email)));
    }

    public void cancelBooking()
    {
        System.out.println(LanguageManager.INSTANCE.getMessage("booking.cancel_hotel"));
        String hotelName = scanner.nextLine();
        System.out.println(LanguageManager.INSTANCE.getMessage("booking.cancel_room"));
        String roomNumber = scanner.nextLine();

        Room room = roomManager.getRoom(hotelName, roomNumber);
        if (room == null || room.isAvailable())
        {
            System.out.println(LanguageManager.INSTANCE.getMessage("booking.not_found"));
            return;
        }

        String sqlSelect = "SELECT customerEmail, days FROM reservations WHERE LOWER(hotelName) = LOWER(?) AND roomNumber = ?";
        String customerEmail = null;
        int reservationDays = 0;

        try (PreparedStatement pstmtSelect = conn.prepareStatement(sqlSelect)) {
            pstmtSelect.setString(1, hotelName);
            pstmtSelect.setString(2, roomNumber);

            try (ResultSet rs = pstmtSelect.executeQuery()) {
                if (rs.next()) {
                    customerEmail = rs.getString("customerEmail");
                    reservationDays = rs.getInt("days");
                }
                else
                {
                    System.out.println(LanguageManager.INSTANCE.getMessage("booking.not_found"));
                    return;
                }
            }
        }
        catch (SQLException e)
        {
            System.err.println(MessageFormat.format(
                    LanguageManager.INSTANCE.getMessage("error.booking_get"),
                    e.getMessage()
            ));
            return;
        }

        String sqlDelete = "DELETE FROM reservations WHERE LOWER(hotelName) = LOWER(?) AND roomNumber = ?";
        String sqlUpdate = "UPDATE rooms SET status = 'available' WHERE LOWER(hotelName) = LOWER(?) AND number = ?";

        try (PreparedStatement pstmtDelete = conn.prepareStatement(sqlDelete);
             PreparedStatement pstmtUpdate = conn.prepareStatement(sqlUpdate))
        {
            pstmtDelete.setString(1, hotelName);
            pstmtDelete.setString(2, roomNumber);
            pstmtDelete.executeUpdate();

            pstmtUpdate.setString(1, hotelName);
            pstmtUpdate.setString(2, roomNumber);
            pstmtUpdate.executeUpdate();

        }
        catch (SQLException e)
        {
            System.err.println(MessageFormat.format(
                    LanguageManager.INSTANCE.getMessage("error.booking_delete"),
                    e.getMessage()
            ));
            return;
        }

        Customer customer = customerManager.getCustomerByEmail(customerEmail);
        Hotel hotel = hotelManager.getHotelByName(hotelName);

        Reservation cancelledReservation = new StandardReservation(customer, hotel, room, reservationDays);

        notifyReservationCancelled(cancelledReservation);
        System.out.println(LanguageManager.INSTANCE.getMessage("booking.cancelled"));
    }
}