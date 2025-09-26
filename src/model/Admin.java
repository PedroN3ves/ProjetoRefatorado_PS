package model;

import util.LanguageManager;

import java.text.MessageFormat;

public class Admin extends User
{
    public Admin(String name, String email)
    {
        super(name, email);
    }

        public void addHotel(Hotel hotel)
        {
            String message = MessageFormat.format(
                    LanguageManager.getMessage("admin.add"),
                    hotel.getName()
            );
            System.out.println(message);
        }
}
