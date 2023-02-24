package il.cshaifasweng.OCSFMediatorExample.entities;


import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name="User")
public class ParkingWorker extends User implements Serializable {

    @ManyToOne
    private ParkingLot parkingLot;

    public ParkingWorker(String userName, String password, String firstName, String lastName, int permission, ParkingLot parkingLot) {
        super(userName, password, firstName, lastName, permission);
        this.parkingLot = parkingLot;
    }

    public ParkingWorker() {

    }

    public ParkingLot getParkingLot() {
        return parkingLot;
    }

    public void setParkingLot(ParkingLot parkingLot) {
        this.parkingLot = parkingLot;
    }
}
