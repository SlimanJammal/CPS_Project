package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.Message;

public class loginWorkerEvent {
    Message msg;
    public loginWorkerEvent(Message msg) {
        this.msg=msg;
    }
}
