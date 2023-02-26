package il.cshaifasweng.OCSFMediatorExample.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Vector;

@Entity
@Table(name="Userlik")
public class RegionalManager extends User implements Serializable {



    @OneToMany
    List<ParkingLot> parkingLots;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "id_")
    List<PricesUpdateRequest> pricesUpdateRequests;

    public RegionalManager(String userName, String password, String firstName, String lastName, int permission, int id, List<ParkingLot> parkingLots, Vector<PricesUpdateRequest> pricesUpdateRequests) {
        super(userName, password, firstName, lastName, permission);

        this.parkingLots = parkingLots;
        this.pricesUpdateRequests = pricesUpdateRequests;
    }

    public RegionalManager() {

    }
}
