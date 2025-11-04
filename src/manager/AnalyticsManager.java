package manager;

import model.Room;

import util.DatabaseManager;
import util.LanguageManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.List;
import java.util.Scanner;

public class AnalyticsManager
{
    private RoomManager roomManager;
    private Scanner scanner;
    private final Connection conn;

    public AnalyticsManager(RoomManager roomManager, Scanner scanner)
    {
        this.roomManager = roomManager;
        this.scanner = scanner;
        this.conn = DatabaseManager.getInstance().getConnection();
    }

    public void showHotelAnalytics()
    {
        System.out.println(LanguageManager.INSTANCE.getMessage("analytics.prompt_hotel_name"));
        String hotelName = scanner.nextLine();
        List<Room> hotelRooms = roomManager.getRoomsByHotel(hotelName);

        if (hotelRooms.isEmpty())
        {
            System.out.println(LanguageManager.INSTANCE.getMessage("analytics.no_rooms"));
            return;
        }

        int total = hotelRooms.size();

        int occupiedCount = 0;
        int maintenanceCount = 0;
        double revenue = 0;

        for (Room r : hotelRooms)
        {
            switch (r.getState().getStatusKey())
            {
                case "room.status.occupied":
                    occupiedCount++;
                    break;
                case "room.status.maintenance":
                    maintenanceCount++;
                    break;
            }
        }
        String sql = "SELECT SUM(totalCost) AS totalRevenue FROM reservations WHERE LOWER(hotelName) = LOWER(?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql))
        {
            pstmt.setString(1, hotelName);

            try (ResultSet rs = pstmt.executeQuery())
            {
                if (rs.next())
                {
                    revenue = rs.getDouble("totalRevenue");
                }
            }
        }
        catch (SQLException e)
        {
            System.err.println("Erro ao calcular receita: " + e.getMessage());
        }

        int roomsAvailableForSale = total - maintenanceCount;
        double occupancyRate = 0.0;

        if (roomsAvailableForSale > 0)
        {
            occupancyRate = (occupiedCount / (double) roomsAvailableForSale) * 100;
        }

        System.out.printf(LanguageManager.INSTANCE.getMessage("analytics.summary"),
                total,
                occupiedCount,
                maintenanceCount,
                occupancyRate,
                revenue
        );
    }
}