package com.kanaxis.sms.dao.impl;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;

import com.kanaxis.sms.dao.StudentDao;
import com.kanaxis.sms.model.Classes;
import com.kanaxis.sms.model.Employee;
import com.kanaxis.sms.model.Section;
import com.kanaxis.sms.model.Student;
import com.kanaxis.sms.util.ResultData;
import com.kanaxis.sms.util.Students;

public class StudentDaoImpl implements StudentDao{
	
	@Autowired
	SessionFactory sessionFactory;

	Session session = null;
	Transaction tx = null;

	@Override
	public ResultData uploadStudents(Vector dataHolder) {
		
		session = sessionFactory.openSession();
		tx = session.beginTransaction();
		Student student = null;
		ResultData resultData= new ResultData();
		String firstName="";
		String lastName="";
		String rollNumber="";
		String dateOfBirth="";
		String gender="";
		String bloodGroup="";
		String relegion="";
		String castCategory="";
		String subCast="";
		String physicalDisability="";
		String motherTongue="";
		String localAddress="";
		String city="";
		String state="";
		String pincode="";
		String permAddress="";
		String permCity="";
		String permState="";
		String permPincode="";
		String motherName="";
		String motherOccupation="";
		String motherEducation="";
		String fatherName="";
		String fatherOccupation="";
		String fatherEducation="";
		String totalIncome="";
		String parentAsGuardian="";
		String primaryContactNumber="";
		String secondaryContactNumber="";
		String email = "";
		String joinedDate = "";
		String classes="";
		String section="";
		Query query = null;
		Calendar cal = null;
		String secondary = "";
		String secondary1="";
		
		String sqlQuery = "INSERT INTO student(first_name,last_name,roll_number,date_of_birth,gender,blood_group,relegion,cast_category,"
				+ "subcast,mother_tongue,local_address,city,state,pincode,perm_address,perm_city,perm_state,"
				+ "perm_pincode,mother_full_name,mother_occupation,mother_education,father_full_name,father_occupation,"
				+ "total_income,primary_contact_number,secondary_contact_number,email,joined_date,created_date,"
				+ "classes_id,section_id,father_education,physical_disability,parent_as_guardian) VALUES ";

		for(Iterator iterator = dataHolder.iterator();iterator.hasNext();) {
            List list = (List) iterator.next();
            firstName = list.get(0).toString();
            lastName = list.get(1).toString();
            rollNumber = list.get(2).toString();
            String rollNumber1[] = rollNumber.split("\\.");
            dateOfBirth = list.get(3).toString();            
            gender = list.get(4).toString();
            bloodGroup = list.get(5).toString();
            relegion = list.get(6).toString();
            castCategory = list.get(7).toString();
            subCast = list.get(8).toString();
            physicalDisability = list.get(9).toString();
            motherTongue = list.get(10).toString();
            localAddress = list.get(11).toString();
            city = list.get(12).toString();
            state = list.get(13).toString();
            pincode = list.get(14).toString();
            String pincode1[] = pincode.split("\\.");
            permAddress = list.get(15).toString();
            permCity = list.get(16).toString();
            permState = list.get(17).toString();
            permPincode = list.get(18).toString();
            String permPincode1[] = permPincode.split("\\.");
            motherName = list.get(19).toString();
            motherOccupation = list.get(20).toString();
            motherEducation = list.get(21).toString();
            fatherName = list.get(22).toString();
            fatherOccupation = list.get(23).toString();
            fatherEducation = list.get(24).toString();
            totalIncome = list.get(25).toString();
            String totalIncome1[] = totalIncome.split("\\.");
            parentAsGuardian = list.get(26).toString();
            primaryContactNumber = list.get(27).toString();
            String primary = primaryContactNumber.replaceAll("[.E]*","");
            String primary1 = primary.substring(0,primary.length()-1);
            secondaryContactNumber = list.get(28).toString();
             secondary = secondaryContactNumber.replaceAll("[.E]*","");
             secondary1 = secondary.substring(0,secondary.length()-1);
            email = list.get(29).toString();
            joinedDate = list.get(30).toString();
            classes = list.get(31).toString();
            section = list.get(32).toString();            
            

            try {            	
            	student = new Student();
            	//student = (Student) session.get(Student.class, new Integer(rollNumber1[0]));
            	query = session.createSQLQuery("select * from student where roll_number=:rollNumber and classes_id in (select id from classes where class_name=:className) and"
            			+ " section_id in (select id from section where section_name=:sectionName and classes_id=classes_id)");
            	query.setParameter("className", classes);
            	query.setParameter("sectionName", section);
            	query.setParameter("rollNumber", rollNumber1[0]);
            	Student stList = (Student) query.uniqueResult();
            	
            	if(stList == null){
            	/*student.setFirstName(firstName);
            	student.setLastName(lastName);
            	student.setRollNumber(Integer.parseInt(rollNumber1[0]));
            	DateFormat date = new SimpleDateFormat("dd-MM-yyyy");
            	student.setDateOfBirth(date.parse(dateOfBirth));
            	student.setGender(gender);
            	student.setBloodGroup(bloodGroup);
            	student.setRelegion(relegion);
            	student.setCastCategory(castCategory);
            	student.setSubcast(subCast);
            	student.setPhysicalDisability(Boolean.parseBoolean(physicalDisability));
            	student.setMotherTongue(motherTongue);
            	student.setLocalAddress(localAddress);
            	student.setCity(city);
            	student.setState(state);
            	student.setPincode(Double.parseDouble(pincode1[0]));
            	student.setPermAddress(permAddress);
            	student.setPermCity(permCity);
            	student.setPermState(permState);
            	student.setPermPincode(Double.parseDouble(permPincode1[0]));
            	student.setMotherFullName(motherName);
            	student.setMotherOccupation(motherOccupation);
            	student.setMotherEducation(motherEducation);
            	student.setFatherFullName(fatherName);
            	student.setFatherOccupation(fatherOccupation);
            	student.setFatherEducation(fatherEducation);
            	student.setTotalIncome(Double.parseDouble(totalIncome1[0]));
            	student.setParentAsGuardian(Boolean.parseBoolean(parentAsGuardian));
            	student.setPrimaryContactNumber(primary1);
            	
            	student.setSecondaryContactNumber(secondary1);
            	
            	student.setEmail(email);
            	student.setJoinedDate(date.parse(joinedDate));*/
            	
            	//cal = Calendar.getInstance();
            	//student.setCreatedDate(cal.getTime());
            	
            	 query = session.createQuery("from Classes where className=:class_name");
            	query.setParameter("class_name", classes);
            	Classes classes1 = (Classes) query.uniqueResult();
            	
            	query = session.createQuery("from Section where sectionName=:section_name and classes=:class_id");
            	query.setParameter("section_name", section);
            	query.setParameter("class_id", classes1);
            	Section section1 = (Section) query.uniqueResult();
            	
            	//student.setClasses(classes1);
            	//student.setSection(section1);
            	
            	Date date1 = new SimpleDateFormat("dd-MM-yyyy").parse(dateOfBirth);
				java.sql.Date sqlDate = new java.sql.Date(date1.getTime());
				
				Date date2 = new SimpleDateFormat("dd-MM-yyyy").parse(joinedDate);
				java.sql.Date sqlDate1 = new java.sql.Date(date2.getTime());
				
				java.util.Date today = new java.util.Date();
				java.sql.Timestamp sqlTimestamp = new Timestamp(
						today.getTime());
            	if(iterator.hasNext()){
            	sqlQuery = sqlQuery+"(" +"'"+firstName+"'"+","+"'"+lastName+"'"+","+rollNumber1[0]+","+"'"+sqlDate+"'"+","+"'"+gender+"'"+","+
            	                      "'"+bloodGroup+"'"+","+"'"+relegion+"'"+","+"'"+castCategory+"'"+","+"'"+subCast+"'"+","+
            			              "'"+motherTongue+"'"+","+"'"+localAddress+"'"+","+"'"+city+"'"+","+"'"+state+"'"+","+
            	                      pincode1[0]+","+"'"+permAddress+"'"+","+"'"+permCity+"'"+","+"'"+permState+"'"+","+permPincode1[0]+","+"'"+motherName+"'"+
            			              ","+"'"+motherOccupation+"'"+","+"'"+motherEducation+"'"+","+"'"+fatherName+"'"+","+"'"+fatherOccupation+"'"+","+totalIncome1[0]+","+
            	                      "'"+primary1+"'"+","+"'"+secondary1+"'"+","+"'"+email+"'"+","+"'"+sqlDate1+"'"+","+"'"+sqlTimestamp+"'"+","+
            			              classes1.getId()+","+section1.getId()+","+"'"+fatherEducation+"'"+","+physicalDisability+","+parentAsGuardian+"),";
            	}else{
            		sqlQuery = sqlQuery+"(" +"'"+firstName+"'"+","+"'"+lastName+"'"+","+rollNumber1[0]+","+"'"+sqlDate+"'"+","+"'"+gender+"'"+","+
  	                      "'"+bloodGroup+"'"+","+"'"+relegion+"'"+","+"'"+castCategory+"'"+","+"'"+subCast+"'"+","+
  			              "'"+motherTongue+"'"+","+"'"+localAddress+"'"+","+"'"+city+"'"+","+"'"+state+"'"+","+
  	                      pincode1[0]+","+"'"+permAddress+"'"+","+"'"+permCity+"'"+","+"'"+permState+"'"+","+permPincode1[0]+","+"'"+motherName+"'"+
  			              ","+"'"+motherOccupation+"'"+","+"'"+motherEducation+"'"+","+"'"+fatherName+"'"+","+"'"+fatherOccupation+"'"+","+totalIncome1[0]+","+
  	                      "'"+primary1+"'"+","+"'"+secondary1+"'"+","+"'"+email+"'"+","+"'"+sqlDate1+"'"+","+"'"+sqlTimestamp+"'"+","+
  			              classes1.getId()+","+section1.getId()+","+"'"+fatherEducation+"'"+","+physicalDisability+","+parentAsGuardian+");";
            	}
            	
            	
            	}else{
            		resultData.object = stList;
            		resultData.status = false;
            		resultData.message = "data is duplicated please check";
            		break;
            	}
               
            } catch (Exception e) {
            	resultData.object = null;
            	resultData.status = false;
        		resultData.message = "Some thing went wrong please contact your admin";
            } 
        }
		try{
			
			System.out.println(sqlQuery);
			query = session.createSQLQuery(sqlQuery);
			int result = query.executeUpdate();
			tx.commit();
			session.close();
        	resultData.object = null;
    		resultData.status = true;
    		resultData.message = "Students added successfully";
			
		}catch(Exception e){
			tx.rollback();
			resultData.object = null;
        	resultData.status = false;
    		resultData.message = "Some thing went wrong please contact your admin";
		}
		
		return resultData;
	}

