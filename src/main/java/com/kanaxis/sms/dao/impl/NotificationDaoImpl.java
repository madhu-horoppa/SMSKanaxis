package com.kanaxis.sms.dao.impl;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import com.kanaxis.sms.dao.NotificationDao;
import com.kanaxis.sms.model.Classes;
import com.kanaxis.sms.model.Examtype;
import com.kanaxis.sms.model.Messagetype;
import com.kanaxis.sms.model.Notifications;
import com.kanaxis.sms.model.Section;
import com.kanaxis.sms.model.Student;
import com.kanaxis.sms.model.Teachers;
import com.kanaxis.sms.model.User;
import com.kanaxis.sms.util.NotifictionDetails;
import com.kanaxis.sms.util.ResultData;

public class NotificationDaoImpl implements NotificationDao{
	
	@Autowired
	SessionFactory sessionFactory;

	Session session = null;
	Transaction tx = null;

	@Override
	public ResultData addNotifications(String from_id, String messageType_id,
			String message, String to_id, String class_id, String section_id) {
		int toId = 0;
		int classId = 0;
		int sectionId = 0;
		session = sessionFactory.openSession();
		tx = session.beginTransaction();
		ResultData resultData = new ResultData();
		Notifications notification = null;
		Calendar  cal = null;
		int fromId = Integer.parseInt(from_id);
		int messageTypeId = Integer.parseInt(messageType_id);
		if(to_id!=null){
		 toId = Integer.parseInt(to_id);
		}
		if(class_id!=null){
		 classId = Integer.parseInt(class_id);
		}
		if(section_id!=null){
		 sectionId = Integer.parseInt(section_id);
		}
		try{
		User user = (User) session.get(User.class, new Integer(fromId));
		if(user == null){
			
			resultData.status = false;
			resultData.message = "No user found with this id";
			return resultData;
			
		}
		
		Messagetype messageType = (Messagetype) session.get(Messagetype.class, new Integer(messageTypeId));
		if(messageType == null){
			
			resultData.status = false;
			resultData.message = "No message type found with this id";
			return resultData;
			
		}
		
		if(messageType.getMessageType().equals("Individual")){
			notification = new Notifications();
			//User user1 = (User) session.get(User.class, new Integer(toId));
			
			//Student student = (Student) session.get(Student.class, toId);
			
			notification.setUser(user);
			notification.setFromName(user.getRole().getRoleName());
			notification.setMessagetype(messageType);
			notification.setMessage(message);
			notification.setStatus(false);
			notification.setToId(toId);
			notification.setToName("Parent");
			notification.setClassId(classId);
			notification.setSectionId(sectionId);
			cal = Calendar.getInstance();
			notification.setCreatedDate(cal.getTime());
		}else if(messageType.getMessageType().equals("Classwise")){
			notification = new Notifications();
			notification.setUser(user);
			notification.setFromName(user.getRole().getRoleName());
			notification.setMessagetype(messageType);
			notification.setMessage(message);
			notification.setStatus(false);
			notification.setClassId(classId);
			notification.setSectionId(sectionId);
			cal = Calendar.getInstance();
			notification.setCreatedDate(cal.getTime());
			
		}else if(messageType.getMessageType().equals("General")){
			notification = new Notifications();
			notification.setUser(user);
			notification.setFromName(user.getRole().getRoleName());
			notification.setMessagetype(messageType);
			notification.setMessage(message);
			notification.setStatus(false);
			cal = Calendar.getInstance();
			notification.setCreatedDate(cal.getTime());
			
		}else if(messageType.getMessageType().equals("Teacher")){
			notification = new Notifications();
			
			notification.setUser(user);
			notification.setFromName(user.getRole().getRoleName());
			notification.setMessagetype(messageType);
			notification.setMessage(message);
			notification.setStatus(false);
			notification.setToId(toId);
			notification.setToName("Teacher");
			cal = Calendar.getInstance();
			notification.setCreatedDate(cal.getTime());
			
		}else if(messageType.getMessageType().equals("Activities")){
			notification = new Notifications();
			notification.setUser(user);
			notification.setFromName(user.getRole().getRoleName());
			notification.setMessagetype(messageType);
			notification.setMessage(message);
			notification.setStatus(false);
			cal = Calendar.getInstance();
			notification.setCreatedDate(cal.getTime());
			
		}
		
		
		session.save(notification);
		tx.commit();
		session.close();
		
		resultData.status = true;
		resultData.message = "Notification added successfully";		
		return resultData;
		}catch(Exception e){
			
			resultData.status = false;
			resultData.message = "Some thing went wrong please contact your admin";	
			return resultData;
		}
	}

