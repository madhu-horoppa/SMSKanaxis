package com.kanaxis.sms.services;

import com.kanaxis.sms.util.ResultData;

public interface TeacherService {
	
	public ResultData getTeachersList() throws Exception;
	public ResultData getTeacherDetails(int teacherId) throws Exception;
	public ResultData addTeachers(String teacherName, String qualification, String email,String exp, String photo, String phone1, String phone2, String address,String joiningDate,
            String gender, String jobTitle);
	public ResultData updateTeachers(String id, String teacherName, String qualification, String email,String exp, String photo, String phone1, String phone2, String address,String joiningDate,
            String gender, String jobTitle);
	public ResultData getTeacherByEmail(String email);

}
