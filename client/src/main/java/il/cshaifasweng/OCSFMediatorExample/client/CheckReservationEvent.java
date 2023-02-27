package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.Message;

public class CheckReservationEvent {
    private Message message;

    public Message getMessage() {
        return message;
    }

    public CheckReservationEvent(Message message) {
        this.message = message;
    }


}
