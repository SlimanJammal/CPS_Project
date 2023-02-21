package il.cshaifasweng.OCSFMediatorExample.entities;

import javax.persistence.*;
import java.util.Vector;

@Entity
@Table
public class regionalManager extends Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToMany
    Vector<ParkingLot> parkingLots;

    Vector<PricesUpdateRequest> pricesUpdateRequests;

}
