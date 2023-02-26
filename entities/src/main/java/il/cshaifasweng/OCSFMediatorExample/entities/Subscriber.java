package il.cshaifasweng.OCSFMediatorExample.entities;


import javax.persistence.*;

@Entity
@Table (name = "Subscribers")
public class Subscriber extends Customer{

    private String subscriptionType;

    @OneToOne(cascade = CascadeType.ALL)
    Subscription subscription;
}
