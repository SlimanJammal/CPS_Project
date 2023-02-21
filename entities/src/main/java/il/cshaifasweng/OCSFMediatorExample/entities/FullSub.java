package il.cshaifasweng.OCSFMediatorExample.entities;

import org.jetbrains.annotations.NotNull;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table
public class FullSub extends Subscription{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    String FullSubId;
    @NotNull
    String CarNumber;
    @NotNull
    String CustomerId;

    @NotNull
    Date StartDate;  //format year:month:day   // we need year and month because we might have a sub close to the end of december
    @NotNull
    Date StartParking; //format includes days we will use it as a counter for if it reaches 14
    @NotNull
    String SubNum;
    public FullSub(){}

    public FullSub(String id,String carnum)
    {
        this.CustomerId=id;
        this.CarNumber=carnum;
    }

    public Date getStartDate() {
        return StartDate;
    }

    public String getCarNumber() {
        return CarNumber;
    }

    public String getFullSubId() {
        return FullSubId;
    }

    public String getSubNum() {
        return SubNum;
    }

    public Date getStartParking() {
        return StartParking;
    }

    public String getCustomerId() {
        return CustomerId;
    }

    public void setCustomerId(String customerId) {
        CustomerId = customerId;
    }

    public void setCarNumber(String carNumber) {
        CarNumber = carNumber;
    }

    public void setStartDate(Date startDate) {
        StartDate = startDate;
    }

    public void setFullSubId(String fullSubId) {
        FullSubId = fullSubId;
    }

    public void setStartParking(Date startParking) {
        StartParking = startParking;
    }

    public void setSubNum(String subNum) {
        SubNum = subNum;
    }

}
