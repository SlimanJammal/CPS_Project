package il.cshaifasweng.OCSFMediatorExample.server;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.time.LocalTime;

public class Reminder implements Job {
    public void execute(JobExecutionContext context) throws JobExecutionException {
        System.out.println("Updating preorders..");

        SimpleServer.updatePreOrders();

        SimpleServer.SubscriptionReminder();




    }
}
