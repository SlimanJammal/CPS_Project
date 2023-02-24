package il.cshaifasweng.OCSFMediatorExample.server;

import il.cshaifasweng.OCSFMediatorExample.entities.FullSub;
import il.cshaifasweng.OCSFMediatorExample.entities.Message;
import il.cshaifasweng.OCSFMediatorExample.entities.MultiSub;
import il.cshaifasweng.OCSFMediatorExample.entities.PartialSub;
import il.cshaifasweng.OCSFMediatorExample.server.ocsf.AbstractServer;
import il.cshaifasweng.OCSFMediatorExample.server.ocsf.ConnectionToClient;

import java.io.IOException;

public class SimpleServer extends AbstractServer {

	public SimpleServer(int port) {
		super(port);
		
	}

	@Override
	protected void handleMessageFromClient(Object msg, ConnectionToClient client) { //
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
		}
		//===================================================================================
		//BASHAR Emplyee Window
		else if(ms.getMessage().equals("Deactivate Parking Spot"))
		{
			//Deactivate Parking Slot
			//Object #1 - Parking Slot ID
			String ParkingID = ms.getObject1().toString();

		}
		else if(ms.getMessage().equals("Activate Parking Spot"))
		{
			//Activate Parking Slot
			//Object #1 - Parking Slot ID
			String ParkingID = ms.getObject1().toString();
		}
		else if(ms.getMessage().equals("System Request"))
		{
			//Check other parking places to send a vehicle to...
			//Object #1 - Parking ID
			//Object #2 - System Command ID
			String ParkingID = ms.getObject1().toString();
			String SystemCommand = ms.getObject2().toString();
		}
		else if(ms.getMessage().equals("Send To Other Parking"))
		{
			//Check other parking places to send a vehicle to...
			//Object #1 - License Plate ID
			//Object #2 - User ID
			//Object #3 - Parking ID
			String LicenseID = ms.getObject1().toString();
			String UserID = ms.getObject2().toString();
			String ParkingID = ms.getObject3().toString();
		}
		else if(ms.getMessage().equals("Occasion Request"))
		{
			//Check other parking places to send a vehicle to...
			//Object #1 - Parking Slot ID
			//Object #2 - Car ID
			//Object #3 - Occasion ID
			String ParkingSlotID = ms.getObject1().toString();
			String CarNumber = ms.getObject2().toString();
			String OccasionID = ms.getObject3().toString();
		}
		//===================================================================================
		//BASHAR RegisterNewSubscription Window
		else if(ms.getMessage().equals("Register New Subscriber"))
		{
			//Check other parking places to send a vehicle to...
			//Object #1 - Subscriber Type
			//Object #2 - Customer ID
			//Object #3 - Starting Date
			//Object #4 - Entrance Hour
			//Object #5 - Departure Hour
			//Object #6 - Car License ID
			//Object #7 - Regular Parking Lot

			String SubscriberType = ms.getObject1().toString();
			String CustomerID = ms.getObject2().toString();
			String StartingDate = ms.getObject3().toString();
			String EntranceHour = ms.getObject4().toString();
			String DepartureHour = ms.getObject5().toString();
			String CarLicenseID = ms.getObject6().toString();
			String RegularParkingLot = ms.getObject7().toString();


			if(SubscriberType.equals("Single Monthly Subscription"))
			{
				PartialSub input = new PartialSub(CustomerID,CarLicenseID);
				input.setStartDate(StartingDate);

			}
			else if(SubscriberType.equals("Multi Monthly Subscription"))
			{
				MultiSub input = new MultiSub();

			}
			else if(SubscriberType.equals("Fully Subscription"))
			{
				FullSub input = new FullSub(CustomerID,CarLicenseID);
				input.setStartDate(StartingDate);
			}

		}
		//===================================================================================
		//BASHAR Check Status Window
		else if(ms.getMessage().equals("Check Client Spot Status"))
		{
			String CustomerId = ms.getObject1().toString();
			String CarNumber = ms.getObject2().toString();
		}
	}

}
