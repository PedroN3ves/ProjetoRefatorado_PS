package manager;

import model.Hotel;
import model.Room;
import model.state.AvailableState;
import model.state.MaintenanceState;
import model.state.OccupiedState;
import util.DatabaseManager;
import util.LanguageManager;
import util.Values;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class RoomManager
{
    private final Scanner scanner;
    private final HotelManager hotelManager;
    private final Connection conn;

    public RoomManager(Scanner scanner, HotelManager hotelManager)
    {
        this.scanner = scanner;
        this.hotelManager = hotelManager;
        this.conn = DatabaseManager.getInstance().getConnection();
    }

    private Room buildRoomFromResultSet(ResultSet rs) throws SQLException {
        String hotelName = rs.getString("hotelName");
        String number = rs.getString("number");
        String type = rs.getString("type");
        double price = rs.getDouble("price");
        String status = rs.getString("status");

        Room room = new Room(hotelName, number, type, price);

        switch (status)
        {
            case "occupied":
                room.setState(new OccupiedState());
                break;
            case "maintenance":
                room.setState(new MaintenanceState());
                break;
            case "available":
            default:
                room.setState(new AvailableState());
                break;
        }
        return room;
    }

    public void addRoom()
    {
        System.out.println(LanguageManager.INSTANCE.getMessage("room.hotel_name"));
        String hotelName = scanner.nextLine();

        Hotel hotel = hotelManager.getHotelByName(hotelName);
        if (hotel == null)
        {
            System.out.println(LanguageManager.INSTANCE.getMessage("room.hotel_not_found"));
            return;
        }

        System.out.println(LanguageManager.INSTANCE.getMessage("room.room_number"));
        String number = scanner.nextLine();
        System.out.println(LanguageManager.INSTANCE.getMessage("room.room_type"));
        String type = scanner.nextLine();

        Double price = Values.getPrice(type);
        if (price == null)
        {
            System.out.println(LanguageManager.INSTANCE.getMessage("room.invalid"));
            return;
        }

        String sql = "INSERT INTO rooms (hotelName, number, type, price, status) VALUES (?, ?, ?, ?, 'available')";

        try (PreparedStatement pstmt = conn.prepareStatement(sql))
        {
            pstmt.setString(1, hotel.getName());
            pstmt.setString(2, number);
            pstmt.setString(3, type);
            pstmt.setDouble(4, price);

            pstmt.executeUpdate();
            System.out.println(LanguageManager.INSTANCE.getMessage("room.successful"));

        } catch (SQLException e)
        {
            if (e.getErrorCode() == 19 && e.getMessage().contains("UNIQUE constraint failed"))
            {
                System.out.println(LanguageManager.INSTANCE.getMessage("room.exists"));
            } else
            {
                System.err.println(MessageFormat.format(
                        LanguageManager.INSTANCE.getMessage("error.room_add"),
                        e.getMessage()
                ));
            }
        }
    }

    public void listRooms()
    {
        System.out.println(LanguageManager.INSTANCE.getMessage("room.list_rooms"));
        String hotelName = scanner.nextLine();
        boolean found = false;

        String sql = "SELECT * FROM rooms WHERE LOWER(hotelName) = LOWER(?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql))
        {
            pstmt.setString(1, hotelName);

            try (ResultSet rs = pstmt.executeQuery())
            {
                while (rs.next())
                {
                    if (!found) found = true;
                    Room room = buildRoomFromResultSet(rs);
                    System.out.println(room);
                }
            }

            if (!found)
            {
                System.out.println(LanguageManager.INSTANCE.getMessage("room.no_rooms"));
            }
        } catch (SQLException e)
        {
            System.err.println(MessageFormat.format(
                    LanguageManager.INSTANCE.getMessage("error.room_list"),
                    e.getMessage()
            ));
        }
    }

    public Room getAvailableRoom(String hotelName, String number)
    {
        String sql = "SELECT * FROM rooms WHERE LOWER(hotelName) = LOWER(?) AND number = ? AND status = 'available'";

        try (PreparedStatement pstmt = conn.prepareStatement(sql))
        {
            pstmt.setString(1, hotelName);
            pstmt.setString(2, number);

            try (ResultSet rs = pstmt.executeQuery())
            {
                if (rs.next())
                {
                    return buildRoomFromResultSet(rs);
                }
            }
        } catch (SQLException e)
        {
            // CORRIGIDO:
            System.err.println(MessageFormat.format(
                    LanguageManager.INSTANCE.getMessage("error.room_get_available"),
                    e.getMessage()
            ));
        }
        return null;
    }

    public Room getRoom(String hotelName, String number)
    {
        String sql = "SELECT * FROM rooms WHERE LOWER(hotelName) = LOWER(?) AND number = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql))
        {
            pstmt.setString(1, hotelName);
            pstmt.setString(2, number);

            try (ResultSet rs = pstmt.executeQuery())
            {
                if (rs.next())
                {
                    return buildRoomFromResultSet(rs);
                }
            }
        } catch (SQLException e)
        {
            // CORRIGIDO:
            System.err.println(MessageFormat.format(
                    LanguageManager.INSTANCE.getMessage("error.room_get"),
                    e.getMessage()
            ));
        }
        return null;
    }

    public List<Room> getRoomsByHotel(String hotelName)
    {
        List<Room> result = new ArrayList<>();
        String sql = "SELECT * FROM rooms WHERE LOWER(hotelName) = LOWER(?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql))
        {
            pstmt.setString(1, hotelName);

            try (ResultSet rs = pstmt.executeQuery())
            {
                while (rs.next())
                {
                    result.add(buildRoomFromResultSet(rs));
                }
            }
        } catch (SQLException e)
        {
            // CORRIGIDO:
            System.err.println(MessageFormat.format(
                    LanguageManager.INSTANCE.getMessage("error.room_get_by_hotel"),
                    e.getMessage()
            ));
        }
        return result;
    }

    public void setRoomMaintenance()
    {
        System.out.println(LanguageManager.INSTANCE.getMessage("room.maintenance_prompt_hotel"));
        String hotelName = scanner.nextLine();

        System.out.println(LanguageManager.INSTANCE.getMessage("room.maintenance_prompt_room"));
        String roomNumber = scanner.nextLine();

        Room room = getRoom(hotelName, roomNumber);
        if (room == null)
        {
            System.out.println(LanguageManager.INSTANCE.getMessage("room.no_rooms"));
            return;
        }

        if (room.isAvailable())
        {
            String sql = "UPDATE rooms SET status = 'maintenance' WHERE LOWER(hotelName) = LOWER(?) AND number = ?";

            try (PreparedStatement pstmt = conn.prepareStatement(sql))
            {
                pstmt.setString(1, hotelName);
                pstmt.setString(2, roomNumber);

                int rowsAffected = pstmt.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println(MessageFormat.format(
                            LanguageManager.INSTANCE.getMessage("room.maintenance_success"),
                            roomNumber
                    ));
                }

            } catch (SQLException e) {
                System.err.println(MessageFormat.format(
                        LanguageManager.INSTANCE.getMessage("error.room_maintenance_set"),
                        e.getMessage()
                ));
            }
        }
        else
        {
            room.putInMaintenance();
        }
    }
}