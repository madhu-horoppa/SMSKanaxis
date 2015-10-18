package com.kanaxis.sms.dao;

import com.kanaxis.sms.util.ResultData;

public interface SubjectDao {

	public ResultData addSubjects(String subjectsDetails);

	public ResultData getAllsubjects();

	public ResultData getSubjectsByClass(String class_id);

	public ResultData updateSubjects(String class_id, String subjectName,
			String subject_id);

}
