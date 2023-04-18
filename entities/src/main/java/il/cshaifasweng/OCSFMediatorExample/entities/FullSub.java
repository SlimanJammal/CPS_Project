package il.cshaifasweng.OCSFMediatorExample.entities;

import org.jetbrains.annotations.NotNull;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "FullSub")
public class FullSub extends Subscription implements Serializable {

    String FullSubId;
//    @NotNull
    String CarNumber;
//    @NotNull
    String CustomerId;

    LocalDate StartDate;  //format year:month:day   // we need year and month because we might have a sub close to the end of december

    public LocalDate getEndDate() {
        return EndDate;
    }

    //    @NotNull
    LocalDate EndDate;
//    @NotNull
    LocalDate StartParking; //format includes days we will use it as a counter for if it reaches 14
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
        this.StartDate= LocalDate.now();
        this.EndDate = LocalDate.now();
//        updateEndDate();
    }

    public LocalDate getStartDate() {
        return StartDate;
    }

    public String getCarNumber() {
        return CarNumber;
    }


    public String getFullSubId() {
        return FullSubId;
    }

    public String getSubNum() {
        return getSubscriptionNumber();
    }

    public LocalDate getStartParking() {
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

    public void setStartDate(LocalDate startDate)
    {
        StartDate = startDate;
        EndDate = startDate.plusDays(28);
    }

    public void setFullSubId(String fullSubId) {
        FullSubId = fullSubId;
    }

    public void setStartParking(LocalDate startParking) {
        StartParking = startParking;
    }

    public void setSubNum(String subNum) {
        SubNum = subNum;
    }

    public void updateEndDate(){
       LocalDate temp = LocalDate.now();
       EndDate = temp.plusDays(28);
    }

    public void print(){
        System.out.println("Customer ID = "+CustomerId);
        System.out.println("Subscription number= "+ getSubscriptionNumber());
        System.out.println("Car number= "+ getCarNumber());
        System.out.println("Subscription start= "+ getStartDate().toString());
        System.out.println("Subscription End= "+ getEndDate().toString());
    }

}
