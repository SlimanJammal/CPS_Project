package il.cshaifasweng.OCSFMediatorExample.entities;


import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
    private String Status;
    public int getOccupied_slots_num() {
        return occupied_slots_num;
    }

    public void setOccupied_slots_num(int occupied_slots_num) {
        this.occupied_slots_num = occupied_slots_num;
    }

    private int occupied_slots_num;

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    private String name;
    private int numberOfFreeSlots;
    private int numberOfFullSubs;
    private int numberOfPreOrders;



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

    @OneToMany(fetch=FetchType.LAZY, mappedBy="parkingLot")
    private List<ParkingWorker> parkingWorkers;


////    @NotNull
//    public List<PreOrder> getPreordersList() {
//        return preordersList;
//    }
//
//    public void addPreOrder(PreOrder order){
//        preordersList.add(order);
//    }

//    @OneToMany
//    private List<PreOrder> preordersList;

//    public List<OccCustomer> getOccasionalCustomers() {
//        return OccasionalCustomers;
//    }

    public void addOccasionalCustomers(){
//        OccasionalCustomers.add(customer);
       numberOfFreeSlots--;
    }

//    @OneToMany
//    private List<OccCustomer> OccasionalCustomers;

//    @NotNull
    int dimensions;
//    @NotNull

    boolean full;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "parkingLot")
    private List<ParkingSpot> Spots;


    public void setSpotsList(List<ParkingSpot> input){
        Spots = input;
    }
    public void incPreOrderNum(){
        numberOfPreOrders++;
    }
    public void decPreOrderNum(){
        numberOfPreOrders--;
    }
    public void incNumberOfFreeSlots(){numberOfFreeSlots++;}
    public void decNumberOfFreeSlots(){numberOfFreeSlots--;}

    public Boolean existsFreeSlots(int preOrdersNum_){
        if(numberOfFreeSlots-preOrdersNum_ ==0) {
            return false;
        }
        else return true;
    }
    public boolean removeCar(String carNum){
        for(ParkingSpot parkingSpot : Spots){
            if(parkingSpot.Licesnes_Plate.equals(carNum)){
                parkingSpot.reset();
                System.out.println("inside remove car");
                printParkingSpots();
                return true;
            }
        }
        printParkingSpots();
        return false;
    }

    // return value is -1 if car doesnt exist
    public int findAndCalcPrice(String carNum){
        for (ParkingSpot parkingSpot : Spots ){
            if  (parkingSpot.Licesnes_Plate.equals(carNum)){
//                parkingSpot.reset();
                if (parkingSpot.isOccasional()){
                    System.out.println("before hours");
                    int hours = LocalDateTime.now().getHour() - parkingSpot.EntryDate.getHour() + 1;
                    System.out.println("after hours");
                    if(hours < 0 ){
                        // todo fix this 24 thingy
                        return (hours+24) *occasionalPrice.getPrice();
                    }else{
                        return hours * occasionalPrice.getPrice();
                    }
                }
                return 0;
            }
        }
        return -1;
    }

    public ParkingLot(String name_,int width_, int dims, boolean isFull)
    {
        slots_num = width_ *9;
        //kept both to avoid errors
        parkingWorkers = new ArrayList<>();
        width=width_;
        this.numberOfFreeSlots=slots_num;
        this.numberOfPreOrders=0;
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

    public ParkingWorker getParkingWorker0() {
        return parkingWorkers.get(0);
    }

    public void addParkingWorker(ParkingWorker parkingWorker) {
        this.parkingWorkers.add(parkingWorker);
    }

    public void addSpot(ParkingSpot s) {
        Spots.add(s);
    }

    //depth height width
    //depth = 3 , height = 3;
    public int CalculateLocation(int x, int y, int z)
    {
        int delta_x = x * 3 * this.getWidth();
        int delta_y = y * this.getWidth();
        int delta_z = z;
        int delta = delta_x + delta_y + delta_z;
        return delta;
    }

    public void printParkingSpots(){
        for (int i= 0 ; i<Spots.size()-3; i+=3){
            System.out.println(Spots.get(i).CurrentState+" "+Spots.get(i+1).CurrentState+" "+Spots.get(i+2).CurrentState);
        }
    }


}