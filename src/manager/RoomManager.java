package manager;

import model.Hotel;
import model.Room;

import util.LanguageManager;
import util.Values;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class RoomManager
{
    private List<Room> rooms = new ArrayList<>();
    private Scanner scanner;
    private HotelManager hotelManager;

    public RoomManager(Scanner scanner, HotelManager hotelManager)
    {
        this.scanner = scanner;
        this.hotelManager = hotelManager;
    }

    public void addRoom()
    {
        System.out.println(LanguageManager.getMessage("room.hotel_name"));
        String hotelName = scanner.nextLine();
        Hotel hotel = hotelManager.getHotelByName(hotelName);
        if (hotel == null)
        {
            System.out.println(LanguageManager.getMessage("room.hotel_not_found"));
            return;
        }

        System.out.println(LanguageManager.getMessage("room.room_number"));
        String number = scanner.nextLine();
        System.out.println(LanguageManager.getMessage("room.room_type"));
        String type = scanner.nextLine();

        Double price = Values.getPrice(type);
        if (price == null)
        {
            System.out.println(LanguageManager.getMessage("room.invalid"));
            return;
        }

        for (Room r : rooms)
        {
            if (r.getHotelName().equalsIgnoreCase(hotelName) && r.getNumber().equals(number))
            {
                System.out.println(LanguageManager.getMessage("room.exists"));
                return;
            }
        }

        Room room = new Room(hotelName, number, type, price);
        rooms.add(room);
        System.out.println(LanguageManager.getMessage("room.successful"));
    }

    public void listRooms()
    {
        System.out.println(LanguageManager.getMessage("room.list_rooms"));
        String hotelName = scanner.nextLine();
        boolean found = false;
        for (Room r : rooms)
        {
            if (r.getHotelName().equalsIgnoreCase(hotelName))
            {
                System.out.println(r);
                found = true;
            }
        }
        if (!found)
        {
            System.out.println(LanguageManager.getMessage("room.no_rooms"));
        }
    }

    public Room getAvailableRoom(String hotelName, String number)
    {
        for (Room r : rooms)
        {
            if (r.getHotelName().equalsIgnoreCase(hotelName) && r.getNumber().equals(number) && r.isAvailable())
            {
                return r;
            }
        }
        return null;
    }

    public Room getRoom(String hotelName, String number)
    {
        for (Room r : rooms)
        {
            if (r.getHotelName().equalsIgnoreCase(hotelName) && r.getNumber().equals(number))
            {
                return r;
            }
        }
        return null;
    }

    public List<Room> getRoomsByHotel(String hotelName)
    {
        List<Room> result = new ArrayList<>();
        for (Room r : rooms)
        {
            if (r.getHotelName().equalsIgnoreCase(hotelName))
            {
                result.add(r);
            }
        }
        return result;
    }
}