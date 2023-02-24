package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.Message;

public class showPricesEvent {
    Message msg;
    public showPricesEvent(Message msg) {
        this.msg=msg;
    }
}
