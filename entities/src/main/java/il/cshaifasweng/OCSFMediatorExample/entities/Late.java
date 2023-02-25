package il.cshaifasweng.OCSFMediatorExample.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
public class Late {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int LateId ;

    public void setDeletetime(LocalDateTime deletetime) {
        this.Latetime = deletetime;
    }

    public LocalDateTime getDeletetime() {
        return Latetime;
    }

    LocalDateTime Latetime;
}
