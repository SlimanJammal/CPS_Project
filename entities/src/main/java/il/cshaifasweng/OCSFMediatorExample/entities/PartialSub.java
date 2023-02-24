package il.cshaifasweng.OCSFMediatorExample.entities;

import com.sun.istack.NotNull;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table
public class PartialSub extends Subscription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    String PartialSubId;
    @NotNull
    String CarNumber;
    @NotNull
    Date StartDate;  //format year:month:day   // we need year and month because we might have a sub close to the end of december
    boolean Entered;
    @NotNull
    String SubNum;
    @NotNull
    String CustomerId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MultiSubId")
    MultiSub multisub;

    public PartialSub()
    {}
    public PartialSub(String id,String carnum)
    {
        this.CustomerId=id;
        this.CarNumber=carnum;
    }

    public String getCarNumber() {
        return CarNumber;
    }

    public String getPartialSubId() {
        return PartialSubId;
    }

    public String getSubNum() {
        return SubNum;
    }

    public String getCustomerId() {
        return CustomerId;
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

    public void setPartialSubId(String partialSubId) {
        PartialSubId = partialSubId;
    }

    public void setEntered(boolean entered) {
        Entered = entered;
    }
}
