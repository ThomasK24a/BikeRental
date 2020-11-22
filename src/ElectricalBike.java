/**
 * A type of bike used for BikeRentalShop
 * @author Thomas Koops
 * student number: 1396796
 */
public class ElectricalBike extends Bike{
    public ElectricalBike(int bikeID) {
        super(bikeID);
        super.ratePerKm = 0.50;
    }
}
