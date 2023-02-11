package il.cshaifasweng.OCSFMediatorExample.entities;

import com.sun.istack.NotNull;
//import org.jetbrains.annotations.NotNull;

import javax.persistence.*;

@Entity
@Table
public class OccCustomer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String OccCustomerId;
    @NotNull
    private String CarNumber;
    @NotNull
    long StartTime ;

    @NotNull
     String CustomerId;
    long FinishTime ;
    @NotNull
    String Email;

    public OccCustomer()
    {}

    public OccCustomer(String id,String carnum,String mail)
    {
        this.CustomerId=id;
        this.CarNumber=carnum;
        this.Email=mail;
    }



    public String getOccCustomerId() {
        return OccCustomerId;
    }

    public long getFinishTime() {
        return FinishTime;
    }

    public long getStartTime() {
        return StartTime;
    }

    public String getEmail() {
        return Email;
    }

    public String getCarNumber() {
        return CarNumber;
    }

    public String getCustomerId() {
        return CustomerId;
    }

    public void setCustomerId(String customerId) {
        CustomerId = customerId;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public void setCarNumber(String carNumber) {
        CarNumber = carNumber;
    }

    public void setFinishTime(long finishTime) {
        FinishTime = finishTime;
    }

    public void setStartTime(long startTime) {
        StartTime = startTime;
    }

    public void setOccCustomerId(String occCustomerId) {
        OccCustomerId = occCustomerId;
    }
}
