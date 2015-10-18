package com.kanaxis.sms.dao.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import com.kanaxis.sms.dao.MarksDao;
import com.kanaxis.sms.model.Examtype;
import com.kanaxis.sms.model.MarksTable;
import com.kanaxis.sms.model.Student;
import com.kanaxis.sms.model.Subject;
import com.kanaxis.sms.util.Marks;
import com.kanaxis.sms.util.ResultData;
import com.kanaxis.sms.util.StudentMarks;
import com.kanaxis.sms.util.Students;
import com.kanaxis.sms.util.TotalMarks;

public class MarksDaoImpl implements MarksDao{
	
	@Autowired
	SessionFactory sessionFactory;

	Session session = null;
	Transaction tx = null;

	@Override
	public ResultData uploadMarks(String class_id, String section_id,
			String examType_id, Map<String,List<StudentMarks>> map, List<Map.Entry<Double, TotalMarks>> entryList) {
		System.out.println(map);
		session = sessionFactory.openSession();
		tx = session.beginTransaction();
		ResultData resultData= new ResultData();
		int student=0;
		int subjectId=0;
		java.sql.Date sqlDate = null;
		Date date1 = null;
		
		Query query = null;
		String sqlquery = "insert into marks_table(st_id, subj_id, marks_obtained, max_marks, subject_wise_rank, date_of_exam, class_id, section_id, exam_type) values";
		String sqlquery1 = "insert into total_marks(class_id, section_id, examtype_id, total_marks_obtained, max_marks, percentage, class_wise_rank, roll_number) values";
		
		
    	List<String> keysList = new ArrayList<String>(map.keySet());
		for(int keys=0;keys<keysList.size();keys++){
			int subjectWiseRank = 1;
			List<StudentMarks> studentMarksList = map.get(keysList.get(keys));
			
			query = session.createSQLQuery("select id from student where roll_number=:rollNumber and classes_id=:classId and section_id=:sectionId");
	    	query.setParameter("rollNumber", (int)(studentMarksList.get(0).getRollNumber()));
	    	query.setParameter("classId", Integer.parseInt(class_id));
	    	query.setParameter("sectionId", Integer.parseInt(section_id));
	    	 student = (int) query.uniqueResult();
	    	
	    	query = session.createSQLQuery("select id from subject where subject_name=:subjectName and class_id=:classId");
        	query.setParameter("subjectName", keysList.get(keys));
        	query.setParameter("classId", Integer.parseInt(class_id));
        	 subjectId =  (int) query.uniqueResult();
        	
        	 try {
				date1 = new SimpleDateFormat("dd-MM-yyyy").parse(studentMarksList.get(0).getDateOfExam());
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			 sqlDate = new java.sql.Date(date1.getTime());		
			 
			sqlquery = sqlquery + "(" + student + "," + subjectId + "," + (int)studentMarksList.get(0).getMarks() + "," + (int)studentMarksList.get(0).getMaxMarks() + "," + subjectWiseRank + "," +
					"'"+ sqlDate+"'" + ","+ class_id + "," + section_id + "," + examType_id +"),";
			 
			
			for(int i=0;i<studentMarksList.size()-1;i++){
				
				try{
				
				query = session.createSQLQuery("select id from student where roll_number=:rollNumber and classes_id=:classId and section_id=:sectionId");
		    	query.setParameter("rollNumber", (int)(studentMarksList.get(i+1).getRollNumber()));
		    	query.setParameter("classId", Integer.parseInt(class_id));
		    	query.setParameter("sectionId", Integer.parseInt(section_id));
		    	 student = (int) query.uniqueResult();
		    	
		    	query = session.createSQLQuery("select id from subject where subject_name=:subjectName and class_id=:classId");
            	query.setParameter("subjectName", keysList.get(keys));
            	query.setParameter("classId", Integer.parseInt(class_id));
            	 subjectId =  (int) query.uniqueResult();
            	
            	 date1 = new SimpleDateFormat("dd-MM-yyyy").parse(studentMarksList.get(i).getDateOfExam());
				 sqlDate = new java.sql.Date(date1.getTime());					
				 if(keys == keysList.size()-1 && i+1==studentMarksList.size()-1){
				if(studentMarksList.get(i).getMarks() == studentMarksList.get(i+1).getMarks()){
					sqlquery = sqlquery + "(" + student + "," + subjectId + "," + (int)studentMarksList.get(i+1).getMarks() + "," + (int)studentMarksList.get(i+1).getMaxMarks() + "," + subjectWiseRank + "," +
							"'"+ sqlDate+"'" + ","+ class_id + "," + section_id + "," + examType_id +");";
				}else{
					subjectWiseRank++;
					sqlquery = sqlquery + "(" + student + "," + subjectId + "," + (int)studentMarksList.get(i+1).getMarks() + "," + (int)studentMarksList.get(i+1).getMaxMarks() + "," + subjectWiseRank + "," +
							"'"+ sqlDate+"'" + ","+ class_id + "," + section_id + "," + examType_id +");";
				
					 }
				 }else{
					 if(studentMarksList.get(i).getMarks() == studentMarksList.get(i+1).getMarks()){
							sqlquery = sqlquery + "(" + student + "," + subjectId + "," + (int)studentMarksList.get(i+1).getMarks() + "," + (int)studentMarksList.get(i+1).getMaxMarks() + "," + subjectWiseRank + "," +
									"'"+ sqlDate+"'" + ","+ class_id + "," + section_id + "," + examType_id +"),";
						}else{
							subjectWiseRank++;
							sqlquery = sqlquery + "(" + student + "," + subjectId + "," + (int)studentMarksList.get(i+1).getMarks() + "," + (int)studentMarksList.get(i+1).getMaxMarks() + "," + subjectWiseRank + "," +
									"'"+ sqlDate+"'" + ","+ class_id + "," + section_id + "," + examType_id +"),";
						}
				 }
				}catch(Exception e){
					resultData.status = false;
            		resultData.message = "Some thing went wrong please contact your admin";
            		return resultData;
				}
				
				
			}
			
		}
		
		System.out.println(sqlquery);
		
		query = session.createSQLQuery(sqlquery);
    	int result = query.executeUpdate();
    	
    	try{
    		int rank =1;
    		sqlquery1 = sqlquery1 + "("+class_id+","+section_id+","+examType_id+","+(int)entryList.get(0).getValue().getTotalMarks()+","+(int)entryList.get(0).getValue().getMaxMarks()+","+entryList.get(0).getValue().getPercentage()+
    				","+rank+","+entryList.get(0).getKey().intValue()+"),";
    	for(int i=0;i<entryList.size()-1;i++){
    		
    		
    		
    		if(i+1 == entryList.size()-1){
    		if(entryList.get(i).getValue().getTotalMarks() == entryList.get(i+1).getValue().getTotalMarks()){
    			sqlquery1 = sqlquery1 + "("+class_id+","+section_id+","+examType_id+","+(int)entryList.get(i+1).getValue().getTotalMarks()+","+(int)entryList.get(i+1).getValue().getMaxMarks()+","+entryList.get(i+1).getValue().getPercentage()+
        				","+rank+","+entryList.get(i+1).getKey().intValue()+");";
    		}else{
    			rank = rank+1;
    			sqlquery1 = sqlquery1 + "("+class_id+","+section_id+","+examType_id+","+(int)entryList.get(i+1).getValue().getTotalMarks()+","+(int)entryList.get(i+1).getValue().getMaxMarks()+","+entryList.get(i+1).getValue().getPercentage()+
        				","+rank+","+entryList.get(i+1).getKey().intValue()+");";
    		}
    		}else{
    			if(entryList.get(i).getValue().getTotalMarks() == entryList.get(i+1).getValue().getTotalMarks()){
        			sqlquery1 = sqlquery1 + "("+class_id+","+section_id+","+examType_id+","+(int)entryList.get(i+1).getValue().getTotalMarks()+","+(int)entryList.get(i+1).getValue().getMaxMarks()+","+entryList.get(i+1).getValue().getPercentage()+
            				","+rank+","+entryList.get(i+1).getKey().intValue()+"),";
        		}else{
        			rank = rank+1;
        			sqlquery1 = sqlquery1 + "("+class_id+","+section_id+","+examType_id+","+(int)entryList.get(i+1).getValue().getTotalMarks()+","+(int)entryList.get(i+1).getValue().getMaxMarks()+","+entryList.get(i+1).getValue().getPercentage()+
            				","+rank+","+entryList.get(i+1).getKey().intValue()+"),";
        		}
    		}
    		
    		
    		
    	}
    	System.out.println(sqlquery1);
    	query = session.createSQLQuery(sqlquery1);
    	int result1 = query.executeUpdate();
    	}catch(Exception e){
    		resultData.status = false;
    		resultData.message = "Some thing went wrong please contact your admin";
    		return resultData;
    	}
    	
    	
		/*for(Iterator iterator = cellVectorHolder.iterator();iterator.hasNext();) {
			Set set = (Set) iterator.next();
            List list = new ArrayList(set);
            //Collections.sort(list,Marks.marksData);
            for(int i=0;i<list.size();i++){
            	Marks marks = (Marks)list.get(i);
            }
            for(int i=0;i<list.size();i++){
            	try{
            	Marks marks = (Marks)list.get(i);
            	
            	String rollNumber = marks.getRollNumber();
            	String rollNumber1[] = rollNumber.split("\\.");
            	String subject = marks.getSubject();
            	String marksObtained = marks.getMarksObtained();
            	String marksObtained1[] = marksObtained.split("\\.");
            	String maxMarks = marks.getMaxMarks();
            	String maxMarks1[] = maxMarks.split("\\.");
            	String dateOfExam = marks.getDateOfExam();
            	String subjectWiseRank = marks.getSubjectWiseRank();
            	String subjectWiseRank1[] = subjectWiseRank.split("\\.");
            	
            	query = session.createSQLQuery("select id from student where roll_number=:rollNumber and classes_id=:classId and section_id=:sectionId");
            	query.setParameter("rollNumber", Integer.parseInt(rollNumber1[0]));
            	query.setParameter("classId", Integer.parseInt(class_id));
            	query.setParameter("sectionId", Integer.parseInt(section_id));
            	int student = (int) query.uniqueResult();
            	
            	query = session.createSQLQuery("select id from subject where subject_name=:subjectName and class_id=:classId");
            	query.setParameter("subjectName", subject);
            	query.setParameter("classId", Integer.parseInt(class_id));
            	int subjectId =  (int) query.uniqueResult();
            	
            	Date date1 = new SimpleDateFormat("dd-MM-yyyy").parse(dateOfExam);
				java.sql.Date sqlDate = new java.sql.Date(date1.getTime());
				
				if(i == list.size()-1){
					sqlquery = sqlquery + "(" + student + "," + subjectId + "," + marksObtained1[0] + "," + maxMarks1[0] + "," + subjectWiseRank1[0] + "," +
							"'"+ sqlDate+"'" + ","+ class_id + "," + section_id + "," + examType_id +");";
				}else{
					sqlquery = sqlquery + "(" + student + "," + subjectId + "," + marksObtained1[0] + "," + maxMarks1[0] + "," + subjectWiseRank1[0] + "," +
							"'"+ sqlDate+"'" + ","+ class_id + "," + section_id + "," + examType_id +"),";
				}
				
				
            	
            	}catch(Exception e){
            		
            		resultData.status = false;
            		resultData.message = "Some thing went wrong please contact your admin";
            	}            	
            	
            	
            }
			
		}*/
		
		//System.out.println(sqlquery);
    	tx.commit();
    	session.close();
		resultData.status = true;
		resultData.message = "Marks uploaded successfully";
		return resultData;
	}

	@Override
	public ResultData getAllStudentsMarks(String class_id, String section_id,
			String examType_id) {
		session = sessionFactory.openSession();
		tx = session.beginTransaction();
		ResultData resultData= new ResultData();
		Map marksMap = new LinkedHashMap();
		List listMarks = null;
		Map mapMarks = null;
		try{
			Criteria criteria = session.createCriteria(MarksTable.class).add(Restrictions.eq("classes.id", Integer.parseInt(class_id))).add(Restrictions.eq("section.id", Integer.parseInt(section_id))).add(Restrictions.eq("examtype.id", Integer.parseInt(examType_id)));
			List<MarksTable> marksList = criteria.list();
			if(marksList!=null && !marksList.isEmpty()){				
				for(MarksTable marks:marksList){
					
					if(marksMap.containsKey(marks.getStudent().getRollNumber())){
						listMarks = (List) marksMap.get(marks.getStudent().getRollNumber());
						mapMarks = new LinkedHashMap();
						mapMarks.put("id", marks.getId());
						mapMarks.put("subject", marks.getSubject().getSubjectName());
						mapMarks.put("marksObtained", marks.getMarksObtained());
						mapMarks.put("maxMarks", marks.getMaxMarks());
						mapMarks.put("subjectWiseRank", marks.getSubjectWiseRank());
						mapMarks.put("dateOfExam", marks.getDateOfExam());
						listMarks.add(mapMarks);
						marksMap.put(marks.getStudent().getRollNumber(), listMarks);
						
					}else{
						listMarks = new ArrayList();
						mapMarks = new LinkedHashMap();
						mapMarks.put("id", marks.getId());
						mapMarks.put("subject", marks.getSubject().getSubjectName());
						mapMarks.put("marksObtained", marks.getMarksObtained());
						mapMarks.put("maxMarks", marks.getMaxMarks());
						mapMarks.put("subjectWiseRank", marks.getSubjectWiseRank());
						mapMarks.put("dateOfExam", marks.getDateOfExam());
						listMarks.add(mapMarks);
						marksMap.put(marks.getStudent().getRollNumber(), listMarks);
					}
					
				}
				
				resultData.map = marksMap;
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
		return resultData;
	}

	@Override
	public ResultData updateMarks(String class_id, String section_id,
			String examType_id, String dateOfExam, String student_id,
			String subject_id, String marksObtained, String maxMarks,
			String subjectWiseRank, String marks_id) {
		session = sessionFactory.openSession();
		tx = session.beginTransaction();
		ResultData resultData= new ResultData();
		try{
			Date date = new SimpleDateFormat("dd-MM-yyyy").parse(dateOfExam);
			Criteria criteria = session.createCriteria(MarksTable.class).add(Restrictions.eq("classes.id", Integer.parseInt(class_id))).add(Restrictions.eq("section.id", Integer.parseInt(section_id))).add(Restrictions.eq("examtype.id", Integer.parseInt(examType_id))).add(Restrictions.eq("student.id", Integer.parseInt(student_id))).add(Restrictions.eq("subject.id", Integer.parseInt(subject_id)));
			MarksTable marksTable = (MarksTable) criteria.uniqueResult();
			if(marksTable.getId() == Integer.parseInt(marks_id)){
				marksTable.setMarksObtained(Double.parseDouble(marksObtained));
				marksTable.setMaxMarks(Double.parseDouble(maxMarks));
				marksTable.setSubjectWiseRank(Integer.parseInt(subjectWiseRank));
				marksTable.setDateOfExam(date);
				session.update(marksTable);
				tx.commit();
				session.close();
				
				resultData.status = true;
				resultData.message = "Marks updated successfully";
			}
			
			
					
		}catch(Exception e){
			resultData.status = false;
			resultData.message = "Marks updated successfully";
		}
		return resultData;
	}

	@Override
	public ResultData getStudentMarks(String examType_id, String st_id) {
		session = sessionFactory.openSession();
		tx = session.beginTransaction();
		ResultData resultData= new ResultData();
		List marksList = new ArrayList();
		try{
			Criteria criteria = session.createCriteria(MarksTable.class).add(Restrictions.eq("examtype.id", Integer.parseInt(examType_id))).add(Restrictions.eq("student.id", Integer.parseInt(st_id)));
			List<MarksTable> marksTableList = criteria.list();
			if(marksTableList!=null && !marksTableList.isEmpty()){
				
				for(MarksTable marksTable:marksTableList){
					Map marksTableMap = new LinkedHashMap();
					marksTableMap.put(marksTable.getSubject().getSubjectName(), marksTable.getMarksObtained());
					marksTableMap.put("maxMarks", marksTable.getMaxMarks());
					marksTableMap.put("dateOfExam", new SimpleDateFormat("dd-MM-yyyy").format(marksTable.getDateOfExam()));
					marksTableMap.put("subjectWiseRank", marksTable.getSubjectWiseRank());
					marksList.add(marksTableMap);
				}
				
				resultData.listData = marksList;
				resultData.status = true;
				resultData.message = "Data Found";
				
			}else{
				resultData.listData = null;
				resultData.status = false;
				resultData.message = "Data Not Found";
			}
		}catch(Exception e){
			resultData.status = false;
			resultData.message = "Some thing went wrong please contact your admin";
		}
		return resultData;
	}

	@Override
	public ResultData getStudentTotalMarks(String examType_id, String st_id) {
		session = sessionFactory.openSession();
		tx = session.beginTransaction();
		ResultData resultData= new ResultData();
		Map totalMarksMap = new LinkedHashMap();
		try{
			 Student student = (Student) session.get(Student.class, Integer.parseInt(st_id));
			
			
			 Examtype examType = (Examtype) session.get(Examtype.class, Integer.parseInt(examType_id));
			 
			 Query query = session.createQuery("from TotalMarks where examtype=:examType and rollNumber=:roll_number");
			 query.setParameter("examType", examType);
			 query.setParameter("roll_number", student.getRollNumber());
			 com.kanaxis.sms.model.TotalMarks totalMarks = (com.kanaxis.sms.model.TotalMarks) query.uniqueResult();
			 if(totalMarks!=null){
				 totalMarksMap.put("totalMarksObtained", totalMarks.getTotalMarksObtained());
				 totalMarksMap.put("maxMarks", totalMarks.getMaxMarks());
				 totalMarksMap.put("percentage", String.valueOf(totalMarks.getPercentage())+"%");
				 totalMarksMap.put("classWiseRank", totalMarks.getClassWiseRank());
				 resultData.map = totalMarksMap;
				 resultData.status = true;
				 resultData.message = "Data found";
			 }else{
				 resultData.map = null;
				 resultData.status = false;
				 resultData.message = "Data not found";
			 }
			
		}catch(Exception e){
			 resultData.status = false;
			 resultData.message = "Some thing went wrong please contact your admin";
		}
		return resultData;
	}

}
