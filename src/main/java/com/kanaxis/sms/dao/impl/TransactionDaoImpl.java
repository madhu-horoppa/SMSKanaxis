package com.kanaxis.sms.dao.impl;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import com.kanaxis.sms.dao.TransactionDao;
import com.kanaxis.sms.model.TransactionReport;
import com.kanaxis.sms.util.ResultData;

public class TransactionDaoImpl implements TransactionDao{
	
	@Autowired
	SessionFactory sessionFactory;

	Session session = null;
	Transaction tx = null;


	@Override
	public ResultData addTransactionReport(String transactionJson) {
		session = sessionFactory.openSession();
		tx = session.beginTransaction();
		ResultData resultData = new ResultData();
		JSONObject json = null;
		String class_id = "";
		String section_id = "";
		String subject_id = "";
		JSONArray jsonArray = null;
		JSONObject json1 = null;
		String topic = "";
		String date = "";
		String sqlQuery = "insert into transaction_report(class_id, section_id, subj_id, topic, date, createdDate) values";
		try{
			json = new JSONObject(transactionJson);
			if(json.has("class_id")){
				class_id = json.getString("class_id");
			}else{
				resultData.status = false;
				resultData.message = "Class id is mandatory";
				return resultData;
			}
			if(json.has("section_id")){
				section_id = json.getString("section_id");
			}else{
				resultData.status = false;
				resultData.message = "Section id is mandatory";
				return resultData;
			}
			if(json.has("subject_id")){
				subject_id = json.getString("subject_id");
			}else{
				resultData.status = false;
				resultData.message = "Subject id is mandatory";
				return resultData;
			}
			if(json.has("transactionData")){
			jsonArray = json.getJSONArray("transactionData");			
			for(int i=0; i<jsonArray.length(); i++){
				json1 = jsonArray.getJSONObject(i);
				if(json1.has("topic")){
					topic = json1.getString("topic");
				}else{
					resultData.status = false;
					resultData.message = "Topic is mandatory";
					return resultData;
				}
				
				if(json1.has("date")){
					date = json1.getString("date");
				}else{
					resultData.status = false;
					resultData.message = "Date is mandatory";
					return resultData;
				}
				Date date1 = new SimpleDateFormat("dd-MM-yyyy").parse(date);
				java.sql.Date sqlDate = new java.sql.Date(date1.getTime());
				
				
				java.util.Date today = new java.util.Date();
				java.sql.Timestamp sqlTimestamp = new Timestamp(
						today.getTime());
				
				// To check duplicates
				
				
				Query query = session.createSQLQuery("select * from transaction_report where class_id=:calssId and section_id=:sectionId and subj_id=:subjectId and date=:sqlDate");
				query.setParameter("calssId", Integer.parseInt(class_id));
				query.setParameter("sectionId", Integer.parseInt(section_id));
				query.setParameter("subjectId", Integer.parseInt(subject_id));
				query.setParameter("sqlDate", sqlDate);
				
				Object o=  query.uniqueResult();
				
				if(o == null){
				
				if(i == jsonArray.length()-1){
				
				sqlQuery = sqlQuery + "(" + class_id + "," + section_id + "," + subject_id + "," +
				            "'" + topic + "'" +"," + "'" + sqlDate + "'"+ ","+"'"+sqlTimestamp+"'"+");";
				}else{
					sqlQuery = sqlQuery + "(" + class_id + "," + section_id + "," + subject_id + "," +
				            "'" + topic + "'" +"," + "'" + sqlDate + "'"+ ","+"'"+sqlTimestamp+"'"+"),";
				}
				}else{
					
					resultData.status = false;
					resultData.message = "Transaction already exist with this date "+ date;
					return resultData;
				}
			}
			System.out.println(sqlQuery);
			Query query = session.createSQLQuery(sqlQuery);
			int result = query.executeUpdate();
			tx.commit();
			session.close();
			resultData.status = true;
			resultData.message = "Transaction report added successfully";
			
			}else{
				resultData.status = false;
				resultData.message = "Transacion data is mandatory";
				return resultData;
			}
			
			
		}catch(Exception e){
			resultData.status = true;
			resultData.message = "Some thing went wrong please contact your admin";
		}
		return resultData;
		}


