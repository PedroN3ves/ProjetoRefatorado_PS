package model.state;

import model.Room;
import util.LanguageManager;
import java.text.MessageFormat;

public class OccupiedState implements RoomState
{
    @Override
    public void book(Room room)
    {
        System.out.println(MessageFormat.format(
                LanguageManager.INSTANCE.getMessage("room.state_error.already_occupied"),
                room.getNumber()
        ));
    }

    @Override
    public void makeAvailable(Room room)
    {
        room.setState(new AvailableState());
    }

    @Override
    public void markForMaintenance(Room room)
    {
        System.out.println(MessageFormat.format(
                LanguageManager.INSTANCE.getMessage("room.maintenance_fail_occupied"),
                room.getNumber()
        ));
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