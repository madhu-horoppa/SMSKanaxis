package com.kanaxis.sms.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
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

import com.kanaxis.sms.dao.SectionDao;
import com.kanaxis.sms.model.Classes;
import com.kanaxis.sms.model.Section;
import com.kanaxis.sms.model.Subject;
import com.kanaxis.sms.util.ResultData;

public class SectionDaoImpl implements SectionDao{
	@Autowired
	SessionFactory sessionFactory;

	Session session = null;
	Transaction tx = null;
	@Override
	public ResultData addSection(String sectionJson) {
		ResultData resultData = new ResultData();
		session = sessionFactory.openSession();
		tx = session.beginTransaction();
		Section section = null;
		JSONObject json = null;
		String class_id = "";
		String sqlQuery = "insert into section (section_name, class_id) values";
		try{
			
			json = new JSONObject(sectionJson);
			if(json.has("class_id")){
			class_id = json.getString("class_id");
			}else{
				resultData.status = false;
				resultData.message = "Class is manadatory";
				return resultData;
			}
			if(json.has("sections")){
			JSONArray sectionJsonArray = json.getJSONArray("sections");
			
			for(int i =0;i<sectionJsonArray.length();i++){
			Classes classes = (Classes) session.get(Classes.class, new Integer(Integer.parseInt(class_id)));
			
			Query query = session.createQuery("from Section where classes=:class_id and sectionName=:section_name");
			query.setParameter("class_id", classes);
			query.setParameter("section_name", sectionJsonArray.get(i));
			section = (Section) query.uniqueResult();
			if(section == null){
				if(i == sectionJsonArray.length()-1){
				sqlQuery = sqlQuery+"("+ "'"+sectionJsonArray.get(i) + "'"+"," + class_id + "); ";
				}else{
					sqlQuery = sqlQuery+"("+ "'"+sectionJsonArray.get(i) + "'"+ "," + class_id + "), ";
				}
				
			}else{
				resultData.status = false;
				resultData.message = "Section "+sectionJsonArray.get(i)+" already exist for this class "+classes.getClassName();
			}
			}
			System.out.println(sqlQuery);
			Query query = session.createSQLQuery(sqlQuery);
			int result = query.executeUpdate();
			tx.commit();
			session.close();
			resultData.status = true;
			resultData.message = "Section added successfully";
			}else{
				resultData.status = false;
				resultData.message = "Sections are mandatory";
				return resultData;
			}
			
		}catch(Exception e){
			tx.rollback();
			resultData.status = false;
			resultData.message = "Some thing went wrong please contact your admin";
			
		}
		return resultData;
	}
	@Override
	public ResultData viewAllSctions() {
		ResultData resultData = new ResultData();
		session = sessionFactory.openSession();
		tx = session.beginTransaction();
		Map mapSection = new LinkedHashMap();
		List listSection = null;
		try{
			Query query = session.createQuery("from Section");
			List<Section> sectionsList = query.list();
			if(sectionsList!=null && !sectionsList.isEmpty()){
				
				for(Section section : sectionsList){
				     
				if( mapSection.containsKey(section.getClasses().getClassName()) ) {
					 listSection = (List) mapSection.get(section.getClasses().getClassName());
					 listSection.add(section.getSectionName());
					 mapSection.put(section.getClasses().getClassName(), listSection);
				} else {
					 listSection = new ArrayList();
					listSection.add(section.getSectionName());
					mapSection.put(section.getClasses().getClassName(), listSection);
				}
				}
				
				resultData.map = mapSection;
				resultData.status = true;
				resultData.message = "Data found";
				
			}else{
				resultData.map = null;
				resultData.status = true;
				resultData.message = "Data found";
			}
		}catch(Exception e){
			resultData.map = null;
			resultData.status = false;
			resultData.message = "Some thing went wrong please contact your admin";
		}
		return resultData;
	}
	@Override
	public ResultData getSectionByClass(String class_id) {
		session = sessionFactory.openSession();
		tx = session.beginTransaction();
		ResultData resultData = new ResultData();
		Map sectionMap = new LinkedHashMap();
		Map mapSection = null;
		try{
			
			Criteria criteria  = session.createCriteria(Section.class)
					 .add(Restrictions.eq("classes.id", Integer.parseInt(class_id)));
			List<Section> sectionsListList = criteria.list();
			if(sectionsListList!=null && !sectionsListList.isEmpty()){
				for(Section section : sectionsListList){
					if(sectionMap.containsKey(section.getClasses().getClassName())){
						mapSection = (Map) sectionMap.get(section.getClasses().getClassName());
						mapSection.put(section.getId(), section.getSectionName());
						sectionMap.put(section.getClasses().getClassName(),mapSection );
						
					}else{
						mapSection = new LinkedHashMap();
						mapSection.put(section.getId(), section.getSectionName());
						sectionMap.put(section.getClasses().getClassName(), mapSection);
					}
				}
				resultData.map = sectionMap;
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
	public ResultData getAllclasses() {
		session = sessionFactory.openSession();
		ResultData resultData = new ResultData();
		Map mapclasses = new LinkedHashMap();
		try{
			
			Query query = session.createQuery("from Classes");
			List<Classes> classesList = query.list();
			if(classesList!=null && !classesList.isEmpty()){
				
				for(Classes classes:classesList){
					
					mapclasses.put(classes.getId(), classes.getClassName());
					
				}
				
				resultData.map = mapclasses;
				resultData.status = true;
				resultData.message = "Data found";
				
			}
			
			
		}catch(Exception e){
			resultData.map = null;
			resultData.status = false;
			resultData.message = "Some thing went wrong please contact your admin";
		}
		return resultData;
	}
	@Override
	public ResultData updateSections(String class_id, String sectionName,
			String section_id) {
		ResultData resultData = new ResultData();
		session = sessionFactory.openSession();
		tx = session.beginTransaction();
		String sqlQuery  = "";
		Query query = null;
		try{
			
			Classes classes = (Classes) session.get(Classes.class, Integer.parseInt(class_id));
			 query = session.createQuery("from Section where classes=:class_id and sectionName=:section_name");
			query.setParameter("class_id", classes);
			query.setParameter("section_name", sectionName);
			Section section = (Section) query.uniqueResult();
			if(section == null){
				sqlQuery = "update section set class_id="+class_id+","+"section_name="+"'"+sectionName+"'"+" where id="+section_id;
				System.out.println(sqlQuery);
				query = session.createSQLQuery(sqlQuery);
				int result = query.executeUpdate();
				tx.commit();
				session.close();
				resultData.status = true;
				resultData.message="Section updated successfully";
				return resultData;
			}
			if(section.getId() == Integer.parseInt(section_id)){
				
				sqlQuery = "update section set class_id="+class_id+","+"section_name="+"'"+sectionName+"'"+" where id="+section_id;
				System.out.println(sqlQuery);
				query = session.createSQLQuery(sqlQuery);
				int result = query.executeUpdate();
				tx.commit();
				session.close();
				resultData.status = true;
				resultData.message="Section updated successfully";
				
			}else{
				resultData.status = false;
				resultData.message = "Section already exist";
			}
		}catch(Exception e){
			resultData.status = false;
			resultData.message = "Some thing went wrong please contact your admin";
		}
		return resultData;
	}

}
