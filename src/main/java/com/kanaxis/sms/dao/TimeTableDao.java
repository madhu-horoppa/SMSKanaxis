package com.kanaxis.sms.dao;

import com.kanaxis.sms.util.ResultData;

public interface TimeTableDao {
	
	public ResultData getTimeTable(int classId, int sectionId) throws Exception;

	public ResultData addTimeTable(String timetableJson);
	
}
