package model.state;

import model.Room;

public interface RoomState
{
    void book(Room room);
    void makeAvailable(Room room);
    void markForMaintenance(Room room);
    String getStatusKey();
    boolean isAvailable();
}