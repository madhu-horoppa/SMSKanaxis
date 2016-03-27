package com.kanaxis.sms.dao.impl;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import com.kanaxis.sms.dao.AttendanceDao;
import com.kanaxis.sms.model.Attendance;
import com.kanaxis.sms.model.Section;
import com.kanaxis.sms.model.Student;
import com.kanaxis.sms.util.Holidays;
import com.kanaxis.sms.util.ResultData;

public class AttendanceDaoImpl implements AttendanceDao{
	
	@Autowired
	SessionFactory sessionFactory;

	Session session = null;
	Transaction tx = null;

	@Override
	public ResultData addAttendance(String attendenceJson) {
		session = sessionFactory.openSession();
		tx = session.beginTransaction();
		ResultData resultData = new ResultData();
		JSONObject jsonObject = null;
		String class_id = "";
		String section_id = "";
		String student_id = "";
		String dateOfAbsent = "";
		JSONArray jsonArray = null;
		
		String sqlQuery = "insert into attendance (st_id, status, dateOfAbsent, classes_id, section_id) values";
		try{
			jsonObject = new JSONObject(attendenceJson);
			if(jsonObject.has("class_id")){
				class_id = jsonObject.getString("class_id");
			}else{
				resultData.status = false;
				resultData.message = "Class is mandatory";
				return resultData;
			}
			
			if(jsonObject.has("section_id")){
				section_id = jsonObject.getString("section_id");
				
			}else{
				resultData.status = false;
				resultData.message = "Section is mandatory";
				return resultData;
			}
			if(jsonObject.has("dateOfAbsent")){
				dateOfAbsent = jsonObject.getString("dateOfAbsent");
				
				
			}else{
				resultData.status = false;
				resultData.message = "Section is mandatory";
				return resultData;
			}
			if(jsonObject.has("absentDetails")){
				Date date1 = new SimpleDateFormat("dd-MM-yyyy").parse(dateOfAbsent);
				java.sql.Date sqlDate = new java.sql.Date(date1.getTime());
				
				//to get all student ids by class and by section
				Criteria criteria = session.createCriteria(Section.class).add(Restrictions.eq("id", Integer.parseInt(section_id))).add(Restrictions.eq("classes.id", Integer.parseInt(class_id)));
				Section section = (Section) criteria.uniqueResult();
				Query query = session.createQuery("from Attendance where dateOfAbsent=:dateOfAbsent and classes=:classes_id and section=:section_id");
				query.setParameter("dateOfAbsent", date1);
				query.setParameter("classes_id", section.getClasses());
				query.setParameter("section_id", section);
				List<Attendance> attendanceList = query.list();
				if(attendanceList==null || attendanceList.isEmpty()){
				Criteria criteria1 = session.createCriteria(Student.class).add(Restrictions.eq("classes.id", Integer.parseInt(class_id))).add(Restrictions.eq("section.id", Integer.parseInt(section_id)));
				List<Student> studentsList = criteria1.list();
				for (int j = 0; j < studentsList.size(); j++) {
					if (j == studentsList.size() - 1) {
						sqlQuery = sqlQuery + "("
								+ studentsList.get(j).getId() + "," + true
								+ "," + "'" + sqlDate + "'" + ","
								+ class_id + "," + section_id + ");";
						System.out.println(sqlQuery);
						Query query1 = session.createSQLQuery(sqlQuery);
						int result = query1.executeUpdate();
						//tx.commit();
					} else {
						sqlQuery = sqlQuery + "("
								+ studentsList.get(j).getId() + "," + true
								+ "," + "'" + sqlDate + "'" + ","
								+ class_id + "," + section_id + "),";
					}
				}
				}
				jsonArray = jsonObject.getJSONArray("absentDetails");
				for(int i=0;i<jsonArray.length();i++){
					//Student student = (Student) session.get(Student.class, Integer.parseInt(jsonArray.get(i).toString()));
					
					Criteria criteria1 = session.createCriteria(Student.class).add(Restrictions.eq("id", Integer.parseInt(jsonArray.get(i).toString()))).add(Restrictions.eq("classes.id",Integer.parseInt(class_id))).add(Restrictions.eq("section.id", Integer.parseInt(section_id)));
					Student student = (Student) criteria1.uniqueResult();
					Query query2 = session.createQuery("from Attendance where student=:st_id and dateOfAbsent=:dateOfAbsent");
					query2.setParameter("st_id", student);
					query2.setParameter("dateOfAbsent", date1);
					Attendance attendance = (Attendance) query2.uniqueResult();
					attendance.setStatus(false);
					session.update(attendance);				
				}
				
				tx.commit();
				
			}else{
				resultData.status = false;
				resultData.message = "absentDetails are mandatory";
				return resultData;
			}
			
			
			session.close();
			resultData.status = true;
			resultData.message = "Absenties added successfully";
		}catch(Exception e){
			resultData.status = false;
			resultData.message = "Some thing went wrong please contact your admin";
			
		}
		return resultData;
	}

