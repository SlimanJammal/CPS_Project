package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.Message;

public class ParkingManagerSecondaryEvent {
    private Message message;

    private String utilRes1;
    private String utilRes2;
    private String utilRes3;

    private String cancelRes1;
    private String cancelRes2;
    private String cancelRes3;


    private String late1;
    private String late2;
    private String late3;


    public void setMessage(Message message) {
        this.message = message;
    }

    public String getUtilRes1() {
        return utilRes1;
    }

    public void setUtilRes1(String utilRes1) {
        this.utilRes1 = utilRes1;
    }

    public String getUtilRes2() {
        return utilRes2;
    }

    public void setUtilRes2(String utilRes2) {
        this.utilRes2 = utilRes2;
    }

    public String getUtilRes3() {
        return utilRes3;
    }

    public void setUtilRes3(String utilRes3) {
        this.utilRes3 = utilRes3;
    }

    public String getCancelRes1() {
        return cancelRes1;
    }

    public void setCancelRes1(String cancelRes1) {
        this.cancelRes1 = cancelRes1;
    }

    public String getCancelRes2() {
        return cancelRes2;
    }

    public void setCancelRes2(String cancelRes2) {
        this.cancelRes2 = cancelRes2;
    }

    public String getCancelRes3() {
        return cancelRes3;
    }

    public void setCancelRes3(String cancelRes3) {
        this.cancelRes3 = cancelRes3;
    }

    public String getLate1() {
        return late1;
    }

    public void setLate1(String late1) {
        this.late1 = late1;
    }

    public String getLate2() {
        return late2;
    }

    public void setLate2(String late2) {
        this.late2 = late2;
    }

    public String getLate3() {
        return late3;
    }

    public void setLate3(String late3) {
        this.late3 = late3;
    }




    public Message getMessage() {
        return message;
    }

    public ParkingManagerSecondaryEvent(Message message) {
        this.message = message;
    }





}
