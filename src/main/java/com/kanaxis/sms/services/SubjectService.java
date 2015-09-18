package com.kanaxis.sms.services;

import com.kanaxis.sms.util.ResultData;

public interface SubjectService {

	public ResultData addSubjects(String subjectsDetails);

	public ResultData getAllSubjects();

	public ResultData getSubjectsByClass(String class_id);

}
