package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.Message;

import java.io.Serializable;

public class loginWorkerWebsiteEvent implements Serializable {
    Message msg;
    public loginWorkerWebsiteEvent(Message msg) {
        this.msg=msg;
    }

    public Message getMsg() {
        return msg;
    }

    public void setMsg(Message msg) {
        this.msg = msg;
    }
}
