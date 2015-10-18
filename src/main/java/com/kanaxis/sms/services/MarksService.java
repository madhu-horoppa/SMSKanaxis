package com.kanaxis.sms.services;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import com.kanaxis.sms.util.ResultData;
import com.kanaxis.sms.util.StudentMarks;
import com.kanaxis.sms.util.TotalMarks;

public interface MarksService {

	ResultData uploadMarks(String class_id, String section_id,
			String examType_id, Map<String,List<StudentMarks>> map, List<Map.Entry<Double, TotalMarks>> entryList);

	ResultData getAllStudentsMarks(String class_id, String section_id,
			String examType_id);

	ResultData updateMarks(String class_id, String section_id,
			String examType_id, String dateOfExam, String student_id,
			String subject_id, String marksObtained, String maxMarks,
			String subjectWiseRank, String marks_id);

	ResultData getStudentMarks(String examType_id, String st_id);

	ResultData getStudentTotalMarks(String examType_id, String st_id);

}
