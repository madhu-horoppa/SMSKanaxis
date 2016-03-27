package com.kanaxis.sms.quartz;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.kanaxis.sms.scheduling.MonthlyAttendance;

public class MonthlyScheduledJob extends QuartzJobBean{
	
private MonthlyAttendance monthlyAttendance; 
	
	
	@Override
	protected void executeInternal(JobExecutionContext arg0)
			throws JobExecutionException {
		monthlyAttendance.addMonthlyAttendance();;
	}


	public void setMonthlyAttendance(MonthlyAttendance monthlyAttendance) {
		this.monthlyAttendance = monthlyAttendance;
	}


	


	

}
