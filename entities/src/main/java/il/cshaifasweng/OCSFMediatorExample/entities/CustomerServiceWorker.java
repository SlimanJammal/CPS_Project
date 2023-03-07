package il.cshaifasweng.OCSFMediatorExample.entities;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Table(name = "customerService")
public class CustomerServiceWorker extends User implements Serializable {



    public CustomerServiceWorker(String userName, String password, String firstName, String lastName, int permission) {
        super(userName, password, firstName, lastName, permission);
    }


    public CustomerServiceWorker() {

    }
}
