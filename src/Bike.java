/**
 * Bikes used for BikeRentalShop
 * @author Thomas Koops
 * student number: 1396796
 */
public class Bike {
    private int bikeID;
    protected double ratePerKm;



    public Bike(int bikeID) {
        this.bikeID = bikeID;
        this.ratePerKm = 0.20;
    }

    public int getBikeID() {
        return this.bikeID;
    }

    public void setBikeID(int bikeID) {
        this.bikeID = bikeID;
    }

    public double getRatePerKm() {
        return this.ratePerKm;
    }

    /**
     * Used to get the type of bike this object belongs to
     * @return either "Regular" if this object is the Bike class, "Electrical" if ElectricalBike class, "Mountain" if MountainBike class or "Unknown" if none of these classes
     */
    public String getType(){
        if(this.getClass() == Bike.class){
            return "Regular";
        }else if(this.getClass() == ElectricalBike.class){
            return "Electrical";
        }else if(this.getClass() == MountainBike.class){
            return "Mountain";
        }else{
            return "Unknown";
        }
    }
}