	@Override
	public ResultData getAttendance(String class_id, String section_id) {
		session = sessionFactory.openSession();
		tx = session.beginTransaction();
		ResultData resultData = new ResultData();
		Map mapAttendance = new LinkedHashMap();
		List listAttendance = null;
		Map attendanceMap = null;
		DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
		try{
			
			Criteria criteria = session.createCriteria(Section.class).add(Restrictions.eq("id", Integer.parseInt(section_id))).add(Restrictions.eq("classes.id", Integer.parseInt(class_id)));
			Section section = (Section) criteria.uniqueResult();
			
			Query query = session.createQuery("From Attendance where classes=:classes_id and section=:section_id and status=:status");
			query.setParameter("section_id", section);
			query.setParameter("classes_id", section.getClasses());
			query.setParameter("status", false);
			List<Attendance> attendanceList = query.list();		
			
			if(attendanceList!=null && !attendanceList.isEmpty()){
				for(Attendance attendance : attendanceList){
					
					
					if(mapAttendance.containsKey(df.format(attendance.getDateOfAbsent()))){
						listAttendance = (List) mapAttendance.get(df.format(attendance.getDateOfAbsent()));
						attendanceMap = new LinkedHashMap();
						attendanceMap.put("id",attendance.getId());
						attendanceMap.put("studentName",attendance.getStudent().getFirstName());
						attendanceMap.put("rollNumber", attendance.getStudent().getRollNumber());
						listAttendance.add(attendanceMap);
						mapAttendance.put(df.format(attendance.getDateOfAbsent()), listAttendance);
						
					}else{
						listAttendance = new ArrayList();
						attendanceMap = new LinkedHashMap();
						attendanceMap.put("id",attendance.getId());
						attendanceMap.put("studentName",attendance.getStudent().getFirstName());
						attendanceMap.put("rollNumber", attendance.getStudent().getRollNumber());
						listAttendance.add(attendanceMap);
						mapAttendance.put(df.format(attendance.getDateOfAbsent()), listAttendance);
						
					}
					
					
				}
				
				resultData.map = mapAttendance;
				resultData.status = true;
				resultData.message = "Data found";
			}
			else{
			resultData.map = null;
			resultData.status = false;
			resultData.message = "Data not found";
			}
		}catch(Exception e){
			resultData.map = null;
			resultData.status = false;
			resultData.message = "Some thing went wrong please contact your admin";
		}
		return resultData;
	}

	@Override
	public ResultData updateAttendance(String class_id, String attendance_id,
			String section_id, String dateOfAbsent, String student_id) {
		session = sessionFactory.openSession();
		tx = session.beginTransaction();
		ResultData resultData = new ResultData();
		try{
			
			Date date = new SimpleDateFormat("dd-MM-yyyy").parse(dateOfAbsent);
			java.sql.Date sqlDate = new java.sql.Date(date.getTime());
			String sqlQuery = "update attendance set status="+false+" where classes_id="+class_id+" and section_id="+section_id+" and dateOfAbsent="+"'"+sqlDate+"'"+" and st_id="+student_id;
			Query query = session.createSQLQuery(sqlQuery);
			int result = query.executeUpdate();
			resultData.status = true;
			resultData.message = "attendance ";
		}catch(Exception e){
			resultData.status = false;
			resultData.message = "Some thing went wrong please contact your admin";
		}
		return resultData;
	}

	@Override
	public ResultData uploadHolidays(Set<Holidays> holidaysMap) {
		session = sessionFactory.openSession();
		tx = session.beginTransaction();
		ResultData resultData = new ResultData();
		String sqlQuery = "insert into holidays(type, date, createddate)values";
		
		java.util.Date today = new java.util.Date();
		java.sql.Timestamp sqlTimestamp = new Timestamp(
				today.getTime());
		try{
			List<Holidays> holidaysList = new ArrayList<Holidays>(holidaysMap);
			for(int i=0;i<holidaysList.size();i++){
				Date date1 = new SimpleDateFormat("dd-MM-yyyy").parse(holidaysList.get(i).getDate());
				java.sql.Date sqlDate = new java.sql.Date(date1.getTime());
				if(i == holidaysList.size()-1){
					sqlQuery = sqlQuery + "("+"'"+holidaysList.get(i).getHolidayType()+"'"+","+"'"+sqlDate+"'"+","+"'"+sqlTimestamp+"'"+");";
				}else{
				sqlQuery = sqlQuery + "("+"'"+holidaysList.get(i).getHolidayType()+"'"+","+"'"+sqlDate+"'"+","+"'"+sqlTimestamp+"'"+"),";
				}
			}
			
			System.out.println(sqlQuery);
			Query query = session.createSQLQuery(sqlQuery);
			int result = query.executeUpdate();
			tx.commit();
			session.close();
			
			resultData.status = true;
			resultData.message = "Holidays uploaded successfully";
			
			
		}catch(Exception e){
			resultData.status = false;
			resultData.message = "Some thing went wrong please contact your admin";
		}
		return resultData;
	}

}
