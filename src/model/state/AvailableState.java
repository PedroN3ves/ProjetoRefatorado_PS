package model.state;

import model.Room;
import util.LanguageManager;
import java.text.MessageFormat;

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
        System.out.println(MessageFormat.format(
                LanguageManager.INSTANCE.getMessage("room.state_error.already_available"),
                room.getNumber()
        ));
    }

    @Override
    public void markForMaintenance(Room room)
    {
        room.setState(new MaintenanceState());
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