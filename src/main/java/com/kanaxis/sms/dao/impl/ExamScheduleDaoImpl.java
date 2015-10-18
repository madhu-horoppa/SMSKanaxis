package com.kanaxis.sms.dao.impl;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.xmlbeans.impl.regex.REUtil;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import com.kanaxis.sms.dao.ExamScheduleDao;
import com.kanaxis.sms.model.Classes;
import com.kanaxis.sms.model.Examschedule;
import com.kanaxis.sms.model.Examtype;
import com.kanaxis.sms.model.Messagetype;
import com.kanaxis.sms.model.Subject;
import com.kanaxis.sms.util.ExamSchedule;
import com.kanaxis.sms.util.ResultData;

public class ExamScheduleDaoImpl implements ExamScheduleDao {

	@Autowired
	SessionFactory sessionFactory;

	Session session = null;
	Transaction tx = null;

	@Override
	public ResultData addExamSchedule(String examDetails) {

		session = sessionFactory.openSession();

		ResultData resultData = new ResultData();
		Calendar cal = null;
		JSONObject examDetailsJson = null;
		Query query = null;
		Examschedule examSchedule = new Examschedule();
		tx = session.beginTransaction();
		String className = "";
		String examType = "";
		String subject = "";
		String date = "";
		String startTime = "";
		String endTime = "";
		String sqlQuery = "insert into examschedule (class_id,examtype_id,subject_id,examdate,start_time,end_time,createddate) values";
		// String sql1="";

		try {

			examDetailsJson = new JSONObject(examDetails);
			if(examDetailsJson.has("className")){
			 className = examDetailsJson.getString("className");
			}else{
				resultData.status = false;
				resultData.message = "Class is mandatory";
				return resultData;
			}
			if(examDetailsJson.has("examType")){
			 examType = examDetailsJson.getString("examType");
			}else{
				resultData.status = false;
				resultData.message = "Exam type is mandatory";
				return resultData;
			}
			if(examDetailsJson.has("examDetails")){
			JSONArray examScheduleDetailsArray = examDetailsJson
					.getJSONArray("examDetails");
			for (int i = 0; i < examScheduleDetailsArray.length(); i++) {
				int j = i+1;
				if(examScheduleDetailsArray.getJSONObject(i).has("subject")){
				 subject = examScheduleDetailsArray.getJSONObject(i)
						.getString("subject");
			}else{
				resultData.status = false;
				resultData.message = "Subject is mandatory at details of "+j;
				return resultData;
			}
				if(examScheduleDetailsArray.getJSONObject(i).has("date")){
				 date = examScheduleDetailsArray.getJSONObject(i)
						.getString("date");
				}else{
					resultData.status = false;
					resultData.message = "Date is mandatory at details of "+j;
					return resultData;
				}
				if(examScheduleDetailsArray.getJSONObject(i).has("startTime")){
				 startTime = examScheduleDetailsArray.getJSONObject(i)
						.getString("startTime");
				}else{
					resultData.status = false;
					resultData.message = "Start time is mandatory at details of "+j;
					return resultData;
				}
				if(examScheduleDetailsArray.getJSONObject(i).has("endTime")){
				 endTime = examScheduleDetailsArray.getJSONObject(i)
						.getString("endTime");
				}else{
					resultData.status = false;
					resultData.message = "End time is mandatory at details of "+j;
					return resultData;
				}

				query = session
						.createQuery("from Classes where className=:class_name");
				query.setParameter("class_name", className);
				Classes classes = (Classes) query.uniqueResult();

				query = session
						.createQuery("from Examtype where exType=:ex_type");
				query.setParameter("ex_type", examType);
				Examtype exType = (Examtype) query.uniqueResult();				

				query = session
						.createQuery("from Subject where subjectName=:subject_name and classes=:class_id");
				query.setParameter("subject_name", subject);
				query.setParameter("class_id", classes);
				Subject subjectName = (Subject) query.uniqueResult();

				query = session
						.createQuery("from Examschedule where subject=:subject_id and examtype=:examtype_id and classes=:class_id");
				query.setParameter("subject_id", subjectName);
				query.setParameter("examtype_id", exType);
				query.setParameter("class_id", classes);
				examSchedule = (Examschedule) query.uniqueResult();

				if (examSchedule == null) {
					Date date1 = new SimpleDateFormat("dd-MM-yyyy").parse(date);
					java.sql.Date sqlDate = new java.sql.Date(date1.getTime());
					java.util.Date today = new java.util.Date();
					java.sql.Timestamp sqlTimestamp = new Timestamp(
							today.getTime());

					if (i == examScheduleDetailsArray.length() - 1) {
						sqlQuery = sqlQuery + "(" + classes.getId() + ","
								+ exType.getId() + "," + subjectName.getId()
								+ "," + "'" + sqlDate + "'" + "," + "'"
								+ startTime + "'" + "," + "'" + endTime + "'"
								+ "," + "'"
								+ sqlTimestamp + "'" + ");";
					} else {
						sqlQuery = sqlQuery + "(" + classes.getId() + ","
								+ exType.getId() + "," + subjectName.getId()
								+ "," + "'" + sqlDate + "'" + "," + "'"
								+ startTime + "'" + "," + "'" + endTime + "'"
								+ ","  + "'"
								+ sqlTimestamp + "'),";
					}

				} else {
					resultData.status = false;
					resultData.message = "This subject "
							+ subjectName.getSubjectName()
							+ " is already exist for " + exType.getExType()
							+ " and for class " + classes.getClassName();
					return resultData;
				}

			}
			System.out.println(sqlQuery);
			query = session.createSQLQuery(sqlQuery);
			int result = query.executeUpdate();
			tx.commit();
			session.close();
			resultData.status = true;
			resultData.message = "Exam schedule added successfully";
			}else{
				resultData.status = false;
				resultData.message = "Exam details are mandatory";
				return resultData;
			}

		} catch (Exception e) {
			tx.rollback();
			resultData.status = false;
			resultData.message = "Some thing went wrong please contact your admin";
		}
		return resultData;
	}

