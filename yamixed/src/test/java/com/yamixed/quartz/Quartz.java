/**
 * <br>
 * ------------------------------------------------------------<br>
 * History<br>
 * ------------------------------------------------------------<br>
 * Legend:<br>
 * 　(+) added feature<br>
 * 　(-) deleted feature<br>
 * 　(#) fixed bug<br>
 * 　(^) upgraded implementation<br>
 *<br>
 * V1.00.00 2012-2-24 limj 新建
 * @author limj
 * @since V1.00.00
 */
package com.yamixed.quartz;

import java.util.Date;

import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleTrigger;
import org.quartz.impl.StdSchedulerFactory;

/**
 * @author Administrator
 *
 */
public class Quartz {
	
	private static Scheduler scheduler;
	
	private static SimpleTrigger simpleTrigger;
	
	
	public static void build(Date start){
		try {
			scheduler = new StdSchedulerFactory().getScheduler();
			JobDetail jobDetail = new JobDetail("jobDetail-s1", "jobDetailGroup-s1", TestJob.class); 
			simpleTrigger = new SimpleTrigger("simpleTrigger", "triggerGroup-s1");  
			simpleTrigger.setStartTime(start);
			scheduler.scheduleJob(jobDetail, simpleTrigger);  
			scheduler.start();
		} catch (SchedulerException e) {
			e.printStackTrace();
		}  
	}
	
	
	public static void next(Date next){
		simpleTrigger = new SimpleTrigger("simpleTrigger1", "triggerGroup-s1");  
		simpleTrigger.setJobName("jobDetail-s1");
		simpleTrigger.setJobGroup("jobDetailGroup-s1");
		simpleTrigger.setStartTime(next);
		try {
			Date date = scheduler.rescheduleJob("simpleTrigger", "triggerGroup-s1", simpleTrigger);
			System.out.println(date);
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}
	
	
	public static void shutDown(){
		try {
			scheduler.shutdown();
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}

}
