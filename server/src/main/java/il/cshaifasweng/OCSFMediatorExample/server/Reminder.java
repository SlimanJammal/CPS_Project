package il.cshaifasweng.OCSFMediatorExample.server;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.time.LocalTime;

public class Reminder implements Job {
    public void execute(JobExecutionContext context) throws JobExecutionException {
        System.out.println("executing.....");

        // todo implement
        // todo it's the scheduler helper so we can update the system every 30 second ...
        // todo this class should check all parking orders and parkings (call a function/functions that does/do this)
        // todo and update them according to the time ....


    }
}
