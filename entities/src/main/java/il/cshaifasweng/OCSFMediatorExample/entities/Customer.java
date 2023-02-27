package il.cshaifasweng.OCSFMediatorExample.entities;


import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "Customers")
public abstract class Customer implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Customersid;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "ComplaintId_")
    List<Complaint> complaintsVector;

    public int getId_() {
        return Customersid;
    }
}
