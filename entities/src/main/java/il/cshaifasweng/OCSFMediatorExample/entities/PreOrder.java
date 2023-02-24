package il.cshaifasweng.OCSFMediatorExample.entities;

import com.sun.istack.NotNull;
//import org.jetbrains.annotations.NotNull;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table
public class PreOrder extends Customer{
    @Id
    String PreOrderId;
    @NotNull
    String CarNumber;
    @NotNull  //although it can be altered to if null choose any open parking space?
    String Parking_requested; //most likely to have three values r,c,d
    @NotNull
    Date entrance;  //date includes year:month:day:hour
    @NotNull
    Date exit;      //date includes year:month:day:hour
    @NotNull
    String Email;

    public PreOrder()
    {}

    public PreOrder(String id ,String carnum,String PlaceToPark,String mail)
    {
        this.PreOrderId=id;
        this.CarNumber=carnum;
        this.Parking_requested=PlaceToPark;
        this.Email=mail;
    }



    public String getCarNumber() {
        return CarNumber;
    }

    public String getEmail() {
        return Email;
    }

    public String getPreOrderId() {
        return PreOrderId;
    }

    public Date getEntrance() {
        return entrance;
    }

    public Date getExit() {
        return exit;
    }

    public String getParking_requested() {
        return Parking_requested;
    }

    public void setCarNumber(String carNumber) {
        CarNumber = carNumber;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public void setPreOrderId(String preOrderId) {
        PreOrderId = preOrderId;
    }

    public void setEntrance(Date entrance) {
        this.entrance = entrance;
    }

    public void setExit(Date exit) {
        this.exit = exit;
    }

    public void setParking_requested(String parking_requested) {
        Parking_requested = parking_requested;
    }

}
