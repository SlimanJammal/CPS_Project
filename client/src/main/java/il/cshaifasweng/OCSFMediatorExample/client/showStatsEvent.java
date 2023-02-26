package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.Message;

import java.io.Serializable;

public class showStatsEvent implements Serializable {
    Message msg;
    public showStatsEvent(Message msg) {
        this.msg=msg;
    }

    public Message getMsg() {
        return msg;
    }
}
