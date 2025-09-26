package app;

import manager.HotelManager;
import manager.RoomManager;
import manager.CustomerManager;
import manager.BookingManager;
import manager.ReviewManager;
import manager.AnalyticsManager;

import model.Admin;
import util.CustomerSupport;
import util.LanguageManager;

import java.util.Scanner;

public class Main
{
    public static void main(String[] args)
    {
        Scanner scanner = new Scanner(System.in);

        HotelManager hotelManager = new HotelManager(scanner);
        RoomManager roomManager = new RoomManager(scanner, hotelManager);
        CustomerManager customerManager = new CustomerManager(scanner);
        BookingManager bookingManager = new BookingManager(scanner, customerManager, roomManager, hotelManager);
        ReviewManager reviewManager = new ReviewManager(scanner);
        AnalyticsManager analyticsManager = new AnalyticsManager(roomManager, scanner);
        LanguageManager.initLanguage();

        while (true)
        {
            System.out.println("\n-------- HOTEL BOOKING SYSTEM --------");
            System.out.println("1. " + LanguageManager.getMessage("menu.option1"));
            System.out.println("2. " + LanguageManager.getMessage("menu.option2"));
            System.out.println("3. " + LanguageManager.getMessage("menu.option3"));
            System.out.println("4. " + LanguageManager.getMessage("menu.option4"));
            System.out.println("5. " + LanguageManager.getMessage("menu.option5"));
            System.out.println("6. " + LanguageManager.getMessage("menu.option6"));
            System.out.println("7. " + LanguageManager.getMessage("menu.option7"));
            System.out.println("8. " + LanguageManager.getMessage("menu.option8"));
            System.out.println("9. " + LanguageManager.getMessage("menu.option9"));
            System.out.println("10. " + LanguageManager.getMessage("menu.option10"));
            System.out.println("11. " + LanguageManager.getMessage("menu.option11"));
            System.out.println("12. " + LanguageManager.getMessage("menu.option12"));
            System.out.println("13. " + LanguageManager.getMessage("menu.option13"));
            System.out.println("--------------------------------------");
            System.out.print(LanguageManager.getMessage("choose_an_option"));
            String choice = scanner.nextLine();

            switch (choice)
            {
                case "1":
                    hotelManager.addHotel();
                    break;
                case "2":
                    hotelManager.listHotels();
                    break;
                case "3":
                    roomManager.addRoom();
                    break;
                case "4":
                    roomManager.listRooms();
                    break;
                case "5":
                    customerManager.createCustomer();
                    break;
                case "6":
                    bookingManager.bookRoom();
                    break;
                case "7":
                    bookingManager.cancelBooking();
                    break;
                case "8":
                    reviewManager.addReview();
                    break;
                case "9":
                    reviewManager.showReviews();
                    break;
                case "10":
                {
                    analyticsManager.showHotelAnalytics();
                    break;
                }
                case "11":
                    customerManager.showLoyaltyPoints();
                    break;
                case "12":
                    CustomerSupport.openSupportMenu();
                    break;
                case "13":
                {
                    System.out.println(LanguageManager.getMessage("exiting.msg"));
                    scanner.close();
                    return;
                }
                default:
                    System.out.println(LanguageManager.getMessage("default.msg"));
            }
        }
    }
}
