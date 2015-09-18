package com.kanaxis.sms.services;

import com.kanaxis.sms.util.ResultData;

public interface ClassSubjectTeacherMappingService {

	ResultData addClassSubjectTeacherMapping(String addClassSubjectTeacherMappingJson);

	ResultData getAllClassSubjectTeacherMappings(String class_id,
			String section_id);

}
