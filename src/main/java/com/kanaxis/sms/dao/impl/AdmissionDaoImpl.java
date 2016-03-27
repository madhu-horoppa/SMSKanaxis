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

	@Override
	public ResultData createAdmission(Admission admission) {
		ResultData resultData = new ResultData();
		session = sessionFactory.openSession();
		tx = session.beginTransaction();
		try{
			Query query = session.createQuery("from Admission where email=:email");
			query.setParameter("email", admission.getEmail());
			Admission admission1 = (Admission) query.uniqueResult();
			
			Query query1 = session.createQuery("from Admission where admissionNum=:admission_num");
			query.setParameter("admission_num", admission.getAdmissionNum());
			Admission admission2 = (Admission) query.uniqueResult(); 
			
			if(admission1 == null && admission2==null){
		session.save(admission);
		tx.commit();
		session.close();
		resultData.status=true;
		resultData.message = "Admission created successfully";
			}else{
				resultData.status = false;
				resultData.message = "Admission already exist with this email or admission number";
			}
		}catch(Exception e){
			resultData.message = "Some thing went wrong please contact your admin";
			resultData.status = false;
		}

		return resultData;
	}

	@Override
	public ResultData updateAdmission(String id, Admission admission) {
		ResultData resultData = new ResultData();
		session = sessionFactory.openSession();
		tx = session.beginTransaction();
		try{
			
			Admission admission2 = (Admission) session.get(Admission.class, Integer.parseInt(id));			
			
			
			if(admission2!=null){
				
				Query query = session.createQuery("from Admission where email=:email");
				query.setParameter("email", admission.getEmail());
				Admission admissionDetails = (Admission) query.uniqueResult();
				if(admissionDetails!=null && Integer.parseInt(id)==admissionDetails.getId() || admissionDetails==null){
				admission2.setAdmissionNum(admission.getAdmissionNum());
				admission2.setAdmissionDate(admission.getAdmissionDate());
				admission2.setFirstName(admission.getFirstName());
				admission2.setLastName(admission.getLastName());
				admission2.setClass_(admission.getClass_());
				admission2.setDateOfBirth(admission.getDateOfBirth());
				admission2.setGender(admission.getGender());
				admission2.setBloodGroup(admission.getBloodGroup());
				admission2.setBirthPlace(admission.getBirthPlace());
				admission2.setNationality(admission.getNationality());
				admission2.setReligion(admission.getReligion());
				admission2.setCastCategory(admission.getCastCategory());
				admission2.setPhysicalDisability(admission.getPhysicalDisability());
				admission2.setSubcast(admission.getSubcast());
				admission2.setMotherToungue(admission.getMotherToungue());
				admission2.setLocalAddress(admission.getLocalAddress());
				admission2.setCity(admission.getCity());
				admission2.setState(admission.getState());
				admission2.setPincode(admission.getPincode());
				admission2.setPermAddress(admission.getPermAddress());
				admission2.setPermCity(admission.getPermCity());
				admission2.setPermState(admission.getPermState());
				admission2.setPermPincode(admission.getPermPincode());
				admission2.setPhoneNumber(admission.getPhoneNumber());
				admission2.setEmail(admission.getEmail());
				if(admission.getImage()!=null){
					admission2.setImage(admission.getImage());
				}
				admission2.setMotherFullName(admission.getMotherFullName());
				admission2.setMotherOccupation(admission.getMotherOccupation());
				if(admission.getMotherEducation()!=null){
					admission2.setMotherEducation(admission.getMotherEducation());					
				}
				
				if(admission.getMotherIncome()!=null){
					admission2.setMotherIncome(admission.getMotherIncome());					
				}
				admission2.setFatherFullName(admission.getFatherFullName());
				admission2.setFatherOccupation(admission.getFatherOccupation());
				admission2.setFatherEducation(admission.getFatherEducation());
				admission2.setFatherIncome(admission.getFatherIncome());
				admission2.setParentAsGuardian(admission.getParentAsGuardian());
				if(admission.getPrimaryContactNum()!=null){
					admission2.setPrimaryContactNum(admission.getPrimaryContactNum());
				}
				if(admission.getSecondaryContactNumber()!=null){
					admission2.setSecondaryContactNumber(admission.getSecondaryContactNumber());
				}
				if(admission.getPrevInstitution()!=null){
					admission2.setPrevInstitution(admission.getPrevInstitution());
				}
				if(admission.getYear()!=null){
					admission2.setYear(admission.getYear());
				}
				if(admission.getPreviousClass()!=null){
					admission2.setPreviousClass(admission.getPreviousClass());
				}
				if(admission.getTotalMarks()!=null){
					admission2.setTotalMarks(admission.getTotalMarks());
				}
				if(admission.getMarksObtained()!=null){
					admission2.setMarksObtained(admission.getMarksObtained());
				}
				session.update(admission2);
				tx.commit();
				session.close();
				resultData.status=true;
				resultData.message = "Admission updated successfully";
			}else{
				resultData.status = false;
				resultData.message = "Admission already exist with this email";
			}
			}
			
		}catch(Exception e){
			resultData.status = false;
			resultData.message = "Some thing went wrong please contact your admin";
		}
		return resultData;
	}

}
