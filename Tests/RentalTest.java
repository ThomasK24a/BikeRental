import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;

/**
 * @author Thomas Koops
 * student number: 1396796
 */
public class RentalTest {
    BikeRentalShop bikeRentalShop = new BikeRentalShop();

    void initBikes(){
        bikeRentalShop.addNewBike();
        bikeRentalShop.addNewMountainBike();
        bikeRentalShop.addNewElectricalBike();
        bikeRentalShop.addNewBike();
    }

    void initRentals(){
        //start date is  6-10-2020 10:00:00 GMT
        bikeRentalShop.createRental(0, new Date(1601978400000L));
        //start date is  7-10-2020 11:00:00 GMT
        bikeRentalShop.createRental(1, new Date(1602068400000L));
        //start date is  8-10-2020 12:00:00 GMT
        bikeRentalShop.createRental(2, new Date(1602158400000L));
        //start date is  9-10-2020 13:00:00 GMT
        bikeRentalShop.createRental(3, new Date(1602248400000L));
    }

    @Test
    void returnWithNoRental(){
        //end date is 6-10-2020 09:00:00 GMT
        assertEquals("Error: rental ID does not exist", bikeRentalShop.returnBike(new Date(1601974800000L), 10, 0));
    }

    @Test
    void receiptsWithNoRental(){
        assertEquals("Error: rental ID does not exist", bikeRentalShop.printDepositReceipt(0));
        assertEquals("Error: rental ID does not exist", bikeRentalShop.printReturnReceipt(0));
    }

    @Test
    void receiptsWithRentalsAvailableButNotFinished(){
        initBikes();
        initRentals();
        assertEquals("Regular bike rented. The customer has to pay a €20,00 deposit.", bikeRentalShop.printDepositReceipt(0));
        assertEquals("Error: bike has not yet been returned", bikeRentalShop.printReturnReceipt(0));
    }

    @Test
    void returningBikes(){
        initBikes();
        initRentals();
        //end date is 6-10-2020 09:00:00
        assertEquals("End time can't be before start time, return of bike failed", bikeRentalShop.returnBike(new Date(1601974800000L), 1, 0));
        //end date is 6-10-2020 12:00:00
        assertEquals("Kilometers travelled can't be below 0, return of bike failed", bikeRentalShop.returnBike(new Date(1601985600000L), -1.5, 0));
        //end date is 6-10-2020 12:00:00
        assertEquals("Bike has been successfully returned. The customer has to pay €4,20 for rental 0", bikeRentalShop.returnBike(new Date(1601985600000L), 1, 0));
        //end date is 6-10-2020 12:00:00
        assertEquals("Bike already returned, return of bike failed", bikeRentalShop.returnBike(new Date(1601985600000L), 1, 0));
        //end date is 7-10-2020 12:00:00
        assertEquals("Bike has been successfully returned. The customer has to pay €2,75 for rental 1", bikeRentalShop.returnBike(new Date(1602072000000L), 3, 1));
        //end date is 8-10-2020 12:30:00
        assertEquals("Bike has been successfully returned. The customer has to pay €3,50 for rental 2", bikeRentalShop.returnBike(new Date(1602160200000L), 5, 2));
        //end date is 9-10-2020 13:00:00
        assertEquals("Bike has been successfully returned. The customer has to pay €0,00 for rental 3", bikeRentalShop.returnBike(new Date(1602248400000L), 0, 3));
        assertEquals("The customer has to pay €4,20 for rental 0", bikeRentalShop.printReturnReceipt(0));
    }

    @Test
    void availabilityDuringAndAfterRentals(){
        initBikes();
        initRentals();
        assertEquals(0, bikeRentalShop.totalBikesAvailable());
        assertEquals(null, bikeRentalShop.getAvailableBikeOfType("Regular"));
        assertEquals("Type: Regular, Total km traveled: 0.0, Is available: No", bikeRentalShop.getBikeInfo(0));
        //end date is 6-10-2020 12:00:00 GMT
        bikeRentalShop.returnBike(new Date(1601985600000L), 1, 0);
        assertEquals(1, bikeRentalShop.totalBikesAvailable());
        assertEquals("Type: Regular, Total km traveled: 1.0, Is available: Yes", bikeRentalShop.getBikeInfo(0));
    }

    @Test
    void multipleRentalsOfSameBikeAtDifferentTimes(){
        initBikes();
        initRentals();
        //end date is 6-10-2020 12:00:00 GMT
        bikeRentalShop.returnBike(new Date(1601985600000L), 1.5, 0);
        assertEquals(1.5, bikeRentalShop.totalKmTraveledOfBike(0));
        //start date is 7-10-2020 10:00:00 GMT
        bikeRentalShop.createRental(0, new Date(1601985600000L));
        //end date is 7-10-2020 12:00:00 GMT
        bikeRentalShop.returnBike(new Date(1602064800000L), 3.5, 4);
        assertEquals(5.0, bikeRentalShop.totalKmTraveledOfBike(0));
    }

    @Test
    void multipleRentalsOfSameBikeAtTheSameTime(){
        initBikes();
        initRentals();
        //Start date is 7-10-2020 10:00:00 GMT
        assertEquals(-1, bikeRentalShop.createRental(0, new Date(1601985600000L)));
    }

    @Test
    void calculatePriceWithNoEndTime(){
        initBikes();
        initRentals();
        assertEquals(-1,bikeRentalShop.getRental(0).calculatePrice());
    }


}
