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

import com.kanaxis.sms.dao.TimeTableDao;
import com.kanaxis.sms.model.Admission;
import com.kanaxis.sms.model.Classes;
import com.kanaxis.sms.model.Section;
import com.kanaxis.sms.model.Timetable;
import com.kanaxis.sms.util.ResultData;

public class TimeTableDaoImpl implements TimeTableDao{
	
	@Autowired
	SessionFactory sessionFactory;

	Session session = null;
	Transaction tx = null;

	@Override
	public ResultData getTimeTable(int classId, int sectionId) throws Exception {
		ResultData resultData = new ResultData();
		;
		try{
			session = sessionFactory.openSession();
			Map periodDetails = new LinkedHashMap();
			Map subjectDetails = new LinkedHashMap();;
			Map mapData = new HashMap();
			List listTimeTable = new ArrayList();
			
			Classes classes = (Classes) session.get(Classes.class, new Integer(classId));
			if(classes == null){
				
				resultData.listData = null;
				resultData.message = "No class found with this id";
				resultData.status = false;
				return resultData;				
			}
			
			Section section = (Section) session.get(Section.class, new Integer(sectionId));
			if(section == null){
				resultData.listData = null;
				resultData.message = "No section found with this id";
				resultData.status = false;
				return resultData;
				
			}
			Query query = session.createQuery("From Timetable t where t.classes =:class_id and t.section =:section_id ");
			query.setParameter("class_id", classes);
			query.setParameter("section_id", section);
			List<Timetable> timeTableList = query.list();
			if(timeTableList!=null){
				for(Timetable timeTable : timeTableList){
					if(mapData.containsKey(timeTable.getDays().getDay())){
						List tempListMap = (List) mapData.get(timeTable.getDays().getDay());
						subjectDetails = new HashMap();
					   subjectDetails.put("subjectName", timeTable.getSubject().getSubjectName());
					   subjectDetails.put("time", timeTable.getSection());
					   subjectDetails.put("teacher", timeTable.getTeachers().getTeacherName());
					   tempListMap.add(subjectDetails);
					   mapData.put(timeTable.getDays().getDay(), tempListMap);
					}else{
						List listMap = new ArrayList();
						subjectDetails = new HashMap();
						subjectDetails.put("subjectName", timeTable.getSubject().getSubjectName());
						subjectDetails.put("time", timeTable.getSection());
						subjectDetails.put("teacher", timeTable.getTeachers().getTeacherName());
						listMap.add(subjectDetails);
					    mapData.put(timeTable.getDays().getDay(), listMap);
					}
						
				
			}
				
				listTimeTable.add(mapData);
				resultData.listData = listTimeTable;
				resultData.message = "data found";
				resultData.status = true;
			}else{
				resultData.listData = null;
				resultData.message = "data not found";
				resultData.status = false;
			}
			tx = session.getTransaction();
			session.beginTransaction();
			tx.commit();
			}catch(Exception e){
				resultData.listData = null;
				resultData.message = "Some thing went wrong please contact your admin";
				resultData.status = false;
			}
			return resultData;
	}
	
	

}
