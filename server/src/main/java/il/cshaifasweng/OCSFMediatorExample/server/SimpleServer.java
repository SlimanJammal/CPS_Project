package il.cshaifasweng.OCSFMediatorExample.server;

import il.cshaifasweng.OCSFMediatorExample.entities.*;
import il.cshaifasweng.OCSFMediatorExample.server.ocsf.AbstractServer;
import il.cshaifasweng.OCSFMediatorExample.server.ocsf.ConnectionToClient;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.io.IOException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SimpleServer extends AbstractServer {
	public static Session session;
	private static List<ParkingLot> ParkingLots;
	private static List<Employee> employees ;
	private static List<ParkingManager> parkingManagers;
	public static ArrayList<FullSub> fullSubs=new ArrayList<FullSub>();
	public static ArrayList<MultiSub> multiSubs=new ArrayList<MultiSub>();
	private static ArrayList<PreOrder> preOrders= new ArrayList<PreOrder>();
	private static ArrayList<PartialSub> partialSubs = new ArrayList<PartialSub>();
	private static ArrayList<PricesClass> Prices= new ArrayList<PricesClass>();



	public SimpleServer(int port) {
		super(port);
	}



//
//
//	public void initSesssion() {
//		session = getSessionFactory().openSession();
//		try {
//			session.getTransaction().begin();
//
//
////			session.save();
//			session.flush();
//			session.getTransaction().commit();
//
//		} catch (Exception e) {
//			if (session != null) {
//				session.getTransaction().rollback();
//			}
//		}
//
//
//	}

	private static SessionFactory getSessionFactory() throws HibernateException {
		Configuration configuration = new Configuration();
		configuration.addAnnotatedClass(Employee.class);
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
		configuration.addAnnotatedClass(Parks.class);
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
		else if(ms.getMessage().equals("ParkingManager_alterPrices"))
		{
			// adds a price change request to the regional manager's requests list
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
		else if(ms.getMessage().equals("ParkingManager_showPrices"))
		{
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
		else if(ms.getMessage().equals("ParkingManager_showStats"))
		{

			// todo get all stats from database and then put them in objects 1 to 3 to present them to manager
			Message MSG=new Message("statsReturned");
			client.sendToClient(MSG);
			// do other things
		}else if(ms.getMessage().equals("Complaint"))
		{
			// the complaint which is a class defined in entities
			// is inside message in object1
			// each complaint contains two fields
			// 1- customer ID
			// 2- the complaint text
			// bruv u gud ?

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


			//todo return msg woth "OcasionalParking" in name object1 a string success/fail
			Message msg2 = ParkingEnter(customer,"OccCustomer");

			client.sendToClient(msg2);

			//todo figure out how to deal with time(storing and calculating)




			// do other things
		} else if (ms.getMessage().equals("OneTimeParkingOrder_Submit")){
			//todo check if in DB
			// return OneTimeParkingOrder_Success
			// or OneTimeParkingOrder_Fail
			//data is contained in a vector inside Object1
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
				Message msg2 = ParkingEnter(tempParking,"PreOrder");

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

	}

	private Message ParkingEnter(Object ParkingEntryOrder, String type) {
		//todo
		// this function takes an order such as preorder or occasional customer etc.., and the type in "type"
		// so we can convert it to the given type and add it. it returns a Message and it's fields are set according to
		// the caller.

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
		return null;
	}

	private Message pdfRegional(Message ms) {
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





}
