package model;

import java.util.ArrayList;
import java.util.List;

public class Hotel
{
    private final String name;
    private final String address;


    private final String description;
    private final int starRating;
    private final List<String> amenities;
    private final String contactEmail;
    private final String phoneNumber;
    private final boolean hasPool;
    private final boolean hasGym;
    private final boolean hasSpa;
    private final boolean hasRestaurant;
    private final boolean hasParking;
    private final String checkInTime;
    private final String checkOutTime;

    private Hotel(Builder builder)
    {
        this.name = builder.name;
        this.address = builder.address;
        this.description = builder.description;
        this.starRating = builder.starRating;
        this.amenities = builder.amenities;
        this.contactEmail = builder.contactEmail;
        this.phoneNumber = builder.phoneNumber;
        this.hasPool = builder.hasPool;
        this.hasGym = builder.hasGym;
        this.hasSpa = builder.hasSpa;
        this.hasRestaurant = builder.hasRestaurant;
        this.hasParking = builder.hasParking;
        this.checkInTime = builder.checkInTime;
        this.checkOutTime = builder.checkOutTime;

    }

    public static class Builder
    {
        private final String name;
        private final String address;

        private String description = "";
        private int starRating = 3;
        private List<String> amenities = new ArrayList<>();
        private String contactEmail = "";
        private String phoneNumber = "";
        private boolean hasPool = false;
        private boolean hasGym = false;
        private boolean hasSpa = false;
        private boolean hasRestaurant = false;
        private boolean hasParking = false;
        private String checkInTime = "14:00";
        private String checkOutTime = "12:00";

        public Builder(String name, String address)
        {
            this.name = name;
            this.address = address;
        }

        public Builder description(String description)
        {
            this.description = description;
            return this;
        }

        public Builder starRating(int starRating)
        {
            if (starRating < 1 || starRating > 5)
            {
                throw new IllegalArgumentException("Star rating must be between 1 and 5");
            }
            this.starRating = starRating;
            return this;
        }

        public Builder addAmenity(String amenity)
        {
            this.amenities.add(amenity);
            return this;
        }

        public Builder amenities(List<String> amenities)
        {
            this.amenities = new ArrayList<>(amenities);
            return this;
        }

        public Builder contactEmail(String contactEmail)
        {
            this.contactEmail = contactEmail;
            return this;
        }

        public Builder phoneNumber(String phoneNumber)
        {
            this.phoneNumber = phoneNumber;
            return this;
        }

        public Builder hasPool(boolean hasPool)
        {
            this.hasPool = hasPool;
            return this;
        }

        public Builder hasGym(boolean hasGym)
        {
            this.hasGym = hasGym;
            return this;
        }

        public Builder hasSpa(boolean hasSpa)
        {
            this.hasSpa = hasSpa;
            return this;
        }

        public Builder hasRestaurant(boolean hasRestaurant)
        {
            this.hasRestaurant = hasRestaurant;
            return this;
        }

        public Builder hasParking(boolean hasParking)
        {
            this.hasParking = hasParking;
            return this;
        }

        public Builder checkInTime(String checkInTime)
        {
            this.checkInTime = checkInTime;
            return this;
        }

        public Builder checkOutTime(String checkOutTime)
        {
            this.checkOutTime = checkOutTime;
            return this;
        }

        public Hotel build()
        {
            if(name == null)
            {
                throw new IllegalStateException();
            }
            if(address == null)
            {
                throw new IllegalStateException();
            }

            return new Hotel(this);
        }




    }

    public String getName() { return name; }
    public String getAddress() { return address; }
    public String getDescription() { return description; }
    public int getStarRating() { return starRating; }
    public List<String> getAmenities() { return new ArrayList<>(amenities); }
    public String getContactEmail() { return contactEmail; }
    public String getPhoneNumber() { return phoneNumber; }
    public boolean hasPool() { return hasPool; }
    public boolean hasGym() { return hasGym; }
    public boolean hasSpa() { return hasSpa; }
    public boolean hasRestaurant() { return hasRestaurant; }
    public boolean hasParking() { return hasParking; }
    public String getCheckInTime() { return checkInTime; }
    public String getCheckOutTime() { return checkOutTime; }

    @Override
    public String toString()
    {
        return String.format(
                "Hotel: %s\nEndereço: %s\nEstrelas: %d\nDescrição: %s",
                name, address, starRating, description
        );
    }
}
