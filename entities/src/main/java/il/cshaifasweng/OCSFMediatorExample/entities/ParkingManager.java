package il.cshaifasweng.OCSFMediatorExample.entities;

import javax.persistence.*;
import java.util.Vector;

@Entity
@Table
public class ParkingManager extends User {

    @OneToOne
    private ParkingLot parkingLot;

    @OneToMany
    Vector<PricesUpdateRequest> pricesUpdateRequests;

    public ParkingManager(String userName, String password, String firstName, String lastName, int permission, ParkingLot parkingLot, Vector<PricesUpdateRequest> pricesUpdateRequests) {
        super(userName, password, firstName, lastName, permission);
        this.parkingLot = parkingLot;
        this.pricesUpdateRequests = pricesUpdateRequests;
    }

    public ParkingManager() {

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
