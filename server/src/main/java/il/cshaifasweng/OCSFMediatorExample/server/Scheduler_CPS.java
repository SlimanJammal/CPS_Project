package il.cshaifasweng.OCSFMediatorExample.server;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

public class Scheduler_CPS {
    public static void startJobScheduling() {
        // todo implementation change all

        System.out.println("the reminder is working");
        try{
            JobDetail job1 = JobBuilder.newJob(Reminder.class)
                    .withIdentity("job1", "group1").build();

            Trigger trigger1 = TriggerBuilder.newTrigger()
                    .withIdentity("cronTrigger1", "group1")
                    .withSchedule(CronScheduleBuilder.cronSchedule("0/30 * * * * ?"))
                    .build();

            Scheduler schedulerCPS1 = new StdSchedulerFactory().getScheduler();
            schedulerCPS1.start();
            schedulerCPS1.scheduleJob(job1, trigger1);

            Thread.sleep(100000);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    }

