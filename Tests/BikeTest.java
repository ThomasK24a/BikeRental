import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Thomas Koops
 * student number: 1396796
 */
class BikeTest {
    BikeRentalShop bikeRentalShop = new BikeRentalShop();

    void initBikes(){
        bikeRentalShop.addNewBike();
        bikeRentalShop.addNewMountainBike();
        bikeRentalShop.addNewElectricalBike();
        bikeRentalShop.addNewBike();
    }

    @Test
    void bikeMethodsWithNoBikes(){
        assertEquals(0, bikeRentalShop.totalBikesAvailable());
        assertEquals(null, bikeRentalShop.getBike(0));
        assertEquals("Error: Bike ID not found", bikeRentalShop.getBikeInfo(0));
    }

    @Test
    void getAvailableBikeOfTypeWithNoBikes(){
        assertEquals(null, bikeRentalShop.getAvailableBikeOfType("Regular"));
    }

    @Test
    void getAvailabilityOfBikesWithBikesPresent(){
        initBikes();
        assertEquals(Bike.class, bikeRentalShop.getAvailableBikeOfType("Regular").getClass());
        assertEquals(ElectricalBike.class, bikeRentalShop.getAvailableBikeOfType("Electrical").getClass());
        assertEquals(MountainBike.class, bikeRentalShop.getAvailableBikeOfType("Mountain").getClass());
        assertEquals(4, bikeRentalShop.totalBikesAvailable());
    }

    @Test
    void getBikeInfoTestWithNoRentals(){
        initBikes();
        assertEquals("Type: Regular, Total km traveled: 0.0, Is available: Yes", bikeRentalShop.getBikeInfo(0));
        assertEquals("Type: Mountain, Total km traveled: 0.0, Is available: Yes", bikeRentalShop.getBikeInfo(1));
        assertEquals("Type: Electrical, Total km traveled: 0.0, Is available: Yes", bikeRentalShop.getBikeInfo(2));
    }


}