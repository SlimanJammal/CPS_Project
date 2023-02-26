package il.cshaifasweng.OCSFMediatorExample.entities;


import javax.persistence.*;

@Entity
@Table(name = "Subscriptionsss")
public abstract class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Subscriptionssssubsid_;

    public int getId_() {
        return Subscriptionssssubsid_;
    }
}
