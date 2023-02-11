package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.Message;

public class WarningEvent {
	private Message message;

	public Message getWarning() {
		return message;
	}

	public WarningEvent(Message message) {
		this.message = message;
	}
}
