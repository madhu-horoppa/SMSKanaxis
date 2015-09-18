package com.kanaxis.sms.services.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.kanaxis.sms.dao.SectionDao;
import com.kanaxis.sms.services.SectionService;
import com.kanaxis.sms.util.ResultData;

public class SectionServiceImpl implements SectionService{

	@Autowired
	SectionDao sectionDao;
	@Override
	public ResultData addSection(String class_id, String sectionName) {
		return sectionDao.addSection(class_id, sectionName);
	}
	
	@Override
	public ResultData viewAllSections() {
		// TODO Auto-generated method stub
		return sectionDao.viewAllSctions();
	}

}
