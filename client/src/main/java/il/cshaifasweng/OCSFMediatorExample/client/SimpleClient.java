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
		} else if (ms.getMessage().startsWith("AllowManager")) {
			if(ms.getMessage().endsWith("KIOSK")) {
				EventBus.getDefault().post(new loginManagerKioskEvent((Message) msg));
			} else {
				EventBus.getDefault().post(new loginManagerWebsitekEvent((Message) msg));
			}

		} else if (ms.getMessage().equals("AllowEmployee")) {
			EventBus.getDefault().post(new loginWorkerEvent((Message) msg));
		}else if (ms.getMessage().equals("SubRenewed")) {
			EventBus.getDefault().post(new SubRenewEvent((Message) msg));
		} else if (ms.getMessage().equals("pricesReturned") ||ms.getMessage().equals("prices update request sent") ) {
			EventBus.getDefault().post(new showPricesEvent((Message) msg));
		}else if (ms.getMessage().equals("statsReturned")) {
			EventBus.getDefault().post(new showStatsEvent((Message) msg));

		}else if(ms.getMessage().equals("OneTimeParkingOrder")){
			EventBus.getDefault().post(new OneTimeParkingOrderEvent((Message) msg));
		}
		else if (ms.getMessage().equals("Complaint")) {
			EventBus.getDefault().post(new ComplaintEvent((Message) msg));

		}
		else if (ms.getMessage().equals("OcasionalParking")){
			EventBus.getDefault().post(new OcasionalEvent((Message) msg));
		} else if(ms.getMessage().endsWith("regional")){
			EventBus.getDefault().post(new RegionalManagerEvent((Message) msg));
		}

	}
	
	public static SimpleClient getClient() {
		if (client == null) {
			client = new SimpleClient("localhost", 3000);
		}
		return client;
	}

}
