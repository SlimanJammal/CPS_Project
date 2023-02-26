package il.cshaifasweng.OCSFMediatorExample.entities;


import com.sun.istack.NotNull;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ParkingLots")
public class ParkingLot implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int parking_id;
    static int count = 0;
    private int width;
    private int slots_num;

    public int getOccupied_slots_num() {
        return occupied_slots_num;
    }

    public void setOccupied_slots_num(int occupied_slots_num) {
        this.occupied_slots_num = occupied_slots_num;
    }

    private int occupied_slots_num;



    private String name;


    @OneToOne(cascade = CascadeType.ALL)
    private PricesClass occasionalPrice;

    @OneToOne(cascade = CascadeType.ALL)
    private PricesClass preOrderPrice;

    @OneToOne(cascade = CascadeType.ALL)
    private PricesClass partTimePrice;

    @OneToOne(cascade = CascadeType.ALL)
    private PricesClass fullSubPrice;

    @OneToOne(cascade = CascadeType.ALL)
    private PricesClass MultiCarPrice;

    @OneToOne(cascade = CascadeType.ALL)
    private ParkingManager parkingManager;

    @OneToOne(cascade = CascadeType.ALL)
    private ParkingWorker parkingWorker;


//    @NotNull
    int dimensions;
//    @NotNull

    boolean full;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "parkingLot")
    private List<ParkingSpot> Spots;





    public ParkingLot(String name_,int width_, int dims, boolean isFull)
    {
        slots_num = width_ *9;
        //kept both to avoid errors
        width=width_;
        this.name = name_;
        this.dimensions =dims;
        this.full=isFull;

        Spots = new ArrayList<ParkingSpot>() ;
//        for(int i = 0; i< dimensions; i++)
//        {
//            for(int j=0;j<3;j++)
//            {
//                for(int k=0;k<3;k++)
//                {
//
//                    ParkingSpot S = new ParkingSpot(i,j,k,"empty",this.parking_id);
//                    Spots.add(S);
//                }
//            }
//        }

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isFull() {
        return full;
    }


    public int getDimensions() {
        return dimensions;
    }

    public List<ParkingSpot> getSpots() {
        return Spots;
    }

    public void setDimensions(int dimintions) {
        this.dimensions = dimintions;
    }


    public void setFull(boolean full) {
        this.full = full;
    }

    public void setSpots(List<ParkingSpot> spots) {
        Spots = spots;
    }




    public void setParking_id(int parking_id) {
        this.parking_id = parking_id;
    }

    public static int getCount() {
        return count;
    }

    public static void setCount(int count) {
        ParkingLot.count = count;
    }

    public void setSlots_num(int slots_num) {
        this.slots_num = slots_num;
    }


    public void setParkingManager(ParkingManager parkingManager) {
        this.parkingManager = parkingManager;
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

    public ParkingManager getParkingManager() {
        return parkingManager;
    }

    public ParkingWorker getParkingWorker() {
        return parkingWorker;
    }

    public void setParkingWorker(ParkingWorker parkingWorker) {
        this.parkingWorker = parkingWorker;
    }

    public void addSpot(ParkingSpot s) {
        Spots.add(s);
    }
}