package com.kanaxis.sms.dao;

import com.kanaxis.sms.util.ResultData;

public interface SectionDao {

	public ResultData addSection(String class_id, String sectionName);

	public ResultData viewAllSctions();
}
