package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.Message;

public class loginManagerEvent {
    Message msg;
    public loginManagerEvent(Message msg) {
        this.msg=msg;
    }
}