	@Override
	public ResultData getTransactionReport(String class_id, String section_id, String subject_id) {
		session = sessionFactory.openSession();
		tx = session.beginTransaction();
		ResultData resultData = new ResultData();
		Map transactionReportMap = null;
		List listTransactionReport = null;
		Map mapTransactionReport = new LinkedHashMap();
		try{
			
			Criteria criteria = session.createCriteria(TransactionReport.class).add(Restrictions.eq("classes.id", Integer.parseInt(class_id))).
					           add(Restrictions.eq("section.id", Integer.parseInt(section_id))).add(Restrictions.eq("subject.id", Integer.parseInt(subject_id)));
			List<TransactionReport> transactionReportsList = criteria.list();
			if(transactionReportsList!=null && !transactionReportsList.isEmpty()){
				for(TransactionReport transactionReport : transactionReportsList){
					if (mapTransactionReport.containsKey("transactionData")) {

						listTransactionReport = (List) mapTransactionReport
								.get("transactionData");
						transactionReportMap = new LinkedHashMap();
						transactionReportMap.put("topic", transactionReport.getTopic());
						transactionReportMap.put("date",
								transactionReport.getDate());
						listTransactionReport.add(transactionReportMap);
					} else {

						listTransactionReport = new ArrayList();
						transactionReportMap = new LinkedHashMap();
						transactionReportMap.put("topic", transactionReport.getTopic());
						transactionReportMap.put("date",
								transactionReport.getDate());
						listTransactionReport.add(transactionReportMap);
						mapTransactionReport.put("transactionData",
								listTransactionReport);
					}
					
					
			}
				resultData.map = mapTransactionReport;
				resultData.status = true;
				resultData.message = "Data found";
			}else{
				resultData.map = null;
				resultData.status = false;
				resultData.message = "Data not found";
			}
			
		}catch(Exception e){
			resultData.map = null;
			resultData.status = false;
			resultData.message = "Some thing went wrong please contact your admin";
		}
		return resultData;
	}


	@Override
	public ResultData getTransactionReportForInbox(String class_id,
			String section_id) {
		session = sessionFactory.openSession();
		tx = session.beginTransaction();
		ResultData resultData = new ResultData();
		Map mapTransactionReport = new LinkedHashMap();
		List listTransactionReport1 = new ArrayList();
		Map mapTransactionReport1 = new LinkedHashMap();
		
		
		try{
			Criteria criteria = session.createCriteria(TransactionReport.class).add(Restrictions.eq("classes.id", Integer.parseInt(class_id))).add(Restrictions.eq("section.id",Integer.parseInt(section_id)));
			List<TransactionReport> transactionReportList = criteria.list();
			if(transactionReportList!=null && !transactionReportList.isEmpty()){
				for(TransactionReport transactionReport:transactionReportList){
					
					if(mapTransactionReport1.containsKey(new SimpleDateFormat("dd-MM-yyyy HH:MM:SS").format(transactionReport.getCreatedDate()))){
						//List list = (List) mapTransactionReport1.get(new SimpleDateFormat("dd-MM-yyyy HH:MM:SS").format(transactionReport.getCreatedDate()));
						//for(int i=0; i<list.size();i++){
							//Map map = (Map) list.get(i);
						if(mapTransactionReport.containsKey(transactionReport.getSubject().getSubjectName())){
						List listTransactionReport = (List) mapTransactionReport.get(transactionReport.getSubject().getSubjectName());
						Map transactionReportMap = new LinkedHashMap();
						transactionReportMap.put("topic", transactionReport.getTopic());
						transactionReportMap.put("date", new SimpleDateFormat("dd-MM-yyyy").format(transactionReport.getDate()));
						listTransactionReport.add(transactionReportMap);
						mapTransactionReport.put(transactionReport.getSubject().getSubjectName(), listTransactionReport);						
						}else{
							List listTransactionReport = new ArrayList();
							Map transactionReportMap = new LinkedHashMap();
							transactionReportMap.put("topic", transactionReport.getTopic());
							transactionReportMap.put("date", new SimpleDateFormat("dd-MM-yyyy").format(transactionReport.getDate()));
							listTransactionReport.add(transactionReportMap);					
							mapTransactionReport.put(transactionReport.getSubject().getSubjectName(), listTransactionReport);	
						}			
						
						//}
						/*list.add(mapTransactionReport);
						mapTransactionReport1.put(new SimpleDateFormat("dd-MM-yyyy HH:MM:SS").format(transactionReport.getCreatedDate()), list);*/
					}else{
					List listTransactionReport = new ArrayList();
					List list = new ArrayList();
					Map transactionReportMap = new LinkedHashMap();
					mapTransactionReport = new LinkedHashMap();
					transactionReportMap.put("topic", transactionReport.getTopic());
					transactionReportMap.put("date", new SimpleDateFormat("dd-MM-yyyy").format(transactionReport.getDate()));
					listTransactionReport.add(transactionReportMap);					
					mapTransactionReport.put(transactionReport.getSubject().getSubjectName(), listTransactionReport);				
					list.add(mapTransactionReport);
					mapTransactionReport1.put(new SimpleDateFormat("dd-MM-yyyy HH:MM:SS").format(transactionReport.getCreatedDate()), list);
					}
					
					
				}
				resultData.map = mapTransactionReport1;
				resultData.status = true;
				resultData.message = "Data found";
				
			}else{
				resultData.map = null;
				resultData.status = true;
				resultData.message = "Data not found";
			}
		}catch(Exception e){
			resultData.map = null;
			resultData.status = true;
			resultData.message = "Some thing went wrong please contact your admin";
		}
		return resultData;
	}

}
