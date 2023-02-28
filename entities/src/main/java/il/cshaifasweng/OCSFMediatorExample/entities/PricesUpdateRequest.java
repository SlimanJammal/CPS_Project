package il.cshaifasweng.OCSFMediatorExample.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Vector;


@Entity
@Table(name = "PricesUpdateRequests")
public class PricesUpdateRequest implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int pricesUpdateReqId;


    private Integer parkingManagerID;


    private Integer occasionalPrice;


    private Integer preOrderPrice;


    private Integer partTimePrice;


    private Integer fullSubPrice;


    private Integer MultiCarPrice;

    String request;


    public PricesUpdateRequest(Integer parkingManager, Vector<Integer> pricesClassVector, String request) {
        this.parkingManagerID = parkingManager;
        occasionalPrice = pricesClassVector.get(0);
        preOrderPrice = pricesClassVector.get(1);
        partTimePrice = pricesClassVector.get(2);
        fullSubPrice = pricesClassVector.get(3);
        MultiCarPrice = pricesClassVector.get(4);

        this.request = request + occasionalPrice +" "+ preOrderPrice +" "+partTimePrice+" "+fullSubPrice+" "+MultiCarPrice ;
    }

    public PricesUpdateRequest() {

    }

    public int getPricesUpdateReqId() {
        return pricesUpdateReqId;
    }

    public Integer getParkingManagerID() {
        return parkingManagerID;
    }

    public void setParkingManager(Integer parkingManager) {
        this.parkingManagerID = parkingManager;
    }

//    public List<Integer> getPricesClassVector() {
//        return pricesClassVector;
//    }
//
//    public void setPricesClassVector(Vector<Integer> pricesClassVector) {
//        this.pricesClassVector = pricesClassVector;
//    }

    public String getRequest() {
        return request;
    }

    public void setPricesUpdateReqId(int pricesUpdateReqId) {
        this.pricesUpdateReqId = pricesUpdateReqId;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public Integer getOccasionalPrice() {
        return occasionalPrice;
    }

    public void setOccasionalPrice(Integer occasionalPrice) {
        this.occasionalPrice = occasionalPrice;
    }

    public Integer getPreOrderPrice() {
        return preOrderPrice;
    }

    public void setPreOrderPrice(Integer preOrderPrice) {
        this.preOrderPrice = preOrderPrice;
    }

    public Integer getPartTimePrice() {
        return partTimePrice;
    }

    public void setPartTimePrice(Integer partTimePrice) {
        this.partTimePrice = partTimePrice;
    }

    public Integer getFullSubPrice() {
        return fullSubPrice;
    }

    public void setFullSubPrice(Integer fullSubPrice) {
        this.fullSubPrice = fullSubPrice;
    }

    public Integer getMultiCarPrice() {
        return MultiCarPrice;
    }

    public void setMultiCarPrice(Integer multiCarPrice) {
        MultiCarPrice = multiCarPrice;
    }
}
