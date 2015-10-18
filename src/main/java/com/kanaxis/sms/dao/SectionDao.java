package com.kanaxis.sms.dao;

import com.kanaxis.sms.util.ResultData;

public interface SectionDao {

	public ResultData addSection(String sectionJson);

	public ResultData viewAllSctions();

	public ResultData getSectionByClass(String class_id);

	public ResultData getAllclasses();

	public ResultData updateSections(String class_id, String section,
			String section_id);
}
