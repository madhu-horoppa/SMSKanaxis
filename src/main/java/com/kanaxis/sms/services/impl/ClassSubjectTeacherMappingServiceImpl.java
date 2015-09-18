package com.kanaxis.sms.services.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.kanaxis.sms.dao.impl.ClassSubjectTeacherMappingDao;
import com.kanaxis.sms.services.ClassSubjectTeacherMappingService;
import com.kanaxis.sms.util.ResultData;

public class ClassSubjectTeacherMappingServiceImpl implements ClassSubjectTeacherMappingService {
	
	@Autowired
	ClassSubjectTeacherMappingDao classSubjectTeacherMappingDao;

	@Override
	public ResultData addClassSubjectTeacherMapping(String addClassSubjectTeacherMappingJson) {
		
		return classSubjectTeacherMappingDao.addClassSubjectTeacherMapping(addClassSubjectTeacherMappingJson);
				                                              
		
	}

	@Override
	public ResultData getAllClassSubjectTeacherMappings(String class_id,
			String section_id) {
		return classSubjectTeacherMappingDao.getAllClassSubjectTeacherMappings(class_id, section_id);
	}

}
