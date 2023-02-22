package il.cshaifasweng.OCSFMediatorExample.entities;

import javax.persistence.*;
import java.util.Vector;


@Entity
@Table
public class PricesUpdateRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    ParkingManager parkingManager;

    @OneToMany
    Vector<PricesClass> pricesClassVector;

    String request;


    public PricesUpdateRequest(ParkingManager parkingManager, Vector<PricesClass> pricesClassVector, String request) {
        this.parkingManager = parkingManager;
        this.pricesClassVector = pricesClassVector;
        this.request = request;
    }

    public PricesUpdateRequest() {

    }

    public int getId() {
        return id;
    }

    public ParkingManager getParkingManager() {
        return parkingManager;
    }

    public void setParkingManager(ParkingManager parkingManager) {
        this.parkingManager = parkingManager;
    }

    public Vector<PricesClass> getPricesClassVector() {
        return pricesClassVector;
    }

    public void setPricesClassVector(Vector<PricesClass> pricesClassVector) {
        this.pricesClassVector = pricesClassVector;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }
}
