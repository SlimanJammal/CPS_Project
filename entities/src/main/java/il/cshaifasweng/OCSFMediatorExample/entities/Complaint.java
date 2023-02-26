package il.cshaifasweng.OCSFMediatorExample.entities;

import com.sun.istack.NotNull;

import javax.persistence.*;

@Entity
@Table(name="Complaints")
public class Complaint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int ComplaintId_;
    @NotNull
    boolean Entered_;


    int customerId_;
    String  complaintText;

    public Complaint(int customerId_, String complaintText) {
        this.customerId_ = customerId_;
        this.complaintText = complaintText;
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
}