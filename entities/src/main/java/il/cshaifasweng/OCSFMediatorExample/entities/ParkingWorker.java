package il.cshaifasweng.OCSFMediatorExample.entities;


import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table
public class ParkingWorker extends Employee{

    @ManyToOne
    private ParkingLot parkingLot;

    public ParkingWorker(String firstName, String lastName, String email, String password, ParkingLot parkingLot) {
        super(firstName, lastName, email, password);
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
