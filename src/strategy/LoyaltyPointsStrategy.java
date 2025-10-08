package strategy;

import model.Reservation;

public interface LoyaltyPointsStrategy
{
    int calculatePoints(Reservation reservation);
}
