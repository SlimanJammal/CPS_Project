package il.cshaifasweng.OCSFMediatorExample.entities;

import java.io.Serializable;
//todo needs to be deleted or moved to client !
public class Complaints implements Serializable {
    String  customerId;
    String  complaintText;

    public Complaints(String Id, String Text){
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