package il.cshaifasweng.OCSFMediatorExample.entities;

//import com.sun.istack.NotNull;
import org.jetbrains.annotations.NotNull;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "ParkingSpot")
public class ParkingSpot {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int spotId ;

    @NotNull
    int height,width,depth;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ParkId")
    private ParkingLot parkingLot;


    public ParkingSpot(int a, int b, int c, String state, ParkingLot thispark)
    {
        height = a;
        width = b;
        depth = c;
        CurrentState = state;
        parkingLot = thispark;

    }


    public ParkingSpot() {

    }

    public int getSpotId() {
        return spotId;
    }

    public int getheight() {
        return height;
    }

    public void setheight(int x) {
        this.height = x;
    }

    public int getwidth() {
        return width;
    }

    public void setwidth(int y) {
        this.width = y;
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
        return parkingLot;
    }

    public void setParkingLot(ParkingLot parkingLot) {
        this.parkingLot = parkingLot;
    }
}
