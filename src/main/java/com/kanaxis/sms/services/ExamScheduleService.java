package com.kanaxis.sms.services;

import com.kanaxis.sms.util.ResultData;

public interface ExamScheduleService {

	public ResultData addExamSchedule(String examDetails);

	public ResultData viewAllExamSchedules(String class_id, String examType_id);

	public ResultData getExamDetailsById(String exam_id);

	public ResultData updateExamDetails(String class_id, String examType_id, String subject_id, String dateOfExam, String startTime, String endTime, String exam_id);

	public ResultData getExamTypes();


	
	

}
