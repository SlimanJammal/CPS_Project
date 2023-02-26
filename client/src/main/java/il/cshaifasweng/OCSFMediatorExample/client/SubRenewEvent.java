package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.Message;

import java.io.Serializable;

public class SubRenewEvent implements Serializable {
    Message msg;
    public SubRenewEvent(Message msg) {
        this.msg=msg;
    }

}
