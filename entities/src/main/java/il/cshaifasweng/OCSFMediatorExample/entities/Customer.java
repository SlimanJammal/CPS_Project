package il.cshaifasweng.OCSFMediatorExample.entities;


import javax.persistence.*;
import java.util.Vector;

@Entity
@Table
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToMany
    Vector<Complaint> complaintsVector;


}
