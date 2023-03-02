package il.cshaifasweng.OCSFMediatorExample.entities;


import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="Userlike")
public class ParkingWorker extends User implements Serializable {

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "parking_id")
    private ParkingLot parkingLot;

    public ParkingWorker(String userName, String password, String firstName, String lastName, int permission, ParkingLot parkingLot) {
        super(userName, password, firstName, lastName, permission);
        this.parkingLot = parkingLot;
    }

    public ParkingWorker() {

    }
    public String getPassword()
    {
        return this.password;
    }

    public ParkingLot getParkingLot() {
        return parkingLot;
    }

    public void setParkingLot(ParkingLot parkingLot) {
        this.parkingLot = parkingLot;
    }
}
