package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.Message;

import java.io.Serializable;

public class RegionalManagerEvent implements Serializable {


        private Message message;

        public Message getMessage() {
            return message;
        }

        public RegionalManagerEvent(Message message) {
            this.message = message;
        }

}
