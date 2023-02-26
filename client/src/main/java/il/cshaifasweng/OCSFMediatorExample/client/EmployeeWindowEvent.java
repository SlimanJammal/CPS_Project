package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.Message;

import java.io.Serializable;

public class EmployeeWindowEvent implements Serializable {
    private Message message;

    public EmployeeWindowEvent(Message message) {
        this.message = message;
    }

    public Message getMessage() {
        return message;
    }

    public void getMessage(Message msg) {
        this.message = msg;
    }

}
