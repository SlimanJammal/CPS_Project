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
	private static List<Manager> managers;
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
		configuration.addAnnotatedClass(Manager.class);
		configuration.addAnnotatedClass(MultiSub.class);
		configuration.addAnnotatedClass(OccCustomer.class);
		configuration.addAnnotatedClass(ParkingLot.class);
		configuration.addAnnotatedClass(ParkingSpot.class);
		configuration.addAnnotatedClass(PartialSub.class);
		configuration.addAnnotatedClass(PreOrder.class);
		configuration.addAnnotatedClass(PricesClass.class);
		configuration.addAnnotatedClass(regionalManager.class);
		configuration.addAnnotatedClass(Complaint.class);

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
			// todo ask regional manager if we need a price change and if agreed then change
			//data stored in seperate objects
			// ocasional price -> object1
			// preOrder price -> object2
			// partTime price -> object3
			// FullSubs price -> object4
			// Multi price -> object5
			PricesClass ocasionalPrice = new PricesClass((int)ms.getObject1(),"ocasionalPrice");
			PricesClass preOrderPrice = new PricesClass((int)ms.getObject2(),"preOrderPrice");
			PricesClass PartTimePrice = new PricesClass((int)ms.getObject3(),"PartTimePrice");
			PricesClass fullSubPrice = new PricesClass((int)ms.getObject4(),"fullSubPrice");
			PricesClass MultiCarPrice = new PricesClass((int)ms.getObject5(),"MultiCarPrice");
			session = getSessionFactory().openSession();
			session.flush();
			session.saveOrUpdate(ocasionalPrice);
			session.saveOrUpdate(preOrderPrice);
			session.saveOrUpdate(PartTimePrice);
			session.saveOrUpdate(fullSubPrice);
			session.saveOrUpdate(MultiCarPrice);
			Message msg2 = new Message("prices updated successfully!");
			client.sendToClient(msg2);
			session.getTransaction().commit();
			session.close();



			// todo change prices in database
			// todo also check which of the objects of the message do not have null in them
		}
		else if(ms.getMessage().equals("ParkingManager_showPrices"))
		{
			// todo get all prices from database and then put them in objects 1 to 5 to present them to manager
			Message MSG=new Message("pricesReturned");
			session = getSessionFactory().openSession();
			session.flush();
			List<PricesClass> pricesList = getAll(PricesClass.class);
			MSG.setObject1(pricesList);
			session.getTransaction().commit();
			session.close();
			client.sendToClient(MSG);
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
			Complaints tempComplaint = (Complaints) ms.getObject1();
			session = getSessionFactory().openSession();
			session.flush();
			session.save(tempComplaint);
			Message msg2 = new Message("Complaint received successfully!");
			client.sendToClient(msg2);
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

			session = getSessionFactory().openSession();
			session.flush();
			session.save(customer);
			Message msg2 = new Message("Entry Success!");
			client.sendToClient(msg2);
			session.getTransaction().commit();
			session.close();

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


				SessionFactory sessionFactory = getSessionFactory();
				session = sessionFactory.openSession();
				session.save(tempParking);
				session.flush();

				Message msg2 = new Message("OneTimeParkingOrder");
				msg2.setObject1("success");

				client.sendToClient(msg2);
				session.getTransaction().commit();
				session.close();
			} else {
				//failed
				Message msg2 = new Message("OneTimeParkingOrder");
				client.sendToClient(msg2);
				msg2.setObject1("fail");
			}

		}

	}

	boolean fieldsChecker_OneTimeParkingOrder(Vector<String> fields){
		//data is contained in a vector inside Object1
		// the data is stored in the following order
		// 0- car number             3- Eta
		// 1- DesiredParking         4- Etd
		// 2- Email                  5- Id nnumber

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
