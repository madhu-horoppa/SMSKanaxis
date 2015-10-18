package com.kanaxis.sms.dao;

import com.kanaxis.sms.util.ResultData;

public interface AttendanceDao {

	ResultData addAttendance(String attendenceJson);

	ResultData getAttendance(String class_id, String section_id);

	ResultData updateAttendance(String class_id, String attendance_id,
			String section_id, String dateOfAbsent, String student_id);

}
