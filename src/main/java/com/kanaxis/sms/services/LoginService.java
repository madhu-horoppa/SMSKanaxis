package com.kanaxis.sms.services;

import com.kanaxis.sms.util.ResultData;
import com.kanaxis.sms.util.UserDetails;


public interface LoginService {
	
	public UserDetails login(String userName, String password) throws Exception;

	public ResultData addParentCredentials(String class_id, String section_id,
			String student_id, String userName);

	public ResultData getAllParentsCredentials(String class_id,
			String section_id);

	public ResultData getCredentialDetails(String user_id);

	public ResultData addTeacherCredentials(String teacher_id, String userName);

	public ResultData getAllTeachersCredentials();

	public ResultData deactivateUser(String user_id, String status);

	public ResultData changePassword(String userName, String password);

}
