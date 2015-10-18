package com.kanaxis.sms.services.impl;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import org.springframework.beans.factory.annotation.Autowired;

import com.kanaxis.sms.dao.MarksDao;
import com.kanaxis.sms.services.MarksService;
import com.kanaxis.sms.util.ResultData;
import com.kanaxis.sms.util.StudentMarks;
import com.kanaxis.sms.util.TotalMarks;

public class MarksServiceImpl implements MarksService{
	
	@Autowired
	MarksDao marksDao;

	@Override
	public ResultData uploadMarks(String class_id, String section_id,
			String examType_id, Map<String,List<StudentMarks>> map, List<Map.Entry<Double, TotalMarks>> entryList) {
		return marksDao.uploadMarks(class_id, section_id, examType_id, map, entryList);
	}

	@Override
	public ResultData getAllStudentsMarks(String class_id, String section_id,
			String examType_id) {
		return marksDao.getAllStudentsMarks(class_id, section_id, examType_id);
	}

	@Override
	public ResultData updateMarks(String class_id, String section_id,
			String examType_id, String dateOfExam, String student_id,
			String subject_id, String marksObtained, String maxMarks,
			String subjectWiseRank, String marks_id) {
		return marksDao.updateMarks(class_id, section_id, examType_id, dateOfExam, student_id, subject_id, marksObtained, maxMarks, subjectWiseRank, marks_id);
	}

	@Override
	public ResultData getStudentMarks(String examType_id, String st_id) {
		return marksDao.getStudentMarks(examType_id, st_id);
	}

	@Override
	public ResultData getStudentTotalMarks(String examType_id, String st_id) {
		return marksDao.getStudentTotalMarks(examType_id, st_id);
	}

}
