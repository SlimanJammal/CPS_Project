package il.cshaifasweng.OCSFMediatorExample.entities;


import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "Subscriptionsss")
public abstract class Subscription implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Subscriptionssssubsid_;

    public int getId_() {
        return Subscriptionssssubsid_;
    }
}