	@Override
	public ResultData viewAllExamSchedules(String class_id, String examType_id) {
		session = sessionFactory.openSession();

		ResultData resultData = new ResultData();
		tx = session.beginTransaction();
		String sqlQuery = "";
		ExamSchedule exams = null;
		Map detailsOfExamMap = null;
		List finalList = new ArrayList();
		List detailsOfExamList = new ArrayList();
		String classes = "";
		String examType= "";
		
		try{
			
			
			Criteria criteria  = session.createCriteria(Examschedule.class)
			 .add(Restrictions.eq("classes.id", Integer.parseInt(class_id))).add(Restrictions.eq("examtype.id", Integer.parseInt(examType_id)));
			
			List<Examschedule> examScheduleList = criteria.list();
			
			if(examScheduleList!=null && !examScheduleList.isEmpty()){
				for (Examschedule examSchedule:examScheduleList) {
					int id = examSchedule.getId();
					Map detailsOfExam = new LinkedHashMap();
					detailsOfExamMap = new LinkedHashMap();
				     examType = examSchedule.getExamtype().getExType();
					 classes = examSchedule.getClasses().getClassName();
					String subjectName = examSchedule.getSubject().getSubjectName();
					DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
					String dateOfExam = df.format(examSchedule.getExamdate());
					String startTime = examSchedule.getStartTime();
					String endTime = examSchedule.getEndTime();
					DateFormat df1 = new SimpleDateFormat("dd-MM-yyyy HH:MM:SS");
					String createdDate = df1.format(examSchedule.getCreateddate());
					detailsOfExam.put("id", id);
					detailsOfExam.put("subject", subjectName);
					detailsOfExam.put("date", dateOfExam);
					detailsOfExam.put("startTime",startTime);
					detailsOfExam.put("endTime", endTime);
					detailsOfExam.put("createdDate", createdDate);
					
					detailsOfExamList.add(detailsOfExam);
					
					
					
				}
				
				/*detailsOfExamMap.put("className", classes);
				detailsOfExamMap.put("examType", examType);*/
				detailsOfExamMap.put("examDetails", detailsOfExamList);
				
				
				resultData.map = detailsOfExamMap;
				resultData.status = true;
				resultData.message = "Data found";
			}else{
				resultData.map = null;
				resultData.status = false;
				resultData.message = "No data found";
			}
			
			
		}catch(Exception e){
			resultData.map = null;
			resultData.status = false;
			resultData.message = "Some thing went wrong please contact your admin";
		}
		session.close();
		return resultData;
	}

