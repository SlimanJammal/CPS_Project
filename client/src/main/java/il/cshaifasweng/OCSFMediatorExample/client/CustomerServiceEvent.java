package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.Message;

import java.io.Serializable;

public class CustomerServiceEvent implements Serializable {
    private Message message;

    public CustomerServiceEvent(Message message) {
        this.message = message;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message msg) {
        this.message = msg;
    }
}
