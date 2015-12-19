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

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * @author Administrator
 *
 */
public class TestJob implements Job{
	
	private static int i = 0;
	
	private static final String P = "yyyy-MM-dd HH:mm:ss";
	
	
	@Override
	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		i++;
		System.out.println("aaa");
		if(i == 2){
			Quartz.shutDown();
			return;
		}
		try {
			Quartz.next(new SimpleDateFormat(P).parse("2014-12-31 22:47:00"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
	}

}
