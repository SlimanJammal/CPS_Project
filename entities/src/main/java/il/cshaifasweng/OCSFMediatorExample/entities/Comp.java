package il.cshaifasweng.OCSFMediatorExample.entities;

import java.io.Serializable;
//todo needs to be deleted or moved to client !
public class Comp implements Serializable {
    String  customerId;
    String  complaintText;
    String mail;

    public Comp(String Id, String Text,String email){
        this.customerId = Id;
        this.complaintText = Text;
        this.mail = email;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getCustomerId(){
        return customerId;
    }
    public String getComplaintText(){
        return complaintText;
    }
}