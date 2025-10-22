package model.state;

import model.Room;

public class AvailableState implements RoomState
{
    @Override
    public void book(Room room)
    {
        room.setState(new OccupiedState());
    }

    @Override
    public void makeAvailable(Room room)
    {
        System.out.println("Quarto " + room.getNumber() + " já está disponível.");
    }

    @Override
    public void markForMaintenance(Room room)
    {
        room.setState(new MaintenanceState());
        System.out.println("Quarto " + room.getNumber() + " colocado em manutenção.");
    }

    @Override
    public String getStatusKey()
    {
        return "room.status.available";
    }

    @Override
    public boolean isAvailable()
    {
        return true;
    }
}