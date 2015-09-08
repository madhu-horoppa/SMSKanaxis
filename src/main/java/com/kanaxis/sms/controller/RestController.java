package com.kanaxis.sms.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Vector;

import javax.ws.rs.FormParam;
import javax.ws.rs.QueryParam;

import org.apache.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.kanaxis.sms.model.Admission;
import com.kanaxis.sms.model.Employee;
import com.kanaxis.sms.model.Status;
import com.kanaxis.sms.services.AdmissionService;
import com.kanaxis.sms.services.DataServices;
import com.kanaxis.sms.services.LoginService;
import com.kanaxis.sms.services.NotificationService;
import com.kanaxis.sms.services.StudentService;
import com.kanaxis.sms.services.TeacherService;
import com.kanaxis.sms.services.TimeTableService;
import com.kanaxis.sms.util.ResultData;
import com.kanaxis.sms.util.UserDetails;

@Controller
@RequestMapping("/sms")
public class RestController {

	@Autowired
	DataServices dataServices;
	@Autowired
	LoginService loginService;
	@Autowired
	AdmissionService admissionService;
	@Autowired
	TimeTableService timeTableService;
	@Autowired
	TeacherService teacherService;
	@Autowired
	NotificationService notificationService;
	@Autowired
	StudentService studentService;
	
	

	static final Logger logger = Logger.getLogger(RestController.class);

