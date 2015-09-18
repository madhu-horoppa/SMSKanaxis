package com.kanaxis.sms.dao.impl;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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
		String sqlQuery = "insert into examschedule (class_id,examtype_id,subject_id,examdate,start_time,end_time,createddate) values";
		// String sql1="";

		try {

			examDetailsJson = new JSONObject(examDetails);
			String className = examDetailsJson.getString("className");
			String examType = examDetailsJson.getString("examType");
			JSONArray examScheduleDetailsArray = examDetailsJson
					.getJSONArray("examDetails");
			for (int i = 0; i < examScheduleDetailsArray.length(); i++) {
				String subject = examScheduleDetailsArray.getJSONObject(i)
						.getString("subject");
				String date = examScheduleDetailsArray.getJSONObject(i)
						.getString("date");
				String startTime = examScheduleDetailsArray.getJSONObject(i)
						.getString("startTime");
				String endTime = examScheduleDetailsArray.getJSONObject(i)
						.getString("endTime");

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
					cal = Calendar.getInstance();
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
					Map detailsOfExam = new HashMap();
					detailsOfExamMap = new HashMap();
				     examType = examSchedule.getExamtype().getExType();
					 classes = examSchedule.getClasses().getClassName();
					String subjectName = examSchedule.getSubject().getSubjectName();
					DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
					String dateOfExam = df.format(examSchedule.getExamdate());
					String startTime = examSchedule.getStartTime();
					String endTime = examSchedule.getEndTime();
					DateFormat df1 = new SimpleDateFormat("dd-MM-yyyy HH:MM:SS");
					String createdDate = df1.format(examSchedule.getCreateddate());
					 
					detailsOfExam.put("subject", subjectName);
					detailsOfExam.put("date", dateOfExam);
					detailsOfExam.put("startTime",startTime);
					detailsOfExam.put("endTime", endTime);
					detailsOfExam.put("createdDate", createdDate);
					
					detailsOfExamList.add(detailsOfExam);
					
					
					
				}
				
				detailsOfExamMap.put("className", classes);
				detailsOfExamMap.put("examType", examType);
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

	

}
