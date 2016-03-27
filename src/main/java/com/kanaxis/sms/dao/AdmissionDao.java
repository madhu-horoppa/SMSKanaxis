package com.kanaxis.sms.dao;

import com.kanaxis.sms.model.Admission;
import com.kanaxis.sms.util.ResultData;

public interface AdmissionDao {

	public ResultData getAdmissionsList() throws Exception;
	public ResultData getAdmissionDetails(int admissionId) throws Exception;
	public ResultData createAdmission(Admission admission);
	public ResultData updateAdmission(String id, Admission admission);
}
