package com.kanaxis.sms.services.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.kanaxis.sms.dao.AdmissionDao;
import com.kanaxis.sms.dao.TeacherDao;
import com.kanaxis.sms.model.Teachers;
import com.kanaxis.sms.services.TeacherService;
import com.kanaxis.sms.util.ResultData;

public class TeacherServiceImpl implements TeacherService{
	
	@Autowired
	TeacherDao teacherDao;

	@Override
	public ResultData getTeachersList() throws Exception {
		// TODO Auto-generated method stub
		return teacherDao.getTeachersList();
	}

	@Override
	public ResultData getTeacherDetails(int teacherId) throws Exception {
		// TODO Auto-generated method stub
		return teacherDao.getTeacherDetails(teacherId);
	}

	@Override
	public ResultData addTeachers(String teacherName, String qualification,
			String email, String exp, String photo, String phone1,
			String phone2, String address, String joiningDate, String gender,
			String jobTitle) {
		ResultData resultData = getTeacherByEmail(email);
		if(resultData.status){
		return teacherDao.addTechers(teacherName, qualification, email, exp, photo, phone1, phone2, address, joiningDate, gender, jobTitle);
		}else{
			return resultData;
		}
	}

	@Override
	public ResultData updateTeachers(String id, String teacherName, String qualification,
			String email, String exp, String photo, String phone1,
			String phone2, String address, String joiningDate, String gender,
			String jobTitle) {
		ResultData resultData = getTeacherByEmail(email);
		if(resultData.status){
			return teacherDao.updateTeachers(id, teacherName, qualification, email, exp, photo, phone1, phone2, address, joiningDate, gender, jobTitle);
		}else{
			Teachers teacher = (Teachers) resultData.object;
			if(Integer.parseInt(id) == teacher.getId()){
		return teacherDao.updateTeachers(id, teacherName, qualification, email, exp, photo, phone1, phone2, address, joiningDate, gender, jobTitle);
			}else{
				return resultData;
			}
		}
	}

	@Override
	public ResultData getTeacherByEmail(String email) {
		
		return teacherDao.getTeacherByEmail(email);
	}

}
