package com.kanaxis.sms.dao.impl;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;

import com.kanaxis.sms.dao.NotificationDao;
import com.kanaxis.sms.model.Classes;
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
			User user1 = (User) session.get(User.class, new Integer(toId));
			if(user1 == null){
				
				resultData.status = false;
				resultData.message = "No user found with this id";
				return resultData;
				
			}
			
			notification.setFromId(fromId);
			notification.setFromName(user.getRole().getRoleName());
			notification.setMessagetype(messageType);
			notification.setMessage(message);
			notification.setStatus(false);
			notification.setToId(toId);
			notification.setToName(user1.getRole().getRoleName());
			notification.setClassId(classId);
			notification.setSectionId(sectionId);
			cal = Calendar.getInstance();
			notification.setCreatedDate(cal.getTime());
		}else if(messageType.getMessageType().equals("Classwise")){
			notification = new Notifications();
			notification.setFromId(fromId);
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
			notification.setFromId(fromId);
			notification.setFromName(user.getRole().getRoleName());
			notification.setMessagetype(messageType);
			notification.setMessage(message);
			notification.setStatus(false);
			cal = Calendar.getInstance();
			notification.setCreatedDate(cal.getTime());
			
		}else if(messageType.getMessageType().equals("Teacher")){
			notification = new Notifications();
			User user1 = (User) session.get(User.class, new Integer(toId));
			if(user1 == null){
				
				resultData.status = false;
				resultData.message = "No user found with this id";
				return resultData;
				
			}
			notification.setFromId(fromId);
			notification.setFromName(user.getRole().getRoleName());
			notification.setMessagetype(messageType);
			notification.setMessage(message);
			notification.setStatus(false);
			notification.setToId(toId);
			notification.setToName(user1.getRole().getRoleName());
			cal = Calendar.getInstance();
			notification.setCreatedDate(cal.getTime());
			
		}
		
		tx = session.beginTransaction();
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
	public ResultData getNotifications() {
		session = sessionFactory.openSession();
		ResultData resultData = new ResultData();
		NotifictionDetails notificationDetails = null;
		List listNotifications = new ArrayList();
		try{
		Query query = session.createQuery("from Notifications where fromName = :from_name order by createdDate desc");
		query.setParameter("from_name", "Admin");
		List<Notifications> notificationsList = query.list();
		
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
					
					Classes classes = (Classes) session.get(Classes.class, new Integer(notifications.getClassId()));
					
					Query query1 = session.createQuery("from Section where id=:id and classes=:class_id");
					query1.setParameter("class_id", classes);
					query1.setParameter("id", notifications.getSectionId());
					Section section = (Section) query.uniqueResult();
					
					Query query3 = session.createQuery("from Student where id=:id and sectionId=:section_id and classId:=class_id");
					query3.setParameter("id", notifications.getToId());
					query3.setParameter("section_id", section);
					query3.setParameter("class_id", classes);
					Student student = (Student) query3.uniqueResult();
					if(student!=null){
						notificationDetails.setMessageType(notifications.getMessagetype().getMessageType());
						notificationDetails.setMessage(notifications.getMessage());
						notificationDetails.setClasses(classes.getClassName());
						notificationDetails.setSection(section.getSectionName());
						notificationDetails.setRollNum(student.getRollNumber());
					}
				}else if(notifications.getMessagetype().getMessageType().equals("Teacher")){
					notificationDetails = new NotifictionDetails();
					Query query1 = session.createQuery("from Teachers where id=:id");
					query1.setParameter("id", notifications.getToId());
					Teachers teacher = (Teachers) query.uniqueResult();
					if(teacher!=null){
						notificationDetails.setMessageType(notifications.getMessagetype().getMessageType());
						notificationDetails.setMessage(notifications.getMessage());
						notificationDetails.setToName(teacher.getTeacherName());
					}
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

}
