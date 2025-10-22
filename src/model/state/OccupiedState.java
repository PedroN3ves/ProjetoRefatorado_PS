package model.state;

import model.Room;

public class OccupiedState implements RoomState
{
    @Override
    public void book(Room room)
    {
        System.out.println("Erro: Quarto " + room.getNumber() + " já está ocupado.");
    }

    @Override
    public void makeAvailable(Room room)
    {
        room.setState(new AvailableState());
    }

    @Override
    public void markForMaintenance(Room room)
    {
        System.out.println("Erro: Não é possível mover quarto ocupado (" + room.getNumber() + ") para manutenção.");
    }

    @Override
    public String getStatusKey()
    {
        return "room.status.occupied";
    }

    @Override
    public boolean isAvailable()
    {
        return false;
    }
}