	@Override
	public ResultData getAllStudents(String class_id, String section_id) {
		session = sessionFactory.openSession();
		tx = session.beginTransaction();
		ResultData resultData = new ResultData();
		Query query = null;
		List listStudents = new ArrayList();
		try{
			
			Classes classes = (Classes) session.get(Classes.class, new Integer(Integer.parseInt(class_id)));
			
			query = session.createQuery("from Section where id=:section_id and classes=:class_id");
			query.setParameter("section_id", Integer.parseInt(section_id));
			query.setParameter("class_id", classes);
			Section section = (Section) query.uniqueResult();
			
			query = session.createQuery("from Student where classes=:class_id and section=:section_id");
			query.setParameter("class_id", classes);
			query.setParameter("section_id", section);
			List<Student> studentList = query.list();
			
			if(studentList!=null && !studentList.isEmpty()){
				for(Student student:studentList){
				Students students = new Students();
				students.setId(student.getId());
				students.setFirstName(student.getFirstName());
				students.setLastName(student.getLastName());
				students.setRollNumber(student.getRollNumber());
				DateFormat date = new SimpleDateFormat("dd-MM-yyyy");
				students.setDateOfBirth(date.format(student.getDateOfBirth()));
				students.setGender(student.getGender());
				students.setBloodGroup(student.getBloodGroup());
				students.setRelegion(student.getRelegion());
				students.setCastCategory(student.getCastCategory());
				students.setSubcast(student.getSubcast());
				students.setPhysicalDisability(student.getPhysicalDisability());
				students.setMotherTongue(student.getMotherTongue());
				students.setLocalAddress(student.getLocalAddress());
				students.setCity(student.getCity());
				students.setState(student.getState());
				students.setPincode(student.getPincode());
				students.setPermAddress(student.getPermAddress());
				students.setPermCity(student.getPermCity());
				students.setPermState(student.getPermState());
				students.setPermPincode(student.getPermPincode());
				students.setMotherFullName(student.getMotherFullName());
				students.setMotherOccupation(student.getMotherOccupation());
				students.setMotherEducation(student.getMotherEducation());
				students.setFatherFullName(student.getFatherFullName());
				students.setFatherOccupation(student.getFatherOccupation());
				students.setFatherEducation(student.getFatherEducation());
				students.setTotalIncome(student.getTotalIncome());
				students.setParentAsGuardian(student.getParentAsGuardian());
				students.setPrimaryContactNumber(student.getPrimaryContactNumber());
				students.setSecondaryContactNumber(student.getSecondaryContactNumber());
				students.setEmail(student.getEmail());
				students.setJoinedDate(date.format(student.getJoinedDate()));
				students.setClasses(student.getClasses().getClassName());
				students.setSection(student.getSection().getSectionName());
				DateFormat date1 = new SimpleDateFormat("dd-MM-yyyy HH:MM:SS");
				students.setCreatedDate(date1.format(student.getCreatedDate()));
				listStudents.add(students);
				
				}
				resultData.listData = listStudents;
				resultData.status = true;
				resultData.message = "Student data found";
			}else{
				resultData.listData = null;
				resultData.status = false;
				resultData.message = "Student data not found";
			}
			
		}catch(Exception e){
			resultData.listData = null;
			resultData.status = false;
			resultData.message = "Some thing went wrong please contact your admin";
		}
		return resultData;
	}

