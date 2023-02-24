package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.Message;

public class ComplaintEvent {
    private Message message;

    public Message getMessage() {
        return message;
    }

    public ComplaintEvent(Message message) {
        this.message = message;
    }


}
