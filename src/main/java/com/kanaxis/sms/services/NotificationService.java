package com.kanaxis.sms.services;

import com.kanaxis.sms.util.ResultData;

public interface NotificationService {
	
	public ResultData addNotifications(String fromId, String messageTypeId, String message, String toId, String classId, String sectionId );
	public ResultData getSentNotifications(String userId);
	public ResultData getNotifications(String id);
	public ResultData getMessageTypes();
	

}
