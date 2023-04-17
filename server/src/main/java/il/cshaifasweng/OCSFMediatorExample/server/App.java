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
        int RunOnce = 0;
        if(RunOnce == 0)
        {
            RunOnce ++;
            SimpleServer.addParkingManagers();
            SimpleServer.addParkingWorkers();
            SimpleServer.addRegionalManager();
            SimpleServer.addCustomerServiceEmployee();
            SimpleServer.Add_Parking_Lots();
        }
        server.listen();
        System.out.println("SERVER IS UP...");
        Scheduler_CPS.startJobScheduling();


    }
}
