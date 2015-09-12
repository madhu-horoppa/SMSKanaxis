package com.kanaxis.sms.dao.impl;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import com.kanaxis.sms.dao.ExamScheduleDao;
import com.kanaxis.sms.model.Classes;
import com.kanaxis.sms.model.Examschedule;
import com.kanaxis.sms.model.Examtype;
import com.kanaxis.sms.model.Messagetype;
import com.kanaxis.sms.model.Subject;
import com.kanaxis.sms.util.ResultData;

public class ExamScheduleDaoImpl implements ExamScheduleDao{
	
	@Autowired
	SessionFactory sessionFactory;

	Session session = null;
	Transaction tx = null;

	@Override
	public ResultData addExamSchedule(String examDetails) {
		
		session = sessionFactory.openSession();
		
		ResultData resultData = new ResultData();
		Calendar  cal = null;
		JSONObject examDetailsJson = null;
		Query query = null;
		Examschedule examSchedule = new Examschedule();
		tx = session.beginTransaction();
		String sqlQuery = "insert into examschedule (class_id,examtype_id,subject_id,examdate,start_time,end_time,messagetype_id,createddate) values";
		String sql1="";
		
		
		try{
			
			examDetailsJson = new JSONObject(examDetails);
			 String className = examDetailsJson.getString("className");
			 String examType = examDetailsJson.getString("examType");
			 String messageType = examDetailsJson.getString("messageType");
			 //String examScheduleDetails = examDetailsJson.getString("examDetails");
			 JSONArray examScheduleDetailsArray = examDetailsJson.getJSONArray("examDetails");
			 for(int i=0;i<examScheduleDetailsArray.length();i++){
				 String subject = examScheduleDetailsArray.getJSONObject(i).getString("subject");
				 String date = examScheduleDetailsArray.getJSONObject(i).getString("date");
				 String startTime = examScheduleDetailsArray.getJSONObject(i).getString("startTime");
				 String endTime = examScheduleDetailsArray.getJSONObject(i).getString("endTime");
				 
				 query = session.createQuery("from Classes where className=:class_name");
				 query.setParameter("class_name", className);
				 Classes classes = (Classes) query.uniqueResult();
				 
				 query = session.createQuery("from Examtype where exType=:ex_type");
				 query.setParameter("ex_type", examType);
				 Examtype exType = (Examtype) query.uniqueResult();
				 
				 query = session.createQuery("from Messagetype where messageType=:message_type");
				 query.setParameter("message_type", messageType);
				 Messagetype msgType = (Messagetype) query.uniqueResult();
				 
				 query = session.createQuery("from Subject where subjectName=:subject_name and classes=:class_id");
				 query.setParameter("subject_name", subject);
				 query.setParameter("class_id", classes);
				 Subject subjectName = (Subject) query.uniqueResult();
				 cal = Calendar.getInstance();
				 Date date1 = new SimpleDateFormat("dd-MM-yyyy").parse(date);				 
				 java.sql.Date sqlDate = new java.sql.Date(date1.getTime());				 
				 java.util.Date today = new java.util.Date();
				 java.sql.Timestamp sqlTimestamp= new Timestamp(today.getTime()); 
				 
				 if(i==examScheduleDetailsArray.length()-1)
				 {
				 sql1 =sql1+ "("+ classes.getId()+","+
				            exType.getId()+","+
						    subjectName.getId()+","+
				            "\""+sqlDate+"\""+","+"\""+startTime+"\""+","+"\""+endTime+"\""+","+msgType.getId()+","+"\""+sqlTimestamp+"\""+");";
				 }
				 /*else
				 {
					 sql1 =sql1+ "("+ classes.getId()+","+
					            exType.getId()+","+
							    subjectName.getId()+","+
					            "\""+sqlDate+"\""+","+"\""+startTime+"\""+","+"\""+endTime+"\""+","+msgType.getId()+","+"\""+sqlTimestamp+"\""+"),";
				 }*/
				 /*examSchedule.setClasses(classes);
				 examSchedule.setExamtype(exType);
				 examSchedule.setMessagetype(msgType);
				 examSchedule.setSubject(subjectName);
				 
				 DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
				 examSchedule .setExamdate(df.parse(date));
				 examSchedule.setStartTime(startTime);
				 examSchedule.setEndTime(endTime);
				 
				 cal = Calendar.getInstance();
				 examSchedule.setCreateddate(cal.getTime());
				 session.save(examSchedule);*/
				 
				 
			 }			 
			 System.out.println(sqlQuery+sql1);
				query = session.createSQLQuery(sqlQuery);
				int result = query.executeUpdate();
			    tx.commit();
				session.close();
			 resultData.status = true;
			 resultData.message = "Exam schedule added successfully";
			 
			
			
		}catch(Exception e){
			e.printStackTrace();
			tx.rollback();
			resultData.status = false;
			 resultData.message = "Some thing went wrong please contact your admin";
		}
			return resultData;
	}

}
