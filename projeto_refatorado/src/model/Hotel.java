package model;

public class Hotel
{
    private String name;
    private String address;
    private String description;

    public Hotel(String name, String address, String description)
    {
        this.name = name;
        this.address = address;
        this.description = description;
    }

    public String getName()
    {
        return name;
    }

    public String getAddress()
    {
        return address;
    }

    public String getDescription()
    {
        return description;
    }

    @Override
    public String
    toString()
    {
        return "\nHotel: " + name + "\nAddress: " + address + "\nDescription: " + description;
    }
}
