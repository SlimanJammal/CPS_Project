package il.cshaifasweng.OCSFMediatorExample.entities;

import javax.persistence.*;
import java.util.Vector;

@Entity
@Table
public class Manager extends Employee {

    @OneToOne
    private ParkingLot parkingLot;

    @OneToMany
    Vector<PricesUpdateRequest> pricesUpdateRequests;

    public Manager(String firstName, String lastName, String email, String password, ParkingLot parkingLot, Vector<PricesUpdateRequest> pricesUpdateRequests) {
        super(firstName, lastName, email, password);
        this.parkingLot = parkingLot;
        this.pricesUpdateRequests = pricesUpdateRequests;
    }

    public Manager() {

    }


    public Vector<PricesUpdateRequest> getPricesUpdateRequests() {
        return pricesUpdateRequests;
    }

    public void setPricesUpdateRequests(Vector<PricesUpdateRequest> pricesUpdateRequests) {
        this.pricesUpdateRequests = pricesUpdateRequests;
    }

    public ParkingLot getParkingLot() {
        return parkingLot;
    }

    public void setParkingLot(ParkingLot parkingLot) {
        this.parkingLot = parkingLot;
    }
}
