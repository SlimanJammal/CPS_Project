package il.cshaifasweng.OCSFMediatorExample.entities;

import java.io.Serializable;

public class CustomerServiceWorker extends User implements Serializable {

    public CustomerServiceWorker(String userName, String password, String firstName, String lastName, int permission) {
        super(userName, password, firstName, lastName, permission);
    }


}
