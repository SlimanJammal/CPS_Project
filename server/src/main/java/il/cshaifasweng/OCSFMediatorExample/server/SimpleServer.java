package il.cshaifasweng.OCSFMediatorExample.server;


import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import il.cshaifasweng.OCSFMediatorExample.entities.*;
import il.cshaifasweng.OCSFMediatorExample.server.ocsf.AbstractServer;
import il.cshaifasweng.OCSFMediatorExample.server.ocsf.ConnectionToClient;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.hibernate.service.ServiceRegistry;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Time;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.Temporal;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SimpleServer extends AbstractServer {


	//todo for mohammed all pdf and statistics
	//todo for mohammed all pdf and statistics
	//todo for mohammed all pdf and statistics
	//todo for mohammed all pdf and statistics
	//todo for mohammed all pdf and statistics
	//todo for mohammed all pdf and statistics
	//todo for mohammed all pdf and statistics
	//todo for mohammed all pdf and statistics







	public static Session session;

	public SimpleServer(int port) {
		super(port);
	}




	private static SessionFactory getSessionFactory() throws HibernateException {
		Configuration configuration = new Configuration();

		configuration.addAnnotatedClass(FullSub.class);
		configuration.addAnnotatedClass(ParkingManager.class);
		configuration.addAnnotatedClass(MultiSub.class);
		configuration.addAnnotatedClass(OccCustomer.class);
		configuration.addAnnotatedClass(ParkingLot.class);
		configuration.addAnnotatedClass(ParkingSpot.class);
		configuration.addAnnotatedClass(PartialSub.class);
		configuration.addAnnotatedClass(PreOrder.class);
		configuration.addAnnotatedClass(PricesClass.class);
		configuration.addAnnotatedClass(RegionalManager.class);
		configuration.addAnnotatedClass(Complaint.class);
		configuration.addAnnotatedClass(Customer.class);
		configuration.addAnnotatedClass(ParkingWorker.class);
		configuration.addAnnotatedClass(PricesUpdateRequest.class);
		configuration.addAnnotatedClass(Subscriber.class);
		configuration.addAnnotatedClass(Subscription.class);

		ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
				.applySettings(configuration.getProperties())
				.build();
		return configuration.buildSessionFactory(serviceRegistry);
	}

	public static <T> List<T> getAll (Class<T> object) {
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<T> criteriaQuery = builder.createQuery(object);
		Root<T> rootEntry = criteriaQuery.from(object);
		CriteriaQuery<T> allCriteriaQuery = criteriaQuery.select(rootEntry);
		TypedQuery<T> allQuery = session.createQuery(allCriteriaQuery);
		return allQuery.getResultList();
	}




	@Override
	protected void handleMessageFromClient(Object msg, ConnectionToClient client) throws IOException { //
		String msgString = msg.toString();
		Message ms= (Message)msg;
		if (msgString.startsWith("#warning")) {
			Message message = new Message("Warning from server!");

				client.sendToClient(message);
				System.out.format("Sent warning to client %s\n", client.getInetAddress().getHostAddress());


		} else if (ms.getMessage().equals("cancelOrder")) {
            Message cancelingmsg=(Message) msg;
			session.getSessionFactory().openSession();
			String cancelinghql = "FROM PreOrder ";
			Query query = session.createQuery(cancelinghql);
			List<PreOrder> results = query.getResultList();
			PreOrder refund=new PreOrder();


			for(PreOrder record : results)
			{
				if(     record.getCarNumber().equals(cancelingmsg.getObject1())
						&& record.getEmail().equals(cancelingmsg.getObject3())
				        && record.getEntrance().equals(cancelingmsg.getObject4())
						&& record.getExit().equals(cancelingmsg.getObject5())
						&& record.getPreOrderId().equals(cancelingmsg.getObject6())
					)
				{
					try {
						refund=record;
						DeletedOrders entityToAdd = new DeletedOrders();
						entityToAdd.setDeletetime( LocalDateTime.now()); //format= dd/mm/yyyy
						entityToAdd.setOrder(record);
						session.save(entityToAdd);
						session.beginTransaction().commit();
						PreOrder entityToDelete = session.get(PreOrder.class, record.getId());
						session.delete(entityToDelete);
						session.beginTransaction().commit();
						session.flush();
					}
					catch (Exception exp)
					{
						session.getTransaction().rollback();
					}
					finally {
						session.close();
					}

				}
			}

		// here I want to calculate the refund:
			Date cancellationTime=new Date();
			Date expectedEntrance = refund.getEntrance();

			Duration duration = Duration.between((Temporal) cancellationTime, (Temporal) expectedEntrance);
			long hours = duration.toHours();
			if(hours<0)
			{
				// no refund time already passed
			}
			else if (hours>=0 && hours<=1) {
				System.out.println("refund 10%");

			}
			else if (hours>=1 && hours<3) {
				System.out.println("refund 50%");

			}
			else if (hours>=3) {
				System.out.println("refund 90%");

			}


		} else if (ms.getMessage().equals("loginManager")) {

				Message MSG=new Message("AllowManager");
				String[] data = {ms.getID(),ms.getPassword()} ;

				Message msg1 = tryLogIn(data);
				User user1 = (User) msg1.getObject1();
				int permission_check = user1.getPermission();
				if(msg1.getMessage().equals("tryLogin_UserFound") && permission_check == 0 ){
					//regional
					MSG.setObject1("success");
					MSG.setObject2(msg1.getObject1()); // return User
					MSG.setObject3(permission_check);


				}
				else if(msg1.getMessage().equals("tryLogin_UserFound") && permission_check == 1 ){
					//regular manager
					MSG.setObject1("success");
					MSG.setObject2(msg1.getObject1()); // return User
					MSG.setObject3(permission_check);
				}else {

					MSG.setObject1("fail");
				}

				client.sendToClient(MSG);
		}
		else if(ms.getMessage().equals("loginEmployee"))
		{

			Message MSG=new Message("AllowEmployee");
			String[] data = {ms.getID(),ms.getPassword()} ;

			Message msg1 = tryLogIn(data);
			User user1 = (User) msg1.getObject1();
			int permission_check = user1.getPermission();
			if(msg1.getMessage().equals("tryLogin_UserFound") && permission_check == 2 ){
				//parking worker
				MSG.setObject1("success");
				MSG.setObject2(msg1.getObject1()); // return User
				MSG.setObject3(permission_check);
			}else {
				MSG.setObject1("fail");
			}
			client.sendToClient(MSG);
		}
		else if(ms.getMessage().equals("RenewSub")) {
			Boolean flag = false;
			String Subnumber = (String) ms.getSubNum();
			String LicencePlateNum = (String) ms.getLicensePlate();


			// todo when we find the subscriber to renew what do we do ??
			List<PartialSub> partialListDB = getAll(PartialSub.class);
			List<FullSub> fullListDB = getAll(FullSub.class);
			List<MultiSub> multiListDB = getAll(MultiSub.class);
			for (PartialSub partialSub : partialListDB) {
				String ID = partialSub.getPartialSubId();
				String Licence = partialSub.getCarNumber();
				if (Subnumber.equals(ID) && Licence.equals(LicencePlateNum)) {
					// add action here
					flag = true;
					break;
				}
			}
			for (FullSub fullsub : fullListDB) {
				String ID = fullsub.getCustomerId();
				String Licence = fullsub.getCarNumber();
				if (Subnumber.equals(ID) && Licence.equals(LicencePlateNum)) {
					// add action here
					flag = true;
					break;
				}
			}
			for (MultiSub multisub : multiListDB) {
				String ID = multisub.getPartialSubId();
				String Licence = multisub.getCarNumber();
				if (Subnumber.equals(ID) && Licence.equals(LicencePlateNum)) {
					// add action here
					flag = true;
					break;
				}
			}

			// if the client is found inform him that sub is renewed
			if (flag) {
				Message MSG = new Message("SubRenewed");
				MSG.setObject1("success");
				client.sendToClient(MSG);
			} else {
				Message MSG = new Message("SubRenewed");
				MSG.setObject1("fail");
				client.sendToClient(MSG);
			}
		}
		else if(ms.getMessage().equals("ParkingManager_alterPrices")) {
			// adds a price change request to the regional manager's requests list
			//data stored in separate objects
			// occasional price -> object1
			// preOrder price -> object2
			// partTime price -> object3
			// FullSubs price -> object4
			// Multi price -> object5
			PricesClass occasionalPrice = new PricesClass((int)ms.getObject1(),"occasionalPrice");
			PricesClass preOrderPrice = new PricesClass((int)ms.getObject2(),"preOrderPrice");
			PricesClass PartTimePrice = new PricesClass((int)ms.getObject3(),"PartTimePrice");
			PricesClass fullSubPrice = new PricesClass((int)ms.getObject4(),"fullSubPrice");
			PricesClass MultiCarPrice = new PricesClass((int)ms.getObject5(),"MultiCarPrice");

			Vector<PricesClass> prices_request_vector = new Vector<PricesClass>();

			prices_request_vector.add(occasionalPrice);
			prices_request_vector.add(preOrderPrice);
			prices_request_vector.add(PartTimePrice);
			prices_request_vector.add(fullSubPrice);
			prices_request_vector.add(MultiCarPrice);

			//get manger's name of current window to alter accordingly
			ParkingManager Manager =  (ParkingManager) ms.getObject6();

			//make new request to add to the DB
			PricesUpdateRequest new_request = new PricesUpdateRequest(Manager,prices_request_vector,"plz_change");
			try {
			session = getSessionFactory().openSession();
			session.beginTransaction();
			// add new request for the list so the regional manager can see it.
			session.save(new_request);

			session.flush();
			session.getTransaction().commit();


			Message msg2 = new Message("prices update request sent");
			client.sendToClient(msg2);
				} catch (Exception exception) {
					if (session != null) {
					session.getTransaction().rollback();
			}
				Message msg2 = new Message("failed_transaction");
				client.sendToClient(msg2);
				System.err.println("An error occurred, changes have been rolled back.");
				exception.printStackTrace();
				} finally {
				session.close();
			}

		}

		else if(ms.getMessage().equals("ParkingManager_showPrices")) {
			Message MSG=new Message("pricesReturned");
			try{

				session = getSessionFactory().openSession();
				session.beginTransaction();
				CriteriaBuilder builder = session.getCriteriaBuilder();
				CriteriaQuery<ParkingLot> query = builder.createQuery(ParkingLot.class);
				query.from(ParkingLot.class);
				List<ParkingLot> data = session.createQuery(query).getResultList();
				ParkingManager parkingManager = (ParkingManager)ms.getObject1();
				int id_ = parkingManager.getParkingLot().getParking_id();
				List<PricesClass> pricesList = null;

				for (ParkingLot datum : data) {
					if (datum.getParking_id() == id_) {
						pricesList.add(datum.getOccasionalPrice());
						pricesList.add(datum.getPreOrderPrice());
						pricesList.add(datum.getPartTimePrice());
						pricesList.add(datum.getFullSubPrice());
						pricesList.add(datum.getMultiCarPrice());
					}
				}

				MSG.setObject1(pricesList);
				session.flush();
				session.getTransaction().commit();
			} catch (Exception exception) {
			if (session != null) {
				session.getTransaction().rollback();
			}
				MSG.setMessage("failed_transaction");
				System.err.println("An error occurred, changes have been rolled back.");
				exception.printStackTrace();
			} finally {
				client.sendToClient(MSG);
				session.close();
		}

		}

		else if(ms.getMessage().equals("ParkingManager_showStats")) {

			session.getSessionFactory().openSession();

			String hql = "FROM DeletedOrders ";
			String Latehql = "FROM Late ";

			Query query = session.createQuery(hql);
			Query Latequery = session.createQuery(Latehql);

			List<DeletedOrders> DeletedOrdersList = query.getResultList();
			List<Late> LateList = query.getResultList();

			int Deletedmean = 0;
			for (DeletedOrders order : DeletedOrdersList) {
				String date = (String) order.getDeletetime().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
				String today = (String) (LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
				if (date.equals(today)) {
					Deletedmean++;
				}
			}

			int Latemean = 0;
			for (Late order : LateList) {
				String date = (String) order.getDeletetime().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
				String today = (String) (LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
				if (date.equals(today)) {
					Latemean++;
				}
				// todo get all stats from database and then put them in objects 1 to 3 to present them to manager
				Message MSG = new Message("statsReturned");
				MSG.setObject1(Deletedmean);
				MSG.setObject2(Latemean);
				client.sendToClient(MSG);

			}
		}

		else if(ms.getMessage().equals("Complaint")) {
			// the complaint which is a class defined in entities
			// is inside message in object1
			// each complaint contains two fields
			// 1- customer ID
			// 2- the complaint text
			// bruv u gud ? -> no im not gud im god

			Complaints tempComplaint = (Complaints) ms.getObject1();
			Complaint new_complaint = new Complaint();
			new_complaint.setComplaintText(tempComplaint.getComplaintText());
			new_complaint.setCustomerId(tempComplaint.getCustomerId());

			session = getSessionFactory().openSession();
			session.beginTransaction();
			session.save(tempComplaint);
			Message msg2 = new Message("Complaint received successfully!");
			client.sendToClient(msg2);
			session.flush();
			session.getTransaction().commit();
			session.close();

			// useless code to check if things work
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
			OccCustomer customer = new OccCustomer((String) ms.getObject1(),(String)ms.getObject2(),(String)ms.getObject3());
			String temp = (String)ms.getObject4();
			String[] parsed = temp.split(":");
			System.out.println(parsed[0]);
			System.out.println(parsed[1]);
			System.out.println(parsed[2]);

			Time tempTime = new Time(Integer.parseInt(parsed[0]),Integer.parseInt(parsed[1]),Integer.parseInt(parsed[2]));
			customer.setStartTime(tempTime);

			try {
				session = getSessionFactory().openSession();
				session.beginTransaction();
				// add new occasional customer to the db .
				session.save(customer);

				session.flush();
				session.getTransaction().commit();

				Message msg2 = routingOrders(customer,"OccCustomer");
				msg2.setObject1("success");
//				Message msg2 = new Message("prices update request sent");
				client.sendToClient(msg2);
			} catch (Exception exception) {
				if (session != null) {
					session.getTransaction().rollback();
				}
				Message msg2 = new Message("OccCustomer");
				msg2.setObject1("fail");
				client.sendToClient(msg2);
				System.err.println("An error occurred, changes have been rolled back.");
				exception.printStackTrace();
			} finally {
				session.close();
			}

			// return msg with "OcasionalParking" in name object1 a string success/fail



		} else if (ms.getMessage().equals("OneTimeParkingOrder_Submit")){

			//**************************** pre order parking **************************//


			//todo check if in DB
			//return OneTimeParkingOrder_Success
			// or OneTimeParkingOrder_Fail
			// data is contained in a vector inside Object1
			// the data is stored in the following order
			// 0- car number             3- Eta
			// 1- DesiredParking         4- Etd
			// 2- Email                  5- Id nnumber
			Vector<String> fields = (Vector<String>)ms.getObject1();

			// checking fields input if okay add the client, else return failed
			// checked everything except time/date
			//todo check if there is empty space in parking
			if (fieldsChecker_OneTimeParkingOrder(fields)) {
				PreOrder tempParking = new PreOrder(fields.get(5), fields.get(0), fields.get(1), fields.get(2));
				String tempEta = fields.get(3);
				String tempEtd = fields.get(4);
				String[] parsedEta = tempEta.split(":");
				String[] parsedEtd = tempEtd.split(":");
				Date Eta = new Date(Integer.parseInt(parsedEta[0]), Integer.parseInt(parsedEta[1]), Integer.parseInt(parsedEta[2]), Integer.parseInt(parsedEta[3]), 0);
				Date Etd = new Date(Integer.parseInt(parsedEtd[0]), Integer.parseInt(parsedEtd[1]), Integer.parseInt(parsedEtd[2]), Integer.parseInt(parsedEtd[3]), 0);
				tempParking.setEntrance(Eta);
				tempParking.setExit(Etd);




				//todo in parkingEnter return message store "OneTimeParkingOrder" in the title,
				// and the first object fail/success as a string, rest of the info choose when implementing
				Message msg2 = routingOrders(tempParking,"PreOrder");

				client.sendToClient(msg2);

			} else {
				//failed
				Message msg2 = new Message("OneTimeParkingOrder");
				client.sendToClient(msg2);
				msg2.setObject1("fail");
			}

		} else if(ms.getMessage().endsWith("regional")){
			Message msg2 = new Message("return_regional");
			switch (ms.getMessage()) {
				case "accept_price_alter_regional" -> msg2 = price_alter(ms, "accept");
				case "decline_price_alter_regional" -> msg2 = price_alter(ms, "decline");
				case "show_stat1_regional", "stat_Parking3_regional", "stat_Parking2_regional", "stat_Parking1_regional", "show_stat3_regional", "show_stat2_regional" -> msg2 = showStatRegional(ms);
				case "pdf_Parking1_regional", "pdf_Parking3_regional", "pdf_Parking2_regional" -> msg2 = pdfRegional(ms);
				case "show_prices1_regional", "show_prices3_regional", "show_prices2_regional" -> msg2 = showPricesRegional(ms);
				case "alterPrices1_regional", "alterPrices3_regional", "alterPrices2_regional" -> msg2 = alterPricesRegionalReq(ms);
				default -> System.out.println("Simple server regional manager error");
			}


		}
		else if(ms.getMessage().equals("Deactivate Parking Spot"))
		{

			//Deactivate Parking Spot
			//Object #1 - Parking Spot ID
			//object #2 parkingLot

			String ParkingSpotID = (ms.getObject1().toString());
			ParkingLot parkingLot = (ParkingLot) ms.getObject2();
			Message msg12 = ParkingSpotStateUpdate(ParkingSpotID,parkingLot, "Deactivate");
			msg12.setMessage("EmployeeWindow");
			client.sendToClient(msg12);

		}
		else if(ms.getMessage().equals("Activate Parking Spot"))
		{

			//Activate Parking Slot
			//Object #1 - Parking Slot ID
			//object #2 parkingLot

			String ParkingSpotID = (ms.getObject1().toString());
			ParkingLot parkingLot = (ParkingLot) ms.getObject2();
			Message msg12 = ParkingSpotStateUpdate(ParkingSpotID,parkingLot, "Activate");
			msg12.setMessage("EmployeeWindow");
			client.sendToClient(msg12);

		}
		else if(ms.getMessage().equals("System Request"))
		{
			//todo all
			//Check other parking places to send a vehicle to...
			//Object #1 - Parking ID
			//Object #2 - System Command ID / ON OR OFF
			String ParkingID = ms.getObject1().toString();
			String SystemCommand = ms.getObject2().toString();

			Message msg69 = ParkingLotCommand(ParkingID,SystemCommand);

		}
		else if(ms.getMessage().equals("Send To Other Parking"))
		{
			//todo all
			//Check other parking places to send a vehicle to...
			//Object #1 - CarNumber
			//Object #2 - Customer ID
			//Object #3 - Parking ID
			String CarNumber = ms.getObject1().toString();
			String CustomerID = ms.getObject2().toString();
			String ParkingID = ms.getObject3().toString();

			Message msg69 = SendToParking(CustomerID,CarNumber,ParkingID);

		}
		else if(ms.getMessage().equals("Occasion Request"))
		{
			//todo all
			//Check other parking places to send a vehicle to...
			//Object #1 - Parking Slot ID
			//Object #2 - Car ID
			//Object #3 - Occasion ID
			String ParkingSlotID = ms.getObject1().toString();
			String CarNumber = ms.getObject2().toString();
			String OccasionID = ms.getObject3().toString();
		}
		//===================================================================================
		// RegisterNewSubscription Window
		else if(ms.getMessage().equals("Register New Subscriber"))
		{
			//todo all
			//Check other parking places to send a vehicle to...
			//Object #1 - Subscriber Type
			//Object #2 - Customer ID
			//Object #3 - Car Number
			//Object #4 - Starting Date ; YYYY:MM:DD
			//Object #5 - Entrance Hour	; HH:MM
			//Object #6 - Departure Hour ; HH:MM
			//Object #7 - Regular Parking Lot - Parking Slot

			String SubscriberType = ms.getObject1().toString();
			String CustomerID = ms.getObject2().toString();
			String CarNumber = ms.getObject3().toString();
			String StartingDate = ms.getObject4().toString();
			String EntranceHour = ms.getObject5().toString();
			String DepartureHour = ms.getObject6().toString();
			String RegularParkingLot = ms.getObject7().toString();

			//Staring Date Components
			int Year = Integer.parseInt(StartingDate.substring(0,3));
			int Month = Integer.parseInt(StartingDate.substring(5,6));
			int Day = Integer.parseInt(StartingDate.substring(8,9));

			//Entrance Hour Components
			//No Need For Now
			//Departure Hour Components
			//No Need For Now

			if(SubscriberType.equals("Single Monthly Subscription"))
			{
				PartialSub input = new PartialSub(CustomerID,CarNumber);
				Date Temp = new Date(Year,Month,Day);
				input.setStartDate(Temp);
				input.setEntranceHour(EntranceHour);
				input.setDepartureHour(DepartureHour);
			}
			else if(SubscriberType.equals("Multi Monthly Subscription"))
			{
				MultiSub input = new MultiSub();
				Date Temp = new Date(Year,Month,Day);
				input.InsertToList(CustomerID,CarNumber,Temp,EntranceHour,DepartureHour);
			}
			else if(SubscriberType.equals("Fully Subscription"))
			{
				FullSub input = new FullSub(CustomerID,CarNumber);
				Date Temp = new Date(Year,Month,Day);
				input.setStartDate(Temp);
			}

		}
		//===================================================================================
		// Check Status Window
		else if(ms.getMessage().equals("Check Client Spot Status"))
		{
			//todo all
			String CustomerId = ms.getObject1().toString();
			String CarNumber = ms.getObject2().toString();
		}

	}



	/**.....................................................................................................................
.MMMMMMM....MMMMMMM..EEEEEEEEEEEEEE..TTTTTTTTTTTTTTTHHHHH.....HHHHH.....OOOOOOOOOO......DDDDDDDDDDDD.......SSSSSSSSSS.....
.MMMMMMM....MMMMMMM..EEEEEEEEEEEEEE..TTTTTTTTTTTTTTTHHHHH.....HHHHH....OOOOOOOOOOOOO....DDDDDDDDDDDDDD....SSSSSSSSSSSS....
.MMMMMMM....MMMMMMM..EEEEEEEEEEEEEE..TTTTTTTTTTTTTTTHHHHH.....HHHHH...OOOOOOOOOOOOOOO...DDDDDDDDDDDDDD...SSSSSSSSSSSSS....
.MMMMMMMM...MMMMMMM..EEEEE................TTTTT.....HHHHH.....HHHHH..OOOOOOOO.OOOOOOO...DDDDD..DDDDDDDD..SSSSSS.SSSSSSS...
.MMMMMMMM..MMMMMMMM..EEEEE................TTTTT.....HHHHH.....HHHHH..OOOOOO.....OOOOOO..DDDDD.....DDDDD..SSSSS....SSSSS...
.MMMMMMMM..MMMMMMMM..EEEEE................TTTTT.....HHHHH.....HHHHH..OOOOO.......OOOOO..DDDDD.....DDDDDD.SSSSSSS..........
.MMMMMMMM..MMMMMMMM..EEEEE................TTTTT.....HHHHH.....HHHHH.HOOOOO.......OOOOO..DDDDD......DDDDD.SSSSSSSSSS.......
.MMMMMMMMMMMMMMMMMM..EEEEEEEEEEEEEE.......TTTTT.....HHHHHHHHHHHHHHH.HOOOO........OOOOO..DDDDD......DDDDD..SSSSSSSSSSSS....
.MMMMMMMMMMMMMMMMMM..EEEEEEEEEEEEEE.......TTTTT.....HHHHHHHHHHHHHHH.HOOOO........OOOOO..DDDDD......DDDDD...SSSSSSSSSSSS...
.MMMMMMMMMMMMMMMMMM..EEEEEEEEEEEEEE.......TTTTT.....HHHHHHHHHHHHHHH.HOOOO........OOOOO..DDDDD......DDDDD.....SSSSSSSSSS...
.MMMMMMMMMMMMMMMMMM..EEEEE................TTTTT.....HHHHH.....HHHHH.HOOOOO.......OOOOO..DDDDD......DDDDD.........SSSSSS...
.MMMMMMMMMMMMMMMMMM..EEEEE................TTTTT.....HHHHH.....HHHHH..OOOOO.......OOOOO..DDDDD.....DDDDDDDSSSS......SSSSS..
.MMMMM.MMMMMMMMMMMM..EEEEE................TTTTT.....HHHHH.....HHHHH..OOOOOO.....OOOOOO..DDDDD.....DDDDD.DSSSSS....SSSSSS..
.MMMMM.MMMMMM.MMMMM..EEEEE................TTTTT.....HHHHH.....HHHHH..OOOOOOOO.OOOOOOO...DDDDD...DDDDDDD..SSSSSSSSSSSSSS...
.MMMMM.MMMMMM.MMMMM..EEEEEEEEEEEEEEE......TTTTT.....HHHHH.....HHHHH...OOOOOOOOOOOOOOO...DDDDDDDDDDDDDD...SSSSSSSSSSSSSS...
.MMMMM.MMMMMM.MMMMM..EEEEEEEEEEEEEEE......TTTTT.....HHHHH.....HHHHH....OOOOOOOOOOOOO....DDDDDDDDDDDDDD....SSSSSSSSSSSS....
.MMMMM..MMMMM..MMMM..EEEEEEEEEEEEEEE......TTTTT.....HHHHH.....HHHHH.....OOOOOOOOOO......DDDDDDDDDDDD.......SSSSSSSSSS.....
..........................................................................................................................*/

	private void EnterParking(Message msg)
	{
		// I assume name of the park is stored in object 4
		//we have cairables called licenes plate and id in message already
		//assume in object 3 the exit date/time;
        String parkName=(String) msg.getObject4();
		ParkingLot pk=new ParkingLot();
	    session.getSessionFactory().openSession();
		String hql="From ParkingLot ";
		Query query = session.createQuery(hql);
		List<ParkingLot> ParkingsList = query.getResultList();
		for(ParkingLot temp:ParkingsList)
		{
			if(temp.getName().equals(parkName))
			{
				pk=temp;
			}
		}
	    ParkingSpot spot= new ParkingSpot();

		if(!pk.isFull())
		{
			for(int i = 0; i< pk.getSlots_num(); i++)
			{
				if(pk.Spots.get(i).getCurrentState().equals("empty"))
				{
					pk.setOccupied_slots_num(pk.getOccupied_slots_num()+1);
					pk.Spots.get(i).setCurrentState("occupied");
					pk.Spots.get(i).setLicesnes_Plate(msg.getLicensePlate());
					pk.Spots.get(i).setCus_ID(msg.getID());
					pk.Spots.get(i).setExitDate((LocalDateTime) msg.getObject3());
				}
			}
		}
		Collections.sort(pk.getSpots(), new Comparator<ParkingSpot>()
		{
			@Override
			public int compare(ParkingSpot p1, ParkingSpot p2) {
				return p1.getExitDate().compareTo(p2.getExitDate());
			}
		});
		  int i=pk.getOccupied_slots_num();
		for (int depth=0;depth<3;depth++)
		{
			for(int height=0;height<3;height++)
			{
			for (int width=0;width<pk.getWidth();width++,i--)
			 {
				  pk.Spots.get(i).setdepth(depth);
				  pk.Spots.get(i).setwidth(width);
				  pk.Spots.get(i).setheight(height);
				  if(i==0)
				  {
					  break;
				  }
			 }
		  }
		}
		try {
			         		session.saveOrUpdate(pk);

		}
		catch (Exception e)
		{
			session.getTransaction().rollback();
		}
		finally {
			       	 session.close();
		}







	}

	public void ExitParking(Message msg) {

		// I assume name of the park is stored in object 4
		//we have cairables called licenes plate and id in message already
		//assume in object 3 the exit date/time;
		String parkName=(String) msg.getObject4();
		ParkingLot pk=new ParkingLot();
		session.getSessionFactory().openSession();
		String hql="From ParkingLot ";
		Query query = session.createQuery(hql);
		List<ParkingLot> ParkingsList = query.getResultList();
		for(ParkingLot temp:ParkingsList)
		{
			if(temp.getName().equals(parkName))
			{
				pk=temp;
			}
		}
		ParkingSpot spot= new ParkingSpot();


			for(int i = 0; i< pk.getSlots_num(); i++) {
				if (pk.Spots.get(i).getCus_ID().equals(msg.getID())
				&& pk.Spots.get(i).getLicesnes_Plate().equals(msg.getLicensePlate())) {

					pk.setOccupied_slots_num(pk.getOccupied_slots_num() -1);
					pk.Spots.get(i).setCurrentState("empty");
					pk.Spots.get(i).setLicesnes_Plate("");
					pk.Spots.get(i).setCus_ID("");
					pk.Spots.get(i).setExitDate(LocalDateTime.MIN);
				}
			}


		Collections.sort(pk.getSpots(), new Comparator<ParkingSpot>()
		{
			@Override
			public int compare(ParkingSpot p1, ParkingSpot p2) {
				return p1.getExitDate().compareTo(p2.getExitDate());
			}
		});


		int i=pk.getOccupied_slots_num();
		for (int depth=0;depth<3;depth++)
		{
			for(int height=0;height<3;height++)
			{
				for (int width=0;width<pk.getWidth();width++,i--)
				{
					pk.Spots.get(i).setdepth(depth);
					pk.Spots.get(i).setwidth(width);
					pk.Spots.get(i).setheight(height);
					if(i==0)
					{
						break;
					}
				}
			}
		}
		try {
			session.saveOrUpdate(pk);

		}
		catch (Exception e)
		{
			session.getTransaction().rollback();
		}
		finally {
			session.close();
		}


	}



	private Message ParkingSpotStateUpdate(String parkingSpotID, ParkingLot parkingLot, String state) {
		int x = Integer.parseInt(parkingSpotID.split("-")[0]);
		int y = Integer.parseInt(parkingSpotID.split("-")[1]);
		int z = Integer.parseInt(parkingSpotID.split("-")[2]);

		Message msg2 = new Message(""); // TODO
		try {
			session = getSessionFactory().openSession();
			session.beginTransaction();

			CriteriaBuilder builder1 = session.getCriteriaBuilder();
			CriteriaQuery<ParkingSpot> query1 = builder1.createQuery(ParkingSpot.class);

			query1.from(ParkingSpot.class);
			List<ParkingSpot> parkingSpots = session.createQuery(query1).getResultList();
			ParkingSpot ParkingSpot = new ParkingSpot(x, y, z, state, parkingLot);

			session.saveOrUpdate(ParkingSpot);

			session.flush();
			session.getTransaction().commit();
		} catch (Exception exception) {
			if (session != null) {
				session.getTransaction().rollback();
			}
			 msg2 = new Message("failed_transaction");

			System.err.println("An error occurred, changes have been rolled back.");
			exception.printStackTrace();
		} finally {
			assert session != null;
			session.close();
		}

		return msg2;
	}

	private Message SendToParking(String CustomerID, String CarNumber , String ParkingID)
	{
		try {
			session = getSessionFactory().openSession();
			session.beginTransaction();

			CriteriaBuilder builder1 = session.getCriteriaBuilder();
			CriteriaQuery<ParkingLot> query1 = builder1.createQuery(ParkingLot.class);
			query1.from(ParkingLot.class);
			List<ParkingLot> ParkingLots = session.createQuery(query1).getResultList();
			ParkingLot ParkingLot = new ParkingLot();
			ParkingLot.setParking_id(Integer.parseInt(ParkingID));
			//ParkingLot.setStatus(SystemCommand);	//NEED TO DO/TALK
			session.save(ParkingLot);

			session.flush();
			session.getTransaction().commit();
		}
		catch (Exception exception) {
			if (session != null) {
				session.getTransaction().rollback();
			}
			Message msg2 = new Message("failed_transaction");

			System.err.println("An error occurred, changes have been rolled back.");
			exception.printStackTrace();
		} finally {
			assert session != null;
			session.close();
		}
		return null;
	}



	private Message routingOrders(Object ParkingEntryOrder, String type) {
		//todo
		//this function takes an order such as preorder or occasional customer etc.., and the type in "type"
		// so we can convert it to the given type and add it. it returns a Message and it's fields are set according to
		// the caller.
		// it should every order to function that saves it/or let's the customer enter the parking

		if (type.equals("PreOrder")){
			PreOrder newOrder = (PreOrder) ParkingEntryOrder;

		}else{
			// here type is "OccCustomer"
		}

		return null;

	}


	private void DeleteParkingOrder(Message msg, ConnectionToClient client){
		//todo

	}

	private Message ParkingLotCommand(String ParkingID, String SystemCommand) {
		//todo
		try {
			session = getSessionFactory().openSession();
			session.beginTransaction();

			CriteriaBuilder builder1 = session.getCriteriaBuilder();
			CriteriaQuery<ParkingLot> query1 = builder1.createQuery(ParkingLot.class);
			query1.from(ParkingLot.class);
			List<ParkingLot> ParkingLots = session.createQuery(query1).getResultList();
			ParkingLot Result = new ParkingLot();
			int DeltaSpots = 0;
			for(int i = 0 ; i < ParkingLots.size(); i++)
			{
				/*if(ParkingLots.get(i).getSpots() - ParkingLots.get(i).getEmptySpots() > DeltaSpots)
				{
					DeltaSpots = ParkingLots.get(i).getSpots() - ParkingLots.get(i).getEmptySpots();
					Result = ParkingLots.get(i);
				}*/
			}

			//ParkingLot.setStatus(SystemCommand);	//NEED TO DO/TALK
			session.save(Result);

			session.flush();
			session.getTransaction().commit();
		}
		catch (Exception exception) {
			if (session != null) {
				session.getTransaction().rollback();
			}
			Message msg2 = new Message("failed_transaction");

			System.err.println("An error occurred, changes have been rolled back.");
			exception.printStackTrace();
		}
		finally {
			assert session != null;
			session.close();
		}

		return null;
	}

	private Message alterPricesRegionalReq(Message ms) {
		//data stored in seperate objects
		// ocasional price -> object1
		// preOrder price -> object2
		// partTime price -> object3
		// FullSubs price -> object4
		// Multi price -> object5
		PricesClass occasionalPrice = new PricesClass((int)ms.getObject1(),"occasionalPrice");
		PricesClass preOrderPrice = new PricesClass((int)ms.getObject2(),"preOrderPrice");
		PricesClass PartTimePrice = new PricesClass((int)ms.getObject3(),"PartTimePrice");
		PricesClass fullSubPrice = new PricesClass((int)ms.getObject4(),"fullSubPrice");
		PricesClass MultiCarPrice = new PricesClass((int)ms.getObject5(),"MultiCarPrice");

		Vector<PricesClass> prices_request_vector = new Vector<PricesClass>();

		prices_request_vector.add(occasionalPrice);
		prices_request_vector.add(preOrderPrice);
		prices_request_vector.add(PartTimePrice);
		prices_request_vector.add(fullSubPrice);
		prices_request_vector.add(MultiCarPrice);

		//get manger's name of current window to alter accordingly



		//make new request to add to the DB

		Message msg2 = new Message("prices update request sent");

		try {

			session = getSessionFactory().openSession();
			session.beginTransaction();

			CriteriaBuilder builder1 = session.getCriteriaBuilder();
			CriteriaQuery<ParkingLot> query1 = builder1.createQuery(ParkingLot.class);
			query1.from(ParkingLot.class);
			List<ParkingLot> parkingLots = session.createQuery(query1).getResultList();
			ParkingManager Manager = new ParkingManager();

			if(ms.getMessage().equals("alterPrices1_regional")){
				Manager = parkingLots.get(0).getParkingManager();
			}else if(ms.getMessage().equals("alterPrices2_regional")){
				Manager = parkingLots.get(1).getParkingManager();
			}else if(ms.getMessage().equals("alterPrices3_regional")){
				Manager = parkingLots.get(2).getParkingManager();
			} else {
				System.out.println("MO3AAAAAAAAAAAAAAAAAAAAAAD_BLA HBL");
			}

			PricesUpdateRequest new_request = new PricesUpdateRequest(Manager,prices_request_vector,"plz_change");
			// add new request for the list so the regional manager can see it.
			session.save(new_request);

			session.flush();
			session.getTransaction().commit();


			msg2 = new Message("prices update request sent regional");

		} catch (Exception exception) {
			if (session != null) {
				session.getTransaction().rollback();
			}
			 msg2 = new Message("failed_transaction");

			System.err.println("An error occurred, changes have been rolled back.");
			exception.printStackTrace();
		} finally {
			assert session != null;
			session.close();
		}

		return msg2;
	}



	private Message showPricesRegional(Message ms) {
		Message MSG = new Message("show_prices_regional");
		// index is parking manager getter helper
		int index = 0;
		if(ms.getMessage().equals("show_prices1_regional")){
			MSG.setObject1("1");
			index = 0;
		}else if(ms.getMessage().equals("show_prices2_regional")){
			MSG.setObject1("2");
			index = 1;
		}else  if(ms.getMessage().equals("show_prices3_regional")){
			MSG.setObject1("3");
			index = 2;
		}

		try{

			session = getSessionFactory().openSession();
			session.beginTransaction();
			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<ParkingLot> query = builder.createQuery(ParkingLot.class);
			query.from(ParkingLot.class);
			List<ParkingLot> data = session.createQuery(query).getResultList();
			ParkingManager parkingManager = data.get(index).getParkingManager();
			int id_ = parkingManager.getParkingLot().getParking_id();
			List<PricesClass> pricesList = null;

			for (ParkingLot datum : data) {
				if (datum.getParking_id() == id_) {

					pricesList.add(datum.getOccasionalPrice());
					pricesList.add(datum.getPreOrderPrice());
					pricesList.add(datum.getPartTimePrice());
					pricesList.add(datum.getFullSubPrice());
					pricesList.add(datum.getMultiCarPrice());
				}
			}

			MSG.setObject1(pricesList);
			session.flush();
			session.getTransaction().commit();
		} catch (Exception exception) {
			if (session != null) {
				session.getTransaction().rollback();
			}
			MSG.setMessage("failed_transaction");
			System.err.println("An error occurred, changes have been rolled back.");
			exception.printStackTrace();
		} finally {

			assert session != null;
			session.close();
		}
		return MSG;
	}

	private Message showStatRegional(Message ms) {
		//todo
		return null;
	}

	private Message pdfRegional(Message ms){
			try {
				// Create a new Document
				Document document = new Document();
				// Create a new PdfWriter
				PdfWriter.getInstance(document, new FileOutputStream("Parking.pdf"));
				// Open the Document
				document.open();
				// Add content to the Document
				for (int i = 0; i < 3; i++) {
					document.add(new Paragraph("Hello, world!"));
				}
				// Close the Document
				document.close();
			} catch (FileNotFoundException | DocumentException e) {
				e.printStackTrace();
			}

			return null;
		}


	private Message price_alter(Message ms, String res) {
		//changes price with a given update price request
		Message msg1 = new Message("requests_list_update_regional");
		Message ms2 = new Message("price_decline_regional");

		if(res.equals("accept")){

			try{
			session = getSessionFactory().openSession();
			session.beginTransaction();

			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<PricesUpdateRequest> query = builder.createQuery(PricesUpdateRequest.class);
			query.from(PricesUpdateRequest.class);
			List<PricesUpdateRequest> requestList = session.createQuery(query).getResultList();

			int request_num = (Integer) ms.getObject1();
			PricesUpdateRequest temp = new PricesUpdateRequest();
			for(PricesUpdateRequest ptr : requestList){
				if(ptr.getId() == request_num){
					temp=ptr;
				}
			}



			CriteriaBuilder builder1 = session.getCriteriaBuilder();
			CriteriaQuery<ParkingLot> query1 = builder1.createQuery(ParkingLot.class);
			query1.from(ParkingLot.class);
			List<ParkingLot> parkingLots = session.createQuery(query1).getResultList();

			for(ParkingLot parkingLot : parkingLots){
				if(parkingLot.getParking_id() == temp.getParkingManager().getParkingLot().getParking_id()){
					parkingLot.setOccasionalPrice(temp.getPricesClassVector().get(0));
					parkingLot.setPreOrderPrice(temp.getPricesClassVector().get(1));
					parkingLot.setPartTimePrice(temp.getPricesClassVector().get(2));
					parkingLot.setFullSubPrice(temp.getPricesClassVector().get(3));
					parkingLot.setMultiCarPrice(temp.getPricesClassVector().get(4));
				}
			}
			session.update(parkingLots);
			session.flush();
			session.getTransaction().commit();

		} catch (Exception exception) {
			if (session != null) {
				session.getTransaction().rollback();
			}

			System.err.println("An error occurred, changes have been rolled back.");
			exception.printStackTrace();
			} finally {

			session.close();
		}

		}else{

			return ms2;
		}
		return msg1;

	}

	boolean fieldsChecker_OneTimeParkingOrder(Vector<String> fields){
		//data is contained in a vector inside Object1
		// the data is stored in the following order
		// 0- car number             3- Eta
		// 1- DesiredParking         4- Etd
		// 2- Email                  5- Id number

		String regex_multi_number = "\\d+";
		Pattern p = Pattern.compile(regex_multi_number);
		Matcher m = p.matcher(fields.get(0));

		if(!m.matches()){
			//0- car number field error
			return false;
		}

		String regex_one_number = "\\d";
		Pattern q = Pattern.compile(regex_multi_number);
		Matcher s = q.matcher(fields.get(1));

		// 3 is parkings number update if changed !!!!!!!!!
		if(!s.matches() || s.matches() && (Integer.parseInt(fields.get(1)) > 3 || Integer.parseInt(fields.get(1)) < 1)){
			// 1- parking number error
			return false;
		}


		String regex_email = ".+@.+"; // email should start with a char and contain a @ after it and then more chars
		Pattern pp = Pattern.compile(regex_multi_number);
		Matcher mail = pp.matcher(fields.get(2));

		if(!mail.matches()){
			//2- email error
			return false;
		}



		Matcher mma = p.matcher(fields.get(5));

		if(!mma.matches()){
			//5- id number wrong
			return false;
		}

		return true;
	}


	private static Message tryLogIn(String[] data) {
		/** checks login pw and username in db with two strings given
		*returns the user in object1 in message
		* and in the name of the message the result
		* you can check what's the users type according to his permission lvl in field permission in User
		* 0 is regional manager, 1 is a branch manager, 2 parking worker.
		 * if user logged in his status set to active in field connected*/


		String userName = data[0];
		String password = data[1];
		Message msg = new Message("LoginTry");
		SessionFactory sessionFactory = getSessionFactory();
		session = sessionFactory.openSession();
		String sqlQ = "FROM User U WHERE U.userName = :user_name";
		Query query = session.createQuery(sqlQ);
		query.setParameter("user_name", userName);
		List<User> list = query.list();
		if (!list.isEmpty()) {
			User user = list.get(0);
			if (user.checkPassword(password)) {
				if (user.getConnected()) msg.setMessage("tryLogin_UserAlreadyConnected");
				else {
					user.setConnected(true);
					System.out.println(user.getFirstName() + " " + user.getLastName());
					try {
						session.beginTransaction();
						session.update(user);
						session.getTransaction().commit();
						msg.setObject1(user);
						msg.setMessage("tryLogin_UserFound");
						System.out.println("tryLogin_UserFound");
					} catch (HibernateException e) {
						if (session != null)
							session.getTransaction().rollback();
						e.printStackTrace();
						msg.setMessage("tryLogin_User_UnknownLogInError");
					}
				}
			} else msg.setMessage("tryLogin_UserNotFound");

		} else msg.setMessage("tryLogin_UserNotFound");


		return msg;
	}


	private static Message tryLogOut(User user) {

		Message msg = new Message("");
		try {
			user.setConnected(false);
			SessionFactory sessionFactory = getSessionFactory();
			session = sessionFactory.openSession();
			session.beginTransaction();
			session.update(user);
			session.getTransaction().commit();
			msg.setMessage("tryLogOut_LoggedOut");
		} catch (HibernateException e) {
			e.printStackTrace();
			msg.setMessage("tryLogOut_UnknownLogOutError");
		}

		return msg;

	}



	public static void addRegionalManager() {
		try {
			Vector<PricesUpdateRequest> prices_update_req = new Vector<PricesUpdateRequest>();
			RegionalManager regionalManager = new RegionalManager("Sliman1","0123456789","Sliman","Jammal",0,1,null,prices_update_req);
			SessionFactory sessionFactory = getSessionFactory();
			session = sessionFactory.openSession();
			session.beginTransaction();
			session.save(regionalManager);
			session.getTransaction().commit();
		} catch (HibernateException e) {
			e.printStackTrace();

		}
	}

	public static void addParkingManagers() {
		try {
			//Parking lot for each manager is updated later
			Vector<PricesUpdateRequest> prices_update_req1 = new Vector<PricesUpdateRequest>();
			User MANGER_1 = new ParkingManager("MO_EID","PASS","MOHAMMED","EID",1,null,prices_update_req1);
			SessionFactory sessionFactory = getSessionFactory();
			session = sessionFactory.openSession();
			session.beginTransaction();
			session.save(MANGER_1);
			Vector<PricesUpdateRequest> prices_update_req2 = new Vector<PricesUpdateRequest>();
			User MANGER_2 = new ParkingManager("MO_SAAED","PASS2","MOHAMMED","KODSIE",1,null,prices_update_req2);
			session.save(MANGER_2);
			Vector<PricesUpdateRequest> prices_update_req3 = new Vector<PricesUpdateRequest>();
			User MANGER_3 = new ParkingManager("BASHAR","PASS3","BASHAR","BASHOTY",1,null,prices_update_req3);

			session.save(MANGER_3);
			session.getTransaction().commit();
		} catch (HibernateException e) {
			e.printStackTrace();
		}
	}



	public static void addParkingWorkers( ) {
		// 3 parking workers 1 for each parking
		int index = 3;
		//assigned parking lot is added later
		try {
			for(int i =0 ; i < index; i++) {
				User ParkingWorker = new ParkingWorker("WORKER"+Integer.toString(index),"pass"+Integer.toString(index),"worker"+Integer.toString(index),"fam"+Integer.toString(index),2,null);
				SessionFactory sessionFactory = getSessionFactory();
				session = sessionFactory.openSession();
				session.beginTransaction();
				session.save(ParkingWorker);
				session.getTransaction().commit();
				 }
		} catch (HibernateException e) {
			e.printStackTrace();
		}
	}


	public static void Add_Parking_Lots() {
		try {
			/** adds three parking lots to db **/
			//todo needed time zone addition
			//todo add each parking lot to a manager and a worker and vise versa
			System.out.println("Parking Lot1");
			//Getting the default zone id
			ZoneId defaultZoneId = ZoneId.systemDefault();


			ParkingLot parkingLot1 = new ParkingLot("German_Colony",4,4,false);
			session.save(parkingLot1);
			session.flush();
//			Date d = new Date(2021 - 1900, 7, 11);
//			instant = d.toInstant();
//			LocalDate localD1 = instant.atZone(defaultZoneId).toLocalDate();

			//___________________________________________________________________________________________
			System.out.println("Parking Lot2");
			ParkingLot parkingLot2 = new ParkingLot("Hanmal",4,5,false);
			session.save(parkingLot1);
			session.flush();
//			Date d = new Date(2021 - 1900, 7, 11);
//			instant = d.toInstant();
//			LocalDate localD1 = instant.atZone(defaultZoneId).toLocalDate();
			//________________________________________________________________________________________________________________
			System.out.println("Parking Lot2");

			ParkingLot parkingLot3 = new ParkingLot("Bat-Galim",4,5,false);
			session.save(parkingLot1);
			session.flush();
//			Date d = new Date(2021 - 1900, 7, 11);
//			instant = d.toInstant();
//			LocalDate localD1 = instant.atZone(defaultZoneId).toLocalDate();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}



}
