package model.state;

import model.Room;
import util.LanguageManager;
import java.text.MessageFormat;

public class MaintenanceState implements RoomState
{
    @Override
    public void book(Room room)
    {
        System.out.println(MessageFormat.format(
                LanguageManager.INSTANCE.getMessage("room.state_error.in_maintenance"),
                room.getNumber()
        ));
    }

    @Override
    public void makeAvailable(Room room)
    {
        room.setState(new AvailableState());
        System.out.println(MessageFormat.format(
                LanguageManager.INSTANCE.getMessage("room.state_success.made_available"),
                room.getNumber()
        ));
    }

    @Override
    public void markForMaintenance(Room room)
    {
        System.out.println(MessageFormat.format(
                LanguageManager.INSTANCE.getMessage("room.maintenance_fail_already"),
                room.getNumber()
        ));
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