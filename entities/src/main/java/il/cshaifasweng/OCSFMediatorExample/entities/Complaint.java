package il.cshaifasweng.OCSFMediatorExample.entities;

import java.io.Serializable;

public class Complaint implements Serializable {
    String  customerId;
    String  complaintText;

    public Complaint(String Id, String Text){
        this.customerId = Id;
        this.complaintText = Text;
    }

    public String getCustomerId(){
        return customerId;
    }
    public String getComplaintText(){
        return complaintText;
    }
}
