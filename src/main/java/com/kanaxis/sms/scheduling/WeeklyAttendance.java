package com.kanaxis.sms.scheduling;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import com.kanaxis.sms.model.Attendance;
import com.kanaxis.sms.model.Section;

@Component("weeklyAttendance")
public class WeeklyAttendance {
	
	@Autowired
	SessionFactory sessionFactory;

	Session session = null;
	Transaction tx = null;
	
	
	public void addWeeklyAttendance(){
		//System.out.println("I am called by Quartz jobBean using CronTriggerFactoryBean");
		 session = sessionFactory.openSession();
		 tx = session.beginTransaction();
		Query query = null;
		
		String sqlQuery = "insert into weekwise_attendance (st_id,month,week_num,date_of_absent,total_days_present,total_days_absent,Num_of_working_days) values";
		
		Map attendanceMap = new LinkedHashMap();
		Map studentMap = new LinkedHashMap();
		Map studentAttendanceMap = new LinkedHashMap();
		
		try{
			
		query = session.createQuery("from Section");
		List<Section> sectionList = query.list();
		
		if(sectionList!=null){
			for(Section section:sectionList){
			
				query = session.createQuery("from Attendance where section=:section and classes=:classes and weeklyStatus=:weeklyStatus");
				query.setParameter("section", section);
				query.setParameter("classes", section.getClasses());
				query.setParameter("weeklyStatus", false);
				List<Attendance> attendanceList = query.list();
				
				if(attendanceList!=null && !attendanceList.isEmpty()){
					for(Attendance attendance:attendanceList){
						
						
						//Creating map based on date
						if(attendanceMap.containsKey(new SimpleDateFormat("dd-MM-yyyy").format(attendance.getDateOfAbsent()))){
							List listAttendance = (List) attendanceMap.get(new SimpleDateFormat("dd-MM-yyyy").format(attendance.getDateOfAbsent()));
							Map mapAttendance = new LinkedHashMap();
							mapAttendance.put("st_id", attendance.getStudent().getId());
							mapAttendance.put("status", attendance.getStatus());
							mapAttendance.put("Month", new SimpleDateFormat("MMM").format(attendance.getDateOfAbsent().getTime()));
							listAttendance.add(mapAttendance);
							attendanceMap.put(new SimpleDateFormat("dd-MM-yyyy").format(attendance.getDateOfAbsent()), listAttendance);
							
						}else{
							Map mapAttendance = new LinkedHashMap();
							mapAttendance.put("st_id", attendance.getStudent().getId());
							mapAttendance.put("status", attendance.getStatus());							
							mapAttendance.put("Month", new SimpleDateFormat("MMM").format(attendance.getDateOfAbsent().getTime()));
							List listAttendance = new ArrayList();
							listAttendance.add(mapAttendance);
							attendanceMap.put(new SimpleDateFormat("dd-MM-yyyy").format(attendance.getDateOfAbsent()), listAttendance);
						}
						
						//Creating map based on student
						if(studentMap.containsKey(attendance.getStudent().getId())){
							List studentList = (List) studentMap.get(attendance.getStudent().getId());
							Map mapStudent = new LinkedHashMap();
							mapStudent.put("status", attendance.getStatus());
							mapStudent.put("date", new SimpleDateFormat("dd-MM-yyyy").format(attendance.getDateOfAbsent()));
							studentList.add(mapStudent);
							studentMap.put(attendance.getStudent().getId(), studentList);
						}else{
							Map mapStudent = new LinkedHashMap();
							mapStudent.put("status", attendance.getStatus());							
							mapStudent.put("date", new SimpleDateFormat("dd-MM-yyyy").format(attendance.getDateOfAbsent()));
							List studentList = new ArrayList();
							studentList.add(mapStudent);
							studentMap.put(attendance.getStudent().getId(), studentList);
						}
						
						attendance.setWeeklyStatus(true);
						session.update(attendance);
						
					}
					
					
					//To calculate student absent days and present days
					List stList = new ArrayList(studentMap.keySet());
					for(int i=0; i<=stList.size()-1;i++){
						int totalDaysAbsent=0;
						int totalDaysPresent=0;
						List list = (List) studentMap.get(stList.get(i));
						for(int j=0;j<=list.size()-1;j++){
							Map map = (Map) list.get(j);
							if((boolean) map.get("status")){
								totalDaysPresent++;
							}else{
								totalDaysAbsent++;
							}
						}
						Map absentiesMap = new LinkedHashMap();
						absentiesMap.put("totalDaysPresent", totalDaysPresent);
						absentiesMap.put("totalDaysAbsent", totalDaysAbsent);
						List studentAttendanceList = new ArrayList();
						studentAttendanceList.add(absentiesMap);
						
						studentAttendanceMap.put(stList.get(i), studentAttendanceList);
					}
					
					//To insert weekly attendance
					
					List stIdList = new ArrayList(studentAttendanceMap.keySet());
					for(int i=0;i<=stIdList.size()-1;i++){
						List list = (List) studentAttendanceMap.get(stIdList.get(i));
						for(int j=0;j<=list.size()-1;j++){
							Map map = (Map) list.get(j);
							if(i==stIdList.size()-1){
							sqlQuery = sqlQuery + "("+stIdList.get(i)+","+null+","+0+","+null+","+map.get("totalDaysPresent")+","+map.get("totalDaysAbsent")+","+attendanceMap.keySet().size()+");";
							}else{
								sqlQuery = sqlQuery + "("+stIdList.get(i)+","+null+","+0+","+null+","+map.get("totalDaysPresent")+","+map.get("totalDaysAbsent")+","+attendanceMap.keySet().size()+"),";
							}
						}
					}
					/*System.out.println("date map "+attendanceMap);
					System.out.println("Student map "+studentMap);
					System.out.println("absenties map "+studentAttendanceMap);*/
					query = session.createSQLQuery(sqlQuery);
					 int result = query.executeUpdate();
				}
				
			}
			
			 tx.commit();
			 session.close();
		}
				
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	
}
