package il.cshaifasweng.OCSFMediatorExample.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Vector;

@Entity
@Table(name="User")
public class RegionalManager extends User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToMany
    Vector<ParkingLot> parkingLots;

    @OneToMany
    Vector<PricesUpdateRequest> pricesUpdateRequests;

    public RegionalManager(String userName, String password, String firstName, String lastName, int permission, int id, Vector<ParkingLot> parkingLots, Vector<PricesUpdateRequest> pricesUpdateRequests) {
        super(userName, password, firstName, lastName, permission);
        this.id = id;
        this.parkingLots = parkingLots;
        this.pricesUpdateRequests = pricesUpdateRequests;
    }

    public RegionalManager() {

    }
}
