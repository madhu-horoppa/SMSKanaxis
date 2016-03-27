package com.kanaxis.sms.dao.impl;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;

import com.kanaxis.sms.dao.StudentDao;
import com.kanaxis.sms.model.Admission;
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
	public ResultData uploadStudents(Vector<Set> dataHolder) {
		
		session = sessionFactory.openSession();
		tx = session.beginTransaction();
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
            Set set = (Set) iterator.next();
            List list = new ArrayList(set);
            for(int i=0;i<list.size();i++){
            Students students = (Students)list.get(i);
            firstName = students.getFirstName();
            lastName = students.getLastName();
            rollNumber = students.getRollNumber();
            String rollNumber1[] = rollNumber.split("\\.");
            dateOfBirth = students.getDateOfBirth();            
            gender = students.getGender();
            bloodGroup = students.getBloodGroup();
            relegion = students.getRelegion();
            castCategory = students.getCastCategory();
            subCast = students.getSubcast();
            physicalDisability = students.getPhysicalDisability();
            motherTongue = students.getMotherTongue();
            localAddress = students.getLocalAddress();
            city = students.getCity();
            state = students.getState();
            pincode = students.getPincode();
            String pincode1[] = pincode.split("\\.");
            permAddress = students.getPermAddress();
            permCity = students.getPermCity();
            permState = students.getPermState();
            permPincode = students.getPermPincode();
            String permPincode1[] = permPincode.split("\\.");
            motherName = students.getMotherFullName();
            motherOccupation = students.getMotherOccupation();
            motherEducation = students.getMotherEducation();
            fatherName = students.getFatherFullName();
            fatherOccupation = students.getFatherOccupation();
            fatherEducation = students.getFatherEducation();
            totalIncome = students.getTotalIncome();
            String totalIncome1[] = totalIncome.split("\\.");
            parentAsGuardian = students.getParentAsGuardian();
            primaryContactNumber = students.getPrimaryContactNumber();
            String primary = primaryContactNumber.replaceAll("[.E]*","");
            String primary1 = primary.substring(0,primary.length()-1);
            secondaryContactNumber = students.getSecondaryContactNumber();
             secondary = secondaryContactNumber.replaceAll("[.E]*","");
             secondary1 = secondary.substring(0,secondary.length()-1);
            email = students.getEmail();
            joinedDate = students.getJoinedDate();
            classes = students.getClasses();
            section = students.getSection();            
            

            try {            	
            	
            	 query = session.createQuery("from Classes where className=:class_name");
            	query.setParameter("class_name", classes);
            	Classes classes1 = (Classes) query.uniqueResult();
            	
            	query = session.createQuery("from Section where sectionName=:section_name and classes=:class_id");
            	query.setParameter("section_name", section);
            	query.setParameter("class_id", classes1);
            	Section section1 = (Section) query.uniqueResult();
            	
            	
            	Date date1 = new SimpleDateFormat("dd-MM-yyyy").parse(dateOfBirth);
				java.sql.Date sqlDate = new java.sql.Date(date1.getTime());
				
				Date date2 = new SimpleDateFormat("dd-MM-yyyy").parse(joinedDate);
				java.sql.Date sqlDate1 = new java.sql.Date(date2.getTime());
				
				java.util.Date today = new java.util.Date();
				java.sql.Timestamp sqlTimestamp = new Timestamp(
						today.getTime());
            	if(i == list.size()-1){
            		sqlQuery = sqlQuery+"(" +"'"+firstName+"'"+","+"'"+lastName+"'"+","+rollNumber1[0]+","+"'"+sqlDate+"'"+","+"'"+gender+"'"+","+
  	                      "'"+bloodGroup+"'"+","+"'"+relegion+"'"+","+"'"+castCategory+"'"+","+"'"+subCast+"'"+","+
  			              "'"+motherTongue+"'"+","+"'"+localAddress+"'"+","+"'"+city+"'"+","+"'"+state+"'"+","+
  	                      pincode1[0]+","+"'"+permAddress+"'"+","+"'"+permCity+"'"+","+"'"+permState+"'"+","+permPincode1[0]+","+"'"+motherName+"'"+
  			              ","+"'"+motherOccupation+"'"+","+"'"+motherEducation+"'"+","+"'"+fatherName+"'"+","+"'"+fatherOccupation+"'"+","+totalIncome1[0]+","+
  	                      "'"+primary1+"'"+","+"'"+secondary1+"'"+","+"'"+email+"'"+","+"'"+sqlDate1+"'"+","+"'"+sqlTimestamp+"'"+","+
  			              classes1.getId()+","+section1.getId()+","+"'"+fatherEducation+"'"+","+physicalDisability+","+parentAsGuardian+");";
            	}else{
            		sqlQuery = sqlQuery+"(" +"'"+firstName+"'"+","+"'"+lastName+"'"+","+rollNumber1[0]+","+"'"+sqlDate+"'"+","+"'"+gender+"'"+","+
  	                      "'"+bloodGroup+"'"+","+"'"+relegion+"'"+","+"'"+castCategory+"'"+","+"'"+subCast+"'"+","+
  			              "'"+motherTongue+"'"+","+"'"+localAddress+"'"+","+"'"+city+"'"+","+"'"+state+"'"+","+
  	                      pincode1[0]+","+"'"+permAddress+"'"+","+"'"+permCity+"'"+","+"'"+permState+"'"+","+permPincode1[0]+","+"'"+motherName+"'"+
  			              ","+"'"+motherOccupation+"'"+","+"'"+motherEducation+"'"+","+"'"+fatherName+"'"+","+"'"+fatherOccupation+"'"+","+totalIncome1[0]+","+
  	                      "'"+primary1+"'"+","+"'"+secondary1+"'"+","+"'"+email+"'"+","+"'"+sqlDate1+"'"+","+"'"+sqlTimestamp+"'"+","+
  			              classes1.getId()+","+section1.getId()+","+"'"+fatherEducation+"'"+","+physicalDisability+","+parentAsGuardian+"),";
            	}
            	
            	
            	
            	
            	
               
            } catch (Exception e) {
            	resultData.listData = null;
            	resultData.status = false;
        		resultData.message = "Some thing went wrong please contact your admin";
            } 
		}
        }
		try{
			
			System.out.println(sqlQuery);
			query = session.createSQLQuery(sqlQuery);
			int result = query.executeUpdate();
			tx.commit();
			session.close();
        	resultData.listData = null;
    		resultData.status = true;
    		resultData.message = "Students added successfully";
			
		}catch(Exception e){
			tx.rollback();
			resultData.listData = null;
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
				students.setRollNumber(String.valueOf(student.getRollNumber()));
				DateFormat date = new SimpleDateFormat("dd-MM-yyyy");
				students.setDateOfBirth(date.format(student.getDateOfBirth()));
				students.setGender(student.getGender());
				students.setBloodGroup(student.getBloodGroup());
				students.setRelegion(student.getRelegion());
				students.setCastCategory(student.getCastCategory());
				students.setSubcast(student.getSubcast());
				students.setPhysicalDisability(String.valueOf(student.getPhysicalDisability()));
				students.setMotherTongue(student.getMotherTongue());
				students.setLocalAddress(student.getLocalAddress());
				students.setCity(student.getCity());
				students.setState(student.getState());
				students.setPincode(String.valueOf(student.getPincode()));
				students.setPermAddress(student.getPermAddress());
				students.setPermCity(student.getPermCity());
				students.setPermState(student.getPermState());
				students.setPermPincode(String.valueOf(student.getPermPincode()));
				students.setMotherFullName(student.getMotherFullName());
				students.setMotherOccupation(student.getMotherOccupation());
				students.setMotherEducation(student.getMotherEducation());
				students.setFatherFullName(student.getFatherFullName());
				students.setFatherOccupation(student.getFatherOccupation());
				students.setFatherEducation(student.getFatherEducation());
				students.setTotalIncome(String.valueOf(student.getTotalIncome()));
				students.setParentAsGuardian(String.valueOf(student.getParentAsGuardian()));
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
			students.setRollNumber(String.valueOf(student.getRollNumber()));
			DateFormat date = new SimpleDateFormat("dd-MM-yyyy");
			students.setDateOfBirth(date.format(student.getDateOfBirth()));
			students.setGender(student.getGender());
			students.setBloodGroup(student.getBloodGroup());
			students.setRelegion(student.getRelegion());
			students.setCastCategory(student.getCastCategory());
			students.setSubcast(student.getSubcast());
			students.setPhysicalDisability(String.valueOf(student.getPhysicalDisability()));
			students.setMotherTongue(student.getMotherTongue());
			students.setLocalAddress(student.getLocalAddress());
			students.setCity(student.getCity());
			students.setState(student.getState());
			students.setPincode(String.valueOf(student.getPincode()));
			students.setPermAddress(student.getPermAddress());
			students.setPermCity(student.getPermCity());
			students.setPermState(student.getPermState());
			students.setPermPincode(String.valueOf(student.getPermPincode()));
			students.setMotherFullName(student.getMotherFullName());
			students.setMotherOccupation(student.getMotherOccupation());
			students.setMotherEducation(student.getMotherEducation());
			students.setFatherFullName(student.getFatherFullName());
			students.setFatherOccupation(student.getFatherOccupation());
			students.setFatherEducation(student.getFatherEducation());
			students.setTotalIncome(String.valueOf(student.getTotalIncome()));
			students.setParentAsGuardian(String.valueOf(student.getParentAsGuardian()));
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

	@Override
	public ResultData updateStudent(String id, Student student) {
		session = sessionFactory.openSession();
		tx = session.beginTransaction();
		ResultData resultData = new ResultData();
		Student student2 = (Student) session.get(Student.class, Integer.parseInt(id));
		if(student2!=null){
			Query query = session.createQuery("from Student where email=:email");
			query.setParameter("email", student.getEmail());
			Student studentDetails = (Student) query.uniqueResult();
			if(studentDetails!=null && Integer.parseInt(id)==studentDetails.getId() || studentDetails==null){
				student2.setFirstName(student.getFirstName());
				student2.setLastName(student.getLastName());
				student2.setRollNumber(student.getRollNumber());
				student2.setDateOfBirth(student.getDateOfBirth());
				student2.setGender(student.getGender());
				student2.setBloodGroup(student.getBloodGroup());
				student2.setRelegion(student.getRelegion());
				student2.setCastCategory(student.getCastCategory());
				student2.setSubcast(student.getSubcast());
				student2.setMotherTongue(student.getMotherTongue());
				student2.setLocalAddress(student.getLocalAddress());
				student2.setCity(student.getCity());
				student2.setState(student.getState());
				student2.setPincode(student.getPincode());
				student2.setPermAddress(student.getPermAddress());
				student2.setPermCity(student.getPermCity());
				student2.setPermState(student.getPermState());
				student2.setPermPincode(student.getPermPincode());
				student2.setMotherFullName(student.getMotherFullName());
				student2.setMotherOccupation(student.getMotherOccupation());
				student2.setMotherEducation(student.getMotherEducation());
				student2.setFatherFullName(student.getFatherFullName());
				student2.setFatherOccupation(student.getFatherOccupation());
				student2.setFatherEducation(student.getFatherEducation());
				student2.setTotalIncome(student.getTotalIncome());
				student2.setPrimaryContactNumber(student.getPrimaryContactNumber());
				student2.setSecondaryContactNumber(student.getSecondaryContactNumber());
				student2.setEmail(student.getEmail());
				student2.setJoinedDate(student.getJoinedDate());
				student2.setPhysicalDisability(student.getPhysicalDisability());
				student2.setParentAsGuardian(student.getParentAsGuardian());
				session.update(student2);
				tx.commit();
				session.close();
				resultData.status=true;
				resultData.message = "Admission updated successfully";
			}else{
				resultData.status = false;
				resultData.message = "Student already exist with this email";
			}
		}
		return resultData;
	}

	

}
