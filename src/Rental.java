import java.text.DecimalFormat;
import java.util.Date;

/**
 * Keeps track of the details of a bike rental for BikeRentalShop.
 * @author Thomas Koops
 * student number: 1396796
 */
public class Rental {
    private Bike bikeRented;
    private Date startTime;
    private Date endTime;
    private double kmTraveled;
    private int rentalID;

    public Rental(Bike bikeRented, Date startTime, int rentalID) {
        this.bikeRented = bikeRented;
        this.startTime = startTime;
        this.rentalID = rentalID;
        this.kmTraveled = 0;
    }

    public Bike getBikeRented() {
        return this.bikeRented;
    }

    public void setBikeRented(Bike bikeRented) {
        this.bikeRented = bikeRented;
    }

    public Date getStartTime() {
        return this.startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return this.endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public double getKmTraveled() {
        return this.kmTraveled;
    }

    public void setKmTraveled(double kmTraveled) {
        this.kmTraveled = kmTraveled;
    }

    public int getRentalID() {
        return this.rentalID;
    }

    public void setRentalID(int rentalID) {
        this.rentalID = rentalID;
    }

    /**
     * Used to end a rental and make a bike available again.
     * @param endTime the time at which the bike was returned
     * @param kmTraveled the amount of kilometers the bike travelled during the rental
     * @return a number between 0 and 3. 0 if all the parameters were correct and the rental ended, 1 if the rental already ended, 2 if the end time was before the start time and 3 if the kmTravelled was below 0. If the return is between 1 and 3 the endTime and kmTravelled are not updated
     */
    public int endRental(Date endTime, double kmTraveled){
        if(hasEnded()){
            return 1;
        }
        if(this.startTime.after(endTime)){
            return 2;
        }

        if(kmTraveled < 0){
            return 3;
        }

        this.endTime = endTime;
        this.kmTraveled = kmTraveled;
        return 0;
    }

    /**
     * Used to calculate the price the customer has to pay at the end of their rental
     * @return will be the calculated cost of the rental based on the time elapsed between start and end time, kilometers travelled during the rental and type of bike. The return is rounded down to 2 decimals. If the rental hasn't ended yet the return will be -1.
     */
    public double calculatePrice(){
        if(hasEnded()){
            long timeElapsed = this.endTime.getTime() - this.startTime.getTime();
            double elapsedHours = (double) timeElapsed/3600000;
            double price = this.bikeRented.getRatePerKm() * this.kmTraveled + elapsedHours * 2;
            //will round the price down to 2 decimals
            DecimalFormat moneyFormat = new DecimalFormat("0.00");
            moneyFormat.format(price);
            return price;
        }
        return -1;
    }

    /**
     * Used to see if the rental has ended already or not
     * @return either true if the end time is set or false if the end time has not yet been set
     */
    public boolean hasEnded(){
        return this.endTime != null;
    }
}
