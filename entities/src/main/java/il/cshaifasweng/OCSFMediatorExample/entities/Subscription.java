package il.cshaifasweng.OCSFMediatorExample.entities;


import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "Subscriptionsss")
public abstract class Subscription implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Subscriptionssssubsid_;

    public String getSubscriptionNumber() {
        return subscriptionNumber;
    }

    public void setSubscriptionNumber(String subscriptionNumber) {
        this.subscriptionNumber = subscriptionNumber;
    }

    private String subscriptionNumber;

    public int getId_() {
        return Subscriptionssssubsid_;
    }

    boolean mail_sent;

    public boolean isMail_sent() {
        return mail_sent;
    }
}
