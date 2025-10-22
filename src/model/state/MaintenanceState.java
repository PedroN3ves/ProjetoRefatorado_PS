package model.state;

import model.Room;

public class MaintenanceState implements RoomState
{
    @Override
    public void book(Room room)
    {
        System.out.println("Erro: Quarto " + room.getNumber() + " está em manutenção.");
    }

    @Override
    public void makeAvailable(Room room)
    {
        room.setState(new AvailableState());
        System.out.println("Quarto " + room.getNumber() + " saiu da manutenção e está disponível.");
    }

    @Override
    public void markForMaintenance(Room room)
    {
        System.out.println("Quarto " + room.getNumber() + " já está em manutenção.");
    }

    @Override
    public String getStatusKey()
    {
        return "room.status.maintenance";
    }

    @Override
    public boolean isAvailable()
    {
        return false;
    }
}