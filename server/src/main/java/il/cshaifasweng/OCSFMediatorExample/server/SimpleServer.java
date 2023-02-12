package il.cshaifasweng.OCSFMediatorExample.server;

import il.cshaifasweng.OCSFMediatorExample.entities.Complaint;
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

				client.sendToClient(message);
				System.out.format("Sent warning to client %s\n", client.getInetAddress().getHostAddress());


		} else if (ms.getMessage().equals("loginManager")) {
			//do things
		}
		else if(ms.getMessage().equals("loginEmployee"))
		{
			// do other things
		}else if(ms.getMessage().equals("Complaint"))
		{
			Complaint tempComplaint = (Complaint) ms.getObject1();
			if(tempComplaint.getCustomerId().equals("314640426")){
				ms.setObject1("we recived the right information");
				client.sendToClient(ms);
			}else{
				ms.setObject1("information is wong");
				client.sendToClient(ms);
			}
			// do other things
		}else if(ms.getMessage().equals("OcasionalParking"))
		{
			client.sendToClient(ms);
			// Id number is saved as a string in object1
			// license plate number is saved as a string in object2
			// email is saved as a string in object3
			// leaving time is saved as a string in object4
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
