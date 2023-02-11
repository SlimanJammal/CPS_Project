package il.cshaifasweng.OCSFMediatorExample.server;

import il.cshaifasweng.OCSFMediatorExample.entities.Message;
import il.cshaifasweng.OCSFMediatorExample.server.ocsf.AbstractServer;
import il.cshaifasweng.OCSFMediatorExample.server.ocsf.ConnectionToClient;

import java.io.IOException;

public class SimpleServer extends AbstractServer {

	public SimpleServer(int port) {
		super(port);
		
	}

	@Override
	protected void handleMessageFromClient(Object msg, ConnectionToClient client) throws IOException { //
		String msgString = msg.toString();
		Message ms= (Message)msg;
		if (msgString.startsWith("#warning")) {
			Message message = new Message("Warning from server!");
			try {
				client.sendToClient(message);
				System.out.format("Sent warning to client %s\n", client.getInetAddress().getHostAddress());
			} catch (IOException e) {
				e.printStackTrace();
			}

		} else if (ms.getMessage().equals("loginManager")) {
			//do things
		}
		else if(ms.getMessage().equals("loginEmployee"))
		{
			// do other things
		} else if (ms.getMessage().equals("OneTimeParkingOrder_Submit")){
			//todo check if in DB
			// return OneTimeParkingOrder_Success
			// or OneTimeParkingOrder_Fail

			Message message = new Message("OneTimeParkingOrder");
			message.setObject1("OneTimeParkingOrder_Success");

			client.sendToClient(message);

		}

	}

}