	@Override
	public ResultData getSentNotifications(String user_id) {
		session = sessionFactory.openSession();
		ResultData resultData = new ResultData();
		NotifictionDetails notificationDetails = null;
		List listNotifications = new ArrayList();
		try{
		
			Criteria criteria = session.createCriteria(Notifications.class).add(Restrictions.eq("user.id", Integer.parseInt(user_id))).addOrder(Order.desc("createdDate"));
			List<Notifications> notificationsList = criteria.list();
		
		if(notificationsList!=null){
			for(Notifications notifications:notificationsList){
				if(notifications.getMessagetype().getMessageType().equals("General")){
					notificationDetails = new NotifictionDetails();
					notificationDetails.setMessageType(notifications.getMessagetype().getMessageType());
					notificationDetails.setMessage(notifications.getMessage());
					DateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:MM:SS");
					String date = df.format(notifications.getCreatedDate());
					notificationDetails.setCreatedDate(date);
				}else if(notifications.getMessagetype().getMessageType().equals("Classwise")){
					notificationDetails = new NotifictionDetails();
					
					
					Classes classes = (Classes) session.get(Classes.class, new Integer(notifications.getClassId()));
					
					Query query1 = session.createQuery("from Section where id=:id and classes=:class_id");
					query1.setParameter("class_id", classes);
					query1.setParameter("id", notifications.getSectionId());
					Section section = (Section) query1.uniqueResult();
					if(section!=null){
						notificationDetails.setMessageType(notifications.getMessagetype().getMessageType());
						notificationDetails.setMessage(notifications.getMessage());
						notificationDetails.setClasses(classes.getClassName());
						notificationDetails.setSection(section.getSectionName());
						DateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:MM:SS");
						String date = df.format(notifications.getCreatedDate());
						notificationDetails.setCreatedDate(date);
					}
				}else if(notifications.getMessagetype().getMessageType().equals("Individual")){
                       notificationDetails = new NotifictionDetails();					
					
					Student student = (Student) session.get(Student.class, notifications.getToId());
					
				
					if(student!=null){
						notificationDetails.setMessageType(notifications.getMessagetype().getMessageType());
						notificationDetails.setMessage(notifications.getMessage());
						notificationDetails.setClasses(student.getClasses().getClassName());
						notificationDetails.setSection(student.getSection().getSectionName());
						notificationDetails.setToName(notifications.getToName());
						notificationDetails.setRollNum(student.getRollNumber());
						notificationDetails.setStudentName(student.getFirstName());
						DateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:MM:SS");
						notificationDetails.setCreatedDate(df.format(notifications.getCreatedDate()));
					}
				}else if(notifications.getMessagetype().getMessageType().equals("Teacher")){
					notificationDetails = new NotifictionDetails();
					Teachers teacher = (Teachers) session.get(Teachers.class, notifications.getToId());
					if(teacher!=null){
						notificationDetails.setMessageType(notifications.getMessagetype().getMessageType());
						notificationDetails.setMessage(notifications.getMessage());
						notificationDetails.setToName(teacher.getTeacherName());
						notificationDetails.setCreatedDate(new SimpleDateFormat("dd-MM-yyyy HH:MM:SS").format(notifications.getCreatedDate()));
					}
				}else if(notifications.getMessagetype().getMessageType().equals("Activities")){
					notificationDetails = new NotifictionDetails();
					notificationDetails.setMessageType(notifications.getMessagetype().getMessageType());
					notificationDetails.setMessage(notifications.getMessage());
					DateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:MM:SS");
					String date = df.format(notifications.getCreatedDate());
					notificationDetails.setCreatedDate(date);
				}else if(notifications.getMessagetype().getMessageType().equals("Admin")){
					notificationDetails = new NotifictionDetails();
					notificationDetails.setMessageType(notifications.getMessagetype().getMessageType());
					notificationDetails.setMessage(notifications.getMessage());
					DateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:MM:SS");
					String date = df.format(notifications.getCreatedDate());
					notificationDetails.setCreatedDate(date);
				}
                  listNotifications.add(notificationDetails);
			}
			
			resultData.listData = listNotifications;
			resultData.status = true;
			resultData.message = "data found";
		}else{
			resultData.listData = null;
			resultData.status = false;
			resultData.message = "data not found";
		}
		}catch(Exception e){
			resultData.listData = null;
			resultData.status = false;
			resultData.message = "Some thing went wrong please contact your admin";	
		}
		return resultData;
	}

	
	@Override
	public ResultData getNotifications(String user_id) {
		session = sessionFactory.openSession();
		ResultData resultData = new ResultData();
		List listNotifications = new ArrayList();
		List<Notifications> notificationsList = null;
		DateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:MM:SS");
		try{
			User user = (User) session.get(User.class, Integer.parseInt(user_id));
			if(user.getRole().getRoleName().equals("Teacher")){
			Query query = session.createQuery("from Notifications where toId=:to_id order by createdDate desc");
			query.setParameter("to_id", user.getTeacherId());
			 notificationsList = query.list();
			if(notificationsList!=null && !notificationsList.isEmpty()){
				for(Notifications notifications : notificationsList){
					Map map = new LinkedHashMap();
					Student student = (Student) session.get(Student.class, notifications.getUser().getStId());
					map.put("messageFrom", notifications.getFromName());
					map.put("messageTo", notifications.getToName());
					map.put("message", notifications.getMessage());
					map.put("parentName", notifications.getUser().getName());
					map.put("teachertName", user.getName());
					map.put("stdentName", student.getFirstName());
					map.put("rollNumber", student.getRollNumber());
					map.put("createdDate", df.format(notifications.getCreatedDate()));
					listNotifications.add(map);
					
				}
				resultData.listData = listNotifications;
				resultData.status = true;
				resultData.message = "Data found";
			}else{
				resultData.listData = null;
				resultData.status = false;
				resultData.message = "Data not found";
			}
			
			}else if(user.getRole().getRoleName().equals("Parent")){
				
				Query query = session.createQuery("from Notifications where toId=:to_id order by createdDate desc");
				query.setParameter("to_id", user.getStId());
				 notificationsList = query.list();
				if(notificationsList!=null && !notificationsList.isEmpty()){
					for(Notifications notifications : notificationsList){
						Map map = new LinkedHashMap();
						Student student = (Student) session.get(Student.class, user.getStId());
						map.put("messageFrom", notifications.getFromName());
						map.put("messageTo", notifications.getToName());
						map.put("parentName", user.getName());
						map.put("teacherName", notifications.getUser().getName());
						map.put("message", notifications.getMessage());
						map.put("TeacherName", notifications.getUser().getName());
						map.put("createdDate", df.format(notifications.getCreatedDate()));
						listNotifications.add(map);
						
					}
					resultData.listData = listNotifications;
					resultData.status = true;
					resultData.message = "Data found";
				}else{
					resultData.listData = null;
					resultData.status = false;
					resultData.message = "Data not found";
				}
				
			}else{
				Query query = session.createQuery("from Notifications where toName=:toName OR toName=:toName1 order by createdDate desc");
				query.setParameter("toName", "Teacher");
				query.setParameter("toName1", "Admin");
				notificationsList = query.list();
				if(notificationsList!=null && !notificationsList.isEmpty()){
					for(Notifications notifications : notificationsList){
						if(notifications.getToName().equals("Teacher")){
							Map map = new LinkedHashMap();
							Student student = (Student) session.load(Student.class, notifications.getUser().getStId());
							Teachers teacher = (Teachers) session.load(Teachers.class, notifications.getToId());
							map.put("messageFrom", notifications.getFromName());
							map.put("massageTo", notifications.getToName());
							map.put("message", notifications.getMessage());
							map.put("parentName", notifications.getUser().getName());
							map.put("teacherName", teacher.getTeacherName());							
							map.put("stdentName", student.getFirstName());
							map.put("rollNumber", student.getRollNumber());
							map.put("createdDate", df.format(notifications.getCreatedDate()));
							listNotifications.add(map);
						
					}else if(notifications.getToName().equals("Admin")){
						Map map = new LinkedHashMap();						
						if(notifications.getFromName().equals("Parent")){
						 Student student = (Student) session.load(Student.class, notifications.getUser().getStId());
						map.put("messageFrom", notifications.getFromName());
						map.put("messageTo", notifications.getToName());
						map.put("message", notifications.getMessage());
						map.put("parentName", notifications.getUser().getName());						
						map.put("stdentName", student.getFirstName());
						map.put("rollNumber", student.getRollNumber());
						map.put("createdDate", df.format(notifications.getCreatedDate()));
						listNotifications.add(map);
						}else{
							map.put("messageFrom", notifications.getFromName());
							map.put("message", notifications.getMessage());
							map.put("teacherName", notifications.getUser().getName());
							map.put("createdDate", df.format(notifications.getCreatedDate()));
							listNotifications.add(map);
						}
					}
					}
					resultData.listData = listNotifications;
					resultData.status = true;
					resultData.message = "Data found";
					
				}else{
					resultData.listData = null;
					resultData.status = false;
					resultData.message = "Data not found";
				}
				
				
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
	public ResultData getMessageTypes() {
		session = sessionFactory.openSession();
		ResultData resultData = new ResultData();
		tx = session.beginTransaction();
		Map messageTypeMap = new LinkedHashMap();
		try{
			Query query = session.createQuery("from Messagetype");
			List<Messagetype> messageTypeList = query.list();
			if(messageTypeList!=null && !messageTypeList.isEmpty()){
				for(Messagetype messageType: messageTypeList){
					
					messageTypeMap.put(messageType.getId(), messageType.getMessageType());
					
				}
				
				resultData.map = messageTypeMap;
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
