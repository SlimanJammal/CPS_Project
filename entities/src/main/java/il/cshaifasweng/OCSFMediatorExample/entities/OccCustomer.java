package il.cshaifasweng.OCSFMediatorExample.entities;

import com.sun.istack.NotNull;
//import org.jetbrains.annotations.NotNull;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

@Entity
@Table(name = "Customer")
public class OccCustomer extends Customer implements Serializable {

    private String OccCustomerId;
//    @NotNull
    private String CarNumber;
//    @NotNull
//    long StartTime ;
    LocalTime StartTime;

    LocalDate StartDate;


//    @NotNull
     String CustomerId;
//    long FinishTime ;
    LocalTime FinishTime;
//    @NotNull
    String Email;

    int parking_lot_id;

    public OccCustomer()
    {OccCustomerId = getCustomerId();}

    public OccCustomer(String id,String carnum,String mail)
    {
        this.CustomerId=id;
        this.CarNumber=carnum;
        this.Email=mail;
        OccCustomerId = getCustomerId();
    }

    public LocalDate getStartDate() {
        return  LocalDate.now();
    }

    public String getOccCustomerId() {
        return OccCustomerId;
    }

//    public Date getFinishTime() {
//        return FinishTime;
//    }

    public LocalTime getStartTime() {
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

    public void setFinishTime(LocalTime finishTime) {
        FinishTime = finishTime;
    }

    public LocalTime getFinishTime(){return FinishTime;}

    public void setStartTime(LocalTime startTime) {
        StartTime = startTime;
    }

    public void setOccCustomerId(String occCustomerId) {
        OccCustomerId = occCustomerId;
    }

    public int getParking_lot_id() {
        return parking_lot_id;
    }

    public void setParking_lot_id(int parking_lot_id) {
        this.parking_lot_id = parking_lot_id;
    }

    public void setStartDate(LocalDate startDate) {
        StartDate = startDate;
    }

}
