package com.kanaxis.sms.dao.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;

import com.kanaxis.sms.dao.TeacherDao;
import com.kanaxis.sms.model.Admission;
import com.kanaxis.sms.model.Teachers;
import com.kanaxis.sms.util.ResultData;
import com.kanaxis.sms.util.Teacher;

public class TeacherDaoImpl implements TeacherDao{
	
	@Autowired
	SessionFactory sessionFactory;

	Session session = null;
	Transaction tx = null;

	@Override
	public ResultData getTeachersList() throws Exception {
		ResultData resultData = new ResultData();
		Teacher teacher = null;
		List listTeachers = new ArrayList();
		DateFormat df = null;
		try{
		session = sessionFactory.openSession();
		tx = session.beginTransaction();
		Query query = session.createQuery("From Teachers");
		List<Teachers> teachersList = query.list();
		if(teachersList!=null && !teachersList.isEmpty()){
			for(Teachers teachers : teachersList){				
				teacher = new Teacher();
				teacher.setId(teachers.getId());
				teacher.setTeacherName(teachers.getTeacherName());
				teacher.setQualification(teachers.getQualification());
				teacher.setEmail(teachers.getEmail());
				teacher.setExp(teachers.getExperience());
				if(teachers.getPhoto()!=null){
					teacher.setPhoto(teachers.getPhoto());
				}
				teacher.setPhone1(teachers.getPhone1());
				if(teachers.getPhone2()!=null){
					teacher.setPhoto(teachers.getPhone2());
				}
				teacher.setAddress(teachers.getAddress());
				teacher.setAddress(teachers.getAddress());
				df = new SimpleDateFormat("dd-MM-yyyy");
				String date = df.format(teachers.getJoiningDate());				
				teacher.setJoiningDate(date);
				listTeachers.add(teacher);
			
			}
				
		resultData.listData = listTeachers;
		resultData.status = true;
		resultData.message = "data Found";
			
		}else{
			resultData.listData = null;
			resultData.status = false;
			resultData.message = "data not found";
		}
		tx.commit();
		session.close();
		}catch(Exception e){
			resultData.listData = null;
			resultData.status = false;
			resultData.message = "Some thing went wrong please contact your admin";
		}
		return resultData;
	}

	@Override
	public ResultData getTeacherDetails(int teacherId) throws Exception {
		// TODO Auto-generated method stub
		ResultData resultData = new ResultData();
		Teacher teacher = new Teacher();
		DateFormat df = null;
		try{
		session = sessionFactory.openSession();
		Teachers teachers = (Teachers) session.get(Teachers.class,
				new Integer(teacherId));
		if(teachers!=null){
			teacher.setId(teachers.getId());
			teacher.setTeacherName(teachers.getTeacherName());
			teacher.setQualification(teachers.getQualification());
			teacher.setEmail(teachers.getEmail());
			teacher.setExp(teachers.getExperience());
			if(teachers.getPhoto()!=null){
				teacher.setPhoto(teachers.getPhoto());
			}
			teacher.setPhone1(teachers.getPhone1());
			if(teachers.getPhone2()!=null){
				teacher.setPhoto(teachers.getPhone2());
			}
			teacher.setAddress(teachers.getAddress());
			df = new SimpleDateFormat("dd-MM-yyyy");
			String date = df.format(teachers.getJoiningDate());
			teacher.setJoiningDate(date);
			resultData.object = teacher;
			resultData.message = "data found";
			resultData.status = true;
		}else{
			resultData.object = null;
			resultData.message = "data not found";
			resultData.status = false;
		}
		tx = session.getTransaction();
		session.beginTransaction();
		tx.commit();
		}catch(Exception e){
			resultData.object = null;
			resultData.message = "Some thing went wrong please contact your admin";
			resultData.status = false;
		}
		return resultData;
	}

