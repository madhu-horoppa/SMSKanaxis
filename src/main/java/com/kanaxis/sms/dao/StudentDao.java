package com.kanaxis.sms.dao;

import java.util.Vector;

import com.kanaxis.sms.util.ResultData;
import com.kanaxis.sms.util.Students;

public interface StudentDao {
	
	public ResultData uploadStudents(Vector vector);
	public ResultData getAllStudents(String class_id, String section_id);
	public ResultData getStudentDetails(String student_id);

}
