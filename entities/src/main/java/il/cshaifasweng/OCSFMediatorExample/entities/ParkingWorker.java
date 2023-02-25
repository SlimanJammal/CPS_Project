package il.cshaifasweng.OCSFMediatorExample.entities;


import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table
public class ParkingWorker extends User{

    @ManyToOne
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
