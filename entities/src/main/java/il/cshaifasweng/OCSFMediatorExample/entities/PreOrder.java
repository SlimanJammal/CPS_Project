package il.cshaifasweng.OCSFMediatorExample.entities;

//import org.jetbrains.annotations.NotNull;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "PreOrderss")
public class PreOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_;
//    @NotNull
    String PreOrderId;
//    @NotNull
    String CarNumber;
//    @NotNull  //although it can be altered to if null choose any open parking space?
    String Parking_requested; //most likely to have three values r,c,d
//    @NotNull
    Date entrance;  //date includes year:month:day:hour
//    @NotNull
    Date exit_;      //date includes year:month:day:hour
//    @NotNull
    String Email_;

    public PreOrder()
    {}

    public PreOrder(String id_, String carnum, String PlaceToPark, String mail)
    {
        this.PreOrderId= id_;
        this.CarNumber=carnum;
        this.Parking_requested=PlaceToPark;
        this.Email_ =mail;
    }

    public int getId_() {
        return id_;
    }

    public String getCarNumber() {
        return CarNumber;
    }

    public String getEmail_() {
        return Email_;
    }

    public String getPreOrderId() {
        return PreOrderId;
    }

    public Date getEntrance() {
        return entrance;
    }

    public Date getExit_() {
        return exit_;
    }

    public String getParking_requested() {
        return Parking_requested;
    }

    public void setCarNumber(String carNumber) {
        CarNumber = carNumber;
    }

    public void setEmail_(String email_) {
        Email_ = email_;
    }

    public void setPreOrderId(String preOrderId) {
        PreOrderId = preOrderId;
    }

    public void setEntrance(Date entrance) {
        this.entrance = entrance;
    }

    public void setExit_(Date exit) {
        this.exit_ = exit;
    }

    public void setParking_requested(String parking_requested) {
        Parking_requested = parking_requested;
    }

}
