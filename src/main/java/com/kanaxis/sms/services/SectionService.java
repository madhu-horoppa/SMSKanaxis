package com.kanaxis.sms.services;

import com.kanaxis.sms.util.ResultData;

public interface SectionService {

	public ResultData addSection(String class_id, String sectionName);

	public ResultData viewAllSections();
}
