package il.cshaifasweng.OCSFMediatorExample.entities;

import javax.persistence.*;
import java.util.List;
import java.util.Vector;


@Entity
@Table(name = "PricesUpdateRequest")
public class PricesUpdateRequest {
    private static final long serialVersionUID = -8224097662914849956L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_;

    @ManyToOne
    ParkingManager parkingManager;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "price_id")
    List<PricesClass> pricesClassVector;

    String request;


    public PricesUpdateRequest(ParkingManager parkingManager, List<PricesClass> pricesClassVector, String request) {
        this.parkingManager = parkingManager;
        this.pricesClassVector = pricesClassVector;
        this.request = request;
    }

    public PricesUpdateRequest() {

    }

    public int getId_() {
        return id_;
    }

    public ParkingManager getParkingManager() {
        return parkingManager;
    }

    public void setParkingManager(ParkingManager parkingManager) {
        this.parkingManager = parkingManager;
    }

    public List<PricesClass> getPricesClassVector() {
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
