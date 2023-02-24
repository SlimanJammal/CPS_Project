package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.Message;

import java.io.Serializable;

public class OneTimeParkingOrderEvent implements Serializable {
    private Message message;

    private String idNumber;
    private String email;
    private String arrivalTimeDate;
    private String license;
    private String desiredParking;
    private String leavingTimeDate;


    public Message getMessage() {
        return message;
    }

    public OneTimeParkingOrderEvent(Message message) {
        this.message = message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getArrivalTimeDate() {
        return arrivalTimeDate;
    }

    public void setArrivalTimeDate(String arrivalTimeDate) {
        this.arrivalTimeDate = arrivalTimeDate;
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public String getDesiredParking() {
        return desiredParking;
    }

    public void setDesiredParking(String desiredParking) {
        this.desiredParking = desiredParking;
    }

    public String getLeavingTimeDate() {
        return leavingTimeDate;
    }

    public void setLeavingTimeDate(String leavingTimeDate) {
        this.leavingTimeDate = leavingTimeDate;
    }
}

