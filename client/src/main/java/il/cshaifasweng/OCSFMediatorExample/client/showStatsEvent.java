package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.Message;

public class showStatsEvent {
    Message msg;
    public showStatsEvent(Message msg) {
        this.msg=msg;
    }
}
