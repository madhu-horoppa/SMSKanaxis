package com.kanaxis.sms.dao;

import com.kanaxis.sms.util.ResultData;

public interface AdmissionDao {

	public ResultData getAdmissionsList() throws Exception;
	public ResultData getAdmissionDetails(int admissionId) throws Exception;
}
