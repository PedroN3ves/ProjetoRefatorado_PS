package manager;

import model.Admin;
import model.Hotel;
import util.DatabaseManager;
import util.LanguageManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.MessageFormat;
import java.util.Scanner;

public class HotelManager
{
    Admin admin = new Admin("Administrador Chefe", "amdmin@hotelbooking.com");
    private final Scanner scanner;
    private final Connection conn;

    public HotelManager(Scanner scanner)
    {
        this.scanner = scanner;
        this.conn = DatabaseManager.getInstance().getConnection();
    }

    public void addHotel()
    {
        System.out.println(LanguageManager.INSTANCE.getMessage("hotel.hotel_name"));
        String name = scanner.nextLine();
        System.out.println(LanguageManager.INSTANCE.getMessage("hotel.address"));
        String address = scanner.nextLine();
        System.out.println(LanguageManager.INSTANCE.getMessage("hotel.description"));
        String description = scanner.nextLine();

        String sql = "INSERT INTO hotels (name, address, description) VALUES (?, ?, ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql))
        {
            pstmt.setString(1, name);
            pstmt.setString(2, address);
            pstmt.setString(3, description);

            pstmt.executeUpdate();

            Hotel newHotel = new Hotel.Builder(name, address)
                    .description(description)
                    .build();
            admin.addHotel(newHotel);

        } catch (SQLException e)
        {
            if (e.getErrorCode() == 19 && e.getMessage().contains("UNIQUE constraint failed"))
            {
                System.out.println(LanguageManager.INSTANCE.getMessage("hotel.already_exists"));
            }
            else
            {
                System.err.println(MessageFormat.format(
                        LanguageManager.INSTANCE.getMessage("error.hotel_add"),
                        e.getMessage()
                ));
            }
        }
    }

    public void listHotels()
    {
        String sql = "SELECT name, address, description FROM hotels";
        boolean found = false;

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql))
        {
            int i = 1;
            while (rs.next())
            {
                if (!found) found = true;

                Hotel hotel = new Hotel.Builder(rs.getString("name"), rs.getString("address"))
                        .description(rs.getString("description"))
                        .build();

                System.out.println("\nHotel " + (i++) + ":");
                System.out.println(hotel);
            }

            if (!found)
            {
                System.out.println(LanguageManager.INSTANCE.getMessage("hotel.empty"));
            }

        }
        catch (SQLException e)
        {
            System.err.println(MessageFormat.format(
                    LanguageManager.INSTANCE.getMessage("error.hotel_list"),
                    e.getMessage()
            ));
        }
    }

    public Hotel getHotelByName(String name)
    {
        String sql = "SELECT name, address, description FROM hotels WHERE LOWER(name) = LOWER(?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql))
        {
            pstmt.setString(1, name);

            try (ResultSet rs = pstmt.executeQuery())
            {
                if (rs.next())
                {
                    return new Hotel.Builder(rs.getString("name"), rs.getString("address"))
                            .description(rs.getString("description"))
                            .build();
                }
            }
        }
        catch (SQLException e)
        {
            System.err.println(MessageFormat.format(
                    LanguageManager.INSTANCE.getMessage("error.hotel_get"),
                    e.getMessage()
            ));
        }

        return null;
    }
}