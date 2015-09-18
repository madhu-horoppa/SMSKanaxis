package com.kanaxis.sms.dao.impl;

import org.apache.commons.codec.binary.Base64;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.jws.soap.SOAPBinding.Use;

import com.kanaxis.sms.dao.LoginDao;
import com.kanaxis.sms.model.Employee;
import com.kanaxis.sms.model.Role;
import com.kanaxis.sms.model.Student;
import com.kanaxis.sms.model.Teachers;
import com.kanaxis.sms.model.User;
import com.kanaxis.sms.util.ResultData;
import com.kanaxis.sms.util.UserDetails;

public class LoginDaoImpl implements LoginDao{
	
	@Autowired
	SessionFactory sessionFactory;

	Session session = null;
	Transaction tx = null;

	@Override
	public UserDetails login(String userName, String password) throws Exception {
		UserDetails userDetails = new UserDetails();
		try{
		
		session = sessionFactory.openSession();		
		Query query = session.createQuery("FROM User user WHERE user.userName=:userName");
		query.setParameter("userName",userName);
		User user = (User) query.uniqueResult();
		//To deode the password
		byte[] valueDecoded= Base64.decodeBase64(user.getPassword().getBytes());
        String dbPassword = new String(valueDecoded);
        if(password.equals(dbPassword)){
        	userDetails.setName(user.getName());
        	userDetails.setRoleId(user.getRole().getId());
        	userDetails.setMessage("user successfully logedin");
        	userDetails.setStatus(true);        	
        }else{
        	userDetails.setMessage("userName/password wrong");
        	userDetails.setStatus(false);
        }
		}catch(Exception e){
			
			userDetails.setMessage("Some thing went wrong please contact your admin");
        	userDetails.setStatus(false);
		}
         session.close();
		return userDetails;
	}

	@Override
	public ResultData addParentCredentials(String class_id, String section_id,
			String student_id, String userName) {
		session = sessionFactory.openSession();
		tx = session.beginTransaction();
		ResultData resultData = new ResultData();
		Calendar cal;
		User user = null;
		Role role = (Role) session.get(Role.class, new Integer(2));
		
		Student student = (Student) session.get(Student.class, new Integer(Integer.parseInt(student_id)));
		try{
			Query query = session.createQuery("from User where stId=:st_id");
			query.setParameter("st_id", Integer.parseInt(student_id));
			user = (User) query.uniqueResult();
			if(user == null){
				user = new User();
			Random r = new Random( System.currentTimeMillis() );
		    int random = 10000 + r.nextInt(20000);
		    byte[]   bytesEncoded = Base64.encodeBase64(String.valueOf("random").getBytes());
		    String password = new String(bytesEncoded);
		    user.setName(student.getFatherFullName());
		    user.setStId(Integer.parseInt(student_id));
		    user.setRole(role);
		    user.setUserName(userName);
		    user.setPassword(password);
		    user.setClassId(Integer.parseInt(class_id));
		    user.setSectionId(Integer.parseInt(section_id));
		    cal = Calendar.getInstance();
		    user.setCreatedDate(cal.getTime());
		    user.setStatus(true);
		    session.save(user);
		    tx.commit();
		    session.close();
		    
		    resultData.status = true;
		    resultData.message = "Pareant credentials added successfully";
			}else{
				resultData.status = true;
			    resultData.message = "Pareant credentials alredy exist for this student "+student.getFirstName();
			    
			}
			
		}catch(Exception e){
			resultData.status = false;
			resultData.message = "Some thing went wrong please contact your admin";
		}
		return resultData;
		
		
	}

	@Override
	public ResultData getAllParentsCredentials(String class_id,
			String section_id) {
		session = sessionFactory.openSession();
		tx = session.beginTransaction();
		ResultData resultData = new ResultData();
		List listUsers = new ArrayList();
		try{
			
			Query query = session.createQuery("from User where classId=:class_id and sectionId=:section_id and status=:status");
			query.setParameter("class_id", Integer.parseInt(class_id));
			query.setParameter("section_id", Integer.parseInt(section_id));
			query.setParameter("status", true);
			List<User> usersList = query.list();
			if(usersList!=null && !usersList.isEmpty()){
				
				for(User user : usersList){
					
					Student student = (Student) session.get(Student.class, user.getStId());
					Map mapUsers = new LinkedHashMap();
					mapUsers.put("id", user.getId());
					mapUsers.put("parentName", user.getName());
					mapUsers.put("userName", user.getUserName());
					mapUsers.put("stdentName", student.getFirstName());
					DateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:MM:SS");
					mapUsers.put("createdDate", df.format(user.getCreatedDate()));
					listUsers.add(mapUsers);					
					
				}
				
				resultData.listData = listUsers;
				resultData.status = true;
				resultData.message = "Data found";
				
			}else{
				resultData.listData = null;
				resultData.status = false;
				resultData.message = "Data not found";
			}
			
			
			
		}catch(Exception e){
			resultData.listData = null;
			resultData.status = false;
			resultData.message = "Some thing went wrong please contact your admin";
		}
		session.close();
		return resultData;
	}

