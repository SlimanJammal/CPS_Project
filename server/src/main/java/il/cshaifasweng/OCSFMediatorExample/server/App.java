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
//        SimpleServer.Add_Parking_Lots();
//        SimpleServer.addParkingManagers();
//        SimpleServer.addParkingWorkers();
//        SimpleServer.addRegionalManager();

        server.listen();
        System.out.println("SERVER IS UP...");
        //todo uncomment this after implementing (3ekronet bsht4el bs bswesh ashe)
        Scheduler_CPS.startJobScheduling();
    }
}
