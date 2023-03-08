package il.cshaifasweng.OCSFMediatorExample.entities;

import com.sun.istack.NotNull;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="Complaints")
public class Complaint implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int ComplaintId_;
//    @NotNull
    boolean Entered_;
    String mail;


    int customerId_;
    String  complaintText;

    public Complaint(int customerId_, String complaintText, String email) {
        this.customerId_ = customerId_;
        this.complaintText = complaintText;
        this.mail = email;
    }

    public Complaint() {

    }

    public int getCustomerId_() {
        return customerId_;
    }

    public void setCustomerId_(int customerId) {
        this.customerId_ = customerId;
    }

    public String getComplaintText() {
        return complaintText;
    }

    public void setComplaintText(String complaintText) {
        this.complaintText = complaintText;
    }

    public int getComplaintId_() {
        return ComplaintId_;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }
}