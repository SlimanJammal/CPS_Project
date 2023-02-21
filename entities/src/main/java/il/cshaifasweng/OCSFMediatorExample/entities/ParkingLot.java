package il.cshaifasweng.OCSFMediatorExample.entities;


import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table
public class ParkingLot implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int parking_id;
    static int count = 0;
    private int width;
    private int slots_num;
    private PricesClass occasionalPrice;
    private PricesClass preOrderPrice;
    private PricesClass partTimePrice;
    private PricesClass fullSubPrice;
    private PricesClass MultiCarPrice;


    public ParkingLot(int width_)
    {
        slots_num = width_ *9;
        parking_id = count;
        count++;
        width=width_;

        //per hour
        occasionalPrice = new PricesClass(8,"ocasionalPrice");
        preOrderPrice = new PricesClass(7,"preOrderPrice");

        //constant price
        partTimePrice = new PricesClass(60* preOrderPrice.getPrice(),"PartTimePrice");
        fullSubPrice = new PricesClass(72* preOrderPrice.getPrice(),"fullSubPrice");

        //this times number of cars registered
        MultiCarPrice = new PricesClass(54* preOrderPrice.getPrice(),"MultiCarPrice");
    }

    public ParkingLot() {
    }

    public PricesClass getOccasionalPrice() {
        return occasionalPrice;
    }

    public void setOccasionalPrice(PricesClass occasionalPrice) {
        this.occasionalPrice = occasionalPrice;
    }

    public PricesClass getPreOrderPrice() {
        return preOrderPrice;
    }

    public void setPreOrderPrice(PricesClass preOrderPrice) {
        this.preOrderPrice = preOrderPrice;
    }

    public PricesClass getPartTimePrice() {
        return partTimePrice;
    }

    public void setPartTimePrice(PricesClass partTimePrice) {
        this.partTimePrice = partTimePrice;
    }

    public PricesClass getFullSubPrice() {
        return fullSubPrice;
    }

    public void setFullSubPrice(PricesClass fullSubPrice) {
        this.fullSubPrice = fullSubPrice;
    }

    public PricesClass getMultiCarPrice() {
        return MultiCarPrice;
    }

    public void setMultiCarPrice(PricesClass multiCarPrice) {
        MultiCarPrice = multiCarPrice;
    }

    public int getSlots_num() {
        return slots_num;
    }

    public int getParking_id() {
        return parking_id;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }
}