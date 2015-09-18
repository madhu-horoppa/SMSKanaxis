package com.kanaxis.sms.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;

import com.kanaxis.sms.dao.SectionDao;
import com.kanaxis.sms.model.Classes;
import com.kanaxis.sms.model.Section;
import com.kanaxis.sms.util.ResultData;

public class SectionDaoImpl implements SectionDao{
	@Autowired
	SessionFactory sessionFactory;

	Session session = null;
	Transaction tx = null;
	@Override
	public ResultData addSection(String class_id, String sectionName) {
		ResultData resultData = new ResultData();
		session = sessionFactory.openSession();
		tx = session.beginTransaction();
		Section section = null;
		try{
			
			Classes classes = (Classes) session.get(Classes.class, new Integer(Integer.parseInt(class_id)));
			
			Query query = session.createQuery("from Section where classes=:class_id and sectionName=:section_name");
			query.setParameter("class_id", classes);
			query.setParameter("section_name", sectionName);
			section = (Section) query.uniqueResult();
			if(section == null){
				section = new Section();
				section.setClasses(classes);
				section.setSectionName(sectionName);
				session.save(section);
				tx.commit();
				session.close();
				resultData.status = true;
				resultData.message = "Section added successfully";
			}else{
				resultData.status = false;
				resultData.message = "Section "+sectionName+" already exist for this class "+classes.getClassName();
			}
			
			
		}catch(Exception e){
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

}
