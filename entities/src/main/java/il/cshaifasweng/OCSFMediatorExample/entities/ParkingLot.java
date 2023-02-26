package il.cshaifasweng.OCSFMediatorExample.entities;


import com.sun.istack.NotNull;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "ParkingLot")
public class ParkingLot implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int parking_id;
    static int count = 0;
    private int width;
    private int slots_num;
    private String name;
    private int numberOfFreeSlots;
    private int numberOfFullSubs;
    private int numberOfPreOrders;



    @OneToOne
    private PricesClass occasionalPrice;

    @OneToOne
    private PricesClass preOrderPrice;

    @OneToOne
    private PricesClass partTimePrice;

    @OneToOne
    private PricesClass fullSubPrice;

    @OneToOne
    private PricesClass MultiCarPrice;

    @OneToOne
    private ParkingManager parkingManager;

    public List<PreOrder> getPreordersList() {
        return preordersList;
    }

    public void addPreOrder(PreOrder order){
        preordersList.add(order);
    }

    @OneToMany
    private List<PreOrder> preordersList;

    public List<OccCustomer> getOccasionalCustomers() {
        return OccasionalCustomers;
    }

    public void addOccasionalCustomers(OccCustomer customer){
        OccasionalCustomers.add(customer);
        for(int i = 0; i< dimensions*9; i++)
        {

                    if(Spots.get(i).CurrentState.equals("available")){
                        Spots.get(i).setCurrentState("taken");
                        numberOfFreeSlots--;
                    }

        }
    }

    @OneToMany
    private List<OccCustomer> OccasionalCustomers;

    @NotNull
    int dimensions;
    @NotNull

    boolean full;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "park")
    private List<ParkingSpot> Spots;


    public void incPreOrderNum(){
        numberOfPreOrders++;
    }

    public Boolean existsFreeSlots(){
        if(numberOfFreeSlots-numberOfPreOrders == width*9) {
            return false;
        }
        else return true;
    }

    public ParkingLot(String name_,int width_, int dims, boolean isFull)
    {
        slots_num = width_ *9;
        //kept both to avoid errors
        parking_id = id;
        width=width_;
        this.numberOfFreeSlots=slots_num;
        this.numberOfPreOrders=0;
        this.name = name_;
        this.dimensions =dims;
        this.full=isFull;
        for(int i = 0; i< dimensions; i++)
        {
            for(int j=0;j<3;j++)
            {
                for(int k=0;k<3;k++)
                {
                    Spots.add(new ParkingSpot(i,j,k,"available",this));
                }
            }
        }

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

    public int getId() {
        return id;
    }

    public int getNumberOfSubs() {
        return numberOfFullSubs;
    }

    public void setNumberOfSubs(int numberOfSubs) {
        this.numberOfFullSubs = numberOfSubs;
    }

    public int getNumberOfPreOrders() {
        return numberOfPreOrders;
    }

    public void setNumberOfPreOrders(int numberOfPreOrders) {
        this.numberOfPreOrders = numberOfPreOrders;
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
}