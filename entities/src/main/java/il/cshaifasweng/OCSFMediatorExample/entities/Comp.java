package il.cshaifasweng.OCSFMediatorExample.entities;

import java.io.Serializable;
//todo needs to be deleted or moved to client !
public class Comp implements Serializable {
    String  customerId;
    String  complaintText;
    String mail;

    public Comp(String Id, String Text){
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