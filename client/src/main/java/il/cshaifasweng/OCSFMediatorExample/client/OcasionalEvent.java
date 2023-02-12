package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.Message;

public class OcasionalEvent {
    private Message message;

    public Message getMessage() {
        return message;
    }

    public OcasionalEvent(Message message) {
        this.message = message;
    }
}
