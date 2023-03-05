package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.Message;

public class SubRenewEventWebsite {
    private Message msg;
    public SubRenewEventWebsite (Message msg) {
        this.msg=msg;
    }

    public Message getMsg() {
        return msg;
    }

    public void setMsg(Message msg) {
        this.msg = msg;
    }
}
