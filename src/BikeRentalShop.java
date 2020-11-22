import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;

/**
 * Includes functionality for tracking the rental of bicycles.
 * @author Thomas Koops
 * student number: 1396796
 */
public class BikeRentalShop {
    private HashSet<Bike> bikes;
    private int bikeIDIncremental;
    private HashSet<Rental> rentals;
    private int rentalIDIncremental;
    private double deposit;

    public BikeRentalShop() {
        this.bikes = new HashSet<>();
        this.bikeIDIncremental = 0;
        this.rentals = new HashSet<>();
        this.rentalIDIncremental = 0;
        this.deposit = 20.0;
    }

    public int getBikeIDIncremental() {
        return this.bikeIDIncremental;
    }

    public void setBikeIDIncremental(int bikeIDIncremental) {
        this.bikeIDIncremental = bikeIDIncremental;
    }

    public int getRentalIDIncremental() {
        return this.rentalIDIncremental;
    }

    public void setRentalIDIncremental(int rentalIDIncremental) {
        this.rentalIDIncremental = rentalIDIncremental;
    }

    /**
     * Adds a regular bike to the bikes hashset
     * @return the bike ID of the new bike
     */
    public int addNewBike(){
        Bike bike = new Bike(bikeIDIncremental);
        bikes.add(bike);
        return bikeIDIncremental++;
    }

    /**
     * Adds a electrical bike to the bikes hashset
     * @return the bike ID of the new electrical bike
     */
    public int addNewElectricalBike(){
        ElectricalBike bike = new ElectricalBike(bikeIDIncremental);
        bikes.add(bike);
        return bikeIDIncremental++;
    }

    /**
     * Adds a mountain bike to the bikes hashset
     * @return the bike ID of the new mountain bike
     */
    public int addNewMountainBike(){
        MountainBike bike = new MountainBike(bikeIDIncremental);
        bikes.add(bike);
        return bikeIDIncremental++;
    }

    /**
     * Adds a rental to the rentals hashset
     * @param bikeID the bike id of the bike rented
     * @param startTime the date of the start of the rental
     * @return the rental ID of the new rental if the parameters are valid or -1 if the rental failed to be created
     */
    public int createRental(int bikeID, Date startTime){
        Bike bikeRented = getBike(bikeID);
        if(bikeRented != null && isBikeAvailable(bikeID)){
            Rental rental = new Rental(bikeRented, startTime, rentalIDIncremental);
            rentals.add(rental);
            return rentalIDIncremental++;
        }
        return -1;
    }

    /**
     * Get a bike stored in the bikes hashset
     * @param bikeID the id of the bike
     * @return the bike belonging to the given ID or null if no bike with that ID was found
     */
    public Bike getBike(int bikeID){
        Iterator<Bike> bikeIterator = bikes.iterator();
        while(bikeIterator.hasNext()){
            Bike nextBike = bikeIterator.next();
            if(nextBike.getBikeID() == bikeID){
                return nextBike;
            }
        }
        return null;
    }

    /**
     * Get a rental stored in the rentals hashset
     * @param rentalID the id of the rental
     * @return the rental belonging to the given ID or null if no rental with that ID was found
     */
    public Rental getRental(int rentalID){
        for (Rental nextRental : rentals) {
            if (nextRental.getRentalID() == rentalID) {
                return nextRental;
            }
        }
        return null;
    }

    /**
     * Retrieves every single rental of a certain bike
     * @param bikeID the id of the bike that was used in these rentals
     * @return an arraylist that contains every rental of the bike or an empty arraylist if either the bike was not found or no rentals with that bike were found
     */
    private ArrayList<Rental> getRentalsOfBike(int bikeID){
        Iterator<Rental> rentalIterator = this.rentals.iterator();
        ArrayList<Rental> rentalsOfBike = new ArrayList<>();
        while(rentalIterator.hasNext()){
            Rental nextRental = rentalIterator.next();
            if(nextRental.getBikeRented().getBikeID() == bikeID){
                rentalsOfBike.add(nextRental);
            }
        }
        return rentalsOfBike;
    }

    /**
     * Gets the total amount of kilometers travelled on a bike by looking through each rental with that bike
     * @param bikeID the id of the bike
     * @return the total amount of kilometers travelled or 0 if either the bike was not found or if the bike has no rentals
     */
    public double totalKmTraveledOfBike(int bikeID){
        ArrayList<Rental> rentalsOfBike = this.getRentalsOfBike(bikeID);
        double totalKmTraveled = 0;
        for(Rental rental : rentalsOfBike){
            totalKmTraveled += rental.getKmTraveled();
        }
        return totalKmTraveled;
    }

