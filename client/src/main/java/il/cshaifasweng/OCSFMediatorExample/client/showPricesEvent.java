package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.Message;

import java.io.Serializable;

public class showPricesEvent implements Serializable {
    Message msg;
    public showPricesEvent(Message msg) {
        this.msg=msg;
    }
}
