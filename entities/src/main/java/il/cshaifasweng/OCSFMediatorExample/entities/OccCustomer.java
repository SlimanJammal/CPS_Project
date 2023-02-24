package il.cshaifasweng.OCSFMediatorExample.entities;

import com.sun.istack.NotNull;
//import org.jetbrains.annotations.NotNull;

import javax.persistence.*;
import java.sql.Time;
import java.util.Date;

@Entity
@Table(name = "Customer")
public class OccCustomer extends Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String OccCustomerId;
    @NotNull
    private String CarNumber;
    @NotNull
//    long StartTime ;
    Time StartTime;

    @NotNull
     String CustomerId;
//    long FinishTime ;
    Time FinishTime;
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

    public Date getFinishTime() {
        return FinishTime;
    }

    public Date getStartTime() {
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

    public void setFinishTime(Time finishTime) {
        FinishTime = finishTime;
    }

    public void setStartTime(Time startTime) {
        StartTime = startTime;
    }

    public void setOccCustomerId(String occCustomerId) {
        OccCustomerId = occCustomerId;
    }
}
