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

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    private String Email;
    private String subscriptionNumber;

    public int getId_() {
        return Subscriptionssssubsid_;
    }

    boolean mail_sent;

    public boolean isMail_sent() {
        return mail_sent;
    }

    public void setMail_sent(boolean mail_sent) {
        this.mail_sent = mail_sent;
    }
}
