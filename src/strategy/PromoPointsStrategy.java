package strategy;

import model.Reservation;

public class PromoPointsStrategy implements LoyaltyPointsStrategy
{
    @Override
    public int calculatePoints(Reservation reservation)
    {
        return (int) (reservation.getTotalCost() * 0.05);
    }
}
