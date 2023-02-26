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
    private int pricesUpdateReqId;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "UserID")
    ParkingManager parkingManager;

    @ElementCollection
    List<Integer> pricesClassVector;

    String request;


    public PricesUpdateRequest(ParkingManager parkingManager, Vector<Integer> pricesClassVector, String request) {
        this.parkingManager = parkingManager;
        this.pricesClassVector = pricesClassVector;
        this.request = request;
    }

    public PricesUpdateRequest() {

    }

    public int getPricesUpdateReqId() {
        return pricesUpdateReqId;
    }

    public ParkingManager getParkingManager() {
        return parkingManager;
    }

    public void setParkingManager(ParkingManager parkingManager) {
        this.parkingManager = parkingManager;
    }

    public List<Integer> getPricesClassVector() {
        return pricesClassVector;
    }

    public void setPricesClassVector(Vector<Integer> pricesClassVector) {
        this.pricesClassVector = pricesClassVector;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }
}
