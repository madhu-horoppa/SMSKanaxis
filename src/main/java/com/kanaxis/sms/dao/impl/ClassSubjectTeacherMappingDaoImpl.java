package com.kanaxis.sms.dao.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import com.kanaxis.sms.model.ClassSubjectTeacherMapping;
import com.kanaxis.sms.util.ResultData;

public class ClassSubjectTeacherMappingDaoImpl implements ClassSubjectTeacherMappingDao{
	
	@Autowired
	SessionFactory sessionFactory;

	Session session = null;
	Transaction tx = null;

	@Override
	public ResultData addClassSubjectTeacherMapping(
			String addClassSubjectTeacherMappingJson) {

		session = sessionFactory.openSession();
		tx = session.beginTransaction();
		ResultData resultData = new ResultData();
		String sqlQuery = "insert into class_subject_teacher_mapping (class_id, subject_id, teacher_id, section_id) values(";
		JSONObject jsonObject = null;

		try {
			jsonObject = new JSONObject(addClassSubjectTeacherMappingJson);
			String class_id = jsonObject.getString("class_id");
			String section_id = jsonObject.getString("section_id");
			JSONArray jsonArray = jsonObject
					.getJSONArray("classSubjectTeacherMappingDetails");
			for (int i = 0; i < jsonArray.length(); i++) {
				String subject_id = jsonArray.getJSONObject(i).getString(
						"subject_id");
				String teacher_id = jsonArray.getJSONObject(i).getString(
						"teacher_id");
				Criteria criteria = session
						.createCriteria(ClassSubjectTeacherMapping.class)
						.add(Restrictions.eq("classes.id",
								Integer.parseInt(class_id)))
						.add(Restrictions.eq("subject.id",
								Integer.parseInt(subject_id)))
						.add(Restrictions.eq("teachers.id",
								Integer.parseInt(teacher_id)))
						.add(Restrictions.eq("section.id",
								Integer.parseInt(section_id)));
				// To check duplicates
				ClassSubjectTeacherMapping classSubjectTeacherMapping = (ClassSubjectTeacherMapping) criteria
						.uniqueResult();
				if (classSubjectTeacherMapping == null) {
					if(i == jsonArray.length()-1){
					sqlQuery = sqlQuery + class_id + "," + subject_id + ","
							+ teacher_id + "," + section_id + ");";
					}else{
						sqlQuery = sqlQuery + class_id + "," + subject_id + ","
								+ teacher_id + "," + section_id + "),(";
					}

					
				} else {
					resultData.status = false;
					resultData.message = "Teacher "+classSubjectTeacherMapping.getTeachers().getTeacherName()+" already exist For class "+classSubjectTeacherMapping.getClasses().getClassName()+ " section "+classSubjectTeacherMapping.getSection().getSectionName()
							              + " subject "+classSubjectTeacherMapping.getSubject().getSubjectName();
					return resultData;
				}
			}

			Query query = session.createSQLQuery(sqlQuery);
			query.executeUpdate();
			tx.commit();
			session.close();
			resultData.status = true;
			resultData.message = "Class Subject Teacher Mapping added successfully";

		} catch (Exception e) {
			resultData.status = false;
			resultData.message = "Some thing went wrong please contact your admin";
		}

		return resultData;
	}

	@Override
	public ResultData getAllClassSubjectTeacherMappings(String class_id,
			String section_id) {
		session = sessionFactory.openSession();
		tx = session.beginTransaction();
		ResultData resultData = new ResultData();
		List listClassSubjectTeacherMapping = new ArrayList();
		try{
			Criteria criteria = session.createCriteria(ClassSubjectTeacherMapping.class).add(Restrictions.eq("classes.id", Integer.parseInt(class_id))).
					add(Restrictions.eq("section.id", Integer.parseInt(section_id)));
			List<ClassSubjectTeacherMapping> classSubjectTeacherMappingList = criteria.list();
			if(classSubjectTeacherMappingList!=null){
			for(ClassSubjectTeacherMapping classSubjectTeacherMapping : classSubjectTeacherMappingList){
				Map mapClassSubjectTeacherMapping = new LinkedHashMap();
				//mapClassSubjectTeacherMapping.put("map_id", classSubjectTeacherMapping.getId());
				mapClassSubjectTeacherMapping.put("subject", classSubjectTeacherMapping.getSubject().getSubjectName());
				mapClassSubjectTeacherMapping.put("teacher", classSubjectTeacherMapping.getTeachers().getTeacherName());
				listClassSubjectTeacherMapping.add(mapClassSubjectTeacherMapping);				
			}
			resultData.listData = listClassSubjectTeacherMapping;
			resultData.status = true;
			resultData.message = "Data found";
			
			}else{
				resultData.listData = null;
				resultData.status = false;
				resultData.message = "No data found";
			}
		}catch(Exception e){
			resultData.listData = null;
			resultData.status = false;
			resultData.message = "Some thing went wrong please contact your admin";
		}
		return resultData;
	}

}
