package il.cshaifasweng.OCSFMediatorExample.entities;


import javax.persistence.*;

@Entity
@Table(name = "Subscription")
public class Subscription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

}
