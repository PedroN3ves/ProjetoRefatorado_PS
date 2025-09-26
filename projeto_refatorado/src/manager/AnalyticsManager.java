package manager;

import model.Room;

import util.LanguageManager;

import java.util.List;
import java.util.Scanner;

public class AnalyticsManager
{
    private RoomManager roomManager;
    private Scanner scanner;

    public AnalyticsManager(RoomManager roomManager, Scanner scanner)
    {
        this.roomManager = roomManager;
        this.scanner = scanner;
    }

    public void showHotelAnalytics()
    {
        System.out.println(LanguageManager.getMessage("analytics.prompt_hotel_name"));
        String hotelName = scanner.nextLine();
        List<Room> hotelRooms = roomManager.getRoomsByHotel(hotelName);

        if (hotelRooms.isEmpty())
        {
            System.out.println(LanguageManager.getMessage("analytics.no_rooms"));
            return;
        }

        int total = hotelRooms.size();
        int occupied = 0;
        double revenue = 0;

        for (Room r : hotelRooms)
        {
            if (!r.isAvailable())
            {
                occupied++;
                revenue += r.getPrice();
            }
        }

        double occupancyRate = (occupied / (double)total) * 100;
        System.out.printf(LanguageManager.getMessage("analytics.summary"),
                total, occupied, occupancyRate, revenue);
    }
}
