package il.cshaifasweng.OCSFMediatorExample.entities;

import com.sun.istack.NotNull;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "PartialSubs")
public class PartialSub extends Subscription  implements Serializable {


//    @NotNull
    String CarNumber;
//    @NotNull
    LocalDate StartDate;  //format year:month:day   // we need year and month because we might have a sub close to the end of december

    public LocalDate getEndDate() {
        return EndDate;
    }

    //    @NotNull
    LocalDate EndDate;
    boolean Entered;
//    @NotNull
    String SubNum;
//    @NotNull
    String CustomerId;

//    @NotNull
    String EntranceHour,DepartureHour;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MultiSubId")
    MultiSub multisub;

    public PartialSub()
    {}
    public PartialSub(String id,String carnum)
    {

        this.CustomerId=id;
        this.CarNumber=carnum;
        this.setSubscriptionNumber(id);

        this.StartDate =  LocalDate.now();
        this.EndDate =  LocalDate.now();
        mail_sent =false;
        //updateEndDate();//this initializes end date according to start date;
    }

    public String getCarNumber() {
        return CarNumber;
    }

//    public String getPartialSubId() {
//        return PartialSubId;
//    }

    public String getSubNum() {
        return getSubscriptionNumber();
    }

    public String getCustomerId() {
        return CustomerId;
    }

    public void updateEndDate(){
        LocalDate temp = LocalDate.now();
        this.EndDate = temp.plusDays(28);
    }

    public boolean isEntered() {
        return Entered;
    }

    public LocalDate getStartDate() {
        return StartDate;
    }

    public void setSubNum(String subNum) {
        SubNum = subNum;
    }

    public void setCustomerId(String customerId) {
        CustomerId = customerId;
    }

    public void setStartDate(LocalDate startDate) {

        StartDate = startDate;
        EndDate = startDate.plusDays(28);
    }

    public void setCarNumber(String carNumber) {
        CarNumber = carNumber;
    }

//    public void setPartialSubId(String partialSubId) {
//        PartialSubId = partialSubId;
//    }

    public void setEntered(boolean entered) {
        Entered = entered;
    }

    public String getEntranceHour() {
        return EntranceHour;
    }

    public void setEntranceHour(String entranceHour) {
        EntranceHour = entranceHour;
    }

    public String getDepartureHour() {
        return DepartureHour;
    }

    public void setDepartureHour(String entranceHour) {
        DepartureHour = DepartureHour;
    }
    public void print(){
        System.out.println("Customer ID = "+CustomerId);
        System.out.println("Subscription number= "+ getSubscriptionNumber());
        System.out.println("Car number= "+ getCarNumber());
        System.out.println("Subscription start= "+ getStartDate().toString());
        System.out.println("Subscription End= "+ getEndDate().toString());
    }
}
