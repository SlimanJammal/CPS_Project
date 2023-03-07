package il.cshaifasweng.OCSFMediatorExample.entities;


import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table (name = "Subscribers")
public class Subscriber extends Customer implements Serializable {

    private String subscriptionType;

    boolean mail_sent;

    @OneToOne(cascade = CascadeType.ALL)
    Subscription subscription;
}
