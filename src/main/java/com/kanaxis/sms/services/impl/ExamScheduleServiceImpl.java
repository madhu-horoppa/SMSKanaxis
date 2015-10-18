package com.kanaxis.sms.services.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.kanaxis.sms.dao.ExamScheduleDao;
import com.kanaxis.sms.services.ExamScheduleService;
import com.kanaxis.sms.util.ResultData;

public class ExamScheduleServiceImpl implements ExamScheduleService{
	
	@Autowired
	ExamScheduleDao examScheduleDao;

	@Override
	public ResultData addExamSchedule(String examDetails) {
		return examScheduleDao.addExamSchedule(examDetails);
	}

	@Override
	public ResultData viewAllExamSchedules(String class_id, String examType_id) {
		// TODO Auto-generated method stub
		return examScheduleDao.viewAllExamSchedules(class_id, examType_id);
	}

	@Override
	public ResultData getExamDetailsById(String exam_id) {
		return examScheduleDao.getExamDetailsById(exam_id);
	}

	@Override
	public ResultData updateExamDetails(String class_id, String examType_id, String subject_id, String dateOfExam,
			String startTime, String endTime, String exam_id) {
		return examScheduleDao.updateExamDetails(class_id, examType_id, subject_id, dateOfExam, startTime, endTime, exam_id);
	}

	@Override
	public ResultData getExamTypes() {
		return examScheduleDao.getExamTypes();
	}

	

	

}
