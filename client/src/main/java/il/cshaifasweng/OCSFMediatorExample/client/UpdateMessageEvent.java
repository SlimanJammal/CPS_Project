package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.Message;

import java.io.Serializable;

public class UpdateMessageEvent  implements Serializable {
    private Message message;

    public Message getMessage() {
        return message;
    }

    public UpdateMessageEvent(Message message) {
        this.message = message;
    }
}
