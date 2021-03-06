package com.kanaxis.sms.services.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.kanaxis.sms.dao.AdmissionDao;
import com.kanaxis.sms.model.Admission;
import com.kanaxis.sms.services.AdmissionService;
import com.kanaxis.sms.util.ResultData;

public class AdmissionServiceImpl implements AdmissionService{
	
	@Autowired
	AdmissionDao admissionDao;

	@Override
	public ResultData getAdmissionsList() throws Exception {
		// TODO Auto-generated method stub
		return admissionDao.getAdmissionsList();
	}
	
	@Override
	public ResultData getAdmissionDetails(int admissionId) throws Exception {
		return admissionDao.getAdmissionDetails(admissionId);
	}

	@Override
	public ResultData createAdmission(Admission admission) {
		return admissionDao.createAdmission(admission);
	}

	@Override
	public ResultData updateAdmission(String id, Admission admission) {
		return admissionDao.updateAdmission(id, admission);
	}

}
