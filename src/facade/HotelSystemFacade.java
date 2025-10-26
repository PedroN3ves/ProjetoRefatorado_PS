package facade;

import manager.HotelManager;
import manager.RoomManager;
import manager.CustomerManager;
import manager.BookingManager;
import manager.ReviewManager;
import manager.AnalyticsManager;
import observer.EmailNotificationObserver;
import observer.SystemLogObserver;
import util.CustomerSupport;

import java.util.Scanner;

public class HotelSystemFacade
{
    private final HotelManager hotelManager;
    private final RoomManager roomManager;
    private final CustomerManager customerManager;
    private final BookingManager bookingManager;
    private final ReviewManager reviewManager;
    private final AnalyticsManager analyticsManager;
    private final Scanner scanner;

    public HotelSystemFacade(Scanner scanner)
    {
        this.scanner = scanner;

        this.hotelManager = new HotelManager(scanner);
        this.roomManager = new RoomManager(scanner, hotelManager);
        this.customerManager = new CustomerManager(scanner);
        this.bookingManager = new BookingManager(scanner, customerManager, roomManager, hotelManager);
        this.reviewManager = new ReviewManager(scanner);
        this.analyticsManager = new AnalyticsManager(roomManager, scanner);

        this.bookingManager.addObserver(new EmailNotificationObserver());
        this.bookingManager.addObserver(new SystemLogObserver());
    }

    public void addHotel()


    {
        hotelManager.addHotel();
    }

    public void listHotels()


    {
        hotelManager.listHotels();
    }

    public void addRoom()


    {
        roomManager.addRoom();
    }

    public void listRooms()


    {
        roomManager.listRooms();
    }

    public void createCustomer()

    {
        customerManager.createCustomer();
    }

    public void bookRoom()

    {
        bookingManager.bookRoom();
    }

    public void cancelBooking()

    {
        bookingManager.cancelBooking();
    }

    public void addReview()

    {
        reviewManager.addReview();
    }

    public void showReviews()

    {
        reviewManager.showReviews();
    }

    public void showHotelAnalytics()

    {
        analyticsManager.showHotelAnalytics();
    }

    public void showLoyaltyPoints()
    {
        customerManager.showLoyaltyPoints();
    }

    public void openCustomerSupport()
    {
        CustomerSupport.openSupportMenu();
    }

    public void setRoomMaintenance()
    {
        roomManager.setRoomMaintenance();
    }
}