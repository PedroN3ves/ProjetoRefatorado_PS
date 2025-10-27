package app;


import facade.HotelSystemFacade;
import util.DatabaseManager;
import util.LanguageManager;

import java.util.Scanner;

public class Main
{
    public static void main(String[] args)
    {
        Scanner scanner = new Scanner(System.in);
        LanguageManager.INSTANCE.initLanguage();
        DatabaseManager.getInstance().createInitialTables();
        HotelSystemFacade facade = new HotelSystemFacade(scanner);

        while (true)
        {
            System.out.println("\n-------- HOTEL BOOKING SYSTEM --------");
            System.out.println("1. " + LanguageManager.INSTANCE.getMessage("menu.option1"));
            System.out.println("2. " + LanguageManager.INSTANCE.getMessage("menu.option2"));
            System.out.println("3. " + LanguageManager.INSTANCE.getMessage("menu.option3"));
            System.out.println("4. " + LanguageManager.INSTANCE.getMessage("menu.option4"));
            System.out.println("5. " + LanguageManager.INSTANCE.getMessage("menu.option5"));
            System.out.println("6. " + LanguageManager.INSTANCE.getMessage("menu.option6"));
            System.out.println("7. " + LanguageManager.INSTANCE.getMessage("menu.option7"));
            System.out.println("8. " + LanguageManager.INSTANCE.getMessage("menu.option8"));
            System.out.println("9. " + LanguageManager.INSTANCE.getMessage("menu.option9"));
            System.out.println("10. " + LanguageManager.INSTANCE.getMessage("menu.option10"));
            System.out.println("11. " + LanguageManager.INSTANCE.getMessage("menu.option11"));
            System.out.println("12. " + LanguageManager.INSTANCE.getMessage("menu.option12"));
            System.out.println("13. " + LanguageManager.INSTANCE.getMessage("menu.option13"));
            System.out.println("14. " + LanguageManager.INSTANCE.getMessage("menu.option14"));

            System.out.println("--------------------------------------");
            System.out.print(LanguageManager.INSTANCE.getMessage("choose_an_option"));
            String choice = scanner.nextLine();

            switch (choice)
            {
                case "1":
                    facade.addHotel();
                    break;
                case "2":
                    facade.listHotels();
                    break;
                case "3":
                    facade.addRoom();
                    break;
                case "4":
                    facade.listRooms();
                    break;
                case "5":
                    facade.createCustomer();
                    break;
                case "6":
                    facade.bookRoom();
                    break;
                case "7":
                    facade.cancelBooking();
                    break;
                case "8":
                    facade.addReview();
                    break;
                case "9":
                    facade.showReviews();
                    break;
                case "10":
                    facade.showHotelAnalytics();
                    break;
                case "11":
                    facade.showLoyaltyPoints();
                    break;
                case "12":
                    facade.openCustomerSupport();
                    break;
                case "13":
                    facade.setRoomMaintenance();
                    break;
                case "14":
                {
                    System.out.println(LanguageManager.INSTANCE.getMessage("exiting.msg"));
                    scanner.close();
                    return;
                }
                default:
                    System.out.println(LanguageManager.INSTANCE.getMessage("default.msg"));
            }
        }
    }
}