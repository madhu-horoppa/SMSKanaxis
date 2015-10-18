package com.kanaxis.sms.services;

import com.kanaxis.sms.util.ResultData;

public interface TimeTableService {
	
	public ResultData getTimeTable(int classId, int sectionId) throws Exception;

	public ResultData addTimeTable(String timetableJson);

}
