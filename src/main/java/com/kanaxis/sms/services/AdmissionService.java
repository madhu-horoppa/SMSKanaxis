package com.kanaxis.sms.services;

import com.kanaxis.sms.util.ResultData;

public interface AdmissionService {
	
	public ResultData getAdmissionsList() throws Exception;
	public ResultData getAdmissionDetails(int admissionId) throws Exception;

}
