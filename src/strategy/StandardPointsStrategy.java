package strategy;

import model.Reservation;

public class StandardPointsStrategy implements LoyaltyPointsStrategy
{
    @Override
    public int calculatePoints(Reservation reservation)
    {
        return (int) (reservation.getTotalCost() * 0.10);
    }
}
