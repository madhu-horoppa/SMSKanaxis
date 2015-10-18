package com.kanaxis.sms.services.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.kanaxis.sms.dao.SubjectDao;
import com.kanaxis.sms.services.SubjectService;
import com.kanaxis.sms.util.ResultData;

public class SubjectServiceImpl implements SubjectService{

	@Autowired
	SubjectDao subjectDao;
	@Override
	public ResultData addSubjects(String subjectsDetails) {
		return subjectDao.addSubjects(subjectsDetails);
	}
	@Override
	public ResultData getAllSubjects() {
		// TODO Auto-generated method stub
		return subjectDao.getAllsubjects();
	}
	@Override
	public ResultData getSubjectsByClass(String class_id) {
		// TODO Auto-generated method stub
		return subjectDao.getSubjectsByClass(class_id);
	}
	@Override
	public ResultData updateSubjects(String class_id, String subjectName,
			String subject_id) {
		return subjectDao.updateSubjects(class_id, subjectName, subject_id);
	}

}
