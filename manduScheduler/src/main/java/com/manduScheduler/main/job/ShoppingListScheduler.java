package com.manduScheduler.main.job;

import javax.annotation.PostConstruct;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class ShoppingListScheduler {

	@Autowired
	private Scheduler scheduler;
	
	@PostConstruct
	public void init() {
		try {
			JobDetail jobDetail = JobBuilder.newJob(ShoppingJob.class)
					.withIdentity("testJob", "test")
					.build();
			
			Trigger jobTrigger = TriggerBuilder.newTrigger()
					.withSchedule(CronScheduleBuilder.cronSchedule("0 * * * * ?")) // 6시간 마다 "0 */6 * * * ?"
					.build();
			
			scheduler.scheduleJob(jobDetail, jobTrigger);
			
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}
