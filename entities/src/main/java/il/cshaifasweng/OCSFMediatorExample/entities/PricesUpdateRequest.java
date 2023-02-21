package il.cshaifasweng.OCSFMediatorExample.entities;

import javax.persistence.*;


@Entity
@Table
public class PricesUpdateRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    ParkingManager parkingManager;

    String request;

}
