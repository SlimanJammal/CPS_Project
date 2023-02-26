package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.Message;

import java.io.Serializable;

public class OcasionalEvent implements Serializable {
    private Message message;

    public Message getMessage() {
        return message;
    }

    public OcasionalEvent(Message message) {
        this.message = message;
    }
}
