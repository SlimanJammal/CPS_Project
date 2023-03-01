package il.cshaifasweng.OCSFMediatorExample.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

@Entity
@Table(name = "deletedOrders")
public class DeletedOrders implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int DeletedId ;



    LocalDate deleteDate;

    LocalTime deleteTime;

    int parking_lot_id;

    public LocalDate getDeleteDate() {
        return deleteDate;
    }

    public void setDeleteDate(LocalDate deleteDate) {
        this.deleteDate = deleteDate;
    }

    public LocalTime getDeleteTime() {
        return deleteTime;
    }

    public void setDeleteTime(LocalTime deleteTime) {
        this.deleteTime = deleteTime;
    }

    public int getParking_lot_id() {
        return parking_lot_id;
    }

    public void setParking_lot_id(int parking_lot_id) {
        this.parking_lot_id = parking_lot_id;
    }
}
