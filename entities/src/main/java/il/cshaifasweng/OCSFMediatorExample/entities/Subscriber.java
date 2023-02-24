package il.cshaifasweng.OCSFMediatorExample.entities;


import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table (name = "Customer")
public class Subscriber extends Customer{

    private String subscriptionType;

    @ManyToOne
    Subscription subscription;
}
