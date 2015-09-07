package com.kanaxis.sms.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;

import com.kanaxis.sms.dao.AdmissionDao;
import com.kanaxis.sms.model.Admission;
import com.kanaxis.sms.model.Employee;
import com.kanaxis.sms.util.ResultData;

public class AdmissionDaoImpl implements AdmissionDao{
	
	@Autowired
	SessionFactory sessionFactory;

	Session session = null;
	Transaction tx = null;

	@SuppressWarnings("unchecked")
	@Override
	public ResultData getAdmissionsList() throws Exception {
		ResultData resultData = new ResultData();
		try{
		session = sessionFactory.openSession();
		tx = session.beginTransaction();
		Query query = session.createQuery("From Admission order by admissionDate desc");
		List<Admission> admissionList = query.list();
		if(admissionList!=null && !admissionList.isEmpty()){
		resultData.listData = admissionList;
		resultData.status = true;
		resultData.message = "dataFound";
		}else{
			resultData.listData = null;
			resultData.status = false;
			resultData.message = "data not found";
		}
		tx.commit();
		session.close();
		}catch(Exception e){
			resultData.listData = null;
			resultData.status = false;
			resultData.message = "Some thing went wrong please contact your admin";
		}
		return resultData;
	}
	
	@Override
	public ResultData getAdmissionDetails(int admissionId) throws Exception {
		ResultData resultData = new ResultData();
		try{
		session = sessionFactory.openSession();
		Admission admission = (Admission) session.get(Admission.class,
				new Integer(admissionId));
		if(admission!=null){
			resultData.object = admission;
			resultData.message = "data found";
			resultData.status = true;
		}else{
			resultData.object = null;
			resultData.message = "data not found";
			resultData.status = false;
		}
		tx = session.getTransaction();
		session.beginTransaction();
		tx.commit();
		}catch(Exception e){
			resultData.object = null;
			resultData.message = "Some thing went wrong please contact your admin";
			resultData.status = false;
		}
		return resultData;
	}

}
