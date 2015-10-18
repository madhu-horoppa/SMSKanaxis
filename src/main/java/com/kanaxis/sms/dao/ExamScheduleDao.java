package com.kanaxis.sms.dao;

import com.kanaxis.sms.util.ResultData;

public interface ExamScheduleDao {

	ResultData addExamSchedule(String examDetails);

	ResultData viewAllExamSchedules(String class_id, String examType_id);

	ResultData getExamDetailsById(String exam_id);

	ResultData updateExamDetails(String class_id, String examType_id, String subject_id, String dateOfExam, String startTime, String endTime, String exam_id);

	ResultData getExamTypes();


}
