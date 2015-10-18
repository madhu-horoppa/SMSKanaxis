package com.kanaxis.sms.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import antlr.debug.MessageAdapter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kanaxis.sms.dao.TimeTableDao;
import com.kanaxis.sms.model.Admission;
import com.kanaxis.sms.model.Classes;
import com.kanaxis.sms.model.Days;
import com.kanaxis.sms.model.Section;
import com.kanaxis.sms.model.Timetable;
import com.kanaxis.sms.util.ResultData;

public class TimeTableDaoImpl implements TimeTableDao {

	@Autowired
	SessionFactory sessionFactory;

	Session session = null;
	Transaction tx = null;

	@Override
	public ResultData getTimeTable(int classId, int sectionId) throws Exception {
		ResultData resultData = new ResultData();
		session = sessionFactory.openSession();
		tx = session.beginTransaction();

		try {
			Map subjectDetails = null;
			Map mapData = new LinkedHashMap();
			List listTimeTable = new ArrayList();

			Criteria criteria = session.createCriteria(Timetable.class)
					.add(Restrictions.eq("classes.id", classId))
					.add(Restrictions.eq("section.id", sectionId));
			List<Timetable> timeTableList = criteria.list();
			if (timeTableList != null && !timeTableList.isEmpty()) {
				for (Timetable timeTable : timeTableList) {
					if (mapData.containsKey(timeTable.getDays().getDay())) {
						List tempListMap = (List) mapData.get(timeTable
								.getDays().getDay());
						subjectDetails = new LinkedHashMap();
						subjectDetails.put("id", timeTable.getId());
						subjectDetails.put("subjectName", timeTable
								.getSubject().getSubjectName());
						subjectDetails.put("startTime",
								timeTable.getStartTime());
						subjectDetails.put("endTime", timeTable.getEndTime());
						subjectDetails.put("teacher", timeTable.getTeachers()
								.getTeacherName());
						tempListMap.add(subjectDetails);
						mapData.put(timeTable.getDays().getDay(), tempListMap);
					} else {
						List listMap = new ArrayList();
						subjectDetails = new LinkedHashMap();
						subjectDetails.put("id", timeTable.getId());
						subjectDetails.put("subjectName", timeTable
								.getSubject().getSubjectName());
						subjectDetails.put("startTime",
								timeTable.getStartTime());
						subjectDetails.put("endTime", timeTable.getEndTime());
						subjectDetails.put("teacher", timeTable.getTeachers()
								.getTeacherName());
						listMap.add(subjectDetails);
						mapData.put(timeTable.getDays().getDay(), listMap);
					}

				}
				// listTimeTable.add(mapData);
				System.out.println(listTimeTable);
				resultData.map = mapData;
				resultData.message = "data found";
				resultData.status = true;
			} else {
				resultData.map = null;
				resultData.message = "data not found";
				resultData.status = false;
			}

		} catch (Exception e) {
			resultData.map = null;
			resultData.message = "Some thing went wrong please contact your admin";
			resultData.status = false;
		}
		// tx = session.getTransaction();
		// session.beginTransaction();
		// tx.commit();
		session.close();
		return resultData;
	}

