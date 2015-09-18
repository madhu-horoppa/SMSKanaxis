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

import com.kanaxis.sms.dao.SubjectDao;
import com.kanaxis.sms.model.Classes;
import com.kanaxis.sms.model.Examschedule;
import com.kanaxis.sms.model.Subject;
import com.kanaxis.sms.util.ResultData;

public class SubjectDaoImpl implements SubjectDao{
	@Autowired
	SessionFactory sessionFactory;

	Session session = null;
	Transaction tx = null;
	@Override
	public ResultData addSubjects(String subjectsDetails) {
		
		session = sessionFactory.openSession();

		ResultData resultData = new ResultData();
		JSONObject subjectDetailsJson = null;
		String sqlQuery = "insert into subject (subject_name, class_id) values";
		tx = session.beginTransaction();
		Subject subject = null;
		Query query = null;
		try{
			
			subjectDetailsJson = new JSONObject(subjectsDetails);
			int class_id = subjectDetailsJson.getInt("class_id");
			JSONArray subjectDetailsArray = subjectDetailsJson.getJSONArray("subjects");
			
			Classes classes = (Classes) session.get(Classes.class, new Integer(class_id));
			for(int i=0;i<subjectDetailsArray.length();i++){
				
				 query = session.createQuery("from Subject where subjectName=:subject_name and classes=:class_id");
				query.setParameter("subject_name", subjectDetailsArray.get(i));
				query.setParameter("class_id", classes);
				 subject = (Subject) query.uniqueResult();
				if(subject == null){
					subject = new Subject();
					if(i == subjectDetailsArray.length()-1){
						sqlQuery = sqlQuery+"("+"'"+subjectDetailsArray.get(i)+"'"+","+class_id+");";
					}else{
					sqlQuery = sqlQuery+"("+"'"+subjectDetailsArray.get(i)+"'"+","+class_id+"),";
					}
				}else{
					resultData.status = false;
					resultData.message = "Subject "+subjectDetailsArray.get(i)+" already exist for this class "+classes.getClassName();
					return resultData;
				}
				
				
			}
			System.out.println(sqlQuery);
			query = session.createSQLQuery(sqlQuery);
			int result = query.executeUpdate();
			tx.commit();
			session.close();
			resultData.status = true;
			resultData.message = "Subject details added successfully";
			
		}catch(Exception e){
			resultData.status = false;
			resultData.message = "Some thing went wrong please contact your admin";
		}
		return resultData;
	}
	
	
	@Override
	public ResultData getAllsubjects() {
		session = sessionFactory.openSession();
		tx = session.beginTransaction();
		ResultData resultData = new ResultData(); 
		List listSubject = null;
		Map mapSubject = new LinkedHashMap();
		List totalData = new ArrayList();
		try{
			
			Query query = session.createQuery("from Subject");
			List<Subject> subjectList = query.list();
			if(subjectList!=null && !subjectList.isEmpty()){
				for(Subject subject:subjectList){
					if(mapSubject.containsKey(subject.getClasses().getClassName())){
						listSubject = (List) mapSubject.get(subject.getClasses().getClassName());
						listSubject.add(subject.getSubjectName());
						mapSubject.put(subject.getClasses().getClassName(), listSubject);
						
					}else{
						listSubject = new ArrayList();
						listSubject.add(subject.getSubjectName());
						mapSubject.put(subject.getClasses().getClassName(), listSubject);
					}
					//totalData.add(mapSubject);
					
				}
				
				resultData.map = mapSubject;
				resultData.status = true;
				resultData.message = "Data found";
			}else{
				resultData.map = null;
				resultData.status = false;
				resultData.message = "Data found";
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
	public ResultData getSubjectsByClass(String class_id) {
		session = sessionFactory.openSession();
		tx = session.beginTransaction();
		ResultData resultData = new ResultData();
		Map subjectMap = new LinkedHashMap();
		List listSubject = null;
		try{
			
			Criteria criteria  = session.createCriteria(Subject.class)
					 .add(Restrictions.eq("classes.id", Integer.parseInt(class_id)));
			List<Subject> subjectList = criteria.list();
			if(subjectList!=null && !subjectList.isEmpty()){
				for(Subject subject : subjectList){
					if(subjectMap.containsKey(subject.getClasses().getClassName())){
						listSubject = (List) subjectMap.get(subject.getClasses().getClassName());
						listSubject.add(subject.getSubjectName());
						subjectMap.put(subject.getClasses().getClassName(),listSubject );
						
					}else{
						listSubject = new ArrayList();
						listSubject.add(subject.getSubjectName());
						subjectMap.put(subject.getClasses().getClassName(), listSubject);
					}
				}
				resultData.map = subjectMap;
				resultData.status = true;
				resultData.message = "data found";
			}else{
				resultData.map = null;
				resultData.status = false;
				resultData.message = "no data found";
			}
			
		}catch(Exception e){
			resultData.map = null;
			resultData.status = false;
			resultData.message = "Some thing went wrong please contact yout admin";
		}
		return resultData; 
	}

}
