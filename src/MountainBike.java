/**
 * A type of bike used for BikeRentalShop
 * @author Thomas Koops
 * student number: 1396796
 */
public class MountainBike extends Bike{
    public MountainBike(int bikeID) {
        super(bikeID);
        super.ratePerKm = 0.25;
    }
}
