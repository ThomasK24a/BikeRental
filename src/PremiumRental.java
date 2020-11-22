import java.util.Date;

public class PremiumRental extends Rental {
    public PremiumRental(Bike bikeRented, Date startTime, int rentalID){
        super(bikeRented, startTime, rentalID);

    }

    @Override
    public  double  calculatePrice(){
        double normalPrice = super.calculatePrice();
        if(normalPrice == -1){
            return -1;
        }else{
            return normalPrice * 0.75;
        }
    }
}
