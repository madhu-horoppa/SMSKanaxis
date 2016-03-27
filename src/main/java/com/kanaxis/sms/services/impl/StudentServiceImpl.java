package com.kanaxis.sms.services.impl;

import java.util.Set;
import java.util.Vector;

import org.springframework.beans.factory.annotation.Autowired;

import com.kanaxis.sms.dao.AdmissionDao;
import com.kanaxis.sms.dao.StudentDao;
import com.kanaxis.sms.model.Student;
import com.kanaxis.sms.services.StudentService;
import com.kanaxis.sms.util.ResultData;
import com.kanaxis.sms.util.Students;

public class StudentServiceImpl implements StudentService{
	
	@Autowired
	StudentDao studentDao;

	@Override
	public ResultData uploadStudents(Vector<Set> vector) {
		return studentDao.uploadStudents(vector);
	}

	@Override
	public ResultData getAllStudents(String class_id, String section_id) {
		return studentDao.getAllStudents(class_id, section_id);
	}

	@Override
	public ResultData getStudentDetails(String student_id) {
		// TODO Auto-generated method stub
		return studentDao.getStudentDetails(student_id);
	}

	@Override
	public ResultData updateStudent(String id, Student student) {
		// TODO Auto-generated method stub
		return studentDao.updateStudent(id, student);
	}

	

}
