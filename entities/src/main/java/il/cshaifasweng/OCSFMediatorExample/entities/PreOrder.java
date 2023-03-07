package il.cshaifasweng.OCSFMediatorExample.entities;

//import org.jetbrains.annotations.NotNull;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

@Entity
@Table(name = "PreOrderss")
public class PreOrder  implements Serializable {

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
    LocalDate entranceDate;  //date includes year:month:day
    LocalTime entranceTime; //hour , minute

    LocalDate exitDate;  //date includes year:month:day
    LocalTime exitTime; //hour , minute

    private boolean is_reminded;
    int parking_lot_id;

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
        is_reminded =false;
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

    public LocalDate getEntranceDate() {
        return entranceDate;
    }

    public boolean isIs_reminded() {
        return is_reminded;
    }

    public void setIs_reminded(boolean is_reminded) {
        this.is_reminded = is_reminded;
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

    public void setEntranceDate(LocalDate entrance) {
        this.entranceDate = entrance;
    }

    public LocalTime getEntranceTime() {
        return entranceTime;
    }

    public void setEntranceTime(LocalTime entranceTime) {
        this.entranceTime = entranceTime;
    }

    public LocalDate getExitDate() {
        return exitDate;
    }

    public void setExitDate(LocalDate exitDate) {
        this.exitDate = exitDate;
    }

    public LocalTime getExitTime() {
        return exitTime;
    }

    public void setExitTime(LocalTime exitTime) {
        this.exitTime = exitTime;
    }

    public void setParking_requested(String parking_requested) {
        Parking_requested = parking_requested;
    }

    public int getParking_lot_id() {
        return parking_lot_id;
    }

    public void setParking_lot_id(int parking_lot_id) {
        this.parking_lot_id = parking_lot_id;
    }
}
