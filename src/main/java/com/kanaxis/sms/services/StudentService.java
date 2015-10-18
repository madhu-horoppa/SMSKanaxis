package com.kanaxis.sms.services;

import java.util.Vector;

import com.kanaxis.sms.util.ResultData;
import java.util.Set;

public interface StudentService {
	
	public ResultData uploadStudents(Vector<Set> vector);
	public ResultData getAllStudents(String class_id, String section_id);
	public ResultData getStudentDetails(String student_id);

}
