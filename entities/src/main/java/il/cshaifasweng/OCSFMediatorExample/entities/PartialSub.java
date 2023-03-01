package il.cshaifasweng.OCSFMediatorExample.entities;

import com.sun.istack.NotNull;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "PartialSubs")
public class PartialSub extends Subscription  implements Serializable {


//    @NotNull
    String CarNumber;
//    @NotNull
    Date StartDate;  //format year:month:day   // we need year and month because we might have a sub close to the end of december
//    @NotNull
    Date EndDate;
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

        this.StartDate = new Date();
        updateEndDate();//this initializes end date according to start date;
    }

    public String getCarNumber() {
        return CarNumber;
    }

//    public String getPartialSubId() {
//        return PartialSubId;
//    }

    public String getSubNum() {
        return SubNum;
    }

    public String getCustomerId() {
        return CustomerId;
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

    public boolean isEntered() {
        return Entered;
    }

    public Date getStartDate() {
        return StartDate;
    }

    public void setSubNum(String subNum) {
        SubNum = subNum;
    }

    public void setCustomerId(String customerId) {
        CustomerId = customerId;
    }

    public void setStartDate(Date startDate) {
        StartDate = startDate;
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
}
