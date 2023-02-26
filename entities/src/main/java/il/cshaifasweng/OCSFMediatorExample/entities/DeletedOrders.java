package il.cshaifasweng.OCSFMediatorExample.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
public class DeletedOrders {
    private static final long serialVersionUID = -8224097662914849956L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int DeletedId ;

    public void setOrder(Object order) {
        this.order = order;
    }

    Object order;   //this object is mainly to save what type of parking and all the info we used to order
                    // for statistics, we don't need it, but you might find it useful.

    public void setDeletetime(LocalDateTime deletetime) {
        this.deletetime = deletetime;
    }

    public LocalDateTime getDeletetime() {
        return deletetime;
    }

    LocalDateTime deletetime;
}
