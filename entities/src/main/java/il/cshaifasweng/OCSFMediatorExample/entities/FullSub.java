package il.cshaifasweng.OCSFMediatorExample.entities;

import org.jetbrains.annotations.NotNull;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "FullSub")
public class FullSub extends Subscription implements Serializable {

    String FullSubId;
//    @NotNull
    String CarNumber;
//    @NotNull
    String CustomerId;

    Date StartDate;  //format year:month:day   // we need year and month because we might have a sub close to the end of december
//    @NotNull
    Date EndDate;
//    @NotNull
    Date StartParking; //format includes days we will use it as a counter for if it reaches 14
//    @NotNull
    String SubNum;
    public FullSub(){
        FullSubId = Integer.toString(getId_());
    }

    public FullSub(String id,String carnum)
    {
        this.CustomerId=id;
        this.setSubscriptionNumber(id);
        this.CarNumber=carnum;
        FullSubId = Integer.toString(getId_());
        this.StartDate= new Date();
        updateEndDate();
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

    public void updateEndDate(){
        int startDay = StartDate.getDay();
        int startMonth = StartDate.getMonth();
        int startYear = StartDate.getYear();

        if (startDay+28 > 31){
            startDay = startDay+28 % 30;
            startMonth++;
        }else{
            startDay+=28;
        }
        if(startMonth>12){
            startMonth=1;
            startYear++;
        }

        EndDate.setYear(startYear);
        EndDate.setMonth(startMonth);
        EndDate.setDate(startDay);
    }

}
