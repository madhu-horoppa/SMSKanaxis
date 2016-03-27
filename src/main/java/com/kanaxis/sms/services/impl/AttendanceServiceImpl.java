package com.kanaxis.sms.services.impl;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import com.kanaxis.sms.dao.AttendanceDao;
import com.kanaxis.sms.services.AttendanceService;
import com.kanaxis.sms.util.Holidays;
import com.kanaxis.sms.util.ResultData;

public class AttendanceServiceImpl implements AttendanceService{

	@Autowired
	AttendanceDao attendanceDao;
	
	@Override
	public ResultData addAttendance(String attendenceJson) {
		return attendanceDao.addAttendance(attendenceJson);
	}

	@Override
	public ResultData getAttendance(String class_id, String section_id) {
		
		return attendanceDao.getAttendance(class_id, section_id);
	}

	@Override
	public ResultData updateAttendance(String class_id, String attendance_id,
			String section_id, String dateOfAbsent, String student_id) {
		return attendanceDao.updateAttendance(class_id, attendance_id, section_id, dateOfAbsent, student_id);
	}

	@Override
	public ResultData uploadHolidays(Set<Holidays> holidaysMap) {
		return attendanceDao.uploadHolidays(holidaysMap);
	}

	

}
