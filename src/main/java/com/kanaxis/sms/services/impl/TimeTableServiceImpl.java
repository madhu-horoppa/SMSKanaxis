package com.kanaxis.sms.services.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.kanaxis.sms.dao.AdmissionDao;
import com.kanaxis.sms.dao.TimeTableDao;
import com.kanaxis.sms.services.TimeTableService;
import com.kanaxis.sms.util.ResultData;

public class TimeTableServiceImpl implements TimeTableService{
	
	@Autowired
	TimeTableDao timeTableDao;

	@Override
	public ResultData getTimeTable(int classId, int sectionId) throws Exception {
		// TODO Auto-generated method stub
		return timeTableDao.getTimeTable(classId, sectionId);
	}

}
