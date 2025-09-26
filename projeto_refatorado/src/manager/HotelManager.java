package manager;

import model.Admin;
import model.Hotel;

import util.LanguageManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class HotelManager
{
    private List<Hotel> hotels = new ArrayList<>();
    Admin admin = new Admin("Administrador Chefe", "amdmin@hotelbooking.com");
    private Scanner scanner;


    public HotelManager(Scanner scanner)
    {
        this.scanner = scanner;
    }

    public void addHotel()
    {
        System.out.println(LanguageManager.getMessage("hotel.hotel_name"));
        String name = scanner.nextLine();
        System.out.println(LanguageManager.getMessage("hotel.address"));
        String address = scanner.nextLine();
        System.out.println(LanguageManager.getMessage("hotel.description"));
        String description = scanner.nextLine();

        for (Hotel h : hotels)
        {
            if (h.getName().equalsIgnoreCase(name))
            {
                System.out.println(LanguageManager.getMessage("hotel.already_exists"));
                return;
            }
        }

        Hotel newHotel = new Hotel(name, address, description);
        hotels.add(newHotel);
        admin.addHotel(newHotel);
    }

    public void listHotels()
    {
        if (hotels.isEmpty())
        {
            System.out.println(LanguageManager.getMessage("hotel.empty"));
        }
        else
        {
            int i = 1;
            for (Hotel h : hotels)
            {
                System.out.println("\nHotel " + (i++) + ":");
                System.out.println(h);
            }
        }
    }

    public Hotel getHotelByName(String name)
    {
        for (Hotel h : hotels)
        {
            if (h.getName().equalsIgnoreCase(name))
            {
                return h;
            }
        }
        return null;
    }
}
