package il.cshaifasweng.OCSFMediatorExample.entities;

//import com.sun.istack.NotNull;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "ParkingSpot")
public class ParkingSpot  implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int spotId_;


    int height =-1;
    int width =-1;
    int depth= -1;



    public LocalDateTime getExitDate() {
        return ExitDate;
    }

    public void setExitDate(LocalDateTime exitDate) {
        ExitDate = exitDate;
    }

    LocalDateTime ExitDate;
    public String getCus_ID() {
        return Cus_ID;
    }

    public void setCus_ID(String cus_ID) {
        Cus_ID = cus_ID;
    }

    String Cus_ID;

    public String getLicesnes_Plate() {
        return Licesnes_Plate;
    }

    public void setLicesnes_Plate(String licesnes_Plate) {
        Licesnes_Plate = licesnes_Plate;
    }

    String Licesnes_Plate;

    String CurrentState;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "id")
//    private ParkingLot parkingLot;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "parking_id")
    private ParkingLot parkingLot;

    int parking_lot_id ;

    public ParkingSpot(int a, int b, int c, String state, int thispark,ParkingLot parkingLot_)
    {
        height = a;
        width = b;
        depth = c;
        CurrentState = state;
        parking_lot_id  = thispark;
        parkingLot = parkingLot_;

    }


    public ParkingSpot() {

    }

    public int getSpotId_() {
        return spotId_;
    }

    public int getheight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public int getwidth() {
        return width;
    }


    public int getdepth() {
        return depth;
    }

    public void setdepth(int z) {
        this.depth = z;
    }

    public String getCurrentState() {
        return CurrentState;
    }

    public void setCurrentState(String currentState) {
        CurrentState = currentState;
    }

    public ParkingLot getParkingLot() {
        return parkingLot ;
    }

    public int getParking_lot_id(){
        return parking_lot_id;
    }

    public void setParkingLot(int parkingLot) {
        this.parking_lot_id  = parkingLot;
    }

    public String getLocation()
    {
        String Location = this.depth + "-" + this.height + "-" + this.width;
        return Location;
    }
}