	@RequestMapping(value = "/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	Status addEmployee(@RequestBody Employee employee) {
		try {
			dataServices.addEntity(employee);
			return new Status(1, "Employee added Successfully !");
		} catch (Exception e) {
			// e.printStackTrace();
			return new Status(0, e.toString());
		}

	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public @ResponseBody
	Employee getEmployee(@PathVariable("id") long id) {
		Employee employee = null;
		try {
			employee = dataServices.getEntityById(id);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return employee;
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public @ResponseBody
	List<Employee> getEmployee() {

		List<Employee> employeeList = null;
		try {
			employeeList = dataServices.getEntityList();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return employeeList;
	}

	@RequestMapping(value = "delete/{id}", method = RequestMethod.GET)
	public @ResponseBody
	Status deleteEmployee(@PathVariable("id") long id) {

		try {
			dataServices.deleteEntity(id);
			return new Status(1, "Employee deleted Successfully !");
		} catch (Exception e) {
			return new Status(0, e.toString());
		}

	}
	
	/**
	 * For login
	 * @param userName
	 * @param password
	 * @return
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public @ResponseBody
	UserDetails login(@FormParam("userName") String userName,@FormParam("password") String password) {
		UserDetails userDetails = new UserDetails();
		ResultData resultData = new ResultData();
		
		try {
			resultData = checkValidationForLogin(userName, password);
			if(resultData.status){
			userDetails = loginService.login(userName,password);
		}else{
			userDetails.setMessage(resultData.message);
			userDetails.setStatus(resultData.status);
		}
			
		} catch (Exception e) {
			userDetails.setMessage("Some thing went wrong please contact your admin");
        	userDetails.setStatus(false);
		}
		
		return userDetails;

	}
	
	/**
	 * Check validations for login
	 * @param userName
	 * @param password
	 * @return
	 */
	public ResultData checkValidationForLogin(String userName, String password){
		ResultData resultData = new ResultData();
		resultData.status=true;
		if(userName.isEmpty() && userName == ""){
			resultData.status=false;
			resultData.message="Username is mandatory";
			return resultData;
			
		}
		if(password.isEmpty() && password == ""){
			resultData.status=false;
			resultData.message="Password is mandatory";
			return resultData;
		}
		
		return resultData;
		
		
	}
	
	/**
	 * To get list of admissions
	 * @return
	 */
	@RequestMapping(value = "/getAdmissionsList", method = RequestMethod.GET)
	public @ResponseBody
	Map getAdmissionsList() {
		Map mapData = new LinkedHashMap();
        ResultData resultData = new ResultData();
		List<Admission> admissionsList = null;
		try {
			resultData = admissionService.getAdmissionsList();
			mapData.put("admissionsList", resultData.listData);
			mapData.put("message", resultData.message);
			mapData.put("status", resultData.status);

		} catch (Exception e) {
			mapData.put("admissionsList", null);
			mapData.put("message", "Some thing went wrong please contact your system admin");
			mapData.put("status", false);
		}

		return mapData;
	}
	
	/**
	 * To get admission details by admissionId
	 * @return
	 */
	@RequestMapping(value = "getAdmissionDetails/{admissionId}", method = RequestMethod.GET)
	public @ResponseBody
	Map getAdmissionDetails(@PathVariable("admissionId") int admissionId) {
		Map mapData = new LinkedHashMap();
        ResultData resultData = new ResultData();
		List<Admission> admissionsList = null;
		try {
			resultData = admissionService.getAdmissionDetails(admissionId);
			mapData.put("admissionDetails", resultData.object);
			mapData.put("message", resultData.message);
			mapData.put("status", resultData.status);

		} catch (Exception e) {
			mapData.put("admissionDetails", null);
			mapData.put("message", "Some thing went wrong please contact your system admin");
			mapData.put("status", false);
		}

		return mapData;
	}
	
	/**
	 * To get admission details by admissionId
	 * @return
	 */
	@RequestMapping(value = "/getTimeTable", method = RequestMethod.GET)
	public @ResponseBody
	Map getTimeTable(@QueryParam("classId") int classId, @QueryParam("sectionId") int sectionId) {
		Map mapData = new LinkedHashMap();
        ResultData resultData = new ResultData();
        try {
        	resultData = timeTableService.getTimeTable(classId, sectionId); 
        	mapData.put("timeTable", resultData.listData);
			mapData.put("message", resultData.message);
			mapData.put("status", resultData.status);

		} catch (Exception e) {
			mapData.put("timeTable", null);
			mapData.put("message", "Some thing went wrong please contact your system admin");
			mapData.put("status", false);
		}		       

		return mapData;
	}
	
	/**
	 * To get list of teachers
	 * @return
	 */
	@RequestMapping(value = "/getTeachersList", method = RequestMethod.GET)
	public @ResponseBody
	Map getTeachersList() {
		Map mapData = new HashMap();
        ResultData resultData = new ResultData();
		try {
			resultData = teacherService.getTeachersList();
			mapData.put("teachersList", resultData.listData);
			mapData.put("message", resultData.message);
			mapData.put("status", resultData.status);

		} catch (Exception e) {
			mapData.put("teachersList", null);
			mapData.put("message", "Some thing went wrong please contact your system admin");
			mapData.put("status", false);
		}

		return mapData;
	}
	
	/**
	 * To get teacher details
	 * @return
	 */
	@RequestMapping(value = "/getTeacherDetails/{teacherId}", method = RequestMethod.GET)
	public @ResponseBody
	Map getTeacherDetails(@PathVariable("teacherId") int teacherId) {
		Map mapData = new HashMap();
        ResultData resultData = new ResultData();
		try {
			resultData = teacherService.getTeacherDetails(teacherId);
			mapData.put("teacherDetails", resultData.object);
			mapData.put("message", resultData.message);
			mapData.put("status", resultData.status);

		} catch (Exception e) {
			mapData.put("teacherDetails", null);
			mapData.put("message", "Some thing went wrong please contact your system admin");
			mapData.put("status", false);
		}

		return mapData;
	}
	
	/**
	 * To add admin notifications
	 * @param userName
	 * @param password
	 * @return
	 */
	@RequestMapping(value = "/addNotifications", method = RequestMethod.POST)
	public @ResponseBody
	Map addNotifications(@FormParam("fromId") String fromId,@FormParam("messageTypeId") String messageTypeId,
			                      @FormParam("message") String message,@FormParam("toId") String toId, 
			                      @FormParam("classId") String classId, @FormParam("sectionId") String sectionId) {
		ResultData resultData = new ResultData();
		Map mapData = new HashMap();
		
		try {
			 resultData = notificationService.addNotifications(fromId,messageTypeId,message,toId,classId,sectionId);
			 mapData.put("status", resultData.status);
			 mapData.put("message",resultData.message);
			 
		
			
		} catch (Exception e) {
			
			mapData.put("message", "Some thing went wrong please contact your system admin");
			mapData.put("status", false);
		}
		
		return mapData;
		

	}
	
	/**
	 * To get admin notificationsList
	 * @param userName
	 * @param password
	 * @return
	 */
	@RequestMapping(value = "/getAdminNotifications", method = RequestMethod.GET)
	public @ResponseBody
	Map getAdminNotifications() {
		ResultData resultData = new ResultData();
		Map mapData = new HashMap();
		
		try {
			 resultData = notificationService.getNotifications();
			 mapData.put("notificationDetails", resultData.listData);
			 mapData.put("status", resultData.status);
			 mapData.put("message",resultData.message);
			 
		
			
		} catch (Exception e) {
			
			mapData.put("message", "Some thing went wrong please contact your system admin");
			mapData.put("status", false);
		}
		
		return mapData;
		

	}
	
	/**
	 * To add Teacher
	 * @param userName
	 * @param password
	 * @return
	 */
	@RequestMapping(value = "/addTeacher", method = RequestMethod.POST)
	public @ResponseBody
	Map addTeacher(@FormParam("file") MultipartFile file,@FormParam("teacherName") String teacherName,
						@FormParam("qualification") String qualification,
			            @FormParam("email") String email,@FormParam("exp") String exp, 
			            @FormParam("phone1") String phone1,
			            @FormParam("phone2") String phone2,@FormParam("address") String address,
			            @FormParam("joiningDate") String joiningDate, @FormParam("gender") String gender,
			            @FormParam("jobTitle") String jobTitle ) {
		
		ResultData resultData = new ResultData();
		Map mapData = new HashMap();
		Properties prop = new Properties();
    	InputStream input = null;
		
		try {
			
			resultData = checkValidationFromReacher(teacherName, qualification, email, exp, file, phone1, address, joiningDate, gender, jobTitle);		
			if(resultData.status){
	            try {
	            	
	            	input = RestController.class.getClassLoader().getResourceAsStream("/Details.properties");
	        		prop.load(input);
	        		String path = prop.getProperty("path");
	        		String fullPath = path+"/"+file.getOriginalFilename();
	                byte[] bytes = file.getBytes();
	                BufferedOutputStream stream =
	                        new BufferedOutputStream(new FileOutputStream(new File(fullPath)));
	                stream.write(bytes);
	                stream.close();
	                resultData = teacherService.addTeachers(teacherName,qualification,email,exp,fullPath,phone1,phone2,address,joiningDate,
                            gender,jobTitle);
	                mapData.put("status", resultData.status);
	                mapData.put("message", resultData.message);
	            } catch (Exception e) {
	            	mapData.put("status", false);
	   			 mapData.put("message","Some thing went wrong please contact your admin");
	            }
			}else{
				mapData.put("status", resultData.status);
	   			 mapData.put("message",resultData.message);
			}
			
		
			
		} catch (Exception e) {
			
			mapData.put("message", "Some thing went wrong please contact your system admin");
			mapData.put("status", false);
		}
		
		return mapData;
		

	}
	
	/**
	 * Check validation for techer
	 * @return
	 */
	public ResultData checkValidationFromReacher(String teacherName, String qualification,
			String email, String exp, MultipartFile file, String phone1,String address, String joiningDate, String gender,
			String jobTitle){
		ResultData resultData = new ResultData();
		resultData.status = true;
		if(teacherName.isEmpty()){
			resultData.status = false;
			resultData.message = "teacher name is mandatory ";
			return resultData;
		}
		
		if(qualification.isEmpty()){
			resultData.status = false;
			resultData.message = "Qualification is mandatory ";
			return resultData;
		}
		
		if(email.isEmpty()){
			resultData.status = false;
			resultData.message = "Email is mandatory ";
			return resultData;
		}
		
		if(exp.isEmpty()){
			resultData.status = false;
			resultData.message = "Experience is mandatory ";
			return resultData;
		}
		
		if(file.isEmpty()){
			resultData.status = false;
			resultData.message = "Image is mandatory ";
			return resultData;
		}
		
		if(phone1.isEmpty()){
			resultData.status = false;
			resultData.message = "Image is mandatory ";
			return resultData;
		}
		
		if(address.isEmpty()){
			resultData.status = false;
			resultData.message = "address is mandatory ";
			return resultData;
		}
		
		if(joiningDate.isEmpty()){
			resultData.status = false;
			resultData.message = "Joining date is mandatory ";
			return resultData;
		}
		
		if(gender.isEmpty()){
			resultData.status = false;
			resultData.message = "Gender is mandatory ";
			return resultData;
		}
		
		if(jobTitle.isEmpty()){
			resultData.status = false;
			resultData.message = "Job title is mandatory ";
			return resultData;
		}
				return resultData;
		
	}
	
	/**
	 * To update Teacher
	 * @param userName
	 * @param password
	 * @return
	 */
	@RequestMapping(value = "/updateTeacher", method = RequestMethod.POST)
	public @ResponseBody
	Map updateTeacher( @FormParam("teacherId") String teacherId,
			            @FormParam("file") MultipartFile file,@FormParam("teacherName") String teacherName,
						@FormParam("qualification") String qualification,
			            @FormParam("email") String email,@FormParam("exp") String exp, 
			            @FormParam("phone1") String phone1,
			            @FormParam("phone2") String phone2,@FormParam("address") String address,
			            @FormParam("joiningDate") String joiningDate, @FormParam("gender") String gender,
			            @FormParam("jobTitle") String jobTitle ) {
		
		ResultData resultData = new ResultData();
		Map mapData = new HashMap();
		Properties prop = new Properties();
    	InputStream input = null;
    	String fullPath = "";
		
		try {
			 try {
			if(!file.isEmpty()){	           
	            	
	            	input = RestController.class.getClassLoader().getResourceAsStream("/Details.properties");
	        		prop.load(input);
	        		String path = prop.getProperty("path");
	        		 fullPath = path+"/"+file.getOriginalFilename();
	                byte[] bytes = file.getBytes();
	                BufferedOutputStream stream =
	                        new BufferedOutputStream(new FileOutputStream(new File(fullPath)));
	                stream.write(bytes);
	                stream.close();
	            }
	                resultData = teacherService.updateTeachers(teacherId, teacherName,qualification,email,exp,fullPath,phone1,phone2,address,joiningDate,
                            gender,jobTitle);
	                mapData.put("status", resultData.status);
	                mapData.put("message", resultData.message);
	            } catch (Exception e) {
	            	mapData.put("status", false);
	   			 mapData.put("message","Some thing went wrong please contact your admin");
	            }
			
			
		
			
		} catch (Exception e) {
			
			mapData.put("message", "Some thing went wrong please contact your system admin");
			mapData.put("status", false);
		}
		
		return mapData;
		

	}
	
	/**
	 * To add Students
	 * @param userName
	 * @param password
	 * @return
	 */
	@RequestMapping(value = "/uploadStudents", method = RequestMethod.POST)
	public @ResponseBody
	Map uploadStudents(@FormParam("file") MultipartFile file) {
		
		ResultData resultData = new ResultData();
		Map mapData = new HashMap();
		Properties prop = new Properties();
    	InputStream input = null;
    	Vector cellVectorHolder = new Vector();	
		
			
	            try {
	            	
	            	InputStream myInput = file.getInputStream();
	                XSSFWorkbook myWorkBook = new XSSFWorkbook(myInput);
	                XSSFSheet mySheet = myWorkBook.getSheetAt(0);
	                Iterator rowIter = mySheet.rowIterator();
	                while(rowIter.hasNext()){
	                    XSSFRow myRow = (XSSFRow) rowIter.next();
	                    Iterator cellIter = myRow.cellIterator();
	                    List list = new ArrayList();
	                    while(cellIter.hasNext()){
	                        XSSFCell myCell = (XSSFCell) cellIter.next();
	                        list.add(myCell);
	                    }
	                    cellVectorHolder.addElement(list);
	                }
	            	
	               resultData = studentService.uploadStudents(cellVectorHolder);
	                mapData.put("status", resultData.status);
	                mapData.put("message", resultData.message);
	            } catch (Exception e) {
	            	mapData.put("status", false);
	   			 mapData.put("message","Some thing went wrong please contact your admin");
	            }
					
		
		return mapData;
		

	}
	
	/**
	 * To get all Students
	 * @param userName
	 * @param password
	 * @return
	 */
	@RequestMapping(value = "/getAllStudents", method = RequestMethod.GET)
	public @ResponseBody
	Map getAllStudents(@QueryParam("class_id") String class_id, @QueryParam("section_id") String section_id) {
		
		ResultData resultData = new ResultData();
		Map mapData = new HashMap();		
			
	            try { 	            	
	            	
	               resultData = studentService.getAllStudents(class_id, section_id);
	               mapData.put("studentsList", resultData.listData);
	                mapData.put("status", resultData.status);
	                mapData.put("message", resultData.message);
	            } catch (Exception e) {
	            	mapData.put("studentsList", null);
	            	mapData.put("status", false);
	   			 mapData.put("message","Some thing went wrong please contact your admin");
	            }
					
		
		return mapData;
		

	}
	
	/**
	 * To get Student details
	 * @param userName
	 * @param password
	 * @return
	 */
	@RequestMapping(value = "/getStudentDetails", method = RequestMethod.GET)
	public @ResponseBody
	Map getStudentDetails(@QueryParam("student_id") String student_id) {
		
		ResultData resultData = new ResultData();
		Map mapData = new HashMap();		
			
	            try { 	            	
	            	
	                resultData = studentService.getStudentDetails(student_id);
	                mapData.put("studentDetails", resultData.object);
	                mapData.put("status", resultData.status);
	                mapData.put("message", resultData.message);
	            } catch (Exception e) {
	            	mapData.put("studentDetails", resultData.object);
	            	mapData.put("status", false);
	   			    mapData.put("message","Some thing went wrong please contact your admin");
	            }
					
		
		return mapData;
		

	}
	
	
}
