package il.cshaifasweng.OCSFMediatorExample.entities;

import com.sun.istack.NotNull;

import javax.persistence.*;

@Entity
@Table
public class Complaint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    String ComplaintId;
    @NotNull
    boolean Entered;


    String  customerId;
    String  complaintText;

    public Complaint(String customerId, String complaintText) {
        this.customerId = customerId;
        this.complaintText = complaintText;
    }

    public Complaint() {

    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getComplaintText() {
        return complaintText;
    }

    public void setComplaintText(String complaintText) {
        this.complaintText = complaintText;
    }
}