	@Override
	public ResultData getCredentialDetails(String user_id) {
		session = sessionFactory.openSession();
		tx = session.beginTransaction();
		ResultData resultData = new ResultData();
		Map mapUsers = new LinkedHashMap();
		try{
			User user = (User) session.get(User.class, new Integer(Integer.parseInt(user_id)));
			if(user!=null){
				if(user.getRole().getRoleName().equals("Parent")){
				Student student = (Student) session.get(Student.class, user.getStId());
				
				mapUsers.put("parentName", user.getName());
				mapUsers.put("userName", user.getUserName());
				mapUsers.put("stdentName", student.getFirstName());
				DateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:MM:SS");
				mapUsers.put("createdDate", df.format(user.getCreatedDate()));
				
			}else if(user.getRole().getRoleName().equals("Teacher")){
				mapUsers.put("teacherName", user.getName());
				mapUsers.put("userName", user.getUserName());
				DateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:MM:SS");
				mapUsers.put("createdDate", df.format(user.getCreatedDate()));
			}
				resultData.map = mapUsers;
				resultData.status = true;
				resultData.message = "Data found";
			}
			
		}catch(Exception e){
			resultData.map = null;
			resultData.status = false;
			resultData.message = "Data not found";
		}
		session.close();
		return resultData;
	}

	@Override
	public ResultData addTeacherCredentials(String teacher_id, String userName) {
		session = sessionFactory.openSession();
		tx = session.beginTransaction();
		ResultData resultData = new ResultData();
		User user = null;
		Calendar cal;
		try{
			Query query = session.createQuery("from User where teacherId=:teacher_id");
			query.setParameter("teacher_id", Integer.parseInt(teacher_id));
			 user = (User) query.uniqueResult();			 
			 
			if(user == null){
				user = new User();
				Random r = new Random( System.currentTimeMillis() );
			    int random = 10000 + r.nextInt(20000);
			    byte[]   bytesEncoded = Base64.encodeBase64(String.valueOf("random").getBytes());
			    String password = new String(bytesEncoded);
			    
			    Role role = (Role) session.get(Role.class, new Integer(3));
			    
			    
				Teachers teachers = (Teachers) session.get(Teachers.class, Integer.parseInt(teacher_id));
				user.setName(teachers.getTeacherName());
				user.setTeacherId(Integer.parseInt(teacher_id));
				user.setUserName(userName);
				user.setPassword(password);
				user.setRole(role);
				cal = Calendar.getInstance();
				user.setCreatedDate(cal.getTime());
				user.setStatus(true);
				session.save(user);
				tx.commit();
				session.close();
				resultData.status = true;
				resultData.message = "Teacher credentials added successully";
				
			}else{
				resultData.status = false;
				resultData.message = "This teacher credentials already exist";
			}
			
		}catch(Exception e){
			resultData.status = false;
			resultData.message = "Some thing went wrong please contact your admin";
		}
		return resultData;	
		}

	@Override
	public ResultData getAllTeachersCredentials() {
		session = sessionFactory.openSession();
		tx = session.beginTransaction();
		ResultData resultData = new ResultData();
		List listUsers = new ArrayList();
		try{
			Criteria criteria = session.createCriteria(User.class).add(Restrictions.eq("role.id", 3)).add(Restrictions.eq("status", true));
			List<User> usersList = criteria.list();
			if(usersList != null && !usersList.isEmpty()){
				for(User user : usersList){
					
					//Teachers teacher = (Teachers) session.get(Teachers.class, user.getTeacherId());
					
					Map mapUsers = new LinkedHashMap();
					mapUsers.put("id", user.getId());
					mapUsers.put("teacherName", user.getName());
					mapUsers.put("userName", user.getUserName());
					DateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:MM:SS");
					mapUsers.put("createdDate", df.format(user.getCreatedDate()));
					
					listUsers.add(mapUsers);
					
				}
				
				resultData.listData = listUsers;
				resultData.status = true;
				resultData.message = "Data found";
				
			}else{
				resultData.listData = null;
				resultData.status = false;
				resultData.message = "Data not found";
			}
			
			
			
		}catch(Exception e){
			resultData.listData = null;
			resultData.status = false;
			resultData.message = "Some thing went wrong please contact your admin";
		}
		session.close();
		return resultData;
	}

	@Override
	public ResultData deactivateUser(String user_id, String status) {
		session = sessionFactory.openSession();
		tx = session.beginTransaction();
		ResultData resultData = new ResultData();
		try{
			
			User user = (User) session.get(User.class, new Integer(Integer.parseInt(user_id)));
			if(user!=null){
				user.setStatus(Boolean.parseBoolean(status));
				session.update(user);
			}
			tx.commit();
			session.close();
			resultData.status = true;
			resultData.message = "User status updated successfully";
		}catch(Exception e){
			resultData.status = false;
			resultData.message = "Some thing went wrong please contact your admin";
		}
				
		return resultData;
	}

	@Override
	public ResultData changePassword(String userName, String password) {
		session = sessionFactory.openSession();
		tx = session.beginTransaction();
		ResultData resultData = new ResultData();
		try{
			
			Query query = session.createQuery("from User where userName=:userName");
			query.setParameter("userName", userName);
			User user = (User) query.uniqueResult();
			if(user!=null){
				byte[]   bytesEncoded = Base64.encodeBase64(password.getBytes());
			    String newPassword = new String(bytesEncoded);
			    user.setPassword(newPassword);
			    session.update(user);
			    tx.commit();
			    session.close();
			    resultData.status = true;
			    resultData.message = "Password changed successfully";
			}
			
		}catch(Exception e){
			resultData.status = false;
			resultData.message = "Some thing went wrong please contact your admin";
		}
		return resultData;
		}

}