	@Override
	public ResultData addTimeTable(String timetableJson) {
		ResultData resultData = new ResultData();
		session = sessionFactory.openSession();
		tx = session.beginTransaction();
		JSONObject jsonTimetable = null;
		String class_id = "";
		String section_id = "";
		String subject_id = "";
		String startTime = "";
		String endTime = "";
		String teacher_id = "";
		JSONObject jsonObject = null;
		JSONArray jsonArrayTimetable = null;
		String sqlQuery = "insert into timetable (class_id, section_id, subj_id, teacher_id, start_time, day_id, end_time) values";
		try {

			jsonTimetable = new JSONObject(timetableJson);
			if (jsonTimetable.has("class_id")) {
				class_id = jsonTimetable.getString("class_id");
			}else{
				resultData.status = false;
				resultData.message = "Class is mandatory";
				return resultData; 
			}
			if (jsonTimetable.has("section_id")) {
				section_id = jsonTimetable.getString("section_id");
			}else{
				resultData.status = false;
				resultData.message = "Section is mandatory";
				return resultData; 
			}
			if (jsonTimetable.has("Monday")) {

				jsonArrayTimetable = jsonTimetable.getJSONArray("Monday");
				for (int i = 0; i < jsonArrayTimetable.length(); i++) {
					int j= i+1;
					jsonObject = jsonArrayTimetable.getJSONObject(i);
					if (jsonObject.has("subject_id")) {
						subject_id = jsonObject.getString("subject_id");
					}else{
						resultData.status = false;
						resultData.message = "Subject is mandatory at Monday period "+j;
						return resultData;
					}
					if (jsonObject.has("startTime")) {
						startTime = jsonObject.getString("startTime");
					}else{
						resultData.status = false;
						resultData.message = "Start time is mandatory at Monday period "+j;
						return resultData;
					}
					if (jsonObject.has("endTime")) {
						endTime = jsonObject.getString("endTime");
					}else{
						resultData.status = false;
						resultData.message = "End time is mandatory at Monday period "+j;
						return resultData;
					}
					if (jsonObject.has("teacher_id")) {
						teacher_id = jsonObject.getString("teacher_id");
					}else{
						resultData.status = false;
						resultData.message = "Teacher is mandatory at Monday period "+j;
						return resultData;
					}

					Query query = session
							.createQuery("from Days where day=:day");
					query.setParameter("day", "Monday");
					Days days = (Days) query.uniqueResult();
					
					if(days!=null){

					sqlQuery = sqlQuery + "(" + class_id + "," + section_id
							+ "," + subject_id + "," + teacher_id + "," + "'"
							+ startTime + "'" + "," + days.getId() + "," + "'"
							+ endTime + "'" + "),";
					}

				}

			}

			if (jsonTimetable.has("Tuesday")) {

				jsonArrayTimetable = jsonTimetable.getJSONArray("Tuesday");
				for (int i = 0; i < jsonArrayTimetable.length(); i++) {
					int j= i+1;
					jsonObject = jsonArrayTimetable.getJSONObject(i);
					if (jsonObject.has("subject_id")) {
						subject_id = jsonObject.getString("subject_id");
					}else{
						resultData.status = false;
						resultData.message = "Subject is mandatory at Tuesday periods "+j;
						return resultData;
					}
					if (jsonObject.has("startTime")) {
						startTime = jsonObject.getString("startTime");
					}else{
						resultData.status = false;
						resultData.message = "Start time is mandatory at Tuesday periods "+j;
						return resultData;
					}
					if (jsonObject.has("endTime")) {
						endTime = jsonObject.getString("endTime");
					}else{
						resultData.status = false;
						resultData.message = "End time is mandatory at Tuesday periods "+j;
						return resultData;
					}
					if (jsonObject.has("teacher_id")) {
						teacher_id = jsonObject.getString("teacher_id");
					}else{
						resultData.status = false;
						resultData.message = "Teacher time is mandatory at Tuesday periods "+j;
						return resultData;
					}

					Query query = session
							.createQuery("from Days where day=:day");
					query.setParameter("day", "Tuesday");
					Days days = (Days) query.uniqueResult();
					if(days!=null){
					sqlQuery = sqlQuery + "(" + class_id + "," + section_id
							+ "," + subject_id + "," + teacher_id + "," + "'"
							+ startTime + "'" + "," + days.getId() + "," + "'"
							+ endTime + "'" + "),";
					}

				}

			}
			if (jsonTimetable.has("Wednesday")) {

				jsonArrayTimetable = jsonTimetable.getJSONArray("Wednesday");
				for (int i = 0; i < jsonArrayTimetable.length(); i++) {
					int j= i+1;
					jsonObject = jsonArrayTimetable.getJSONObject(i);
					if (jsonObject.has("subject_id")) {
						subject_id = jsonObject.getString("subject_id");
					}else{
						resultData.status = false;
						resultData.message = "Subject is mandatory at Wednesday period "+j;
						return resultData;
					}
					if (jsonObject.has("startTime")) {
						startTime = jsonObject.getString("startTime");
					}else{
						resultData.status = false;
						resultData.message = "Start time is mandatory at Wednesday period "+j;
						return resultData;
					}
					if (jsonObject.has("endTime")) {
						endTime = jsonObject.getString("endTime");
					}else{
						resultData.status = false;
						resultData.message = "End time is mandatory at Wednesday period "+j;
						return resultData;
					}
					if (jsonObject.has("teacher_id")) {
						teacher_id = jsonObject.getString("teacher_id");
					}else{
						resultData.status = false;
						resultData.message = "Teacher is mandatory at Wednesday period "+j;
						return resultData;
					}

					Query query = session
							.createQuery("from Days where day=:day");
					query.setParameter("day", "Wednesday");
					Days days = (Days) query.uniqueResult();
					if(days!=null){
					sqlQuery = sqlQuery + "(" + class_id + "," + section_id
							+ "," + subject_id + "," + teacher_id + "," + "'"
							+ startTime + "'" + "," + days.getId() + "," + "'"
							+ endTime + "'" + "),";
					}

				}

			}

			if (jsonTimetable.has("Thursday")) {

				jsonArrayTimetable = jsonTimetable.getJSONArray("Thursday");
				for (int i = 0; i < jsonArrayTimetable.length(); i++) {
					int j= i+1;
					jsonObject = jsonArrayTimetable.getJSONObject(i);
					if (jsonObject.has("subject_id")) {
						subject_id = jsonObject.getString("subject_id");
					}else{
						resultData.status = false;
						resultData.message = "Subject is mandatory at Thursday period "+j;
						return resultData;
					}
					if (jsonObject.has("startTime")) {
						startTime = jsonObject.getString("startTime");
					}else{
						resultData.status = false;
						resultData.message = "Start time is mandatory at Thursday period "+j;
						return resultData;
					}
					if (jsonObject.has("endTime")) {
						endTime = jsonObject.getString("endTime");
					}else{
						resultData.status = false;
						resultData.message = "End time is mandatory at Thursday period "+j;
						return resultData;
					}
					if (jsonObject.has("teacher_id")) {
						teacher_id = jsonObject.getString("teacher_id");
					}else{
						resultData.status = false;
						resultData.message = "Teacher is mandatory at Thursday period "+j;
						return resultData;
					}

					Query query = session
							.createQuery("from Days where day=:day");
					query.setParameter("day", "Thursday");
					Days days = (Days) query.uniqueResult();
					if(days!=null){
					sqlQuery = sqlQuery + "(" + class_id + "," + section_id
							+ "," + subject_id + "," + teacher_id + "," + "'"
							+ startTime + "'" + "," + days.getId() + "," + "'"
							+ endTime + "'" + "),";
					}

				}

			}

			if (jsonTimetable.has("Friday")) {

				jsonArrayTimetable = jsonTimetable.getJSONArray("Friday");
				for (int i = 0; i < jsonArrayTimetable.length(); i++) {
					int j= i+1;
					jsonObject = jsonArrayTimetable.getJSONObject(i);
					if (jsonObject.has("subject_id")) {
						subject_id = jsonObject.getString("subject_id");
					}else{
						resultData.status = false;
						resultData.message = "Subject is mandatory at Friday period "+j;
						return resultData;
					}
					if (jsonObject.has("startTime")) {
						startTime = jsonObject.getString("startTime");
					}else{
						resultData.status = false;
						resultData.message = "Start time is mandatory at Friday period "+j;
						return resultData;
					}
					if (jsonObject.has("endTime")) {
						endTime = jsonObject.getString("endTime");
					}else{
						resultData.status = false;
						resultData.message = "End time is mandatory at Friday period "+j;
						return resultData;
					}
					if (jsonObject.has("teacher_id")) {
						teacher_id = jsonObject.getString("teacher_id");
					}else{
						resultData.status = false;
						resultData.message = "Teacher is mandatory at Friday period "+j;
						return resultData;
					}

					Query query = session
							.createQuery("from Days where day=:day");
					query.setParameter("day", "Friday");
					Days days = (Days) query.uniqueResult();
					if(days!=null){
					sqlQuery = sqlQuery + "(" + class_id + "," + section_id
							+ "," + subject_id + "," + teacher_id + "," + "'"
							+ startTime + "'" + "," + days.getId() + "," + "'"
							+ endTime + "'" + "),";
					}

				}

			}

			if (jsonTimetable.has("Saturday")) {

				jsonArrayTimetable = jsonTimetable.getJSONArray("Saturday");
				for (int i = 0; i < jsonArrayTimetable.length(); i++) {
					int j= i+1;
					jsonObject = jsonArrayTimetable.getJSONObject(i);
					if (jsonObject.has("subject_id")) {
						subject_id = jsonObject.getString("subject_id");
					}else{
						resultData.status = false;
						resultData.message = "Subject is mandatory at Saturday period "+j;
						return resultData;
					}
					if (jsonObject.has("startTime")) {
						startTime = jsonObject.getString("startTime");
					}else{
						resultData.status = false;
						resultData.message = "Start time is mandatory at Saturday period "+j;
						return resultData;
					}
					if (jsonObject.has("endTime")) {
						endTime = jsonObject.getString("endTime");
					}else{
						resultData.status = false;
						resultData.message = "End time is mandatory at Saturday period "+j;
						return resultData;
					}
					if (jsonObject.has("teacher_id")) {
						teacher_id = jsonObject.getString("teacher_id");
					}else{
						resultData.status = false;
						resultData.message = "Teacher is mandatory at Saturday period "+j;
						return resultData;
					}

					Query query = session
							.createQuery("from Days where day=:day");
					query.setParameter("day", "Saturday");
					Days days = (Days) query.uniqueResult();
					if(days!=null){
                    if(i == jsonArrayTimetable.length()-1){
					sqlQuery = sqlQuery + "(" + class_id + "," + section_id
							+ "," + subject_id + "," + teacher_id + "," + "'"
							+ startTime + "'" + "," + days.getId() + "," + "'"
							+ endTime + "'" + ");";
                    }else{
                    	sqlQuery = sqlQuery + "(" + class_id + "," + section_id
    							+ "," + subject_id + "," + teacher_id + "," + "'"
    							+ startTime + "'" + "," + days.getId() + "," + "'"
    							+ endTime + "'" + "),";
                    }
					}

				}

			}
			
			System.out.println(sqlQuery);
			Query query = session.createSQLQuery(sqlQuery);
			int result = query.executeUpdate();
			tx.commit();
			session.close();
			resultData.status = true;
			resultData.message = "Timetable created successfully";
		} catch (Exception e) {
			resultData.status = false;
			resultData.message = "Some thing went wrong please contact your admin";
		}
		return resultData;
	}

}
