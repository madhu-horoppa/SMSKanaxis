package com.kanaxis.sms.dao.impl;

import com.kanaxis.sms.util.ResultData;

public interface ClassSubjectTeacherMappingDao {
	

	ResultData addClassSubjectTeacherMapping(String addClassSubjectTeacherMappingJson);

	ResultData getAllClassSubjectTeacherMappings(String class_id,
			String section_id);

	ResultData updateClassSubjectTeacherMapping(String class_id,
			String subject_id, String teacher_id, String section_id, String mapping_id);

}