	@Override
	public ResultData getStudentDetails(String student_id) {
		session = sessionFactory.openSession();
		tx = session.beginTransaction();
		ResultData resultData = new ResultData();
		Students students = new Students();
		try{
			
			Student student = (Student) session.get(Student.class, new Integer(Integer.parseInt(student_id)));
			if(student!=null){
			students.setId(student.getId());
			students.setFirstName(student.getFirstName());
			students.setLastName(student.getLastName());
			students.setRollNumber(student.getRollNumber());
			DateFormat date = new SimpleDateFormat("dd-MM-yyyy");
			students.setDateOfBirth(date.format(student.getDateOfBirth()));
			students.setGender(student.getGender());
			students.setBloodGroup(student.getBloodGroup());
			students.setRelegion(student.getRelegion());
			students.setCastCategory(student.getCastCategory());
			students.setSubcast(student.getSubcast());
			students.setPhysicalDisability(student.getPhysicalDisability());
			students.setMotherTongue(student.getMotherTongue());
			students.setLocalAddress(student.getLocalAddress());
			students.setCity(student.getCity());
			students.setState(student.getState());
			students.setPincode(student.getPincode());
			students.setPermAddress(student.getPermAddress());
			students.setPermCity(student.getPermCity());
			students.setPermState(student.getPermState());
			students.setPermPincode(student.getPermPincode());
			students.setMotherFullName(student.getMotherFullName());
			students.setMotherOccupation(student.getMotherOccupation());
			students.setMotherEducation(student.getMotherEducation());
			students.setFatherFullName(student.getFatherFullName());
			students.setFatherOccupation(student.getFatherOccupation());
			students.setFatherEducation(student.getFatherEducation());
			students.setTotalIncome(student.getTotalIncome());
			students.setParentAsGuardian(student.getParentAsGuardian());
			students.setPrimaryContactNumber(student.getPrimaryContactNumber());
			students.setSecondaryContactNumber(student.getSecondaryContactNumber());
			students.setEmail(student.getEmail());
			students.setJoinedDate(date.format(student.getJoinedDate()));
			students.setClasses(student.getClasses().getClassName());
			students.setSection(student.getSection().getSectionName());
			DateFormat date1 = new SimpleDateFormat("dd-MM-yyyy HH:MM:SS");
			students.setCreatedDate(date1.format(student.getCreatedDate()));
			
			resultData.object = students;
			resultData.status = true;
			resultData.message = "Student data found";
			}else{
				resultData.object = null;
				resultData.status = false;
				resultData.message = "Student data not found";
			}
			
		}catch(Exception e){
			resultData.object = null;
			resultData.status = false;
			resultData.message = "Some thing went wrong please contact your admin";
		}
		return resultData;
	}

	

}
