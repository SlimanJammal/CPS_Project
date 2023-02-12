package il.cshaifasweng.OCSFMediatorExample.server;

import il.cshaifasweng.OCSFMediatorExample.entities.*;
import il.cshaifasweng.OCSFMediatorExample.server.ocsf.AbstractServer;
import il.cshaifasweng.OCSFMediatorExample.server.ocsf.ConnectionToClient;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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







	public void initSesssion() {
		session = getSessionFactory().openSession();
		try {
			session.getTransaction().begin();


//			session.save();
			session.flush();
			session.getTransaction().commit();

		} catch (Exception e) {
			if (session != null) {
				session.getTransaction().rollback();
			}
		}


	}

	private static SessionFactory getSessionFactory() {
		Configuration configuration = new Configuration();
		configuration.addAnnotatedClass(Employee.class);
		configuration.addAnnotatedClass(FullSub.class);
		configuration.addAnnotatedClass(Manager.class);
		configuration.addAnnotatedClass(MultiSub.class);
		configuration.addAnnotatedClass(OccCustomer.class);
		configuration.addAnnotatedClass(ParkingLot.class);
		configuration.addAnnotatedClass(ParkingSlot.class);
		configuration.addAnnotatedClass(PartialSub.class);
		configuration.addAnnotatedClass(PreOrder.class);
		configuration.addAnnotatedClass(PricesClass.class);
		configuration.addAnnotatedClass(regionalManager.class);
		configuration.addAnnotatedClass(complaint.class);

		ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
				.applySettings(configuration.getProperties())
				.build();
		return configuration.buildSessionFactory(serviceRegistry);
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
			// do other things
		}else if(ms.getMessage().equals("Complaint"))
		{
			Complaints tempComplaint = (Complaints) ms.getObject1();
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
