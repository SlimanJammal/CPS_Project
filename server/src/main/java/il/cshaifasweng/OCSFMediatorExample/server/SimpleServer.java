package il.cshaifasweng.OCSFMediatorExample.server;


import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import il.cshaifasweng.OCSFMediatorExample.client.DataSingleton;
import il.cshaifasweng.OCSFMediatorExample.entities.*;
import il.cshaifasweng.OCSFMediatorExample.server.ocsf.AbstractServer;
import il.cshaifasweng.OCSFMediatorExample.server.ocsf.ConnectionToClient;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.Date;

public class SimpleServer extends AbstractServer {


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
		configuration.addAnnotatedClass(CustomerServiceWorker.class);

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


		} else if(ms.getMessage().equals("CheckReservation")){
			Message msg1 = new Message("CheckReservation");
			try {
				System.out.println("1111111111");

				SessionFactory sessionFactory = getSessionFactory();
				session = sessionFactory.openSession();
				session.beginTransaction();


				CriteriaBuilder builder11 = session.getCriteriaBuilder();
				CriteriaQuery<PreOrder> query11 = builder11.createQuery(PreOrder.class);
				query11.from(PreOrder.class);
				List<PreOrder> preOrders = session.createQuery(query11).getResultList();


//				System.out.println("222222222222");
				String Id = (String) ms.getObject1();
				String LicensePlate = (String) ms.getObject2();

				Vector<PreOrder> preOrdersList = new Vector<>();

//				System.out.println("3333333333333333");
				for (PreOrder order : preOrders) {
//					System.out.println("3.5");
//					System.out.println(order.getPreOrderId());
//					System.out.println(order.getCarNumber());
					if (order.getPreOrderId().equals(Id) && order.getCarNumber().equals(LicensePlate)) {
//						System.out.println("aaaaa");
						preOrdersList.add(order);
					}
				}

//				System.out.println("44444444444");
				session.getTransaction().commit();
				session.close();
				msg1.setObject1(preOrdersList);


			}catch (Exception R){

				R.printStackTrace();

			}


			try{
				client.sendToClient(msg1);
			}catch (Exception e){
				e.printStackTrace();
			}



		}else if(ms.getMessage().equals("CancelReservation")){

			SessionFactory sessionFactory = getSessionFactory();
			session = sessionFactory.openSession();
			session.beginTransaction();

			CriteriaBuilder builder11 = session.getCriteriaBuilder();
			CriteriaQuery<PreOrder> query11 = builder11.createQuery(PreOrder.class);
			query11.from(PreOrder.class);
			List<PreOrder> preOrders = session.createQuery(query11).getResultList();

			PreOrder selectedOrder = (PreOrder) ms.getObject1();

			for(PreOrder order : preOrders){
				if(order.getId_() == selectedOrder.getId_()){
					try{
						ms.setMessage("CheckReservation");
						ms.setObject1("cancel");
						ms.setObject2("order canceled successfully!");
						session.remove(order);
						try{
							client.sendToClient(ms);
						}catch (Exception ex){
							ex.printStackTrace();
						}

					} catch (Exception e) {
						ms.setObject1("cancel");
						ms.setObject2("order cancellation was unsuccessfull!");
						e.printStackTrace();
						try{
							client.sendToClient(ms);
						}catch (Exception ex){
							ex.printStackTrace();
						}

					}

				}
			}
			session.getTransaction().commit();
			session.close();
			//todo retun message and whatever
		}else if(ms.getMessage().equals("RegionalManager_ShowPriceRequests")){
			List<PricesUpdateRequest> data21 = new ArrayList<>();
			try {
				SessionFactory sessionFactory = getSessionFactory();
				session = sessionFactory.openSession();
				session.beginTransaction();
				CriteriaBuilder builder = session.getCriteriaBuilder();
				CriteriaQuery<PricesUpdateRequest> query = builder.createQuery(PricesUpdateRequest.class);
				query.from(PricesUpdateRequest.class);
				 data21 = session.createQuery(query).getResultList();
//				Message MSG = new Message("requests_list_update_regional");
				//the needed list is here
//				MSG.setObject1(data21);


				session.flush();
				session.getTransaction().commit();
			}catch (Exception exp)
			{
				exp.printStackTrace();
				System.out.println("transaction failed 1");
				if(session!= null)
				session.getTransaction().rollback();
				else
					System.out.println("transaction failed 1 and session is null");

			}
			finally {

				assert session != null;
				session.close();

				if(session == null)
					System.out.println("session close failed - session in null!");
				System.out.println("show request in server");

				for (PricesUpdateRequest pricesUpdateRequest : data21) {

					System.out.println(pricesUpdateRequest.getRequest());

				}
				Message msag = new Message("req_regional");

				System.out.println("show r22equest in server");
				try {
					msag.setObject1(data21);
					client.sendToClient(msag);
				} catch (Exception ex){
					System.out.println("sending to client failed");
					ex.printStackTrace();
				}

				System.out.println("show r222equest in server");
			}





		}
		else if (ms.getMessage().equals("cancelOrder")) {
            Message cancelingmsg=(Message) msg;
			SessionFactory sessionFactory = getSessionFactory();
			session = sessionFactory.openSession();
			session.beginTransaction();


			CriteriaBuilder builder11 = session.getCriteriaBuilder();
			CriteriaQuery<PreOrder> query11 = builder11.createQuery(PreOrder.class);
			query11.from(PreOrder.class);
			List<PreOrder> preOrders = session.createQuery(query11).getResultList();


			PreOrder refund=new PreOrder();


			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<ParkingLot> query1 = builder.createQuery(ParkingLot.class);
			query1.from(ParkingLot.class);
			List<ParkingLot> parkingLots = session.createQuery(query1).getResultList();
			System.out.println("canceling pre order 1");


			for(PreOrder preOrder : preOrders)
			{
				if(     preOrder.getCarNumber().equals((String)cancelingmsg.getObject1())
						&& preOrder.getEmail_().equals((String)cancelingmsg.getObject3())
						&& preOrder.getPreOrderId().equals((String) cancelingmsg.getObject6())
					)
				{
					try {

						for(ParkingLot parkingLot:parkingLots){
							if(preOrder.getParking_lot_id() == parkingLot.getParking_id()){
								parkingLot.setNumber_of_canceled_preorders(parkingLot.getNumber_of_canceled_preorders()+1);
								session.update(parkingLot);
							}
						}

						System.out.println("canceling pre order 2");
						refund=preOrder;
						session.delete(preOrder);
						session.flush();
						session.getTransaction().commit();
						System.out.println("canceling pre order 3");
					}
					catch (Exception exp)
					{
						exp.printStackTrace();
						if(session != null)
							session.getTransaction().rollback();
					}
					finally {
						session.close();
					}

				}
			}
			System.out.println("canceling pre order refund");

			//refund
			LocalDate order_date = refund.getEntranceDate();
			LocalTime order_time = refund.getEntranceTime();
			Message msg333 = new Message("OneTimeParkingOrder");
			msg333.setObject1("no refund");
			if(order_date.isBefore(LocalDate.now())) {
				if(order_time.isBefore(LocalTime.now())) {
					LocalTime diff =  order_time.minusHours(LocalTime.now().getHour());
					long diif_ = diff.getHour();
					if (diif_ < 1 ) {
						System.out.println("refund 10%");
						msg333.setObject1("refund 10%");
					} else if (diif_ < 3) {
						System.out.println("refund 50%");
						msg333.setObject1("refund 50%");
					} else {
						System.out.println("refund 90%");
						msg333.setObject1("refund 90%");
					}
				}
			}
			try {
				System.out.println("canceling pre order going back");
				client.sendToClient(msg333);

			}catch (Exception e){
				e.printStackTrace();
			}

		} else if (ms.getMessage().startsWith("loginManager")) {
				Message MSG=new Message("AllowManager_KIOSK");
				if(ms.getMessage().endsWith("KIOSK")) {
					MSG = new Message("AllowManager_KIOSK");
				} else {
					MSG = new Message("AllowManager_WEBSITE");
			}

				String[] data = {ms.getID(),ms.getPassword()} ;
				//todo remove this is debugging amsdkasml
				logout_by_string_debug_heleper(data);
				//
				System.out.println("logingMANGER IS IN SERVER");
				int permission_check = 7;
				Message msg1 = tryLogIn(data);
				User user1 = (User) msg1.getObject1();
				if(msg1.getMessage().equals("tryLogin_UserFound")){
					permission_check = user1.getPermission();
				}
				System.out.println(msg1.getMessage());
				System.out.println(permission_check);
				if(msg1.getMessage().equals("tryLogin_UserFound") && permission_check == 0 ){
					//regional
					MSG.setObject1("success");
					MSG.setObject2(msg1.getObject1()); // return User
					MSG.setObject3(permission_check);
				} else if(msg1.getMessage().equals("tryLogin_UserFound") && permission_check == 1 ){
					//regular manager
					MSG.setObject1("success");
					MSG.setObject2(msg1.getObject1()); // return User
					MSG.setObject3(permission_check);

					session = getSessionFactory().openSession();
					session.beginTransaction();

					CriteriaBuilder builder = session.getCriteriaBuilder();
					CriteriaQuery<PricesUpdateRequest> query = builder.createQuery(PricesUpdateRequest.class);
					query.from(PricesUpdateRequest.class);
					List<PricesUpdateRequest> data21 = session.createQuery(query).getResultList();
					MSG.setObject4(data21);
					session.getTransaction().commit();
					session.close();

				}else {

					MSG.setObject1("fail");
					MSG.setObject2(msg1.getObject1()); // return User
					MSG.setObject3(permission_check);
				}

				client.sendToClient(MSG);


		}else if (ms.getMessage().equals("EmployeeLogout")){
			ParkingWorker worker = (ParkingWorker) ms.getObject1();
			Message returnMsg = tryLogOut(worker);
			//tryLogOut_LoggedOut
			if(returnMsg.getMessage().equals("tryLogOut_LoggedOut")){
				Message retMsg = new Message("EmployeeLogout_success");
				retMsg.setObject1(ms.getObject2());
				client.sendToClient(retMsg);
			}

		}
		else if(ms.getMessage().equals("ParkingManager_showStats"))
		{

			try{
			SessionFactory sessionFactory = getSessionFactory();
			session = sessionFactory.openSession();
			session.beginTransaction();

			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<ParkingLot> query = builder.createQuery(ParkingLot.class);
			query.from(ParkingLot.class);
			List<ParkingLot> parkingLots = session.createQuery(query).getResultList();

			Integer manager_id = (Integer) ms.getObject1();
			int late = 0;
			int canceled =0;
			int done = 0;

			for(ParkingLot parkingLot:parkingLots){
				if(parkingLot.getParkingManager().getid() == manager_id ){
					late = parkingLot.getNumber_of_late_preorders();
					canceled = parkingLot.getNumber_of_canceled_preorders();
					done = parkingLot.getNumber_of_done_preorders();
				}
			}




			Message MSG=new Message("statsReturned");
			MSG.setObject1(canceled);
			MSG.setObject2(late);
			MSG.setObject3(done);
			client.sendToClient(MSG);

			session.getTransaction().commit();
			}catch (Exception e){
				e.printStackTrace();
			}

			session.close();


		}
		else if(ms.getMessage().startsWith("loginEmployee"))
		{
			Message MSG=new Message("AllowEmployeeKiosk");
			if(ms.getMessage().endsWith("_CPS")){
				MSG = new Message("AllowEmployeeCPS");
		}

			int permission_check = 7;
			String[] data = {ms.getID(),ms.getPassword()} ;
			// todo remove later when logout is fixed
			logout_by_string_debug_heleper(data);
			Message msg1 = tryLogIn(data);
			User user1 = (User) msg1.getObject1();
			if(msg1.getMessage().equals("tryLogin_UserFound")){
				 permission_check = user1.getPermission();
			}
			if(msg1.getMessage().equals("tryLogin_UserFound") && (permission_check == 2 || permission_check == 3) ){
				//parking worker
				MSG.setObject1("success");
				MSG.setObject2(msg1.getObject1()); // return User
				MSG.setObject3(permission_check);
			} else{
				MSG.setObject1("fail");
			}

			System.out.println("before sending to client ");
			client.sendToClient(MSG);
		}
		else if(ms.getMessage().startsWith("RenewSub")) {
			System.out.println("renew sub server start.........");
			session = getSessionFactory().openSession();
			session.beginTransaction();


			Boolean flag = false;
			String Subnumber = (String) ms.getSubNum();
			String LicencePlateNum = (String) ms.getLicensePlate();


			System.out.println(ms.getMessage());
			// todo when we find the subscriber to renew what do we do ??
			Message msg2 = new Message("SubRenewed");
			List<PartialSub> partialListDB = getAll(PartialSub.class);
			List<FullSub> fullListDB = getAll(FullSub.class);
			List<MultiSub> multiListDB = getAll(MultiSub.class);
			for (PartialSub partialSub : partialListDB) {
				String ID = partialSub.getCustomerId();
				String Licence = partialSub.getCarNumber();
				if (Subnumber.equals(ID) && Licence.equals(LicencePlateNum)) {
					try {

						partialSub.updateEndDate();
						session.saveOrUpdate(partialSub);
						session.flush();
						session.getTransaction().commit();
						msg2.setMessage("SubRenewed_website");
						System.out.println(ms.getMessage());
						if(ms.getMessage().equals("RenewSub_kiosk")) {
							msg2.setMessage("SubRenewed_kiosk");
						}
//						Message msg2 = new Message("SubRenewed");

					} catch (Exception exception) {
						if (session != null) {
							session.getTransaction().rollback();
						}
						client.sendToClient(msg2);
						exception.printStackTrace();
					} finally {
						session.close();
						client.sendToClient(msg2);
					}
					flag = true;
					break;
				}
			}
			for (FullSub fullsub : fullListDB) {
				String ID = fullsub.getCustomerId();
				String Licence = fullsub.getCarNumber();
				if (Subnumber.equals(ID) && Licence.equals(LicencePlateNum)) {
					try {

						fullsub.updateEndDate();
						session.saveOrUpdate(fullsub);
						session.flush();
						session.getTransaction().commit();
						msg2.setMessage("SubRenewed_website");
						if(ms.getMessage().equals("SubRenewed_kiosk")) {
							msg2.setMessage("SubRenewed_kiosk");
						}
						msg2.setObject1("success");
						client.sendToClient(msg2);
					} catch (Exception exception) {
						if (session != null) {
							session.getTransaction().rollback();
						}
						msg2.setMessage("SubRenewed_website");
						System.out.println(ms.getMessage());
						if(ms.getMessage().equals("SubRenewed_kiosk")) {
							msg2.setMessage("SubRenewed_kiosk");
						}
						client.sendToClient(msg2);
						exception.printStackTrace();
					} finally {
						session.close();
						client.sendToClient(msg2);
					}
					flag = true;
					break;
				}
			}
			for (MultiSub multisub : multiListDB) {
				String ID = multisub.getCustomerId();
				String Licence = multisub.getCarNumber();
				if (Subnumber.equals(ID) && Licence.equals(LicencePlateNum)) {
					try {

						multisub.updateEndDate();
						session.saveOrUpdate(multisub);
						session.flush();
						session.getTransaction().commit();
						msg2.setMessage("SubRenewed_website");
						System.out.println(ms.getMessage());
						if(ms.getMessage().equals("SubRenewed_kiosk")) {
							msg2.setMessage("SubRenewed_kiosk");
						}
						msg2.setObject1("success");
						client.sendToClient(msg2);
					} catch (Exception exception) {
						if (session != null) {
							session.getTransaction().rollback();
						}
						msg2.setMessage("SubRenewed_website");
						System.out.println(ms.getMessage());
						if(ms.getMessage().equals("SubRenewed_kiosk")) {
							msg2.setMessage("SubRenewed_kiosk");
						}
						msg2.setObject1("success");

						exception.printStackTrace();
					} finally {
						session.close();
						client.sendToClient(msg2);
					}
					flag = true;
					break;
				}
			}
//			printSubscribers();
		}
		else if(ms.getMessage().equals("ParkingManager_alterPrices")) {
			// adds a price change request to the regional manager's requests list
			//data stored in separate objects
			// occasional price -> object1
			// preOrder price -> object2
			// partTime price -> object3
			// FullSubs price -> object4
			// Multi price -> object5
			System.out.println("ParkingManager_alterPrices server start1");

			Vector<Integer> prices_request_vector = new Vector<Integer>();
			System.out.println("ParkingManager_alterPrices server start2");
			String one = (String)ms.getObject1();
			prices_request_vector.add(Integer.parseInt(one));
			String two = (String)ms.getObject2();
			prices_request_vector.add(Integer.parseInt(two));
			String three= (String)ms.getObject3();
			prices_request_vector.add(Integer.parseInt(three));
			String four = (String)ms.getObject4();
			prices_request_vector.add(Integer.parseInt(four));
			String five = (String)ms.getObject5();
			prices_request_vector.add(Integer.parseInt(five));

			System.out.println("ParkingManager_alterPrices server start33");
			//get manger's name of current window to alter accordingly
			Integer Managerid =  (Integer) ms.getObject6();

			//make new request to add to the DB

			try {
			session = getSessionFactory().openSession();
			session.beginTransaction();
			ParkingManager	Manager= new ParkingManager();
				CriteriaBuilder builder = session.getCriteriaBuilder();
				CriteriaQuery<ParkingManager> query = builder.createQuery(ParkingManager.class);
				query.from(ParkingManager.class);
				List<ParkingManager> data = session.createQuery(query).getResultList();
				System.out.println("ParkingManager_alterPrices server start3");
				for(ParkingManager a : data){
					if(a.getid() == Managerid){
						Manager = a;

					}
				}

			PricesUpdateRequest new_request = new PricesUpdateRequest(Managerid,prices_request_vector,"prices: ");
			// add new request for the list so the regional manager can see it.
				System.out.println("ParkingManager_alterPrices server start4");
			session.save(new_request);

			session.flush();
			session.getTransaction().commit();

				System.out.println("ParkingManager_alterPrices server start5");
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
				System.out.println("ParkingManager_alterPrices server finish");
				session.getTransaction().commit();
				session.close();
			}

		}

		else if(ms.getMessage().equals("ParkingManager_showPrices")) {
			Message MSG=new Message("pricesReturned");
			System.out.println("in show prices server");
			try{
				SessionFactory sessionFactory = getSessionFactory();
				session = sessionFactory.openSession();
				session.beginTransaction();

				CriteriaBuilder builder = session.getCriteriaBuilder();
				CriteriaQuery<ParkingLot> query = builder.createQuery(ParkingLot.class);
				query.from(ParkingLot.class);
				List<ParkingLot> data = session.createQuery(query).getResultList();
				System.out.println(data.get(0).getName());

				Integer parkingManagerID = (Integer) ms.getObject1();



				List<PricesClass> pricesList = new ArrayList<>();

				for (ParkingLot datum : data) {
					System.out.println("PARKING X ID ="+datum.getParking_id());
					if (datum.getParking_id() == parkingManagerID) {
						System.out.println("in show prices parking found");
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
				System.out.println("show prices time to get back to client");
				client.sendToClient(MSG);
				session.close();
		}

		}

//		else if(ms.getMessage().equals("ParkingManager_showStats")) {
//
//			// todo get all stats from database and then put them in objects 1 to 3 to present them to manager
//			Message MSG=new Message("statsReturned");
//			client.sendToClient(MSG);
//			// do other things
//		}

		else if(ms.getMessage().equals("Complaint")) {
			// the complaint which is a class defined in entities
			// is inside message in object1
			// each complaint contains two fields
			// 1- customer ID
			// 2- the complaint text


			Comp tempComplaint = (Comp) ms.getObject1();
			Complaint new_complaint = new Complaint();
			new_complaint.setComplaintText(tempComplaint.getComplaintText());
			new_complaint.setCustomerId_(Integer.parseInt(tempComplaint.getCustomerId()));
			new_complaint.setMail(tempComplaint.getMail());
			try {
				session = getSessionFactory().openSession();
				session.beginTransaction();
				session.save(new_complaint);
				Message msg2 = new Message("Complaint received successfully!");
				client.sendToClient(msg2);
				session.flush();
				session.getTransaction().commit();
			}catch (Exception e){
				e.printStackTrace();
			}
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
		}else if(ms.getMessage().equals("OcasionalParking2"))
		{
//            System.out.println("working occ");
//			client.sendToClient(ms);
			// Id number is saved as a string in object1
			// license plate number is saved as a string in object2
			// email is saved as a string in object3
			// leaving time is saved as a string in object4
			OccCustomer customer = new OccCustomer((String) ms.getObject1(),(String)ms.getObject2(),(String)ms.getObject3());
			customer.setCustomerId((String) ms.getObject1());
			customer.setStartDate(LocalDate.now());
			String leavingTime = (String)ms.getObject4();
			String[] parsed = leavingTime.split(":");
			//getting parking  id
			String given_parking_name = (String) ms.getObject5();
			try {
				SessionFactory sessionFactory = getSessionFactory();
				session = sessionFactory.openSession();
				session.beginTransaction();

				System.out.println("one time parking order in server111 printing...");

//				CriteriaBuilder builder1 = session.getCriteriaBuilder();
//				CriteriaQuery<ParkingLot> query1 = builder1.createQuery(ParkingLot.class);
//				query1.from(ParkingLot.class);
//				List<ParkingLot> preOrders = session.createQuery(query1).getResultList();
				List<ParkingLot> preOrders = getAll(ParkingLot.class);
				for(ParkingLot a : preOrders){
					if(a.getName().equals(given_parking_name)){
						customer.setParking_lot_id(a.getParking_id());
					}
				}
				session.getTransaction().commit();
				session.close();
			}catch(Exception e){
				System.out.println("failed transaction one time parking");
				e.printStackTrace();
			}
            String id=customer.getCustomerId();
			System.out.println(id);

			String leavingT = (String)ms.getObject4();
			String[] estimated_leave_time = leavingT.split(":");
			LocalTime leave_time = LocalTime.of(Integer.parseInt(estimated_leave_time[0]), Integer.parseInt(estimated_leave_time[1]),1,1);

			customer.setStartTime(leave_time);

			try {


				Message msg2 = routingOrders(customer,"OccCustomer");
				EnterCar(given_parking_name,id,(String)ms.getObject2(),true,leave_time);



				client.sendToClient(msg2);

			} catch (Exception exception) {

				Message msg2 = new Message("OccCustomer");
				msg2.setObject1("fail");
				client.sendToClient(msg2);
				System.err.println("An error occurred, changes have been rolled back.");
				exception.printStackTrace();
			}

			try {
				SessionFactory sessionFactory = getSessionFactory();
				session = sessionFactory.openSession();
				session.beginTransaction();

				System.out.println("one time parking order in server111 printing...");
				CriteriaBuilder builder1 = session.getCriteriaBuilder();
				CriteriaQuery<OccCustomer> query1 = builder1.createQuery(OccCustomer.class);
				query1.from(OccCustomer.class);
				List<OccCustomer> preOrders = session.createQuery(query1).getResultList();
				for(OccCustomer a : preOrders){
					System.out.println(a.getEmail());
				}
				session.getTransaction().commit();
				session.close();
			}catch(Exception e){
				System.out.println("failed transaction one time parking");
				e.printStackTrace();
			}

			// return msg with "OcasionalParking" in name object1 a string success/fail



		} else if (ms.getMessage().equals("OcasionalParking")){
			// Id number is saved as a string in object1
			// license plate number is saved as a string in object2
			// email is saved as a string in object3
			// leaving time is saved as a string in object4
			String Id = (String) ms.getObject1();
			String CarNum = (String) ms.getObject2();
			String email = (String) ms.getObject3();
			String leavingTime = (String) ms.getObject4();
			String parkingLotName = (String) ms.getObject5();

			String[] split = leavingTime.split(":");
			LocalDate startDate = LocalDate.now();
			LocalTime finishTime = LocalTime.of(Integer.parseInt(split[0]),Integer.parseInt(split[1]));
			LocalTime startTime = LocalTime.now();


			OccCustomer newCustomer = new OccCustomer(Id,CarNum,email);

			newCustomer.setStartDate(startDate);
			newCustomer.setFinishTime(finishTime);
			newCustomer.setStartTime(startTime);

			SessionFactory sessionFactory = getSessionFactory();
			session = sessionFactory.openSession();
			session.beginTransaction();
			session.save(newCustomer);
			session.getTransaction().commit();
			session.close();
			Message msg2 = new Message("OccCustomer");
			if (EnterCar(parkingLotName,Id,CarNum,true,finishTime)){
				msg2.setObject1("Success");
				client.sendToClient(msg2);
			}else{
				msg2.setObject1("fail");
				client.sendToClient(msg2);
			}




		}else if (ms.getMessage().equals("OneTimeParkingOrder_Submit")){

			//**************************** pre order parking **************************//



			//return OneTimeParkingOrder_Success
			// or OneTimeParkingOrder_Fail
			// data is contained in a vector inside Object1
			// the data is stored in the following order
			// 0- car number             3- Eta
			// 1- DesiredParking         4- Etd
			// 2- Email                  5- Id nnumber
			// object 2 -> input date    object 3 -> output date

			Vector<String> fields = (Vector<String>)ms.getObject1();
			System.out.println("one time parking order in server");
			// checking fields input if okay add the client, else return failed
			// checked everything except time/date
			if (fieldsChecker_OneTimeParkingOrder(fields)) {
				try{
					System.out.println("one time parking order in server5543");
					PreOrder tempPreOrder = new PreOrder(fields.get(5), fields.get(0), fields.get(1), fields.get(2));
					String fields3 = (String)fields.get(3);
					String fields4 = (String)fields.get(4);


					System.out.println(fields3);
					System.out.println(fields4);

					//input example 22:15-2022:11:24
					// hour in - 0 , minutes in - 1
					String[] estimated_arrival_time = fields3.split(":");
					//0 - year,1 - month,2 -day
					//String[] estimated_arrival_date = fields3.split("-")[1].split(":");

					// hour in - 0 , minutes in - 1
					String[] estimated_leave_time = fields4.split(":");
					//0 - year,1 - month,2 -day
					//String[] estimated_leave_date = fields4.split("-")[1].split(":");

					System.out.println("one time parking order in server5543");
//				 Initialize to a specific date using year, month, and day values
					//LocalDate arrival_date = LocalDate.of(Integer.parseInt(estimated_arrival_date[0]), Integer.parseInt(estimated_arrival_date[1]), Integer.parseInt(estimated_arrival_date[2])+1);
					LocalDate arrival_date = (LocalDate) ((Message) msg).getObject2();
					//hour , minute
					LocalTime arrival_time = LocalTime.of(Integer.parseInt(estimated_arrival_time[0]), Integer.parseInt(estimated_arrival_time[1]),1,1);

					//				 Initialize to a specific date using year, month, and day values
					//LocalDate leave_date = LocalDate.of(Integer.parseInt(estimated_leave_date[0]), Integer.parseInt(estimated_leave_date[1]), Integer.parseInt(estimated_leave_date[2]));
					LocalDate leave_date = (LocalDate) ((Message) msg).getObject3();
					//hour , minute
					LocalTime leave_time = LocalTime.of(Integer.parseInt(estimated_leave_time[0]), Integer.parseInt(estimated_leave_time[1]),1,1);

//				LocalDate Eta = new LocalDateStringConverter(Integer.parseInt(parsedEta[0]), Integer.parseInt(parsedEta[1]), Integer.parseInt(parsedEta[2]), Integer.parseInt(parsedEta[3]), 0);
//				Date Etd = new Date(Integer.parseInt(parsedEtd[0]), Integer.parseInt(parsedEtd[1]), Integer.parseInt(parsedEtd[2]), Integer.parseInt(parsedEtd[3]), 0);



					tempPreOrder.setEntranceDate(arrival_date);
					tempPreOrder.setEntranceTime(arrival_time);
					tempPreOrder.setExitDate(leave_date);
					tempPreOrder.setExitTime(leave_time);


					System.out.println("now should enter routing");

					//todo in parkingEnter return message store "OneTimeParkingOrder" in the title,
					// and the first object fail/success as a string, rest of the info choose when implementing
					Message msg2 = routingOrders(tempPreOrder, "PreOrder");

					client.sendToClient(msg2);
				}catch(Exception e){
					e.printStackTrace();
				}
			} else {
				//failed
				Message msg2 = new Message("OneTimeParkingOrder");
				client.sendToClient(msg2);
				msg2.setObject1("fail");
			}
			try {
				SessionFactory sessionFactory = getSessionFactory();
				session = sessionFactory.openSession();
				session.beginTransaction();

				System.out.println("one time parking order in server111 printing...");
				CriteriaBuilder builder1 = session.getCriteriaBuilder();
				CriteriaQuery<PreOrder> query1 = builder1.createQuery(PreOrder.class);
				query1.from(PreOrder.class);
				List<PreOrder> preOrders = session.createQuery(query1).getResultList();
				for(PreOrder a : preOrders){
					System.out.println(a.getEmail_());
				}
				session.getTransaction().commit();
				session.close();
			}catch(Exception e){
				System.out.println("failed transaction one time parking");
				e.printStackTrace();
			}

		} else if(ms.getMessage().endsWith("regional")){
			Message msg2 = new Message("return_regional");
			System.out.println("IN SERVER REGIONAL MANAGER");
			System.out.println(ms.getMessage());
			switch (ms.getMessage()) {
				case "accept_price_alter_regional" -> msg2 = price_alter(ms, "accept");
				case "decline_price_alter_regional" -> msg2 = price_alter(ms, "decline");
				case "show_stat1_regional", "stat_Parking3_regional", "stat_Parking2_regional", "stat_Parking1_regional", "show_stat3_regional", "show_stat2_regional" -> msg2 = showStatRegional(ms);
				case "pdf_Parking1_regional", "pdf_Parking3_regional", "pdf_Parking2_regional" -> msg2 = pdfRegional(ms);
				case "show_prices1_regional", "show_prices3_regional", "show_prices2_regional" -> msg2 = showPricesRegional(ms);
				case "alterPrices1_regional", "alterPrices3_regional", "alterPrices2_regional" -> msg2 = alterPricesRegionalReq(ms);
				default -> System.out.println("Simple server regional manager error");
			}

			try{
				client.sendToClient(msg2);
			}catch(Exception ex){
				System.out.println("failed to send to server in regional return");
				ex.printStackTrace();
			}

		}
		else if(ms.getMessage().equals("Deactivate Parking Spot"))
		{
			System.out.println("deactivate client server ");

			Message msg00 = new Message("Employee_return");
			msg00.setObject1("failed");

			try {
				SessionFactory sessionFactory = session.getSessionFactory();
				session = sessionFactory.openSession();
				session.beginTransaction();

				CriteriaBuilder builder1 = session.getCriteriaBuilder();
				CriteriaQuery<ParkingWorker> query1 = builder1.createQuery(ParkingWorker.class);
				query1.from(ParkingWorker.class);
				List<ParkingWorker> parkingWorkers  = session.createQuery(query1).getResultList();

				CriteriaBuilder builder11 = session.getCriteriaBuilder();
				CriteriaQuery<ParkingSpot> query11 = builder11.createQuery(ParkingSpot.class);
				query11.from(ParkingSpot.class);
				List<ParkingSpot> parkingSpots  = session.createQuery(query11).getResultList();

				int worker_id = (int) ms.getObject2();
				int spot_id = Integer.parseInt((String) ms.getObject1());

				for(ParkingSpot parkingSpot : parkingSpots){
					System.out.println("spot id - "+parkingSpot.getSpotId_() + "worker id-"+parkingSpot.getParkingLot().getParkingWorker0().getUserID());
					if(parkingSpot.getParkingLot().getParkingWorker0().getUserID() == worker_id && parkingSpot.getSpotId_()== spot_id){
						parkingSpot.setCurrentState("Deactivated");
						session.saveOrUpdate(parkingSpot);
						session.flush();
						msg00.setObject1("Deactivated");
						System.out.println("deactivate client server 1");
					}
				}

				session.getTransaction().commit();
				System.out.println("deactivate client server 2");

			}
			catch (Exception ex)
			{
				if(session != null)
					session.getTransaction().rollback();
				ex.printStackTrace();
			}
			session.close();
			client.sendToClient(msg00);




		}
		else if(ms.getMessage().equals("Activate Parking Spot"))
		{


			Message msg00 = new Message("Employee_return");
			msg00.setObject1("failed");
			System.out.println("activate client server ");
			try {
				SessionFactory sessionFactory = session.getSessionFactory();
				session = sessionFactory.openSession();
				session.beginTransaction();

				CriteriaBuilder builder1 = session.getCriteriaBuilder();
				CriteriaQuery<ParkingWorker> query1 = builder1.createQuery(ParkingWorker.class);
				query1.from(ParkingWorker.class);
				List<ParkingWorker> parkingWorkers  = session.createQuery(query1).getResultList();

				CriteriaBuilder builder11 = session.getCriteriaBuilder();
				CriteriaQuery<ParkingSpot> query11 = builder11.createQuery(ParkingSpot.class);
				query11.from(ParkingSpot.class);
				List<ParkingSpot> parkingSpots  = session.createQuery(query11).getResultList();

				int worker_id = (int) ms.getObject2();
				int spot_id = Integer.parseInt((String) ms.getObject1());
//				System.out.println("given worker id = " + worker_id);
//				System.out.println("given spot id = " + spot_id);
				for(ParkingSpot parkingSpot : parkingSpots){

//					System.out.println("spot id - "+parkingSpot.getSpotId_() + "worker id-"+parkingSpot.getParkingLot().getParkingWorker0().getUserID());
					if(parkingSpot.getParkingLot().getParkingWorker0().getUserID() == worker_id && parkingSpot.getSpotId_()== spot_id){
						parkingSpot.setCurrentState("empty"); // empty is active
						session.saveOrUpdate(parkingSpot);
						session.flush();
						msg00.setObject1("Activated");
					}
				}

				session.getTransaction().commit();

			}
			catch (Exception ex)
			{
				if(session != null)
					session.getTransaction().rollback();
				ex.printStackTrace();
			}
			session.close();
			client.sendToClient(msg00);

		}
		else if(ms.getMessage().equals("System Request"))
		{
			//todo all
			//Check other parking places to send a vehicle to...
			//Object #1 - Parking ID
			//Object #2 - System Command ID / ON OR OFF
			String ParkingID = ms.getObject1().toString();
			String SystemCommand = ms.getObject2().toString();

			Message msg69 = ParkingLotCommand(ParkingID,SystemCommand); //Parking Lot Command has been set
			client.sendToClient(msg69);
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

			Message msg69 = SendToParking(); //"Found A New Parking Lot"

			client.sendToClient(msg69);

		}
		else if(ms.getMessage().equals("submit_occasion"))
		{

			System.out.println("server submit ocassion");
			Message msg00 = new Message("Employee_return");
			msg00.setObject1("failed_occasion");

			try {
				SessionFactory sessionFactory = session.getSessionFactory();
				session = sessionFactory.openSession();
				session.beginTransaction();

				CriteriaBuilder builder1 = session.getCriteriaBuilder();
				CriteriaQuery<ParkingWorker> query1 = builder1.createQuery(ParkingWorker.class);
				query1.from(ParkingWorker.class);
				List<ParkingWorker> parkingWorkers  = session.createQuery(query1).getResultList();

				CriteriaBuilder builder11 = session.getCriteriaBuilder();
				CriteriaQuery<ParkingSpot> query11 = builder11.createQuery(ParkingSpot.class);
				query11.from(ParkingSpot.class);
				List<ParkingSpot> parkingSpots  = session.createQuery(query11).getResultList();

				int worker_id = (int) ms.getObject2();
				int spot_id = Integer.parseInt((String) ms.getObject1());

				for(ParkingSpot parkingSpot : parkingSpots){
					System.out.println("server submit ocassion");
					if(parkingSpot.getParkingLot().getParkingWorker0().getUserID() == worker_id && parkingSpot.getSpotId_()== spot_id){
						parkingSpot.setCurrentState("Saved_for_Occasion"); // empty is active
						session.saveOrUpdate(parkingSpot);
						session.flush();
						msg00.setObject1("occasion_submitted");
					}
				}

				session.getTransaction().commit();

			}
			catch (Exception ex)
			{
				if(session != null)
					session.getTransaction().rollback();
				ex.printStackTrace();
			}
			session.close();
			client.sendToClient(msg00);

		}
		else if(ms.getMessage().equals("cancel_Occasion"))
		{

			Message msg00 = new Message("Employee_return");
			msg00.setObject1("failed_occasion");

			try {
				SessionFactory sessionFactory = session.getSessionFactory();
				session = sessionFactory.openSession();
				session.beginTransaction();

				CriteriaBuilder builder1 = session.getCriteriaBuilder();
				CriteriaQuery<ParkingWorker> query1 = builder1.createQuery(ParkingWorker.class);
				query1.from(ParkingWorker.class);
				List<ParkingWorker> parkingWorkers  = session.createQuery(query1).getResultList();

				CriteriaBuilder builder11 = session.getCriteriaBuilder();
				CriteriaQuery<ParkingSpot> query11 = builder11.createQuery(ParkingSpot.class);
				query11.from(ParkingSpot.class);
				List<ParkingSpot> parkingSpots  = session.createQuery(query11).getResultList();

				int worker_id = (int) ms.getObject2();
				int spot_id = Integer.parseInt((String) ms.getObject1());

				for(ParkingSpot parkingSpot : parkingSpots){
					if(parkingSpot.getParkingLot().getParkingWorker0().getUserID() == worker_id && parkingSpot.getSpotId_()== spot_id){
						parkingSpot.setCurrentState("empty"); // empty is active
						session.saveOrUpdate(parkingSpot);
						session.flush();
						msg00.setObject1("occasion_canceled");
					}
				}

				session.getTransaction().commit();

			}
			catch (Exception ex)
			{
				if(session != null)
					session.getTransaction().rollback();
				ex.printStackTrace();
			}
			session.close();

			client.sendToClient(msg00);



		}else if(ms.getMessage().startsWith("cs_")) {
			Message msg45 = new Message("cs_");
			msg45.setObject1(ms.getObject1());

			if(ms.getMessage().endsWith("accept")){

				msg45 = complaintUpdate(msg45,"accept");

			}else if(ms.getMessage().endsWith("decline")){
				msg45 = complaintUpdate(msg45,"decline");

			}else {
				msg45 = complaintUpdate(msg45,"refresh");
			}

			client.sendToClient(msg45);

		}else if(ms.getMessage().equals("ExitParking")){
			SessionFactory sessionFactory = getSessionFactory();
			session = sessionFactory.openSession();
			session.beginTransaction();
			String subNumber = ms.getSubNum();
			String CarNumber = ms.getLicensePlate();
			String parkingLotName = (String) ms.getObject1();

			int price = removeCarFromParking(CarNumber,parkingLotName);

			Message returnMsg = new Message("ExitParkingReply");

			if(price < 0){
				returnMsg.setObject1("Car not found !");
			}else {
				returnMsg.setObject1("Your account has been charged "+price+" shekels");
			}

			session.close();
			client.sendToClient(returnMsg);

		}else if(ms.getMessage().equals("EnterParking4")){
			SessionFactory sessionFactory = getSessionFactory();
			session = sessionFactory.openSession();
			session.beginTransaction();
			String subNumber = ms.getSubNum();
			String CarNumber = ms.getLicensePlate();
			String parkingLotName = (String) ms.getObject1();

			//todo open a session for getAll
			List<PartialSub> partialSubs = getAll(PartialSub.class);
			List<MultiSub> multiSubs = getAll(MultiSub.class);
			List<FullSub> fullSubs = getAll(FullSub.class);
			List<PreOrder> preOrders = getAll(PreOrder.class);
			boolean found = false;
			boolean sub = false;
			System.out.println(partialSubs.size()+","+multiSubs.size()+","+fullSubs.size());


			for (PreOrder preOrder : preOrders){
				if (preOrder.getPreOrderId().equals(subNumber) && preOrder.getCarNumber().equals(CarNumber)) {
					found = true;
					session.delete(preOrder);
					break;
				}
			}
			for (PartialSub partialSub : partialSubs){
				if (partialSub.getSubNum().equals(subNumber) && partialSub.getCarNumber().equals(CarNumber)) {

					found = true;
					sub = true;
					break;
				}
			}
			for (MultiSub multiSub : multiSubs){
				if (multiSub.getSubNum().equals(subNumber) && multiSub.getCarNumber().equals(CarNumber)) {

					found = true;
					sub = true;
					break;
				}
			}
			for (FullSub fullSub : fullSubs){
				if (fullSub.getSubNum().equals(subNumber) && fullSub.getCarNumber().equals(CarNumber)) {

					found = true;
					sub = true;
					break;
				}
			}

			LocalTime time = LocalTime.now();
			Message returnMsg = new Message("EnterParkingReply");
			if(!found){

				returnMsg.setObject1("Subscription not found");
			}else if(sub){

				if(EnterCar(parkingLotName,subNumber,CarNumber,false,time)){
					returnMsg.setObject1("Car Entered Successfully");
				}else{
					returnMsg.setObject1("Unable to Enter Car");
				}

			}else{
				if(EnterCar(parkingLotName,subNumber,CarNumber,true,time)){
					returnMsg.setObject1("Car Entered Successfully");
				}else{
					returnMsg.setObject1("Unable to Enter Car");
				}
			}

			client.sendToClient(returnMsg);
			session.getTransaction().commit();
			session.close();



		}

		// RegisterNewSubscription Window
		else if(ms.getMessage().equals("Register New Subscriber"))
		{
			System.out.println("servers side");
			//todo all
			//Check other parking places to send a vehicle to...
			//Object #1 - Subscriber Type
			//Object #2 - Customer ID
			//Object #3 - Starting Date ; YYYY:MM:DD
			//Object #4 - Car Number
			//Object #5 - Entrance Hour	; HH:MM
			//Object #6 - Departure Hour ; HH:MM
			//Object #7 - Email


			String SubscriberType = ms.getObject1().toString();
			System.out.println(SubscriberType);
			String CustomerID = ms.getObject2().toString();
			String CarNumber = ms.getObject4().toString();
			String StartingDate = ms.getObject3().toString();
			String EntranceHour = ms.getObject5().toString();
			System.out.println(StartingDate );
			String DepartureHour = ms.getObject6().toString();
			String Email = ms.getObject7().toString();
			System.out.println("servers side after loading values");

			//Staring Date Components

			System.out.println(StartingDate );
			String[] split = StartingDate.split("/");
			System.out.println(split[0]+split[1]+split[2] );
			//Staring Date Components
			int Year = Integer.parseInt(split[2]);
			int Month = Integer.parseInt(split[1]);
			int Day = Integer.parseInt(split[0]);

			//Entrance Hour Components
			//No Need For Now
			//Departure Hour Components
			//No Need For Now

			session = getSessionFactory().openSession();
			session.beginTransaction();
			Message MSG=new Message("RegisterNewSub");

			System.out.println("servers side before checking type of sub");

			if(SubscriberType.equals("Single Monthly Subscription"))
			{
				System.out.println("single monthly");
				boolean newCustumer = true;
				System.out.println(CustomerID);
				PartialSub input = new PartialSub(CustomerID,CarNumber);
				input.setEmail(Email);
				System.out.println(input.getCustomerId());
				LocalDate Temp = LocalDate.of(Year, Month, Day);
				input.setStartDate(Temp);
				input.setEntranceHour(EntranceHour);
				input.setDepartureHour(DepartureHour);
				List<PartialSub> partialSubs = getAll(PartialSub.class);
				for (PartialSub partialSub:partialSubs){
					if (partialSub.getCustomerId().equals(CustomerID)) {
						newCustumer = false;
						break;
					}
				}
				try{
					if(newCustumer){
						session.save(input);
						session.flush();
						session.getTransaction().commit();
						MSG.setMessage("success");
					}else{
						MSG.setMessage("Customer already exists");
					}

				} catch (Exception exception) {
					if (session != null) {
						session.getTransaction().rollback();
					}

					MSG.setMessage("fail");
					exception.printStackTrace();
				} finally {
					client.sendToClient(MSG);
					session.close();
				}
				System.out.println("sub added");
			}
			else if(SubscriberType.equals("Multi Monthly Subscription"))

			{   System.out.println("multi monthly");
				MultiSub input = new MultiSub(CustomerID,CarNumber);
				LocalDate Temp = LocalDate.of(Year,Month,Day);
				input.InsertToList(CustomerID,CarNumber,Temp,EntranceHour,DepartureHour);
				input.setStartDate(Temp);
				input.setEmail(Email);
// todo here we can have a problem if we are trying to multiple cars and one of them exists
				Boolean newCustomer = true;
				Boolean newCar = true;
				List<MultiSub> multiSubs = getAll(MultiSub.class);
				MultiSub tempmultiSub = null;
				for (MultiSub multiSub : multiSubs){
					if(multiSub.getCustomerId().equals(CustomerID)){
						newCustomer = false;

					}
				}
				try{
						if(newCustomer){
						session.save(input);
						session.flush();
						session.getTransaction().commit();
						MSG.setMessage("customer added successfully");
					}else{
						MSG.setMessage("fail");
					}

				} catch (Exception exception) {
					if (session != null) {
						session.getTransaction().rollback();
					}

					MSG.setMessage("fail");
					exception.printStackTrace();
				} finally {
					client.sendToClient(MSG);
					session.close();
				}
			}
			else if(SubscriberType.equals("Fully Subscription"))
			{
				System.out.println("full sub");
				FullSub input = new FullSub(CustomerID,CarNumber);
				LocalDate Temp = LocalDate.of(Year,Month,Day);
				input.setStartDate(Temp);
				input.setEmail(Email);
				boolean newCustomer = true;
				List<FullSub> fullSubs = getAll(FullSub.class);
				for(FullSub fullSub : fullSubs){
					if(fullSub.getCustomerId().equals(CustomerID)){
						newCustomer = false;
					}
				}

				try{
					if(newCustomer) {
						session.save(input);
						session.flush();
						session.getTransaction().commit();
						MSG.setMessage("success");
					}else{
						MSG.setMessage("fail customer exists");
					}
				} catch (Exception exception) {
					if (session != null) {
						session.getTransaction().rollback();
					}

					MSG.setMessage("fail");
					exception.printStackTrace();
				} finally {
					client.sendToClient(MSG);
					session.close();
				}

			}

			printSubscribers();
		}
		else if(ms.getMessage().equals("Check Client Spot Status"))
		{
			//todo all
			String CustomerId = ms.getObject1().toString();
			String CarNumber = ms.getObject2().toString();

			Message msg69 = CheckRequestStatus(CustomerId,CarNumber);
			client.sendToClient(msg69);
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




	private Message complaintUpdate(Message msg45, String status) {
		Message ms = new Message("cs");

		try{
			SessionFactory sessionFactory = getSessionFactory();
			session = sessionFactory.openSession();
			session.beginTransaction();

			CriteriaBuilder builder1 = session.getCriteriaBuilder();
			CriteriaQuery<Complaint> query1 = builder1.createQuery(Complaint.class);
			query1.from(Complaint.class);
			List<Complaint> complaints  = session.createQuery(query1).getResultList();
			Complaint complaint_to_remove = new Complaint();
			if(!status.equals("refresh")) {
				int complaint_id =  Integer.parseInt((String) msg45.getObject1());
				for (Complaint complaint : complaints) {
					if (complaint.getComplaintId_() == complaint_id) {
						session.delete(complaint);
						complaint_to_remove =complaint;
						System.out.println("email -  to send to "+complaint.getMail());
						if (status.equals("accept")) {
							try {

								System.out.println("email -  to send to in accept "+complaint.getMail());
								new EmailSender().sendMail(complaint.getComplaintId_() + " complaint", " following your complaint on our service you received a 100 shekels coupon", complaint.getMail());
								System.out.println("email back from sending"+complaint.getMail());
							}catch (Exception e){
								e.printStackTrace();
							}
							ms = new Message("cs_accept");
						} else {
							try {
								System.out.println("email -  to send to in decline "+complaint.getMail());
							new EmailSender().sendMail(complaint.getComplaintId_() +" complaint","your complaint was denied.",complaint.getMail());
							}catch (Exception e){
							ms = new Message("cs_decline");
								e.printStackTrace();
							}
						}
//						complaints.remove(complaint);
					}
				}
			}

			for(Complaint a :  complaints){
				System.out.println("complaint- " +a.getComplaintId_());
				System.out.println("text- " +a.getComplaintText());
				System.out.println("\n\n\n");
			}
			complaints.remove(complaint_to_remove);
			ms.setObject2(complaints);
			session.getTransaction().commit();



		}catch(Exception e){
			if(session != null)
				session.getTransaction().rollback();
			e.printStackTrace();
		}
		session.close();

		return ms;
	}

	private Message CheckReservationStatus(Message ms) {

		CriteriaBuilder builder1 = session.getCriteriaBuilder();
		CriteriaQuery<PreOrder> query1 = builder1.createQuery(PreOrder.class);
		query1.from(PreOrder.class);
		List<PreOrder> preOrders  = session.createQuery(query1).getResultList();

		String customerId = ms.getObject1().toString();
		int parking_id = 0;

		CriteriaBuilder builder11 = session.getCriteriaBuilder();
		CriteriaQuery<ParkingLot> query11 = builder11.createQuery(ParkingLot.class);
		query11.from(ParkingLot.class);
		List<ParkingLot> ParkingLots  = session.createQuery(query11).getResultList();

		for(ParkingLot parkingLot:ParkingLots){
			if(parkingLot.getName().equals(ms.getObject2().toString())){
				parking_id = parkingLot.getParking_id();
			}
		}

		for(PreOrder preOrder:preOrders){
			System.out.println("preorder -"+preOrder.getPreOrderId());
			if(preOrder.getPreOrderId().equals(customerId) && preOrder.getParking_lot_id() == parking_id){
				Message msg = new Message("checkReservation");
				msg.setObject1("Your order is confirmed, See you "+preOrder.getEntranceDate());

				return  msg;
			}
		}

		Message msg = new Message("checkReservation");
		msg.setObject1("Your order was not found");

		return  msg;
	}




	private boolean EnterCar(String parkingName,String customerID,String licencePlate, boolean isOccasional,LocalTime time){

		SessionFactory sessionFactory = getSessionFactory();
		session = sessionFactory.openSession();
		session.beginTransaction();

		List<ParkingLot> parkingLots = getAll(ParkingLot.class);
		for(ParkingLot parkingLot : parkingLots){
			if (parkingLot.getName().equals(parkingName)){

				if(parkingLot.isFull()){
					return false;
				}else{
					List<ParkingSpot> parkingSpots = parkingLot.getSpots();



//					CriteriaBuilder builder1 = session.getCriteriaBuilder();
//					CriteriaQuery<PreOrder> query1 = builder1.createQuery(PreOrder.class);
//					query1.from(PreOrder.class);
//					List<PreOrder> preOrders  = session.createQuery(query1).getResultList();

					List<PreOrder> preOrders = getAll(PreOrder.class);

					for (PreOrder preOrder : preOrders){
						if(preOrder.getCarNumber().equals(licencePlate) && preOrder.getParking_lot_id() == parkingLot.getParking_id()){
							preOrders.remove(preOrder);
							parkingLot.decPreOrderNum();
						}
					}

					for(ParkingSpot parkingSpot : parkingSpots){
						if(parkingSpot.getCurrentState().equals("empty")){

							//////////////////// THIS IS IRRELEVANT FOR NOW ////////////////////////
							// instead of getting the list from the parking lot we need to update
							// the values inside the parking lot
							// so maybe get size and then use internal functions like getstatus and what not
							// to get the data and update it
							// takeSpot(cusId,Plate,Index){set spot to taken and add values }
							parkingSpot.setCurrentState("taken");
							parkingSpot.setCus_ID(customerID);
							parkingSpot.setLicesnes_Plate(licencePlate);
							LocalDateTime timeNow = LocalDateTime.now();
							parkingSpot.setEntryDate(timeNow);
							parkingLot.decNumberOfFreeSlots();
							parkingSpot.setOccasional(isOccasional);
							// todo add start time inside parking spot

							parkingLot.setSpotsList(parkingSpots);
							try {
								session.update(parkingLot);
								session.getTransaction().commit();
							}catch (HibernateException e) {
								e.printStackTrace();
							}
							parkingLot.printParkingSpots();
							session.close();
							return true;

						}
						else{
							if(parkingSpot.getCus_ID().equals(customerID)&&parkingSpot.getLicesnes_Plate().equals(licencePlate)){
								return false;
							}

						}
					}
					session.close();
					return false;

				}


			}
		}

		return true;
	}

	private void EnterParking(Message msg)
	{
		// I assume name of the park is stored in object 4
		//we have variables called licenes plate and id in message already
		//assume in object 3 the exit date/time
		//;

			System.out.println("first");
			String parkName = (String) msg.getObject4();
			System.out.println("After message");
			ParkingLot pk = new ParkingLot();
			System.out.println("After Parking lot");
			String hql = "From ParkingLot ";
			System.out.println("After query");
			try {
				this.session=getSessionFactory().openSession();
				this.session.beginTransaction();
			}catch (Exception exp)
			{
				exp.printStackTrace();
			}
			System.out.println("After open session");
	    	Query query = null;
			try {
				 query = this.session.createQuery(hql);
			}
			catch (Exception ex)
			{
				ex.printStackTrace();
			}

			System.out.println("After query");
			List<ParkingLot> ParkingsList = query.getResultList();

		System.out.println("second");
		for(ParkingLot temp:ParkingsList)
		{
			if(temp.getName().equals(parkName))
			{
				pk=temp;
			}
		}
	    ParkingSpot spot= new ParkingSpot();
		System.out.println("third");
		if(!pk.isFull())
		{
			for(int i = 0; i< pk.getSlots_num(); i++)
			{
				if(pk.getSpots().get(i).getCurrentState().equals("empty"))
				{
					pk.setOccupied_slots_num(pk.getOccupied_slots_num()+1);
					pk.getSpots().get(i).setCurrentState("occupied");
					pk.getSpots().get(i).setLicesnes_Plate(msg.getLicensePlate());
					pk.getSpots().get(i).setCus_ID(msg.getID());
					pk.getSpots().get(i).setExitDate((LocalDateTime) msg.getObject3());
				}
			}
		}
		System.out.println("forth");
//		Collections.sort(pk.getSpots(), new Comparator<ParkingSpot>()
//		{
//			@Override
//			public int compare(ParkingSpot p1, ParkingSpot p2) {
//				return p1.getExitDate().compareTo(p2.getExitDate());
//			}
//		});

		  int i=pk.getOccupied_slots_num();
		for (int depth=0;depth<3;depth++)
		{
			for(int height=0;height<3;height++)
			{
			for (int width=0;width<pk.getWidth();width++,i--)
			 {
				  pk.getSpots().get(i).setdepth(depth);
				  pk.getSpots().get(i).setWidth(width);
				  pk.getSpots().get(i).setHeight(height);
				  if(i==0)
				  {
					  break;
				  }
			 }
		  }
		}
		try {
			         		this.session.saveOrUpdate(pk);

		}
		catch (Exception e)
		{
			this.session.getTransaction().rollback();
		}
		finally {
			this.session.flush();
			this.session.beginTransaction().commit();
			       	 this.session.close();
		}
		System.out.println("last");






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
				if (pk.getSpots().get(i).getCus_ID().equals(msg.getID())
				&& pk.getSpots().get(i).getLicesnes_Plate().equals(msg.getLicensePlate())) {

					pk.setOccupied_slots_num(pk.getOccupied_slots_num() -1);
					pk.getSpots().get(i).setCurrentState("empty");
					pk.getSpots().get(i).setLicesnes_Plate("");
					pk.getSpots().get(i).setCus_ID("");
					pk.getSpots().get(i).setExitDate(LocalDateTime.MIN);
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
					pk.getSpots().get(i).setdepth(depth);
					pk.getSpots().get(i).setWidth(width);
					pk.getSpots().get(i).setHeight(height);
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

	private Message CheckRequestStatus(String customerId, String carNumber) {
		Message Result = new Message("");
		try {
			session = getSessionFactory().openSession();
			session.beginTransaction();

			CriteriaBuilder builder1 = session.getCriteriaBuilder();
			CriteriaQuery<ParkingLot> query1 = builder1.createQuery(ParkingLot.class);
			query1.from(ParkingLot.class);
			List<ParkingLot> ParkingLots = session.createQuery(query1).getResultList();
			ParkingSpot CustomerSpot = new ParkingSpot();
			Boolean IsFound = false;
			for(int i = 0 ; i < ParkingLots.size(); i++)
			{
				for(int j = 0; j < ParkingLots.get(i).getSpots().size();j++)
				{
					//Double Check on Customer ID & Car Num
					if(ParkingLots.get(i).getSpots().get(j).getCus_ID() == customerId && ParkingLots.get(i).getSpots().get(j).getLicesnes_Plate() == carNumber)
					{
						CustomerSpot = ParkingLots.get(i).getSpots().get(j);
						Result.setObject1(CustomerSpot);
						Result.setObject2(CustomerSpot.getCurrentState());
						Result.setObject3(CustomerSpot.getLocation());
						Result.setObject4(CustomerSpot.getParkingLot());

						break;
					}
				}
				if(IsFound == true)
				{
					break;
				}
			}
			session.save(CustomerSpot);

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
		return Result;

	}

	private Message ParkingSpotStateUpdate(String parkingSpotID, ParkingLot parkingLot, String state) {
		int x = Integer.parseInt(parkingSpotID.split("-")[0]); //depth
		int y = Integer.parseInt(parkingSpotID.split("-")[1]); //height
		int z = Integer.parseInt(parkingSpotID.split("-")[2]); //width

		Message msg2 = new Message(""); // TODO
		try {
			session = getSessionFactory().openSession();
			session.beginTransaction();

			CriteriaBuilder builder1 = session.getCriteriaBuilder();
			CriteriaQuery<ParkingLot> query1 = builder1.createQuery(ParkingLot.class);

			query1.from(ParkingLot.class);
			List<ParkingLot> ParkingLots = session.createQuery(query1).getResultList();
			ParkingSpot ParkingSpot = new ParkingSpot(x, y, z, state,parkingLot.getParking_id(), parkingLot);
			for(ParkingLot var : ParkingLots)
			{
				if(var.getParking_id() == parkingLot.getParking_id())
				{
					//offset calculation : spots(depth - const,height - const , width)
					//location = depth * height * width + 3 * width + width
					int delta = var.CalculateLocation(x,y,z);
					parkingLot.getSpots().set(delta,ParkingSpot);
					msg2.setObject1(parkingLot.getParking_id());
					msg2.setObject1(x);
					msg2.setObject1(y);
					msg2.setObject1(z);
					break;
				}
			}

			msg2.setMessage("Parking Spot has been " + state + "ed successfully!");

			//NEED TO TALK
			session.saveOrUpdate(ParkingSpot);
			//session.saveOrUpdate(parkingLot);
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

	private Message SendOccasionRequest(String parkingSlotID, String carNumber, String occasionID, String parkingLotID) {
		int x = Integer.parseInt(parkingSlotID.split("-")[0]); //depth
		int y = Integer.parseInt(parkingSlotID.split("-")[1]); //height
		int z = Integer.parseInt(parkingSlotID.split("-")[2]); //width

		Message Result = new Message("");
		try {
			session = getSessionFactory().openSession();
			session.beginTransaction();

			CriteriaBuilder builder1 = session.getCriteriaBuilder();
			CriteriaQuery<ParkingLot> query1 = builder1.createQuery(ParkingLot.class);
			query1.from(ParkingLot.class);
			List<ParkingLot> ParkingLots = session.createQuery(query1).getResultList();
			for(ParkingLot var : ParkingLots)
			{
				if(var.getParking_id() == Integer.parseInt(parkingLotID))
				{
					int delta = var.CalculateLocation(x,y,z);
					var.getSpots().get(delta).setLicesnes_Plate(carNumber);
					var.getSpots().get(delta).setCurrentState(occasionID);
				}
			}
			session.save(ParkingLots);

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
		return Result;
	}

	private Message SendToParking() {
		//Done
		Message Result = new Message("");
		try
		{
			session = getSessionFactory().openSession();
			session.beginTransaction();

			CriteriaBuilder builder1 = session.getCriteriaBuilder();
			CriteriaQuery<ParkingLot> query1 = builder1.createQuery(ParkingLot.class);
			query1.from(ParkingLot.class);
			List<ParkingLot> ParkingLots = session.createQuery(query1).getResultList();
			ParkingLot ParkingLot = new ParkingLot();
			for(int i = 0 ; i < ParkingLots.size(); i++)
			{
				if(!ParkingLots.get(i).isFull())
				{
					ParkingLot = ParkingLots.get(i);
					Result.setMessage("Found A New Parking Lot");
					Result.setObject1(ParkingLot.getParking_id());
					Result.setObject2(ParkingLot.getName());
					break;
				}
			}
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
		}
		finally {
			assert session != null;
			session.close();
		}
		return Result;


	}

	private Message routingOrders(Object ParkingEntryOrder, String type) {
		System.out.println("in routing order");
		//this function takes an order such as preorder or occasional customer etc.., and the type in "type"
		// so we can convert it to the given type and add it. it returns a Message and it's fields are set according to
		// the caller.
		// it should every order to function that saves it/or let's the customer enter the parking
		Message msg2 = new Message("");
		try{
			SessionFactory sessionFactory =getSessionFactory();
			session =sessionFactory.openSession();
			session.beginTransaction();
			System.out.println("in routing order");

		if (type.equals("PreOrder")){
			PreOrder newOrder = (PreOrder) ParkingEntryOrder;
			String parking = newOrder.getParking_requested();
			List<ParkingLot> parkingList = getAll(ParkingLot.class);
			for(ParkingLot parkingLot : parkingList){
				if(parkingLot.getName().equals(parking)) {

					PreOrder order = (PreOrder) ParkingEntryOrder;
					int preOrdersNumber = getPreOrdersNumberByDateInParkingLot(parkingLot, order.getEntranceDate());
					int deactivatedSpotsNumber = getNotEmptySpotsNumberInParkinglot(parkingLot);
					//and then send the result to existsFreeSlots
					System.out.println("parking width:  "+parkingLot.getWidth());
					if (parkingLot.existsFreeSlots(preOrdersNumber+deactivatedSpotsNumber)) {
//						parkingLot.addPreOrder(newOrder);
						parkingLot.incPreOrderNum();

						order.setParking_lot_id(parkingLot.getParking_id());
//						session.saveOrUpdate(parkingLot);
						session.save(order);
//						session.flush();

						session.flush();
//						session.getTransaction().commit();
						System.out.println("preOrder added");
						msg2 = new Message("OneTimeParkingOrder");
						msg2.setObject1("success");

						msg2 = new Message("OneTimeParkingOrder");
						msg2.setObject1("success");


					}else {
						msg2 = new Message("OneTimeParkingOrder");
						msg2.setObject1("preorder_parking_is_full");
					}
				}


		}
		}else{
			// here type is "OccCustomer"
			System.out.println("in routing occasional");
			OccCustomer newCustomer = (OccCustomer) ParkingEntryOrder;
			DataSingleton data = DataSingleton.getInstance();

			List<ParkingLot> parkingList = getAll(ParkingLot.class);
			System.out.println("in routing occasional 2");
			for(ParkingLot parkingLot : parkingList){
				if(parkingLot.getParking_id() == newCustomer.getParking_lot_id()){
					System.out.println("in routing occasional cc");
					OccCustomer order = (OccCustomer) ParkingEntryOrder;
					int preOrdersNumber = getPreOrdersNumberByDateInParkingLot(parkingLot,order.getStartDate());
					int deactivatedSpotsNumber = getNotEmptySpotsNumberInParkinglot(parkingLot);
					if(parkingLot.existsFreeSlots(preOrdersNumber+deactivatedSpotsNumber)){
						System.out.println("in routing occasional dd");
							parkingLot.addOccasionalCustomers();
						System.out.println("in routing occasional saving...");
							order.setParking_lot_id(parkingLot.getParking_id());
							session.save(order);
							session.flush();
//							session.saveOrUpdate(parkingLot);
							System.out.println("occasional added");
							msg2 = new Message("OccCustomer");
							msg2.setObject1("success");
						System.out.println("in routing occasional saving... saved");

					}else {
						System.out.println("in routing occasional 2 fuull");
						msg2 = new Message("OneTimeParkingOrder");
						msg2.setObject1("occasional_parking_is_full");
					}
				}
			}


		}

			session.getTransaction().commit();
		}catch (Exception e){
			if (session != null) {
				session.getTransaction().rollback();
			}
			msg2 = new Message("OccCustomer");
			msg2.setObject1("fail");
			e.printStackTrace();
		}
		session.close();

		System.out.println("routing orders time to return");
		System.out.println("message is : "+ msg2.getMessage());
		return msg2;

	}

	private int getNotEmptySpotsNumberInParkinglot(ParkingLot parkingLot) {

		System.out.println("getting not empty spots number");
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<ParkingSpot> query = builder.createQuery(ParkingSpot.class);
		query.from(ParkingSpot.class);
		List<ParkingSpot> parkingSpots = session.createQuery(query).getResultList();
		int count =0;

		for(ParkingSpot parkingSpot : parkingSpots){
			if(parkingSpot.getParking_lot_id() == parkingLot.getParking_id() && !parkingSpot.getCurrentState().equals("empty")){
				count++;
			}
		}


		return count;
	}

	private int getPreOrdersNumberByDateInParkingLot(ParkingLot parkingLot, LocalDate entranceDate) {
		System.out.println("getting preOrdersNumber");
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<PreOrder> query = builder.createQuery(PreOrder.class);
		query.from(PreOrder.class);
		List<PreOrder> preOrders = session.createQuery(query).getResultList();
		int count = 0;
		for(PreOrder preOrder : preOrders){
			if(preOrder.getParking_requested().equals(parkingLot.getName()) && preOrder.getEntranceDate() == entranceDate){
				count++;
			}
		}

		return count;
	}

	private Message ParkingLotCommand(String ParkingID, String SystemCommand) {
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
				if(ParkingLots.get(i).getParking_id() == Integer.parseInt(ParkingID))
				{
					Result = ParkingLots.get(i);
					Result.setStatus(SystemCommand);
					break;
				}
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
		//data stored in separate objects
		// occasional price -> object1
		// preOrder price -> object2
		// partTime price -> object3
		// FullSubs price -> object4
		// Multi price -> object5
		System.out.println("in alter prices regional req");

		Vector<Integer> prices_request_vector = new Vector<Integer>();

		String one = (String)ms.getObject1();
		prices_request_vector.add(Integer.parseInt(one));
		String two = (String)ms.getObject2();
		prices_request_vector.add(Integer.parseInt(two));
		String three= (String)ms.getObject3();
		prices_request_vector.add(Integer.parseInt(three));
		String four = (String)ms.getObject4();
		prices_request_vector.add(Integer.parseInt(four));
		String five = (String)ms.getObject5();
		prices_request_vector.add(Integer.parseInt(five));

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
			System.out.println("in alter prices regional req1121321311");
			PricesUpdateRequest new_request = new PricesUpdateRequest(Manager.getid(),prices_request_vector,"plz_change");
			// add new request for the list so the regional manager can see it.
			session.save(new_request);
			System.out.println("in alter prices regional req1111");
			session.flush();
			session.getTransaction().commit();

			System.out.println("in alter prices regional req222");
			msg2 = new Message("prices update request sent regional");

		} catch (Exception exception) {
			exception.printStackTrace();
			if (session != null) {
				session.getTransaction().rollback();
			}
			 msg2 = new Message("failed_transaction");

			System.out.println("An error occurred, changes have been rolled back.");
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
			List<PricesClass> pricesList = new ArrayList<>();

			for (ParkingLot datum : data) {
				if (datum.getParking_id() == id_) {

					pricesList.add(datum.getOccasionalPrice());
					pricesList.add(datum.getPreOrderPrice());
					pricesList.add(datum.getPartTimePrice());
					pricesList.add(datum.getFullSubPrice());
					pricesList.add(datum.getMultiCarPrice());
				}
			}

			MSG.setObject2(pricesList);
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

		Message MSG=new Message("stat_regional");
		try{
			SessionFactory sessionFactory = getSessionFactory();
			session = sessionFactory.openSession();
			session.beginTransaction();

			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<ParkingLot> query = builder.createQuery(ParkingLot.class);
			query.from(ParkingLot.class);
			List<ParkingLot> parkingLots = session.createQuery(query).getResultList();

			String parking_name = (String) ms.getObject1();
			int late = 0;
			int canceled =0;
			int done = 0;

			for(ParkingLot parkingLot:parkingLots){
				if(parkingLot.getName().equals(parking_name) ){
					late = parkingLot.getNumber_of_late_preorders();
					canceled = parkingLot.getNumber_of_canceled_preorders();
					done = parkingLot.getNumber_of_done_preorders();
				}
			}





			MSG.setObject1(canceled);
			MSG.setObject2(late);
			MSG.setObject3(done);
			MSG.setObject4(ms.getObject1());

			session.getTransaction().commit();
		}catch (Exception e){
			e.printStackTrace();
		}

		session.close();
		return  MSG;

	}

	private Message pdfRegional(Message ms){
			//index chooses parking lot to show status of.
			System.out.println("pdf regional entered");
			int index = 0;
			 if(ms.getMessage().equals("pdf_Parking2_regional")){
				index = 1;
			}else if(ms.getMessage().equals("pdf_Parking3_regional")) {
				index =2;
			}

			try {
				// Create a new Document
				Document document = new Document();
				// Create a new PdfWriter
				PdfWriter.getInstance(document, new FileOutputStream("Parking_"+index+".pdf"));
				// Open the Document
				document.open();
				StringBuilder parking_spots_state = new StringBuilder();
				// Add content to the Document
				try {
					session = getSessionFactory().openSession();
					session.beginTransaction();

					CriteriaBuilder builder = session.getCriteriaBuilder();
					CriteriaQuery<ParkingLot> query = builder.createQuery(ParkingLot.class);
					query.from(ParkingLot.class);
					List<ParkingLot> parkingLots = session.createQuery(query).getResultList();

					List<ParkingSpot> parkingSpots = parkingLots.get(index).getSpots();
					parking_spots_state.append("Parking Lot").append(index).append(" spots stat:");
					for (int i = 0; i < parkingSpots.size(); i++) {
						if(i == 0){
							parking_spots_state.append("\n");
						}
						if(index == 0) {

							parking_spots_state.append("  ");
							parking_spots_state.append(i);
							parking_spots_state.append("  ");
							parking_spots_state.append(parkingSpots.get(i).getCurrentState());
							parking_spots_state.append("  ");
							if(i%4 == 0 && i%3 == 0 && i !=0) {
								parking_spots_state.append("\n");
								parking_spots_state.append("\n");
								parking_spots_state.append("\n");
								parking_spots_state.append("\n");
							}
						} else if( index == 1){

							parking_spots_state.append("  ");
							parking_spots_state.append(i);
							parking_spots_state.append("  ");
							parking_spots_state.append(parkingSpots.get(i).getCurrentState());
							parking_spots_state.append("   ");
							if(i%5 == 0 && i%3 == 0 && i !=0) {
								parking_spots_state.append("\n");
								parking_spots_state.append("\n");
								parking_spots_state.append("\n");
								parking_spots_state.append("\n");
							}
						}else{

							parking_spots_state.append("  ");
							parking_spots_state.append(i);
							parking_spots_state.append("  ");
							parking_spots_state.append(parkingSpots.get(i).getCurrentState());
							parking_spots_state.append("  ");
							if(i%5 == 0 && i%3 == 0 && i !=0) {
								parking_spots_state.append("\n");
								parking_spots_state.append("\n");
								parking_spots_state.append("\n");

							}
						}


					}
				}catch (Exception ee) {
					ee.printStackTrace();
					session.getTransaction().commit();
					session.close();
				}
				document.add(new Paragraph(String.valueOf(parking_spots_state)));
				// Close the Document
				document.close();
			} catch (FileNotFoundException | DocumentException e) {
				e.printStackTrace();
			}
			System.out.println("pdf regional entered");
			return null;
		}



	private Message price_alter(Message ms, String res) {
		//changes price with a given update price request
		System.out.println("IN SERVER REGIONAL MANAGER PRICE ALTER FUNCTION");

		Message msg1 = new Message("req_regional");
		Message ms2 = new Message("req_regional");
		List<PricesUpdateRequest> data21 = new ArrayList<>();
		if(res.equals("accept")){
			System.out.println("IN SERVER REGIONAL MANAGER REQ ACCEPTED");
			try{
			session = getSessionFactory().openSession();
			session.beginTransaction();

			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<PricesUpdateRequest> query = builder.createQuery(PricesUpdateRequest.class);
			query.from(PricesUpdateRequest.class);
			List<PricesUpdateRequest> requestList = session.createQuery(query).getResultList();

			int request_num = Integer.parseInt((String) ms.getObject1());
			PricesUpdateRequest temp = new PricesUpdateRequest();
			for(PricesUpdateRequest ptr : requestList){
				if(ptr.getPricesUpdateReqId() == request_num){
					System.out.println("request to accept found in db");
					temp=ptr;
				}
			}



			CriteriaBuilder builder1 = session.getCriteriaBuilder();
			CriteriaQuery<ParkingLot> query1 = builder1.createQuery(ParkingLot.class);
			query1.from(ParkingLot.class);
			List<ParkingLot> parkingLots = session.createQuery(query1).getResultList();
			Integer ID =0;
			for(ParkingLot parkingLot : parkingLots){
				if(parkingLot.getParkingManager().getid() == temp.getParkingManagerID()){
					PricesClass occasionalPrice = new PricesClass(temp.getOccasionalPrice(),"occasionalPrice");
					PricesClass preOrderPrice = new PricesClass(temp.getPreOrderPrice(),"preOrderPrice");
					PricesClass PartTimePrice = new PricesClass(temp.getPartTimePrice(),"PartTimePrice");
					PricesClass fullSubPrice = new PricesClass(temp.getFullSubPrice(),"fullSubPrice");
					PricesClass MultiCarPrice = new PricesClass(temp.getMultiCarPrice(),"MultiCarPrice");
					parkingLot.setOccasionalPrice(occasionalPrice);
					parkingLot.setPreOrderPrice(preOrderPrice);
					parkingLot.setPartTimePrice(PartTimePrice);
					parkingLot.setFullSubPrice(fullSubPrice);
					parkingLot.setMultiCarPrice(MultiCarPrice);
					ID = parkingLot.getParking_id();
					session.saveOrUpdate(parkingLot);
				}
			}

				session.delete(temp);
				CriteriaBuilder builder44 = session.getCriteriaBuilder();
				CriteriaQuery<PricesUpdateRequest> query44 = builder44.createQuery(PricesUpdateRequest.class);
				query44.from(PricesUpdateRequest.class);
				data21 = session.createQuery(query44).getResultList();
					msg1.setObject1(data21);
					ms2.setObject1(data21);

			session.flush();
			session.getTransaction().commit();

		} catch (Exception exception) {
			if (session != null) {
				session.getTransaction().rollback();
			}

			System.err.println("An error occurred, changes have been rolled back.");
			exception.printStackTrace();
			} finally {

				assert session != null;
				session.close();
		}

		}else{
			System.out.println("IN SERVER REGIONAL MANAGER REQ DENIED");
			return ms2;
		}
		System.out.println("IN SERVER REGIONAL MANAGER REQ ACCEPTED TIME TO RETURN TO HANDLE  IN SERVER");
		return msg1;

	}

	boolean fieldsChecker_OneTimeParkingOrder(Vector<String> fields){
		//data is contained in a vector inside Object1
		// the data is stored in the following order
		// 0- car number             3- Eta
		// 1- DesiredParking         4- Etd
		// 2- Email                  5- Id number

//		String regex_multi_number = "\\d+";
//		Pattern p = Pattern.compile(regex_multi_number);
//		Matcher m = p.matcher(fields.get(0));
//
//		if(!m.matches()){
//			//0- car number field error
//			System.out.println("car number error");
//			return false;
//		}
//
//		String regex_one_number = "\\d";
//		Pattern q = Pattern.compile(regex_multi_number);
//		Matcher s = q.matcher(fields.get(1));
//
//		// 3 is parkings number update if changed !!!!!!!!!
//		if(!s.matches() || s.matches() && (Integer.parseInt(fields.get(1)) > 3 || Integer.parseInt(fields.get(1)) < 1)){
//			// 1- parking number error
//			System.out.println("p number error");
//			return false;
//		}
//
//
//		String regex_email = ".+@.+"; // email should start with a char and contain a @ after it and then more chars
//		Pattern pp = Pattern.compile(regex_multi_number);
//		Matcher mail = pp.matcher(fields.get(2));
//
//		if(!mail.matches()){
//			//2- email error
//			return false;
//		}
//
//
//
//		Matcher mma = p.matcher(fields.get(5));
//
//		if(!mma.matches()){
//			//5- id number wrong
//			return false;
//		}

		return true;
	}

	private static void logout_by_string_debug_heleper(String[] data){
		String userName = data[0];
		String password = data[1];
		System.out.println("data0="+data[0]);
		System.out.println("data1="+data[1]);

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
				if (!user.getConnected()) msg.setMessage("tryLogin_UsernotConnected");
				else {
					user.setConnected(false);
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
						System.out.println(msg.getMessage());
					}
				}
			} else msg.setMessage("tryLogin_UserNotFound");

		} else msg.setMessage("tryLogin_UserNotFound");
			session.close();
	}

	private static Message tryLogIn(String[] data) {
		/** checks login pw and username in db with two strings given
		*returns the user in object1 in message
		* and in the name of the message the result
		* you can check what's the users type according to his permission lvl in field permission in User
		* 0 is regional manager, 1 is a branch manager, 2 parking worker.
		 * if user logged in his status set to active in field connected*/{
		 	System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
			SessionFactory sessionFactory = getSessionFactory();
			session = sessionFactory.openSession();
			session.beginTransaction();
			CriteriaBuilder builder = session.getCriteriaBuilder();
			// extracts list of users fromDB
			CriteriaQuery<User> query111 = builder.createQuery(User.class);
			query111.from(User.class);
			List<User> userList = session.createQuery(query111).getResultList();

			for (User user: userList){
				System.out.println("->"+user.getUserName());
			}
			//just some printing for test purpose
			System.out.println("first username in db"+userList.get(1).getUserName());
			System.out.println("password check res = "+userList.get(1).checkPassword(data[1]));
			session.close();
		}
		String userName = data[0];
		String password = data[1];
		System.out.println("data0="+data[0]);
		System.out.println("data1="+data[1]);

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
						System.out.println(msg.getMessage());
					}
				}
			} else msg.setMessage("tryLogin_UserNotFound");

		} else msg.setMessage("tryLogin_UserNotFound");
			session.close();
		System.out.println(msg.getMessage());
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

			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<ParkingManager> query = builder.createQuery(ParkingManager.class);
			query.from(ParkingManager.class);
			List<ParkingManager> parkingManagers = session.createQuery(query).getResultList();
			System.out.print("\n\n\n***Managerss:***** \n \n\n");
			for (ParkingManager parkingManager :parkingManagers) {
				System.out.print("Id: ");
				System.out.print(parkingManager.getUserID());
				System.out.print(", First Name: ");
				System.out.print(parkingManager.getFirstName());
				System.out.print(", LastName: ");
				System.out.print(parkingManager.getLastName());

				System.out.print('\n');
			}
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
				User ParkingWorker = new ParkingWorker("WORKER"+Integer.toString(i),"pass"+Integer.toString(i),"worker"+Integer.toString(i),"fam"+Integer.toString(i),2,null);
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



	public static void addCustomerServiceEmployee( ) {
		// 1 customer service employee
		int index = 1;
		try {
				System.out.println("qqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqq");
				User customerServiceEmployee = new CustomerServiceWorker("cs","123","jack","fam",3);
				SessionFactory sessionFactory = getSessionFactory();
				session = sessionFactory.openSession();
				session.beginTransaction();
			System.out.println("qqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqq");
				session.save(customerServiceEmployee);
				session.getTransaction().commit();
			System.out.println("qqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqq");
		} catch (HibernateException e) {
			e.printStackTrace();
		}
		session.close();
	}


	public static void Add_Parking_Lots() {
		try {
			/** adds three parking lots to db **/
			System.out.println("Parking Lot1");
			//Getting the default zone id
			ZoneId defaultZoneId = ZoneId.systemDefault();

			SessionFactory sessionFactory = getSessionFactory();
			session = sessionFactory.openSession();
			session.beginTransaction();

			ParkingLot parkingLot1 = new ParkingLot("German_Colony",3,4,false);
//			List<ParkingManager> parkingManagers = getAll()

			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<ParkingManager> query = builder.createQuery(ParkingManager.class);
			query.from(ParkingManager.class);
			List<ParkingManager> parkingManagers = session.createQuery(query).getResultList();


			CriteriaBuilder builder1 = session.getCriteriaBuilder();
			CriteriaQuery<ParkingWorker> query1 = builder1.createQuery(ParkingWorker.class);
			query1.from(ParkingWorker.class);
			List<ParkingWorker> parkingWorkers = session.createQuery(query1).getResultList();



			if(parkingManagers.get(0) == null)
				System.out.println("\n\n\n\n\n\naaaaaaaaaassssssssssssssssssssssssssssssssssssssssssssssssssssssssss\n\n\n");
			parkingLot1.setParkingManager(parkingManagers.get(0));
			parkingLot1.addParkingWorker(parkingWorkers.get(0));
			parkingWorkers.get(0).setParkingLot(parkingLot1);
			parkingManagers.get(0).setParkingLot(parkingLot1);
			session.saveOrUpdate(parkingManagers.get(0));
			session.saveOrUpdate(parkingWorkers.get(0));



//			parking spots initialize
			        for(int i = 0; i< parkingLot1.getDimensions(); i++)
        			{
           				 for(int j=0;j<3;j++)
            				{
             				   for(int k=0;k<3;k++)
               					 {

                   				 ParkingSpot S = new ParkingSpot(i,j,k,"empty",parkingLot1.getParking_id(),parkingLot1);
                   				 session.save(S);
                   				 parkingLot1.addSpot(S);
               					 }
            				}
       				 }

			session.save(parkingLot1);
			//___________________________________________________________________________________________


			System.out.println("Parking Lot2");
			ParkingLot parkingLot2 = new ParkingLot("Hanmal",3,5,false);

			parkingLot2.setParkingManager(parkingManagers.get(1));
			parkingLot2.addParkingWorker(parkingWorkers.get(1));

			parkingWorkers.get(1).setParkingLot(parkingLot2);
			parkingManagers.get(1).setParkingLot(parkingLot2);
			session.saveOrUpdate(parkingManagers.get(1));
			session.saveOrUpdate(parkingWorkers.get(1));
			//parking spots initialize
			for(int i = 0; i< parkingLot2.getDimensions(); i++)
			{
				for(int j=0;j<3;j++)
				{
					for(int k=0;k<3;k++)
					{

						ParkingSpot S = new ParkingSpot(i,j,k,"empty",parkingLot2.getParking_id(),parkingLot2);
						session.save(S);
						parkingLot2.addSpot(S);
					}
				}
			}


			System.out.println("\n\n\n\n\n\naaaaaaaaaassss\n\n\n");
			for(ParkingManager parkingManager: parkingManagers){

				System.out.println(parkingManager.getFirstName());


			}
			session.save(parkingLot2);


			//________________________________________________________________________________________________________________
			System.out.println("Parking Lot3");

			ParkingLot parkingLot3 = new ParkingLot("Bat-Galim",3,5,false);

			parkingLot3.setParkingManager(parkingManagers.get(2));
			parkingLot3.addParkingWorker(parkingWorkers.get(2));
			parkingWorkers.get(2).setParkingLot(parkingLot3);
			parkingManagers.get(2).setParkingLot(parkingLot3);
			session.saveOrUpdate(parkingManagers.get(2));
			session.saveOrUpdate(parkingWorkers.get(2));

			//parking spots initialize
			for(int i = 0; i< parkingLot3.getDimensions(); i++)
			{
				for(int j=0;j<3;j++)
				{
					for(int k=0;k<3;k++)
					{

						ParkingSpot S = new ParkingSpot(i,j,k,"empty",parkingLot3.getParking_id(),parkingLot3);
						session.save(S);
						parkingLot3.addSpot(S);
					}
				}
			}
			session.save(parkingLot3);

			/***************************** print everything for sanity check ************************************/

			CriteriaBuilder builder11 = session.getCriteriaBuilder();
			CriteriaQuery<ParkingLot> query11 = builder11.createQuery(ParkingLot.class);
			query11.from(ParkingLot.class);
			List<ParkingLot> data11 = session.createQuery(query11).getResultList();

			for(int i=0; i<20;i++){
				System.out.println("\n");
				System.out.println("/***************************** print everything for sanity check ************************************/");
			}

			for(ParkingLot parkingLot:data11){
				System.out.println("\n");
				System.out.println("parking lot:");
				System.out.println("name: "+parkingLot.getName());
				System.out.println("parking_id: "+parkingLot.getParking_id());
				System.out.println("Manager_name: "+parkingLot.getParkingManager().getFirstName());
				System.out.println("manager_id: "+parkingLot.getParkingManager().getid());
				System.out.println("parking_dims: "+parkingLot.getDimensions());
				System.out.println("worker_name: "+parkingLot.getParkingWorker0().getFirstName());
				System.out.println("worker_id: "+parkingLot.getParkingWorker0().getUserID());
				System.out.println("\n");
			}




			for(int i=0; i<20;i++){
				System.out.println("\n");
				System.out.println("end___check");
			}


			session.flush();
			session.getTransaction().commit();
			session.close();

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static void updatePreOrders() {
		try {
			SessionFactory sessionFactory = getSessionFactory();
			session = sessionFactory.openSession();
			session.beginTransaction();

			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<PreOrder> query = builder.createQuery(PreOrder.class);
			query.from(PreOrder.class);
			List<PreOrder> data21 = session.createQuery(query).getResultList();
			LocalDate now_date = LocalDate.now();
			LocalTime now_time = LocalTime.now();

			CriteriaBuilder builder1 = session.getCriteriaBuilder();
			CriteriaQuery<ParkingLot> query1 = builder1.createQuery(ParkingLot.class);
			query1.from(ParkingLot.class);
			List<ParkingLot> parkingLots = session.createQuery(query1).getResultList();



			for (PreOrder a : data21) {
				System.out.println("order date = " + a.getEntranceDate());
				System.out.println("order time = " + a.getEntranceTime());
				System.out.println("now date = " + now_date);
				System.out.println("now time = " + now_time);
				System.out.println("cust id - "+a.getPreOrderId());
				System.out.println("parking "+a.getParking_requested());
				if (now_date.isAfter(a.getEntranceDate()) ||( now_date.isEqual(a.getEntranceDate()) && now_time.isAfter(a.getEntranceTime()))) {
					System.out.println("order of car number - " + a.getCarNumber() + " was deleted" + " customer - late");
					System.out.println("cust id - "+a.getPreOrderId());
					System.out.println("parking "+a.getParking_requested());

					for(ParkingLot parkingLot:parkingLots) {
						if (a.getParking_lot_id() == parkingLot.getParking_id()) {
							parkingLot.setNumber_of_late_preorders(parkingLot.getNumber_of_late_preorders() + 1);
							session.update(parkingLot);
						}
					}

					session.delete(a);
					session.flush();
				}
			}

			session.getTransaction().commit();
		} catch (Exception E) {

			E.printStackTrace();
		}
	}


	public void printSubscribers(){
		session = getSessionFactory().openSession();
		session.beginTransaction();
		List<PartialSub> partialSubz = getAll(PartialSub.class);
		for (PartialSub partialSub:partialSubz){
			partialSub.print();
		}
		List<MultiSub> multiSubz = getAll(MultiSub.class);
		for (MultiSub multisub_:multiSubz){
			multisub_.print();
		}
		List<FullSub> fullSubz = getAll(FullSub.class);
		for (FullSub fullsub:fullSubz){
			fullsub.print();
		}
		session.close();
	}





	public static void customerReminder(){
		try {
			SessionFactory sessionFactory = getSessionFactory();
			session = sessionFactory.openSession();
			session.beginTransaction();

			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<PreOrder> query = builder.createQuery(PreOrder.class);
			query.from(PreOrder.class);
			List<PreOrder> data21 = session.createQuery(query).getResultList();
			LocalDate now_date = LocalDate.now();
			LocalTime now_time = LocalTime.now();

			for (PreOrder a : data21) {
				System.out.println("order date = "+a.getEntranceDate());
				System.out.println("order time = "+a.getEntranceTime());
				System.out.println("now date = "+now_date);
				System.out.println("now time = "+now_time);
				if ( now_date.isEqual(a.getEntranceDate())  && Duration.between(now_time,a.getEntranceTime()).toHours() < 1 && !a.isIs_reminded()) {
					new EmailSender().sendMail("Haifa Parkings","Dear Sir, \n"+"this is a reminder for your preOrder parking in "+a.getParking_requested()+"parking."+"\n\n\n\nBest regards,\n Hiafa Parkings.",a.getEmail_());
					a.setIs_reminded(true);
//					session.flush();
				}
			}
			session.getTransaction().commit();
		}catch(Exception E){

			E.printStackTrace();
		}

	}




	public static void SubscriptionReminder() {


		try {
			SessionFactory sessionFactory = getSessionFactory();
			session = sessionFactory.openSession();
			session.beginTransaction();

			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<MultiSub> query = builder.createQuery(MultiSub.class);
			query.from(MultiSub.class);
			List<MultiSub> data21 = session.createQuery(query).getResultList();
			LocalDate now_date = LocalDate.now();
			LocalTime now_time = LocalTime.now();

			for (MultiSub a : data21) {
				System.out.println("subs end date = "+a.getEndDate());
				System.out.println("now date = "+now_date);
				System.out.println("now time = "+now_time);
				System.out.println("email "+a.getEmail());
				if ( now_date.until(a.getEndDate(), ChronoUnit.WEEKS) <= 1 && !a.isMail_sent()) {

					new EmailSender().sendMail("Haifa Parkings","Dear Sir, \n"+"this is a reminder for you to renew your subscription "+"parking."+"\n\n\n\nBest regards,\n Hiafa Parkings.",a.getEmail());
					a.setMail_sent(true);
					session.update(a);
//					session.flush();
				}
			}
			session.getTransaction().commit();
			session.close();
		}catch(Exception E){

			E.printStackTrace();
		}


		try {
			SessionFactory sessionFactory = getSessionFactory();
			session = sessionFactory.openSession();
			session.beginTransaction();

			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<PartialSub> query = builder.createQuery(PartialSub.class);
			query.from(PartialSub.class);
			List<PartialSub> data21 = session.createQuery(query).getResultList();
			LocalDate now_date = LocalDate.now();
			LocalTime now_time = LocalTime.now();

			for (PartialSub a : data21) {
				System.out.println("subs end date = "+a.getEndDate());
				System.out.println("now date = "+now_date);
				System.out.println("now time = "+now_time);
				System.out.println("email "+a.getEmail());
				if ( now_date.until(a.getEndDate(), ChronoUnit.WEEKS) <= 1 && !a.isMail_sent()) {
					new EmailSender().sendMail("Haifa Parkings","Dear Sir, \n"+"this is a reminder for you to renew your subscription "+"parking."+"\n\n\n\nBest regards,\n Hiafa Parkings.",a.getEmail());
//					session.flush();
					a.setMail_sent(true);
					session.update(a);
				}
			}
			session.getTransaction().commit();
			session.close();
		}catch(Exception E){

			E.printStackTrace();
		}



		try {
			SessionFactory sessionFactory = getSessionFactory();
			session = sessionFactory.openSession();
			session.beginTransaction();

			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<FullSub> query = builder.createQuery(FullSub.class);
			query.from(FullSub.class);
			List<FullSub> data21 = session.createQuery(query).getResultList();
			LocalDate now_date = LocalDate.now();
			LocalTime now_time = LocalTime.now();

			for (FullSub a : data21) {
				System.out.println("subs end date = "+a.getEndDate());
				System.out.println("now date = "+now_date);
				System.out.println("now time = "+now_time);
				System.out.println("email "+a.getEmail());
				if ( now_date.until(a.getEndDate(), ChronoUnit.WEEKS) <= 1 && !a.isMail_sent()) {
					new EmailSender().sendMail("Haifa Parkings","Dear Sir, \n"+"this is a reminder for you to renew your subscription "+"parking."+"\n\n\n\nBest regards,\n Hiafa Parkings.",a.getEmail());
//					session.flush();
					a.setMail_sent(true);
					session.update(a);
				}
			}
			session.getTransaction().commit();
			session.close();
		}catch(Exception E){

			E.printStackTrace();
		}

	}




	int removeCarFromParking(String carNumber,String parkingLotName){
		int price = -1;
		System.out.println("inside remove car from parking");
		try {
			SessionFactory sessionFactory = getSessionFactory();
			session = sessionFactory.openSession();
			session.beginTransaction();

			List<ParkingLot> parkingLots = getAll(ParkingLot.class);
			List<ParkingSpot> Spots = getAll(ParkingSpot.class);

			for (ParkingLot parkingLot : parkingLots) {
				if (parkingLot.getName().equals(parkingLotName)) {
					System.out.println("before price calc");
					 price = parkingLot.findAndCalcPrice(carNumber);
					System.out.println("car found and the price is" + price);

					for(ParkingSpot parkingSpot : Spots){
						if(parkingSpot.getLicesnes_Plate().equals(carNumber)){
							System.out.println("foundit");
							parkingSpot.reset();
							parkingSpot.print();
							parkingLot.numberOfFreeSlots++;
							System.out.println("inside remove car");
							session.saveOrUpdate(parkingSpot);
//							printParkingSpots();
						}
					}

					session.saveOrUpdate(parkingLot);
					session.getTransaction().commit();
					System.out.println("after committing ");


				}
			}
		}catch(Exception e){
			if(session != null)
				session.getTransaction().rollback();
			e.printStackTrace();
		}finally {
			assert session != null;
			session.close();
		}
		System.out.println("before sending price ");
		return price;
	}
}
