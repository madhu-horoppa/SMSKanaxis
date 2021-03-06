package com.kanaxis.sms.dao;

import com.kanaxis.sms.util.ResultData;

public interface NotificationDao {
	
	public ResultData addNotifications(String fromId, String messageTypeId, String message, String toId, String classId, String sectionId );
	public ResultData getSentNotifications(String user_id);
	public ResultData getNotifications(String id);
	public ResultData getMessageTypes();

}
