package il.cshaifasweng.OCSFMediatorExample.entities;

//import com.sun.istack.NotNull;
import org.jetbrains.annotations.NotNull;

import javax.persistence.*;

@Entity
@Table(name = "ParkingSpot")
public class ParkingSpot {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int spotId ;

    @NotNull
    int x,y,z;

    String CurrentState;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ParkId")
    private ParkingLot parkingLot;


    public ParkingSpot(int a, int b, int c, String state, ParkingLot thispark)
    {
        x = a;
        y = b;
        z = c;
        CurrentState = state;
        parkingLot = thispark;

    }


    public ParkingSpot() {

    }

    public int getSpotId() {
        return spotId;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getZ() {
        return z;
    }

    public void setZ(int z) {
        this.z = z;
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
