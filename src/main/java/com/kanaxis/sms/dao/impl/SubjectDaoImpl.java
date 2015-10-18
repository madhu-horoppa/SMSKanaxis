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
import com.kanaxis.sms.model.Section;
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
		int class_id = 0;
		try{
			
			subjectDetailsJson = new JSONObject(subjectsDetails);
			if(subjectDetailsJson.has("class_id")){
			 class_id = subjectDetailsJson.getInt("class_id");
			}else{
				resultData.status = false;
				resultData.message = "Class is mandatory";
				return resultData;
			}
			if(subjectDetailsJson.has("subjects")){
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
			}else{
				resultData.status = false;
				resultData.message = "Subjects are mandatory";
				return resultData;
			}
			
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
		Map mapSubject = null;
		try{
			
			Criteria criteria  = session.createCriteria(Subject.class)
					 .add(Restrictions.eq("classes.id", Integer.parseInt(class_id)));
			List<Subject> subjectList = criteria.list();
			if(subjectList!=null && !subjectList.isEmpty()){
				for(Subject subject : subjectList){
					if(subjectMap.containsKey(subject.getClasses().getClassName())){
						mapSubject = (Map) subjectMap.get(subject.getClasses().getClassName());
						mapSubject.put(subject.getId(), subject.getSubjectName());
						subjectMap.put(subject.getClasses().getClassName(),mapSubject );
						
					}else{
						mapSubject = new LinkedHashMap();
						mapSubject.put(subject.getId(), subject.getSubjectName());
						subjectMap.put(subject.getClasses().getClassName(), mapSubject);
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


	@Override
	public ResultData updateSubjects(String class_id, String subjectName,
			String subject_id) {
		ResultData resultData = new ResultData();
		session = sessionFactory.openSession();
		tx = session.beginTransaction();
		String sqlQuery  = "";
		Query query = null;
		try{
			/*Subject subject = (Subject) session.get(Subject.class,Integer.parseInt(subject_id));
			if(subject!=null){
				
			}*/
			Classes classes = (Classes) session.get(Classes.class, Integer.parseInt(class_id));
			 query = session.createQuery("from Subject where classes=:class_id and subjectName=:subject_name");
			query.setParameter("class_id", classes);
			query.setParameter("subject_name", subjectName);
			Subject subject = (Subject) query.uniqueResult();
			if(subject == null){
				sqlQuery = "update subject set class_id="+class_id+","+"subject_name="+"'"+subjectName+"'"+" where id="+subject_id;
				System.out.println(sqlQuery);
				query = session.createSQLQuery(sqlQuery);
				int result = query.executeUpdate();
				tx.commit();
				session.close();
				resultData.status = true;
				resultData.message="Subject updated successfully";
                return resultData;
				
			}
			if(subject.getId() == Integer.parseInt(subject_id)){
				
				sqlQuery = "update subject set class_id="+class_id+","+"subject_name="+"'"+subjectName+"'"+" where id="+subject_id;
				System.out.println(sqlQuery);
				query = session.createSQLQuery(sqlQuery);
				int result = query.executeUpdate();
				tx.commit();
				session.close();
				resultData.status = true;
				resultData.message="Subject updated successfully";
				
			}else{
				resultData.status = false;
				resultData.message = "Subject already exist";
			}
		}catch(Exception e){
			resultData.status = false;
			resultData.message = "Some thing went wrong please contact your admin";
		}
		return resultData;
	}

}