	@Override
	public ResultData addTechers(String teacherName, String qualification,
			String email, String exp, String photo, String phone1,
			String phone2, String address, String joiningDate, String gender,
			String jobTitle) {
		Calendar  cal = null;
		session = sessionFactory.openSession();
		ResultData resultData = new ResultData();
		Teachers teacher = new Teachers();
		try{
			teacher.setTeacherName(teacherName);
			teacher.setAddress(address);
			teacher.setEmail(email);
			teacher.setQualification(qualification);
			teacher.setExperience(Float.parseFloat(exp));
			teacher.setPhoto(photo);
			teacher.setPhone1(phone1);
			if(phone2!=null){
				teacher.setPhone2(phone2);
			}
			DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
			teacher.setJoiningDate(format.parse(joiningDate));
			teacher.setStatus(true);
			teacher.setGender(gender);
			teacher.setJobTitle(jobTitle);
			cal = Calendar.getInstance();
			teacher.setCreatedDate(cal.getTime());
			
			tx = session.beginTransaction();
			session.save(teacher);
			tx.commit();
			session.close();
			
			resultData.status = true;
			resultData.message = "Teacher added successfully";		
			return resultData;
			
		}catch(Exception e){
			resultData.status = false;
			resultData.message = "Some thing went wrong please contact your admin";	
			return resultData;
		}
	}

	@Override
	public ResultData updateTeachers(String id, String teacherName,
			String qualification, String email, String exp, String photo,
			String phone1, String phone2, String address, String joiningDate,
			String gender, String jobTitle) {
		session = sessionFactory.openSession();
		ResultData resultData = new ResultData();
		try{			
			Teachers teacher = (Teachers)session.get(Teachers.class, new Integer(Integer.parseInt(id)));
			if(teacher == null){
				
			}
			if(!teacherName.isEmpty()){			
			teacher.setTeacherName(teacherName);
			}else{
				teacher.setTeacherName(teacher.getTeacherName());
			}
			if(!address.isEmpty()){
			teacher.setAddress(address);
			}else{
				teacher.setAddress(teacher.getAddress());
			}
			if(!email.isEmpty()){
			teacher.setEmail(email);
			}else{
				teacher.setEmail(teacher.getEmail());
			}
			if(!qualification.isEmpty()){
			teacher.setQualification(qualification);
			}else{
				teacher.setQualification(teacher.getQualification());
			}
			if(!exp.isEmpty()){
			teacher.setExperience(Float.parseFloat(exp));
			}else{
				teacher.setExperience(teacher.getExperience());
			}
			if(!photo.isEmpty()){
			teacher.setPhoto(photo);
			}else{
				teacher.setPhoto(teacher.getPhoto());
			}
			if(!phone1.isEmpty()){
			teacher.setPhone1(phone1);
			}else{
				teacher.setPhone1(teacher.getPhone1());
			}
			if(!phone2.isEmpty()){
				teacher.setPhone2(phone2);
			}else{
				if(teacher.getPhone2()!=null){
					teacher.setPhone2(teacher.getPhone2());
				}
			}
			
			if(!joiningDate.isEmpty()){
			DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
			teacher.setJoiningDate(format.parse(joiningDate));
			}else{
				teacher.setJoiningDate(teacher.getJoiningDate());
			}
			if(!gender.isEmpty()){
			teacher.setGender(gender);
			}else{
				teacher.setGender(teacher.getGender());
			}
			if(!jobTitle.isEmpty()){
			teacher.setJobTitle(jobTitle);
			}else{
				teacher.setJobTitle(teacher.getJobTitle());
			}
			
			
			tx = session.beginTransaction();
			session.update(teacher);
			tx.commit();
			session.close();
			
			resultData.status = true;
			resultData.message = "Teacher updated successfully";		
			return resultData;
			
		}catch(Exception e){
			resultData.status = false;
			resultData.message = "Some thing went wrong please contact your admin";	
			return resultData;
		}
	}

	@Override
	public ResultData getTeacherByEmail(String email) {
		ResultData resultData = new ResultData();
		Query query  = null;
		Teachers teacher = null;
		try{
			
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			
			// To check email duplication
			 query = session.createQuery("from Teachers where email=:email");
			query.setParameter("email", email);
			 teacher = (Teachers) query.uniqueResult();
			if(teacher==null){
				resultData.status = true;				
			}else{
				resultData.object = teacher;
				resultData.status = false;
				resultData.message = "Teacher already exist with this email";
			}			
			
		}catch(Exception e){
			resultData.status = false;
			resultData.message = "Some thing went wrong please contact your admin";
			
		}
		return resultData;
	}

	
	

}
