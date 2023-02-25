package il.cshaifasweng.OCSFMediatorExample.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Vector;

@Entity
@Table(name="User")
public class ParkingManager extends User implements Serializable {

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
public String getPassword()
{
    return this.password;
}
public int getid()
{
    return this.Id;
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
