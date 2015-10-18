package com.kanaxis.sms.services;

import com.kanaxis.sms.util.ResultData;

public interface SectionService {

	public ResultData addSection(String sectionJson);

	public ResultData viewAllSections();

	public ResultData getSectionByClass(String class_id);

	public ResultData getAllClasses();

	public ResultData updateSections(String class_id, String section,
			String section_id);
}
