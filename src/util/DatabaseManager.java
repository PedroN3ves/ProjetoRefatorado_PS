package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseManager
{

    private static final String DATABASE_URL = "jdbc:sqlite:hotel_booking.db";

    private static volatile DatabaseManager instance;
    private Connection connection;

    private DatabaseManager()
    {
        try
        {
            Class.forName("org.sqlite.JDBC");
            this.connection = DriverManager.getConnection(DATABASE_URL);
            System.out.println("Conexão com o banco de dados SQLite estabelecida.");
        } catch (ClassNotFoundException e)
        {
            System.err.println("Driver JDBC do SQLite não encontrado. Verifique o Passo 1.");
            e.printStackTrace();
        } catch (SQLException e)
        {
            System.err.println("Erro ao conectar ao banco de dados SQLite.");
            e.printStackTrace();
        }
    }

    public static DatabaseManager getInstance()

    {
        if (instance == null)

        {
            synchronized (DatabaseManager.class)

            {
                if (instance == null)

                {
                    instance = new DatabaseManager();
                }
            }
        }
        return instance;
    }

    public Connection getConnection() {
        try
        {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(DATABASE_URL);
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        return connection;
    }

    public void createInitialTables()
    {
        try (Statement stmt = getConnection().createStatement())
        {

            String sqlHotel = "CREATE TABLE IF NOT EXISTS hotels (" +
                    "  name TEXT PRIMARY KEY NOT NULL," +
                    "  address TEXT NOT NULL," +
                    "  description TEXT" +
                    ")";
            stmt.execute(sqlHotel);

            String sqlCustomer = "CREATE TABLE IF NOT EXISTS customers (" +
                    "  email TEXT PRIMARY KEY NOT NULL," +
                    "  name TEXT NOT NULL," +
                    "  loyaltyPoints INTEGER DEFAULT 0" +
                    ")";
            stmt.execute(sqlCustomer);

            String sqlRoom = "CREATE TABLE IF NOT EXISTS rooms (" +
                    "  hotelName TEXT NOT NULL," +
                    "  number TEXT NOT NULL," +
                    "  type TEXT NOT NULL," +
                    "  price REAL NOT NULL," +
                    "  status TEXT NOT NULL DEFAULT 'available'," +
                    "  PRIMARY KEY (hotelName, number)," +
                    "  FOREIGN KEY (hotelName) REFERENCES hotels(name)" +
                    ")";
            stmt.execute(sqlRoom);

            String sqlReservation = "CREATE TABLE IF NOT EXISTS reservations (" +
                    "  id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "  customerEmail TEXT NOT NULL," +
                    "  hotelName TEXT NOT NULL," +
                    "  roomNumber TEXT NOT NULL," +
                    "  days INTEGER NOT NULL," +
                    "  totalCost REAL NOT NULL," +
                    "  FOREIGN KEY (customerEmail) REFERENCES customers(email)," +
                    "  FOREIGN KEY (hotelName, roomNumber) REFERENCES rooms(hotelName, number)" +
                    ")";
            stmt.execute(sqlReservation);

            String sqlReview = "CREATE TABLE IF NOT EXISTS reviews (" +
                    "  id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "  customerEmail TEXT NOT NULL," +
                    "  hotelName TEXT NOT NULL," +
                    "  rating INTEGER NOT NULL," +
                    "  comment TEXT," +
                    "  FOREIGN KEY (customerEmail) REFERENCES customers(email)," +
                    "  FOREIGN KEY (hotelName) REFERENCES hotels(name)" +
                    ")";
            stmt.execute(sqlReview);

            System.out.println("Tabelas verificadas/criadas com sucesso.");

        } catch (SQLException e)
        {
            System.err.println("Erro ao criar tabelas:");
            e.printStackTrace();
        }
    }
}