package com.kanaxis.sms.dao;

import com.kanaxis.sms.util.ResultData;

public interface ExamScheduleDao {

	ResultData addExamSchedule(String examDetails);

	ResultData viewAllExamSchedules(String class_id, String examType_id);


}
