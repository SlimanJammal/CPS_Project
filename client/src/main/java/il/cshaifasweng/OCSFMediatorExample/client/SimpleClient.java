package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.Message;
import org.greenrobot.eventbus.EventBus;

import il.cshaifasweng.OCSFMediatorExample.client.ocsf.AbstractClient;

public class SimpleClient extends AbstractClient {
	
	private static SimpleClient client = null;

	private SimpleClient(String host, int port) {
		super(host, port);
	}

	@Override
	protected void handleMessageFromServer(Object msg) {
		Message ms=(Message) msg;
		if (ms.getMessage().equals("hello")) {
			EventBus.getDefault().post(new WarningEvent((Message) msg));
		} else if (ms.getMessage().equals("login manager")) {
			EventBus.getDefault().post(new loginManagerEvent((Message) msg));

		}else if(ms.getMessage().equals("OneTimeParkingOrder")){
			EventBus.getDefault().post(new OneTimeParkingOrderEvent((Message) msg));
		}
		else if (ms.getMessage().equals("Complaint")) {
			EventBus.getDefault().post(new ComplaintEvent((Message) msg));

		}
		else if (ms.getMessage().equals("OcasionalParking")){
			EventBus.getDefault().post(new OcasionalEvent((Message) msg));
		}

	}
	
	public static SimpleClient getClient() {
		if (client == null) {
			client = new SimpleClient("localhost", 3000);
		}
		return client;
	}

}