	@Override
	public ResultData getExamDetailsById(String exam_id) {
		session = sessionFactory.openSession();
		ResultData resultData = new ResultData();
		tx = session.beginTransaction();
		Map examScheduleMap = new LinkedHashMap();
		try{
			Examschedule examschedule = (Examschedule) session.get(Examschedule.class, Integer.parseInt(exam_id));
			if(examschedule!=null){
				examScheduleMap.put("class", examschedule.getClasses().getClassName());
				examScheduleMap.put("examType", examschedule.getExamtype().getExType());
				examScheduleMap.put("subject", examschedule.getSubject().getSubjectName());
				examScheduleMap.put("dateOfExam",examschedule.getExamdate());
				examScheduleMap.put("startTime", examschedule.getStartTime());
				examScheduleMap.put("endTime", examschedule.getEndTime());			
				
			}else{
				resultData.map = null;
				resultData.status = false;
				resultData.message = "Data not found";
				return resultData;
			}
			resultData.map = examScheduleMap;
			resultData.status = true;
			resultData.message = "Data found";
		}catch(Exception e){
			resultData.map = null;
			resultData.status = false;
			resultData.message = "Some thing went wrong please contact your admin";
			
		}
		session.close();
		return resultData;
	}

	@Override
	public ResultData updateExamDetails(String class_id, String examType_id, String subject_id, String dateOfExam, String startTime, String endTime, String exam_id) {
		session = sessionFactory.openSession();
		ResultData resultData = new ResultData();
		tx = session.beginTransaction();		
		try{
			Criteria criteria = session.createCriteria(Examschedule.class).add(Restrictions.eq("examtype.id", Integer.parseInt(examType_id))).add(Restrictions.eq("classes.id", Integer.parseInt(class_id))).add(Restrictions.eq("subject.id", Integer.parseInt(subject_id)));
			Examschedule examschedule = (Examschedule) criteria.uniqueResult();
			if(Integer.parseInt(exam_id) == examschedule.getId()){
				Date date1 = new SimpleDateFormat("dd-MM-yyyy").parse(dateOfExam);
				java.sql.Date sqlDate = new java.sql.Date(date1.getTime());
				
				String sqlQuery = "update examschedule set subject_id="+subject_id+",examtype_id="+examType_id+",class_id="+class_id+",examdate="+"'"+sqlDate+"'"+",start_time="+"'"+startTime+"'"+
						           ",end_time="+"'"+endTime+"'"+" where id="+exam_id;
				System.out.println(sqlQuery);
				Query query = session.createSQLQuery(sqlQuery);
				int result = query.executeUpdate();
				tx.commit();
				session.close();
				resultData.status = true;
				resultData.message = "Exam details updated successfully";
				
				
			}else{
				resultData.status = false;
				resultData.message = "This subject "
						+ examschedule.getSubject().getSubjectName()
						+ " is already exist for " + examschedule.getExamtype().getExType()
						+ " and for class " + examschedule.getClasses().getClassName();
				return resultData;
			}
			
		}catch(Exception e){
			resultData.status = false;
			resultData.message = "Some t hing went wrong please contact your admin";
		}
		return resultData;
	}

	@Override
	public ResultData getExamTypes() {
		
		session = sessionFactory.openSession();
		ResultData resultData = new ResultData();
		tx = session.beginTransaction();
		Map examTypeMap = new LinkedHashMap();
		try{
			Query query = session.createQuery("from Examtype");
			List<Examtype> examTypeList = query.list();
			if(examTypeList!=null && !examTypeList.isEmpty()){
				for(Examtype examType: examTypeList){
					
					examTypeMap.put(examType.getId(), examType.getExType());
					
				}
				
				resultData.map = examTypeMap;
				resultData.status = true;
				resultData.message = "Data found";
			}else{
				resultData.map = null;
				resultData.status = false;
				resultData.message = "Data not found";
			}
			
		}catch(Exception e){
			resultData.status = false;
			resultData.message = "Some thing went wrong please contact your admin";
		}
		return resultData;
	}

	

}
