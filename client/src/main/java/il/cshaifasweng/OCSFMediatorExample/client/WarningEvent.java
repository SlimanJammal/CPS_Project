package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.Message;

import java.io.Serializable;

public class WarningEvent  implements Serializable {
	private Message message;

	public Message getWarning() {
		return message;
	}

	public WarningEvent(Message message) {
		this.message = message;
	}
}
