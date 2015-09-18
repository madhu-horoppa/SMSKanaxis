package com.kanaxis.sms.services;

import com.kanaxis.sms.util.ResultData;

public interface ExamScheduleService {

	public ResultData addExamSchedule(String examDetails);

	public ResultData viewAllExamSchedules(String class_id, String examType_id);


	
	

}
