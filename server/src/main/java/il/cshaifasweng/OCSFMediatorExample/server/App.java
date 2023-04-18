package il.cshaifasweng.OCSFMediatorExample.server;

import java.io.IOException;

/**
 * Hello world!
 *
 */
public class App 
{
	
	private static SimpleServer server;
    public static void main( String[] args ) throws IOException
    {
        server = new SimpleServer(3000);

            SimpleServer.addParkingManagers();
            SimpleServer.addParkingWorkers();
            SimpleServer.addRegionalManager();
            SimpleServer.addCustomerServiceEmployee();
            SimpleServer.Add_Parking_Lots();

        server.listen();
        System.out.println("SERVER IS UP...");
        Scheduler_CPS.startJobScheduling();


    }
}
