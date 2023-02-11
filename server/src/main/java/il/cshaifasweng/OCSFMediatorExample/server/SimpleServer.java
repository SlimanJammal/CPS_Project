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
			//todo check in database if exists
			Message MSG=new Message("AllowManager");
				client.sendToClient(MSG);
		}
		else if(ms.getMessage().equals("loginEmployee"))
		{
			// todo check in database if exists
			Message MSG=new Message("AllowEmployee");
			client.sendToClient(MSG);
		}
		else if(ms.getMessage().equals("RenewSub"))
		{
			// todo check in database if exists
			Message MSG=new Message("SubRenewed");
			client.sendToClient(MSG);
		}

	}

}
