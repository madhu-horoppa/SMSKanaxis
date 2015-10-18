package com.kanaxis.sms.services.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.kanaxis.sms.dao.NotificationDao;
import com.kanaxis.sms.services.NotificationService;
import com.kanaxis.sms.util.ResultData;

public class NotificationServiceImpl implements NotificationService{
	
	@Autowired
	NotificationDao notificationDao;

	@Override
	public ResultData addNotifications(String fromId, String messageTypeId,
			String message, String toId, String classId, String sectionId) {
		return notificationDao.addNotifications(fromId,messageTypeId,message,toId,classId,sectionId);
	}

	@Override
	public ResultData getSentNotifications(String user_id) {
		// TODO Auto-generated method stub
		return notificationDao.getSentNotifications(user_id);
	}

	@Override
	public ResultData getNotifications(String id) {
		return notificationDao.getNotifications(id);
	}

	@Override
	public ResultData getMessageTypes() {
		return notificationDao.getMessageTypes();
	}

	
	

}
