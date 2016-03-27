package com.kanaxis.sms.quartz;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.kanaxis.sms.scheduling.WeeklyAttendance;

public class ScheduledJob extends QuartzJobBean{

	
	private WeeklyAttendance weeklyAttendance; 
	
	
	@Override
	protected void executeInternal(JobExecutionContext arg0)
			throws JobExecutionException {
		weeklyAttendance.addWeeklyAttendance();
	}


	public void setWeeklyAttendance(WeeklyAttendance weeklyAttendance) {
		this.weeklyAttendance = weeklyAttendance;
	}

	
}
