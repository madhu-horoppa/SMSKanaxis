package com.kanaxis.sms.services.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.kanaxis.sms.dao.SectionDao;
import com.kanaxis.sms.services.SectionService;
import com.kanaxis.sms.util.ResultData;

public class SectionServiceImpl implements SectionService{

	@Autowired
	SectionDao sectionDao;
	@Override
	public ResultData addSection(String sectionJson) {
		return sectionDao.addSection(sectionJson);
	}
	
	@Override
	public ResultData viewAllSections() {
		// TODO Auto-generated method stub
		return sectionDao.viewAllSctions();
	}

	@Override
	public ResultData getSectionByClass(String class_id) {
		// TODO Auto-generated method stub
		return sectionDao.getSectionByClass(class_id);
	}

	@Override
	public ResultData getAllClasses() {
		return sectionDao.getAllclasses();
	}

	@Override
	public ResultData updateSections(String class_id, String section,
			String section_id) {
		return sectionDao.updateSections(class_id, section, section_id);
	}

}
