package il.cshaifasweng.OCSFMediatorExample.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Vector;

@Entity
@Table(name="Userlikes")
public class ParkingManager extends User implements Serializable {

    @OneToOne
    private ParkingLot parkingLot;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "id_")
    List<PricesUpdateRequest> pricesUpdateRequests;

    public ParkingManager(String userName, String password, String firstName, String lastName, int permission, ParkingLot parkingLot, List<PricesUpdateRequest> pricesUpdateRequests) {
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
    return this.Id_;
}


    public List<PricesUpdateRequest> getPricesUpdateRequests() {
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
