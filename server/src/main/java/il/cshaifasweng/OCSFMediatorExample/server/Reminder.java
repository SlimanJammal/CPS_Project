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
//        ArrayList<HomeLinkTicket> hometickets= DBase.getHomeLinkTicket();
//        for(HomeLinkTicket hlt:hometickets){
//            System.out.println(hlt.getBuyerName());
//            LocalTime lt= LocalTime.now();
//            System.out.println(lt);
//            Long remainingtime=lt.until(hlt.getStartingTime(),MINUTES);
//            System.out.println(remainingtime);
//            System.out.println("link starting time "+hlt.getStartingTime());
//            if(remainingtime<=60&& hlt.isSent()==false){
//                EmailUtil.sendEmailRemainder(hlt);
//                hlt.setSent(true);
//                System.out.println("remainder has been sent");
//                DBase.UpdateHometicket(hlt);
//            }
//        }
    }
}
