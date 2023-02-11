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
		else if(ms.getMessage().equals("alterPrices"))
		{
			// todo ask regional manager if we need a price change and if agreed then change
			// todo change prices in database
			// todo also check which of the objects of the message do not have null in them
		}
		else if(ms.getMessage().equals("showPrices"))
		{
			// todo get all prices from database and then put them in objects 1 to 5 to present them to manager
			Message MSG=new Message("pricesReturned");
			client.sendToClient(MSG);
		}
		else if(ms.getMessage().equals("showStats"))
		{
			// todo get all stats from database and then put them in objects 1 to 3 to present them to manager
			Message MSG=new Message("statsReturned");
			client.sendToClient(MSG);
		}

	}

}
