package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.Message;

import java.io.Serializable;

public class loginManagerEvent implements Serializable {
    Message msg;
    public loginManagerEvent(Message msg) {
        this.msg=msg;
    }

    public Message getMsg() {
        return msg;
    }

    public void setMsg(Message msg) {
        this.msg = msg;
    }
}