    /**
     * Checks if a bike is currently available
     * @param bikeID the id of the bike
     * @return true if the bike has no active rentals, false if the bike does have an active rental
     */
    public boolean isBikeAvailable(int bikeID){
        ArrayList<Rental> rentalsOfBike = this.getRentalsOfBike(bikeID);
        for(Rental rental : rentalsOfBike){
            if(!rental.hasEnded()){
                return false;
            }
        }
        return true;
    }

    /**
     * Gets the info of a bike. Contains the type, the total kilometers travelled and it's availability.
     * @param bikeID the id of the bike
     * @return a string with the type, total km travelled and availability or "Error: Bike ID not found" if the bike was not found
     */
    public String getBikeInfo(int bikeID){
        Bike bike = this.getBike(bikeID);
        if(bike != null){
            return "Type: " + bike.getType() + ", Total km traveled: " + this.totalKmTraveledOfBike(bikeID) + ", Is available: " + (this.isBikeAvailable(bikeID) ? "Yes" : "No");
        }
        return "Error: Bike ID not found";
    }

    /**
     * Gets the total amount of bikes currently available in the bikes hashset
     * @return the total amount of bikes available
     */
    public int totalBikesAvailable(){
        Iterator<Bike> bikeIterator = bikes.iterator();
        int bikesAvailable = 0;
        while(bikeIterator.hasNext()){
            if(this.isBikeAvailable(bikeIterator.next().getBikeID())){
                bikesAvailable++;
            }
        }
        return bikesAvailable;
    }

    /**
     * Gets an arraylist with each bike of a type based on the input
     * @param type either "Regular", "Electrical" or "Mountain"
     * @return an arraylist with each bike of a type
     */
    private ArrayList<Bike> getAllBikesOfType(String type){
        Iterator<Bike> bikeIterator = bikes.iterator();
        ArrayList<Bike> bikesOfType = new ArrayList<>();
        while(bikeIterator.hasNext()) {
            Bike nextBike = bikeIterator.next();
            if (nextBike.getType().contentEquals(type)) {
                bikesOfType.add(nextBike);
            }
        }
        return bikesOfType;

    }

    /**
     * Gets a bike of a type that is currently not rented out
     * @param type either "Regular", "Electrical" or "Mountain"
     * @return an available bike of the right type or null if no available bike was found of that type.
     */
    public Bike getAvailableBikeOfType(String type){
        ArrayList<Bike> bikesOfType = this.getAllBikesOfType(type);
        for(Bike bike : bikesOfType){
            if(this.isBikeAvailable(bike.getBikeID())){
                return bike;
            }
        }
        return null;
    }

    /**
     * Gets the string of the receipt for when a customer starts a rental.
     * @param rentalID the id of the rental
     * @return A string with info about the type of bike rented and the deposit amount or an error if the rental ID does not exist
     */
    public String printDepositReceipt(int rentalID){
        Rental rental = this.getRental(rentalID);
        if(rental != null){
            Bike bike = rental.getBikeRented();
            //will round the price down to 2 decimals
            DecimalFormat moneyFormat = new DecimalFormat("0.00");
            return bike.getType() + " bike rented. The customer has to pay a €" + moneyFormat.format(this.deposit) + " deposit.";
        }
        return "Error: rental ID does not exist";
    }

    /**
     * Gets the string of the receipt for when a customer end a rental
     * @param rentalID the id of the rental
     * @return A string with info about the cost of the rental and the rental ID or an error if the rental ID does not exist or the rental hasn't ended yet
     */
    public String printReturnReceipt(int rentalID){
        Rental rental = this.getRental(rentalID);
        if(rental == null){
            return "Error: rental ID does not exist";
        }

        if(!rental.hasEnded()){
            return "Error: bike has not yet been returned";
        }
        //will round the price down to 2 decimals
        DecimalFormat moneyFormat = new DecimalFormat("0.00");
        return "The customer has to pay €" + moneyFormat.format(rental.calculatePrice()) + " for rental " + rentalID;

    }

    /**
     * Ends the rental and gives info about the ending of the rental
     * @param endTime the time at which the bike was returned
     * @param kmTravelled the amount of kilometers the bike travelled during the rental
     * @param rentalID the id of the rental
     * @return a string with info if the rental successfully ended or not. If it did end a receipt will also be added to the info.
     */
    public String returnBike(Date endTime, double kmTravelled, int rentalID){
        Rental rental = this.getRental(rentalID);
        if(rental == null){
            return "Error: rental ID does not exist";
        }
        switch(rental.endRental(endTime, kmTravelled)){
            case 0:
                return "Bike has been successfully returned. " + printReturnReceipt(rentalID);
            case 1:
                return "Bike already returned, return of bike failed";
            case 2:
                return "End time can't be before start time, return of bike failed";
            case 3:
                return "Kilometers travelled can't be below 0, return of bike failed";
            default:
                return "Error: unidentified end rental code";

        }
    }
}
