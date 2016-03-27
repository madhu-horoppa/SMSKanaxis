
package com.kanaxis.sms.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.Vector;

import javax.ws.rs.FormParam;
import javax.ws.rs.QueryParam;

import org.apache.log4j.Logger;
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
import com.kanaxis.sms.model.Student;
import com.kanaxis.sms.services.AdmissionService;
import com.kanaxis.sms.services.AttendanceService;
import com.kanaxis.sms.services.ClassSubjectTeacherMappingService;
import com.kanaxis.sms.services.DataServices;
import com.kanaxis.sms.services.ExamScheduleService;
import com.kanaxis.sms.services.LoginService;
import com.kanaxis.sms.services.MarksService;
import com.kanaxis.sms.services.NotificationService;
import com.kanaxis.sms.services.SectionService;
import com.kanaxis.sms.services.StudentService;
import com.kanaxis.sms.services.SubjectService;
import com.kanaxis.sms.services.TeacherService;
import com.kanaxis.sms.services.TimeTableService;
import com.kanaxis.sms.services.TransactionService;
import com.kanaxis.sms.util.Holidays;
import com.kanaxis.sms.util.Marks;
import com.kanaxis.sms.util.ResultData;
import com.kanaxis.sms.util.StudentMarks;
import com.kanaxis.sms.util.Students;
import com.kanaxis.sms.util.TotalMarks;

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
	
	@Autowired
	ExamScheduleService examScheduleService;
	
	@Autowired
	SectionService sectionService;
	
	@Autowired
	SubjectService subjectService;
	
	@Autowired
	ClassSubjectTeacherMappingService classSubjectTeacherMappingService;
	
	@Autowired
	TransactionService transactionService;
	
	@Autowired
	AttendanceService attendanceService;
	
	@Autowired
	MarksService marksService;
	
	

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
	Map login(@FormParam("userName") String userName,@FormParam("password") String password) {
		ResultData resultData = new ResultData();
		Map mapData = new LinkedHashMap();
		
		try {
			resultData = checkValidationForLogin(userName, password);
			if(resultData.status){
				resultData = loginService.login(userName,password);
				mapData.put("loginDetails", resultData.map);
				mapData.put("status", resultData.status);
				mapData.put("message", resultData.message);
			
		}else{
			mapData.put("status", resultData.status);
			mapData.put("message", resultData.message);
		}
			
		} catch (Exception e) {
			mapData.put("status", false);
			mapData.put("message", "Some thing went wrong please contact your admin");
		}
		
		return mapData;

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
		if(userName.isEmpty()){
			resultData.status=false;
			resultData.message="Username is mandatory";
			return resultData;
			
		}
		if(password.isEmpty()){
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
        	mapData.put("timeTable", resultData.map);
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
	Map addNotifications(@FormParam("userId") String userId,@FormParam("messageTypeId") String messageTypeId,
			                      @FormParam("message") String message,@FormParam("toId") String toId, 
			                      @FormParam("classId") String classId, @FormParam("sectionId") String sectionId) {
		ResultData resultData = new ResultData();
		Map mapData = new HashMap();
		
		try {
			 resultData = notificationService.addNotifications(userId,messageTypeId,message,toId,classId,sectionId);
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
	@RequestMapping(value = "/getSentNotifications", method = RequestMethod.GET)
	public @ResponseBody
	Map getSentNotifications(@QueryParam("userId") String userId) {
		ResultData resultData = new ResultData();
		Map mapData = new HashMap();
		
		try {
			 resultData = notificationService.getSentNotifications(userId);
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
	 * to upload students
	 * @param file
	 * @return
	 */
	
	@RequestMapping(value = "/uploadStudents", method = RequestMethod.POST)
	public @ResponseBody Map uploadStudents(
			@FormParam("file") MultipartFile file) {

		ResultData resultData = new ResultData();
		Map mapData = new HashMap();
		//Vector<List> cellVectorHolder = new Vector<List>();
		List<Students> duplicateList=null;
		Set<Students>  studentsMap= new HashSet();
		duplicateList=new ArrayList<Students>();
		Vector<Set> cellVectorHolder = new Vector<Set>();
		

		try {

			InputStream myInput = file.getInputStream();
			XSSFWorkbook myWorkBook = new XSSFWorkbook(myInput);
			XSSFSheet mySheet = myWorkBook.getSheetAt(0);
			Iterator rowIter = mySheet.rowIterator();
			while (rowIter.hasNext()) {
				XSSFRow myRow = (XSSFRow) rowIter.next();
				//studentsSet = new HashSet();
				Students currentObj=getStudentObj(myRow);
				if(studentsMap.contains(currentObj)){
					duplicateList.add(currentObj);
					mapData.put("duplicateList", duplicateList);
					mapData.put("status", false);
					mapData.put("message", "Rolle number, class and section combination is duplicated plase check the list");
					return mapData;
				}else{
					boolean isEmailUnique = checkEmailUniquness(currentObj.getEmail().toLowerCase(),studentsMap);
					if(!isEmailUnique){
						mapData.put("duplicateList", null);
						mapData.put("status", false);
						mapData.put("message", "email "+currentObj.getEmail().toLowerCase()+" is duplicated");
						return mapData;
						//duplicateList.add(currentObj);
					}
					else{
						studentsMap.add(currentObj);
					}
				}
				
			}
			cellVectorHolder.add(studentsMap);
			resultData = studentService.uploadStudents(cellVectorHolder);
			mapData.put("duplicateList", resultData.listData);
			mapData.put("status", resultData.status);
			mapData.put("message", resultData.message);
		} catch (Exception e) {
			mapData.put("status", false);
			mapData.put("message",
					"Some thing went wrong please contact your admin");
		}

		return mapData;

	}
	
	/**
	 * To check duplicate email
	 * @param lowerCaseEmail
	 * @param studentsSet
	 * @return
	 */
	private boolean checkEmailUniquness(String lowerCaseEmail,Set<Students> studentsSet)
	{
		for(Students obj: studentsSet){
			if(lowerCaseEmail.equalsIgnoreCase(obj.getEmail())){
				return false;
			}
		}
		return true;
	}

	/**
	 * To check duplicate roll number
	 * @param myRow
	 * @return
	 */
	private Students getStudentObj(XSSFRow myRow){
		Students returnObj = new Students();
		returnObj.setFirstName(myRow.getCell(0).getStringCellValue());
		returnObj.setLastName(myRow.getCell(1).getStringCellValue());
		returnObj.setRollNumber(String.valueOf(myRow.getCell(2).getNumericCellValue()));
		returnObj.setDateOfBirth(myRow.getCell(3).getStringCellValue());
		returnObj.setGender(myRow.getCell(4).getStringCellValue());
		returnObj.setBloodGroup(myRow.getCell(5).getStringCellValue());
		returnObj.setRelegion(myRow.getCell(6).getStringCellValue());
		returnObj.setCastCategory(myRow.getCell(7).getStringCellValue());
		returnObj.setSubcast(myRow.getCell(8).getStringCellValue());
		returnObj.setPhysicalDisability(String.valueOf(myRow.getCell(9).getBooleanCellValue()));
		returnObj.setMotherTongue(myRow.getCell(10).getStringCellValue());
		returnObj.setLocalAddress(myRow.getCell(11).getStringCellValue());
		returnObj.setCity(myRow.getCell(12).getStringCellValue());
		returnObj.setState(myRow.getCell(13).getStringCellValue());
		returnObj.setPincode(String.valueOf(myRow.getCell(14).getNumericCellValue()));
		returnObj.setPermAddress(myRow.getCell(15).getStringCellValue());
		returnObj.setPermCity(myRow.getCell(16).getStringCellValue());
		returnObj.setPermState(myRow.getCell(17).getStringCellValue());
		returnObj.setPermPincode(String.valueOf(myRow.getCell(18).getNumericCellValue()));
		returnObj.setMotherFullName(myRow.getCell(19).getStringCellValue());
		returnObj.setMotherOccupation(myRow.getCell(20).getStringCellValue());
		returnObj.setMotherEducation(myRow.getCell(21).getStringCellValue());
		returnObj.setFatherFullName(myRow.getCell(22).getStringCellValue());
		returnObj.setFatherOccupation(myRow.getCell(23).getStringCellValue());
		returnObj.setFatherEducation(myRow.getCell(24).getStringCellValue());
		returnObj.setTotalIncome(String.valueOf(myRow.getCell(25).getNumericCellValue()));
		returnObj.setParentAsGuardian(String.valueOf(myRow.getCell(26).getBooleanCellValue()));
		returnObj.setPrimaryContactNumber(String.valueOf(myRow.getCell(27).getNumericCellValue()));
		returnObj.setSecondaryContactNumber(String.valueOf(myRow.getCell(28).getNumericCellValue()));
		returnObj.setEmail(myRow.getCell(29).getStringCellValue());
		returnObj.setJoinedDate(myRow.getCell(30).getStringCellValue());
		returnObj.setClasses(myRow.getCell(31).getStringCellValue());
		returnObj.setSection(myRow.getCell(32).getStringCellValue());		
		
		
		 return returnObj;		
	}
	
	/*public Vector checkDuplicatesorUloadStudent(Vector<List> vector){
		ResultData resultData = new ResultData();
		List<Person> personsList = new ArrayList<Person>();
		resultData.status = true;
		for(int i=0;i<vector.size();i++){
			Person p = new Person();
			p.setRollNumber(vector.get(i).get(2).toString());
			p.setClasses(vector.get(i).get(31).toString());
			p.setSection(vector.get(i).get(32).toString());
			personsList.add(p);
			
			}
		Set<Person> uniqueElements = new HashSet<Person>(personsList);
		personsList.clear();
		personsList.addAll(uniqueElements);
		vector.add(personsList);
		return vector;
		
		
	}*/
	
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
	            	resultData = validateGetAllStudents(class_id, section_id);
	            	if(resultData.status){
	               resultData = studentService.getAllStudents(class_id, section_id);
	               mapData.put("studentsList", resultData.listData);
	                mapData.put("status", resultData.status);
	                mapData.put("message", resultData.message);
	            	}else{
	            		mapData.put("status", resultData.status);
		                mapData.put("message", resultData.message);
	            	}
	            } catch (Exception e) {
	            	mapData.put("studentsList", null);
	            	mapData.put("status", false);
	   			    mapData.put("message","Some thing went wrong please contact your admin");
	            }
					
		
		return mapData;
		

	}
	
	/**
	 * validating get all students
	 * @param class_id
	 * @param section_id
	 * @return
	 */
	public ResultData validateGetAllStudents(String class_id, String section_id){
		
		ResultData resultData = new ResultData();
		resultData.status = true;
		if(class_id.isEmpty()){
			resultData.status = false;
			resultData.message = "Class id is mandatory";
			return resultData;
		}
		
		if(section_id.isEmpty()){
			resultData.status = false;
			resultData.message = "Section id is mandatory";
			return resultData;
		}
		
		return resultData;
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
	            	resultData = validateGetStudentDetails(student_id);
	            	if(resultData.status){
	                resultData = studentService.getStudentDetails(student_id);
	                mapData.put("studentDetails", resultData.object);
	                mapData.put("status", resultData.status);
	                mapData.put("message", resultData.message);
	            	}else{
	            		mapData.put("status", resultData.status);
		                mapData.put("message", resultData.message);
	            	}
	            } catch (Exception e) {
	            	mapData.put("studentDetails", resultData.object);
	            	mapData.put("status", false);
	   			    mapData.put("message","Some thing went wrong please contact your admin");
	            }
					
		
		return mapData;
		

	}
	
	/**
	 * validating student details
	 * @param student_id
	 * @return
	 */
	public ResultData validateGetStudentDetails(String student_id){
		ResultData resultData = new ResultData();
		resultData.status = true;
		if(student_id.isEmpty()){
			resultData.status = false;
			resultData.message = "Student id is mandatory";
			return resultData;
		}
		return resultData;
		
	}
	
	/**
	 * T add exam schedule
	 * @param examDetails
	 * @return
	 */
	@RequestMapping(value = "/addExamSchedule", method = RequestMethod.POST)
	public @ResponseBody Map addExamSchedule(@RequestBody String examDetails) {

		ResultData resultData = new ResultData();
		Map mapData = new HashMap();

		try {
			resultData = examScheduleService.addExamSchedule(examDetails);
			mapData.put("status", resultData.status);
			mapData.put("message", resultData.message);
		} catch (Exception e) {
			mapData.put("status", false);
			mapData.put("message",
					"Some thing went wrong please contact your admin");
		}

		return mapData;

	}
	
	/**
	 * to get exam schedules
	 * @param class_id
	 * @param examType_id
	 * @return
	 */
	@RequestMapping(value = "/viewAllExamSchedules", method = RequestMethod.GET)
	public @ResponseBody
	Map viewAllExamSchedules(@QueryParam("class_id") String class_id, @QueryParam("examType_id") String examType_id) {
		
		ResultData resultData = new ResultData();
		Map mapData = new HashMap();		
			
	            try { 	            	
	            	resultData = validateViewAllExamSchedules(class_id, examType_id);
	            	if(resultData.status){
	               resultData = examScheduleService.viewAllExamSchedules(class_id, examType_id);
	               mapData.put("examScheduleDetails", resultData.map);
	                mapData.put("status", resultData.status);
	                mapData.put("message", resultData.message);
	            	}else{
	            		 mapData.put("status", resultData.status);
	 	                mapData.put("message", resultData.message);
	            	}
	            } catch (Exception e) {
	            	mapData.put("examScheduleDetails", null);
	            	mapData.put("status", false);
	   			 mapData.put("message","Some thing went wrong please contact your admin");
	            }
					
		
		return mapData;
		

	}
	
	/**
	 * Validating parameters
	 * @param class_id
	 * @param examType_id
	 * @return
	 */
	public ResultData validateViewAllExamSchedules(String class_id, String examType_id){
		ResultData resultData = new ResultData();
		resultData.status = true;
		
		if(class_id.isEmpty()){
			resultData.status = false;
			resultData.message = "ClassId is mandatory";
			return resultData;
		}
		
		if(examType_id.isEmpty()){
			resultData.status = false;
			resultData.message = "Exam id is manadatory";
		}
		
		return resultData;
		
		
	}
	
	/**
	 * to add section
	 * @param class_id
	 * @param sectionName
	 * @return
	 */
	@RequestMapping(value = "/addSection", method = RequestMethod.POST)
	public @ResponseBody Map addSection(@RequestBody String sectionJson) {

		ResultData resultData = new ResultData();
		Map mapData = new HashMap();

		try {
			//resultData = checkValidationForAddSection(class_id, sectionName);
			resultData = sectionService.addSection(sectionJson);
			mapData.put("status", resultData.status);
			mapData.put("message", resultData.message);
			
		} catch (Exception e) {
			mapData.put("status", false);
			mapData.put("message",
					"Some thing went wrong please contact your admin");
		}

		return mapData;

	}
	
	/**
	 * check validation for add sectin
	 * @param classid
	 * @param sectionName
	 * @return
	 */
	/*public ResultData checkValidationForAddSection(String classid, String sectionName){
		ResultData resultData = new ResultData();
		resultData.status = true;
		
		if(classid.isEmpty()){
			resultData.status = false;
			resultData.message = "class is mandatory";
			return resultData;
		}
		if(sectionName.isEmpty()){
			resultData.status = false;
			resultData.message = "sectin name is mandatry";
			return resultData;
		}
		return resultData;
	}*/
	
	/**
	 * to get all sections
	 * @return
	 */
	@RequestMapping(value = "/viewAllSections", method = RequestMethod.GET)
	public @ResponseBody
	Map viewAllSections() {
		
		ResultData resultData = new ResultData();
		Map mapData = new LinkedHashMap();		
			
	            try { 	            	
	               resultData = sectionService.viewAllSections();
	               mapData.put("sectionDetails", resultData.map);
	                mapData.put("status", resultData.status);
	                mapData.put("message", resultData.message);
	            	
	            } catch (Exception e) {
	            	mapData.put("sectionDetails", null);
	            	mapData.put("status", false);
	   			 mapData.put("message","Some thing went wrong please contact your admin");
	            }
					
		
		return mapData;
		

	}
	
	/**
	 * to get section by class
	 * @return
	 */
	@RequestMapping(value = "/getSectionByClass", method = RequestMethod.GET)
	public @ResponseBody
	Map getSectionByClass(@QueryParam("class_id") String class_id) {
		
		ResultData resultData = new ResultData();
		Map mapData = new LinkedHashMap();		
			
	            try { 	            	
	               resultData = sectionService.getSectionByClass(class_id);
	               mapData.put("sectionDetails", resultData.map);
	                mapData.put("status", resultData.status);
	                mapData.put("message", resultData.message);
	            	
	            } catch (Exception e) {
	            	mapData.put("sectionDetails", null);
	            	mapData.put("status", false);
	   			 mapData.put("message","Some thing went wrong please contact your admin");
	            }
					
		
		return mapData;
		

	}
	
	
	
	/**
	 * To add subjects
	 * @param subjectsDetails
	 * @return
	 */
	@RequestMapping(value = "/addSubjects", method = RequestMethod.POST)
	public @ResponseBody Map addSubjects(@RequestBody String subjectsDetails) {

		ResultData resultData = new ResultData();
		Map mapData = new HashMap();

		try {
			resultData = subjectService.addSubjects(subjectsDetails);
			mapData.put("status", resultData.status);
			mapData.put("message", resultData.message);
		} catch (Exception e) {
			mapData.put("status", false);
			mapData.put("message",
					"Some thing went wrong please contact your admin");
		}

		return mapData;

	}
	
	/**
	 * to get all subjects
	 * @return
	 */
	@RequestMapping(value = "/getAllSubjects", method = RequestMethod.GET)
	public @ResponseBody
	Map getAllSubjects() {
		
		ResultData resultData = new ResultData();
		Map mapData = new LinkedHashMap();		
			
	            try { 	            	
	               resultData = subjectService.getAllSubjects();
	               mapData.put("subjectDetails", resultData.map);
	                mapData.put("status", resultData.status);
	                mapData.put("message", resultData.message);
	            	
	            } catch (Exception e) {
	            	mapData.put("subjectDetails", null);
	            	mapData.put("status", false);
	   			 mapData.put("message","Some thing went wrong please contact your admin");
	            }
					
		
		return mapData;
		

	}
	
	/**
	 * to get all subjects
	 * @return
	 */
	@RequestMapping(value = "/getSubjectsByClass", method = RequestMethod.GET)
	public @ResponseBody
	Map getSubjectsByClass(@QueryParam("class_id") String class_id) {
		
		ResultData resultData = new ResultData();
		Map mapData = new LinkedHashMap();		
			
	            try { 	            	
	               resultData = subjectService.getSubjectsByClass(class_id);
	               mapData.put("subjectDetails", resultData.map);
	                mapData.put("status", resultData.status);
	                mapData.put("message", resultData.message);
	            	
	            } catch (Exception e) {
	            	mapData.put("subjectDetails", null);
	            	mapData.put("status", false);
	   			 mapData.put("message","Some thing went wrong please contact your admin");
	            }
					
		
		return mapData;
		

	}
	
	/**
	 * To add Class Subject Teacher Mapping
	 * @param addClassSubjectTeacherMappingJson
	 * @return
	 */
	
	@RequestMapping(value="/addClassSubjectTeacherMapping", method = RequestMethod.POST)
	public @ResponseBody 
	Map addClassSubjectTeacherMapping(@RequestBody String addClassSubjectTeacherMappingJson){
		ResultData resultData = new ResultData();
		Map mapData = new LinkedHashMap();
		try{
			resultData = classSubjectTeacherMappingService.addClassSubjectTeacherMapping(addClassSubjectTeacherMappingJson);
			mapData.put("status", resultData.status);
			mapData.put("message", resultData.message);
		}catch(Exception e){
			mapData.put("status", false);
			mapData.put("message", "Some thing went wrong please contact your admin");
		}
		return mapData;
		
	}
	
	/**
	 * To get all class subject teacher mapping
	 * @param class_id
	 * @param section_id
	 * @return
	 */
	@RequestMapping(value = "/getAllClassSubjectTeacherMappings", method = RequestMethod.GET)
	public @ResponseBody
	Map getAllClassSubjectTeacherMappings(@QueryParam("class_id") String class_id, @QueryParam("section_id") String section_id) {
		
		ResultData resultData = new ResultData();
		Map mapData = new LinkedHashMap();		
			
	            try { 	            	
	               resultData = classSubjectTeacherMappingService.getAllClassSubjectTeacherMappings(class_id, section_id);
	               mapData.put("classSubjectTeacherMappingDetails", resultData.listData);
	                mapData.put("status", resultData.status);
	                mapData.put("message", resultData.message);
	            	
	            } catch (Exception e) {
	            	mapData.put("classSubjectTeacherMappingDetails", null);
	            	mapData.put("status", false);
	   			 mapData.put("message","Some thing went wrong please contact your admin");
	            }
					
		
		return mapData;
		

	}
	
	
	/**
	 * To add parent credentials
	 * @param class_id
	 * @param section_id
	 * @param student_id
	 * @param userName
	 * @return
	 */
	@RequestMapping(value = "/addParentCredentials", method = RequestMethod.POST)
	public @ResponseBody
	Map addParentCredentials(@FormParam("class_id") String class_id, @FormParam("section_id") String section_id,
			                 @FormParam("student_id") String student_id, @FormParam("userName") String userName){
		
		ResultData resultData = new ResultData();
		Map mapData = new LinkedHashMap();
		try{
			resultData = loginService.addParentCredentials(class_id, section_id, student_id, userName);
			mapData.put("status", resultData.status);
			mapData.put("message", resultData.message);
		}catch(Exception e){
			mapData.put("status", false);
  			 mapData.put("message","Some thing went wrong please contact your admin");
		}
		return mapData;
		
	}
	
	/**
	 * Check validation for Add Parent Credentials
	 * @param stdent_id
	 * @param userName
	 * @return
	 */
	public ResultData chackValdationForAddParentCredentials(String stdent_id, String userName){
		ResultData resultData = new ResultData();
		resultData.status = true;
		
		if(stdent_id.isEmpty()){
			resultData.status = false;
			resultData.message = "Student id is mandatory";
			return resultData;
		}
		
		if(userName.isEmpty()){
			resultData.status = false;
			resultData.message = "User name is mandatory";
			return resultData;
		}
		
		return resultData;
	}
	
	/**
	 * To get all class subject teacher mapping
	 * @param class_id
	 * @param section_id
	 * @return
	 */
	@RequestMapping(value = "/getAllParentsCredentials", method = RequestMethod.GET)
	public @ResponseBody
	Map getAllParentsCredentials(@QueryParam("class_id") String class_id, @QueryParam("section_id") String section_id) {
		
		ResultData resultData = new ResultData();
		Map mapData = new LinkedHashMap();		
			
	            try { 	            	
	               resultData = loginService.getAllParentsCredentials(class_id, section_id);
	               mapData.put("usersListDetails", resultData.listData);
	                mapData.put("status", resultData.status);
	                mapData.put("message", resultData.message);
	            	
	            } catch (Exception e) {
	            	mapData.put("usersListDetails", null);
	            	mapData.put("status", false);
	   			 mapData.put("message","Some thing went wrong please contact your admin");
	            }
					
		
		return mapData;
		

	}
	
	/**
	 * To get all class subject teacher mapping
	 * @param class_id
	 * @param section_id
	 * @return
	 */
	@RequestMapping(value = "/getCredentialDetails", method = RequestMethod.GET)
	public @ResponseBody
	Map getCredentialDetails(@QueryParam("user_id") String user_id) {
		
		ResultData resultData = new ResultData();
		Map mapData = new LinkedHashMap();		
			
	            try { 	            	
	               resultData = loginService.getCredentialDetails(user_id);
	               mapData.put("userDetails", resultData.map);
	                mapData.put("status", resultData.status);
	                mapData.put("message", resultData.message);
	            	
	            } catch (Exception e) {
	            	mapData.put("userDetails", null);
	            	mapData.put("status", false);
	   			 mapData.put("message","Some thing went wrong please contact your admin");
	            }
					
		
		return mapData;
		

	}
	
	/**
	 * To add parent credentials
	 * @param class_id
	 * @param section_id
	 * @param student_id
	 * @param userName
	 * @return
	 */
	@RequestMapping(value = "/addTeacherCredentials", method = RequestMethod.POST)
	public @ResponseBody
	Map addTeacherCredentials(@FormParam("teacher_id") String teacher_id, @FormParam("userName") String userName){
		
		ResultData resultData = new ResultData();
		Map mapData = new LinkedHashMap();
		try{
			resultData = loginService.addTeacherCredentials(teacher_id, userName);
			mapData.put("status", resultData.status);
			mapData.put("message", resultData.message);
		}catch(Exception e){
			mapData.put("status", false);
  			 mapData.put("message","Some thing went wrong please contact your admin");
		}
		return mapData;
		
	}
	
	/**
	 * To get all teachers credentials
	 * @return
	 */
	@RequestMapping(value = "/getAllTeachersCredentials", method = RequestMethod.GET)
	public @ResponseBody
	Map getAllTeachersCredentials(){
		
		ResultData resultData = new ResultData();
		Map mapData = new LinkedHashMap();
		try{
			resultData = loginService.getAllTeachersCredentials();
			mapData.put("teachersCredentilDetails", resultData.listData);
			mapData.put("status", resultData.status);
			mapData.put("message", resultData.message);
		}catch(Exception e){
			mapData.put("teachersCredentilDetails", null);
			mapData.put("status", false);
  			 mapData.put("message","Some thing went wrong please contact your admin");
		}
		return mapData;
		
	}
	
	/**
	 * To deactivate user
	 * @param user_id
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/deactivateUser", method = RequestMethod.POST)
	public @ResponseBody
	Map deactivateUser(@FormParam("user_id") String user_id, @FormParam("status") String status){
		
		ResultData resultData = new ResultData();
		Map mapData = new LinkedHashMap();
		try{
			resultData = loginService.deactivateUser(user_id, status);
			mapData.put("status", resultData.status);
			mapData.put("message", resultData.message);
			
		}catch(Exception e){
			mapData.put("status", false);
 			 mapData.put("message","Some thing went wrong please contact your admin");
		}
		return mapData;
	}
	
	/**
	 * To change passwrd
	 * @param user_id
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/changePassword", method = RequestMethod.POST)
	public @ResponseBody
	Map changePassword(@FormParam("userName") String userName, @FormParam("password") String password, @FormParam("confirmPassword") String confirmPassword){
		
		ResultData resultData = new ResultData();
		Map mapData = new LinkedHashMap();
		try{
			resultData = loginService.changePassword(userName, password);
			mapData.put("status", resultData.status);
			mapData.put("message", resultData.message);
			
		}catch(Exception e){
			mapData.put("status", false);
 			 mapData.put("message","Some thing went wrong please contact your admin");
		}
		return mapData;
	}
	
	/**
	 * To get all classes
	 * @return
	 */
	@RequestMapping(value = "/getAllClasses", method = RequestMethod.GET)
	public @ResponseBody
	Map getAllClasses(){
		
		ResultData resultData = new ResultData();
		Map mapData = new LinkedHashMap();
		try{
			resultData = sectionService.getAllClasses();
			mapData.put("classDetails", resultData.map);
			mapData.put("status", resultData.status);
			mapData.put("message", resultData.message);
		}catch(Exception e){
			mapData.put("classDetails", null);
			mapData.put("status", false);
  			 mapData.put("message","Some thing went wrong please contact your admin");
		}
		return mapData;
		
	}
	
	/**
	 * To add Timetable
	 * @return
	 */
	@RequestMapping(value = "/addTimeTable", method = RequestMethod.POST)
	public @ResponseBody
	Map addTimeTable(@RequestBody String timetableJson) {
		Map mapData = new LinkedHashMap();
        ResultData resultData = new ResultData();
        try {
        	resultData = timeTableService.addTimeTable(timetableJson); 
			mapData.put("message", resultData.message);
			mapData.put("status", resultData.status);

		} catch (Exception e) {
			mapData.put("message", "Some thing went wrong please contact your system admin");
			mapData.put("status", false);
		}		       

		return mapData;
	}
	
	
	/**
	 * 
	 * @param teacher_id
	 * @return
	 */
	@RequestMapping(value = "/getNotificationsForInbox", method = RequestMethod.GET)
	public @ResponseBody
	Map getNotificationsForInbox(@QueryParam("user_id") String user_id) {
		Map mapData = new LinkedHashMap();
        ResultData resultData = new ResultData();
        try {
        	resultData = notificationService.getNotifications(user_id); 
        	mapData.put("notifications", resultData.listData);
			mapData.put("message", resultData.message);
			mapData.put("status", resultData.status);

		} catch (Exception e) {
			mapData.put("notifications", null);
			mapData.put("message", "Some thing went wrong please contact your system admin");
			mapData.put("status", false);
		}		       

		return mapData;
	}
	
	/**
	 * To add transactions
	 * @param transactionJson
	 * @return
	 */
	@RequestMapping(value = "/addTransactionReport", method = RequestMethod.POST)
	public @ResponseBody 
	Map addTransactionReport(@RequestBody String transactionJson){
		Map mapData = new LinkedHashMap();
        ResultData resultData = new ResultData();
        try{
        	
        	resultData = transactionService.addTransactionReport(transactionJson);
        	mapData.put("status", resultData.status);
        	mapData.put("message", resultData.message);
        }catch(Exception e){
        	mapData.put("message", "Some thing went wrong please contact your system admin");
			mapData.put("status", false);
        }
		return mapData;
	}
	
	/**
	 * To get transactions
	 * @param class_id
	 * @param section_id
	 * @param subject_id
	 * @return
	 */
	@RequestMapping(value = "/getTransactionReport", method = RequestMethod.GET)
	public @ResponseBody 
	Map getTransactionReport(@QueryParam("class_id") String class_id, @QueryParam("section_id") String section_id, @QueryParam("subject_id") String subject_id){
		Map mapData = new LinkedHashMap();
        ResultData resultData = new ResultData();
        try{
        	
        	resultData = transactionService.getTransactionReport(class_id, section_id, subject_id);
        	mapData.put("transactionDetails", resultData.map);
        	mapData.put("status", resultData.status);
        	mapData.put("message", resultData.message);
        }catch(Exception e){
        	mapData.put("transactionDetails", null);
        	mapData.put("message", "Some thing went wrong please contact your system admin");
			mapData.put("status", false);
        }
		return mapData;
	}
	
	
	/**
	 * To add addAttendance
	 * @param transactionJson
	 * @return
	 */
	@RequestMapping(value = "/addAttendance", method = RequestMethod.POST)
	public @ResponseBody 
	Map addAttendance(@RequestBody String attendenceJson){
		Map mapData = new LinkedHashMap();
        ResultData resultData = new ResultData();
        try{
        	
        	resultData = attendanceService.addAttendance(attendenceJson);
        	mapData.put("status", resultData.status);
        	mapData.put("message", resultData.message);
        }catch(Exception e){
        	mapData.put("message", "Some thing went wrong please contact your system admin");
			mapData.put("status", false);
        }
		return mapData;
	}
	
	/**
	 * To get absenties list
	 * @param class_id
	 * @param section_id
	 * @return
	 */
	@RequestMapping(value = "/getAttendance", method = RequestMethod.GET)
	public @ResponseBody 
	Map getAttendance(@QueryParam("class_id") String class_id, @QueryParam("section_id") String section_id){
		Map mapData = new LinkedHashMap();
        ResultData resultData = new ResultData();
        try{        	
        	resultData = attendanceService.getAttendance(class_id, section_id);
        	mapData.put("absentiesDetails", resultData.map);
        	mapData.put("status", resultData.status);
        	mapData.put("message", resultData.message);
        }catch(Exception e){
        	mapData.put("absentiesDetails", null);
        	mapData.put("message", "Some thing went wrong please contact your system admin");
			mapData.put("status", false);
        }
		return mapData;
	}
	
	/**
	 * to upload marks
	 * @param file
	 * @return
	 */
	
	@RequestMapping(value = "/uploadMarks", method = RequestMethod.POST)
	public @ResponseBody Map uploadMarks(@FormParam("class_id") String class_id, @FormParam("section_id") String section_id,
			                              @FormParam("examType_id") String examType_id, @FormParam("file") MultipartFile file) {

		ResultData resultData = new ResultData();
		Map mapData = new HashMap();
		//Vector<List> cellVectorHolder = new Vector<List>();
		List<Marks> duplicateList=null;
		Set<Marks>  marksMap= new LinkedHashSet();
		duplicateList=new ArrayList<Marks>();
		Vector<Set> cellVectorHolder = new Vector<Set>();
		Map<String,List<StudentMarks>> map = new LinkedHashMap<String,List<StudentMarks>>();
		Map<Double,List<StudentMarks>> totalMarksMap = new LinkedHashMap<Double,List<StudentMarks>>();
		Map<Double,TotalMarks> totalMarksMap1 = new LinkedHashMap<Double,TotalMarks>();
		try {

			InputStream myInput = file.getInputStream();
			XSSFWorkbook myWorkBook = new XSSFWorkbook(myInput);
			XSSFSheet mySheet = myWorkBook.getSheetAt(0);
			Iterator rowIter = mySheet.rowIterator();
			while (rowIter.hasNext()) {
				XSSFRow myRow = (XSSFRow) rowIter.next();
				Marks currentObj=getMarksObj(myRow);
				if(marksMap.contains(currentObj)){
					duplicateList.add(currentObj);
					mapData.put("duplicateList", duplicateList);
					mapData.put("status", false);
					mapData.put("message", "Rolle number, subject combination is duplicated plase check the list");
					return mapData;
				}
					else{
						marksMap.add(currentObj);
					}
				}
			
			// Add student marks to map besed on subject	
			for(Marks marks:marksMap){
				
				if(map.containsKey(marks.getSubject())){
					StudentMarks studentMarks = new StudentMarks();
					studentMarks.setMarks(Double.parseDouble(marks.getMarksObtained()));
					studentMarks.setRollNumber(Double.parseDouble(marks.getRollNumber()));
					studentMarks.setMaxMarks(Double.parseDouble(marks.getMaxMarks()));
					studentMarks.setDateOfExam(marks.getDateOfExam());
					List<StudentMarks> studentMarksList = map.get(marks.getSubject());
					studentMarksList.add(studentMarks);
					map.put(marks.getSubject(), studentMarksList);					
				}else{
					List<StudentMarks> studentMarksList = new ArrayList<StudentMarks>();
					StudentMarks studentMarks = new StudentMarks();
					studentMarks.setMarks(Double.parseDouble(marks.getMarksObtained()));
					studentMarks.setRollNumber(Double.parseDouble(marks.getRollNumber()));
					studentMarks.setMaxMarks(Double.parseDouble(marks.getMaxMarks()));
					studentMarks.setDateOfExam(marks.getDateOfExam());
					studentMarksList.add(studentMarks);
					map.put(marks.getSubject(), studentMarksList);
				}
				
			}
			
			// To sort the students based on subject marks
			for(String marksData : map.keySet()){
				
				List<StudentMarks> studentMarksList = map.get(marksData);
				Collections.sort(studentMarksList, StudentMarks.marksData);				
			}
			
			
			//Add student marks to map based on roll number
			for(Marks marks : marksMap){				
				//System.out.println(marksList.get(i));
				if(totalMarksMap.containsKey(Double.parseDouble(marks.getRollNumber()))){
					List<StudentMarks> studentMarksList = 	totalMarksMap.get(Double.parseDouble(marks.getRollNumber()));
					StudentMarks studentMarks = new StudentMarks();
					studentMarks.setMaxMarks(Double.parseDouble(marks.getMaxMarks()));
					studentMarks.setMarks(Double.parseDouble(marks.getMarksObtained()));
					studentMarksList.add(studentMarks);
					totalMarksMap.put(Double.parseDouble(marks.getRollNumber()), studentMarksList);	
					
				}else{
					StudentMarks studentMarks = new StudentMarks();
					List<StudentMarks> studentMarksList = new ArrayList<StudentMarks>();
					studentMarks.setMaxMarks(Double.parseDouble(marks.getMaxMarks()));
					studentMarks.setMarks(Double.parseDouble(marks.getMarksObtained()));
					studentMarksList.add(studentMarks);
					totalMarksMap.put(Double.parseDouble(marks.getRollNumber()), studentMarksList);					
				}
				
			}
			
			System.out.println(totalMarksMap);
			// To calculate the total marks and percentage
			List list = new ArrayList(totalMarksMap.keySet());
			for(int key=0;key<list.size();key++){
				double totalMarks = 0;
				double maxMarks = 0;
				List<StudentMarks> listMarks = totalMarksMap.get(list.get(key));
				for(int i=0;i<listMarks.size();i++){
					totalMarks = totalMarks+listMarks.get(i).getMarks();
					maxMarks = maxMarks+listMarks.get(i).getMaxMarks();
					if(i == listMarks.size()-1){
						TotalMarks studentsTotalMarks = new TotalMarks();
						double percentage = (totalMarks/maxMarks)*100;
						studentsTotalMarks.setMaxMarks(maxMarks);
						studentsTotalMarks.setTotalMarks(totalMarks);
						studentsTotalMarks.setPercentage(percentage);						
						//List<TotalMarks> studentsTotalMarksList = new ArrayList<TotalMarks>();
						//studentsTotalMarksList.add(studentsTotalMarks);
						totalMarksMap1.put((Double) list.get(key), studentsTotalMarks);
					}
					
				}
				
			}
			// To sort the students based on total marks
			List<Map.Entry<Double, TotalMarks>> entryList = new ArrayList<Map.Entry<Double, TotalMarks>>(totalMarksMap1.entrySet());
			Collections.sort(entryList, new Comparator<Map.Entry<Double, TotalMarks>>() {
                @Override
                public int compare(Map.Entry<Double, TotalMarks> totalMarks1,
                                   Map.Entry<Double, TotalMarks> totalMarks2) {
                	int  totalMarksData1 = (int) totalMarks1.getValue().getTotalMarks();
                	int  totalMarksData2 = (int) totalMarks2.getValue().getTotalMarks();
                    return totalMarksData2-totalMarksData1;
                }
            }
        );
			System.out.println(entryList);
			//cellVectorHolder.add(marksMap);
			
			resultData = marksService.uploadMarks(class_id, section_id, examType_id, map, entryList);
			mapData.put("duplicateList", resultData.listData);
			mapData.put("status", resultData.status);
			mapData.put("message", resultData.message);
		} catch (Exception e) {
			mapData.put("status", false);
			mapData.put("message",
					"Some thing went wrong please contact your admin");
		}

		return mapData;

	}
	
	/**
	 * To check duplicate roll number
	 * @param myRow
	 * @return
	 */
	private Marks getMarksObj(XSSFRow myRow){
		Marks returnObj = new Marks();
		DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
		returnObj.setRollNumber(String.valueOf(myRow.getCell(0).getNumericCellValue()));
		returnObj.setSubject(myRow.getCell(1).getStringCellValue());
		returnObj.setMarksObtained(String.valueOf(myRow.getCell(2).getNumericCellValue()));
		returnObj.setMaxMarks(String.valueOf(myRow.getCell(3).getNumericCellValue()));
		returnObj.setDateOfExam(df.format(myRow.getCell(4).getDateCellValue()));
		//returnObj.setSubjectWiseRank(String.valueOf(myRow.getCell(5).getNumericCellValue()));		
		
		 return returnObj;		
	}
	
	/**
	 * To get all students marks
	 * @param class_id
	 * @param section_id
	 * @param examType_id
	 * @return
	 */
	@RequestMapping(value = "/getAllStudentsMarks", method = RequestMethod.GET)
	public @ResponseBody 
	Map getAllStudentsMarks(@QueryParam("class_id") String class_id, @QueryParam("section_id") String section_id, @QueryParam("examType_id") String examType_id){
		Map mapData = new LinkedHashMap();
        ResultData resultData = new ResultData();
        try{        	
        	resultData = marksService.getAllStudentsMarks(class_id, section_id, examType_id);
        	mapData.put("marksDetails", resultData.map);
        	mapData.put("status", resultData.status);
        	mapData.put("message", resultData.message);
        }catch(Exception e){
        	mapData.put("marksDetails", null);
        	mapData.put("message", "Some thing went wrong please contact your system admin");
			mapData.put("status", false);
        }
		return mapData;
	}
	
	/**
	 * To get exam details by id
	 * @param exam_id
	 * @return
	 */
	@RequestMapping(value = "/getExamDetailsById", method = RequestMethod.GET)
	public @ResponseBody 
	Map getExamDetailsById(@QueryParam("exam_id") String exam_id){
		Map mapData = new LinkedHashMap();
        ResultData resultData = new ResultData();
        try{        	
        	resultData = examScheduleService.getExamDetailsById(exam_id);
        	mapData.put("examDetails", resultData.map);
        	mapData.put("status", resultData.status);
        	mapData.put("message", resultData.message);
        }catch(Exception e){
        	mapData.put("marksDetails", null);
        	mapData.put("message", "Some thing went wrong please contact your system admin");
			mapData.put("status", false);
        }
		return mapData;
	}
	
	/**
	 * To update exam details
	 * @param class_id
	 * @param examType_id
	 * @param subject_id
	 * @param dateOfExam
	 * @param startTime
	 * @param endTime
	 * @param exam_id
	 * @return
	 */
	@RequestMapping(value = "/updateExamDetails", method = RequestMethod.POST)
	public @ResponseBody 
	Map updateExamDetails(@FormParam("class_id") String class_id, @FormParam("examType_id") String examType_id, 
			              @FormParam("subject_id") String subject_id, @FormParam("dateOfExam") String dateOfExam,
			              @FormParam("startTime") String startTime, @FormParam("endTime") String endTime,@FormParam("exam_id") String exam_id){
		Map mapData = new LinkedHashMap();
        ResultData resultData = new ResultData();
        try{        	
        	resultData = examScheduleService.updateExamDetails(class_id, examType_id, subject_id, dateOfExam, startTime, endTime, exam_id);
        	mapData.put("status", resultData.status);
        	mapData.put("message", resultData.message);
        }catch(Exception e){
        	mapData.put("message", "Some thing went wrong please contact your system admin");
			mapData.put("status", false);
        }
		return mapData;
	}
	
	/**
	 * To update section
	 * @param class_id
	 * @param section
	 * @param section_id
	 * @return
	 */
	@RequestMapping(value = "/updateSections", method = RequestMethod.POST)
	public @ResponseBody 
	Map updateSections(@FormParam("class_id") String class_id, @FormParam("sectionName") String sectionName, @FormParam("section_id") String section_id){
		Map mapData = new LinkedHashMap();
        ResultData resultData = new ResultData();
        try{        	
        	resultData = sectionService.updateSections(class_id, sectionName, section_id);
        	mapData.put("status", resultData.status);
        	mapData.put("message", resultData.message);
        }catch(Exception e){
        	mapData.put("message", "Some thing went wrong please contact your system admin");
			mapData.put("status", false);
        }
		return mapData;
	}
	
	/**
	 * To update subject
	 * @param class_id
	 * @param subjectName
	 * @param subject_id
	 * @return
	 */
	@RequestMapping(value = "/updateSubjects", method = RequestMethod.POST)
	public @ResponseBody 
	Map updateSubjects(@FormParam("class_id") String class_id, @FormParam("subjectName") String subjectName, @FormParam("subject_id") String subject_id){
		Map mapData = new LinkedHashMap();
        ResultData resultData = new ResultData();
        try{        	
        	resultData = subjectService.updateSubjects(class_id, subjectName, subject_id);
        	mapData.put("status", resultData.status);
        	mapData.put("message", resultData.message);
        }catch(Exception e){
        	mapData.put("message", "Some thing went wrong please contact your system admin");
			mapData.put("status", false);
        }
		return mapData;
	}
	
	/**
	 * To update class subject teacher mapping
	 * @param class_id
	 * @param subject_id
	 * @param teacher_id
	 * @param section_id
	 * @param mapping_id
	 * @return
	 */
	@RequestMapping(value = "/updateClassSubjectTeacherMapping", method = RequestMethod.POST)
	public @ResponseBody 
	Map updateClassSubjectTeacherMapping(@FormParam("class_id") String class_id, @FormParam("subject_id") String subject_id, @FormParam("teacher_id") String teacher_id,
			                              @FormParam("section_id") String section_id, @FormParam("mapping_id") String mapping_id){
		Map mapData = new LinkedHashMap();
        ResultData resultData = new ResultData();
        try{        	
        	resultData = classSubjectTeacherMappingService.updateClassSubjectTeacherMapping(class_id, subject_id, teacher_id, section_id, mapping_id);
        	mapData.put("status", resultData.status);
        	mapData.put("message", resultData.message);
        }catch(Exception e){
        	mapData.put("message", "Some thing went wrong please contact your system admin");
			mapData.put("status", false);
        }
		return mapData;
	}
	
	/**
	 * To update class subject teacher mapping
	 * @param class_id
	 * @param subject_id
	 * @param teacher_id
	 * @param section_id
	 * @param mapping_id
	 * @return
	 */
	@RequestMapping(value = "/updateAttendance", method = RequestMethod.POST)
	public @ResponseBody 
	Map updateAttendance(@FormParam("class_id") String class_id, @FormParam("attendance_id") String attendance_id, @FormParam("section_id") String section_id,
			                              @FormParam("dateOfAbsent") String dateOfAbsent, @FormParam("student_id") String student_id){
		Map mapData = new LinkedHashMap();
        ResultData resultData = new ResultData();
        try{        	
        	resultData = attendanceService.updateAttendance(class_id, attendance_id, section_id, dateOfAbsent, student_id);
        	mapData.put("status", resultData.status);
        	mapData.put("message", resultData.message);
        }catch(Exception e){
        	mapData.put("message", "Some thing went wrong please contact your system admin");
			mapData.put("status", false);
        }
		return mapData;
	}
	
	/**
	 * To update marks
	 * @param class_id
	 * @param section_id
	 * @param examType_id
	 * @param dateOfExam
	 * @param student_id
	 * @param subject_id
	 * @param marksObtained
	 * @param maxMarks
	 * @param subjectWiseRank
	 * @param marks_id
	 * @return
	 */
	@RequestMapping(value = "/updateMarks", method = RequestMethod.POST)
	public @ResponseBody 
	Map updateMarks(@FormParam("class_id") String class_id, @FormParam("section_id") String section_id, @FormParam("examType_id") String examType_id,
			                              @FormParam("dateOfExam") String dateOfExam, @FormParam("student_id") String student_id, @FormParam("subject_id") String subject_id,
			                              @FormParam("marksObtained") String marksObtained, @FormParam("maxMarks") String maxMarks,@FormParam("subjectWiseRank") String subjectWiseRank,
			                              @FormParam("marks_id") String marks_id){
		Map mapData = new LinkedHashMap();
        ResultData resultData = new ResultData();
        try{        	
        	resultData = marksService.updateMarks(class_id, section_id, examType_id, dateOfExam, student_id, subject_id, marksObtained, maxMarks, subjectWiseRank, marks_id);
        	mapData.put("status", resultData.status);
        	mapData.put("message", resultData.message);
        }catch(Exception e){
        	mapData.put("message", "Some thing went wrong please contact your system admin");
			mapData.put("status", false);
        }
		return mapData;
	}
	
	
	/**
	 * To get all exam types
	 * @return
	 */
	@RequestMapping(value = "/getExamTypes", method = RequestMethod.GET)
	public @ResponseBody Map getExamTypes(){
		Map mapData = new LinkedHashMap();
        ResultData resultData = new ResultData();
        try{        	
        	resultData = examScheduleService.getExamTypes();
        	mapData.put("examTypeDetails", resultData.map);
        	mapData.put("status", resultData.status);
        	mapData.put("message", resultData.message);
        }catch(Exception e){
        	mapData.put("examTypeDetails", null);
        	mapData.put("message", "Some thing went wrong please contact your system admin");
			mapData.put("status", false);
        }
		return mapData;
	}
	
	/**
	 * To get all message types
	 * @return
	 */
	@RequestMapping(value = "/getMessageTypes", method = RequestMethod.GET)
	public @ResponseBody Map getMessageTypes(){
		Map mapData = new LinkedHashMap();
        ResultData resultData = new ResultData();
        try{        	
        	resultData = notificationService.getMessageTypes();
        	mapData.put("messageTypeDetails", resultData.map);
        	mapData.put("status", resultData.status);
        	mapData.put("message", resultData.message);
        }catch(Exception e){
        	mapData.put("messageTypeDetails", null);
        	mapData.put("message", "Some thing went wrong please contact your system admin");
			mapData.put("status", false);
        }
		return mapData;
	}
	
	
	/**
	 * To get all message types
	 * @return
	 */
	@RequestMapping(value = "/getTransactionReportForInbox", method = RequestMethod.GET)
	public @ResponseBody Map getTransactionReportForInbox(@QueryParam("class_id") String class_id, @QueryParam("section_id") String section_id){
		Map mapData = new LinkedHashMap();
        ResultData resultData = new ResultData();
        try{        	
        	resultData = transactionService.getTransactionReportForInbox(class_id, section_id);
        	mapData.put("transactionDetails", resultData.map);
        	mapData.put("status", resultData.status);
        	mapData.put("message", resultData.message);
        }catch(Exception e){
        	mapData.put("transactionDetails", null);
        	mapData.put("message", "Some thing went wrong please contact your system admin");
			mapData.put("status", false);
        }
		return mapData;
	}
	
	/**
	 * To get Student Marks
	 * @return
	 */
	@RequestMapping(value = "/getStudentMarks", method = RequestMethod.GET)
	public @ResponseBody Map getStudentMarks(@QueryParam("examType_id") String examType_id, @QueryParam("st_id") String st_id){
		Map mapData = new LinkedHashMap();
        ResultData resultData = new ResultData();
        try{        	
        	resultData = marksService.getStudentMarks(examType_id, st_id);
        	mapData.put("marksDetails", resultData.listData);
        	mapData.put("status", resultData.status);
        	mapData.put("message", resultData.message);
        }catch(Exception e){
        	mapData.put("marksDetails", null);
        	mapData.put("message", "Some thing went wrong please contact your system admin");
			mapData.put("status", false);
        }
		return mapData;
	}
	
	/**
	 * To get Student Marks
	 * @return
	 */
	@RequestMapping(value = "/getStudentTotalMarks", method = RequestMethod.GET)
	public @ResponseBody Map getStudentTotalMarks(@QueryParam("examType_id") String examType_id, @QueryParam("st_id") String st_id){
		Map mapData = new LinkedHashMap();
        ResultData resultData = new ResultData();
        try{        	
        	resultData = marksService.getStudentTotalMarks(examType_id, st_id);
        	mapData.put("totalMarksDetails", resultData.map);
        	mapData.put("status", resultData.status);
        	mapData.put("message", resultData.message);
        }catch(Exception e){
        	mapData.put("totalMarksDetails", null);
        	mapData.put("message", "Some thing went wrong please contact your system admin");
			mapData.put("status", false);
        }
		return mapData;
	}
	
	/**
	 * to upload students
	 * @param file
	 * @return
	 */
	
	@RequestMapping(value = "/uploadHolidays", method = RequestMethod.POST)
	public @ResponseBody Map uploadHolidays(
			@FormParam("file") MultipartFile file) {

		ResultData resultData = new ResultData();
		Map mapData = new HashMap();
		//Vector<List> cellVectorHolder = new Vector<List>();
		List<Holidays> duplicateList=null;
		Set<Holidays>  holidaysMap= new HashSet();
		duplicateList=new ArrayList<Holidays>();
		Vector<Set> cellVectorHolder = new Vector<Set>();
		

		try {

			InputStream myInput = file.getInputStream();
			XSSFWorkbook myWorkBook = new XSSFWorkbook(myInput);
			XSSFSheet mySheet = myWorkBook.getSheetAt(0);
			Iterator rowIter = mySheet.rowIterator();
			while (rowIter.hasNext()) {
				XSSFRow myRow = (XSSFRow) rowIter.next();
				//studentsSet = new HashSet();
				Holidays currentObj=getHolidaysObj(myRow);
				if(holidaysMap.contains(currentObj)){
					duplicateList.add(currentObj);
					mapData.put("duplicateList", duplicateList);
					mapData.put("status", false);
					mapData.put("message", "Data duplicated please check the data");
					return mapData;
				}else{
						holidaysMap.add(currentObj);
					}
				}
				
			System.out.println(holidaysMap);
			//cellVectorHolder.add(holidaysMap);
			resultData = attendanceService.uploadHolidays(holidaysMap);
			mapData.put("duplicateList", resultData.listData);
			mapData.put("status", resultData.status);
			mapData.put("message", resultData.message);
		} catch (Exception e) {
			mapData.put("status", false);
			mapData.put("message",
					"Some thing went wrong please contact your admin");
		}

		return mapData;

	}	

	/**
	 * To check duplicate roll number
	 * @param myRow
	 * @return
	 */
	private Holidays getHolidaysObj(XSSFRow myRow){
		Holidays returnObj = new Holidays();
		DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
		returnObj.setHolidayType(myRow.getCell(0).getStringCellValue());
		try{
		returnObj.setDate((myRow.getCell(1).getStringCellValue()));
		}catch(IllegalStateException  ne){
			returnObj.setDate(df.format(myRow.getCell(1).getDateCellValue()));
		}
		 return returnObj;		
	}
	
	/**
	 * To add admission details
	 * @param employee
	 * @return
	 */
	@RequestMapping(value = "/createAdmission", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	Map createAdmission(@RequestBody Admission admission) {
		ResultData resultData = new ResultData();
		Map mapData = new HashMap();
		try {
			// Check validation for admission details
			resultData = checkValidationFoAdmission(admission);
			if(resultData.status){
			resultData = admissionService.createAdmission(admission);
			mapData.put("status", resultData.status);
			mapData.put("message", resultData.message);
			}else{
				mapData.put("status", resultData.status);
				mapData.put("message", resultData.message);
			}
		} catch (Exception e) {
			
			mapData.put("status", false);
			mapData.put("message",
					"Some thing went wrong please contact your admin");
			
		}
		
		return mapData;

	}
	
	//To check validation for add admission details
	public ResultData checkValidationFoAdmission(Admission admission){
		
		ResultData resultData = new ResultData();
		resultData.status = true;
		if(admission.getAdmissionNum()==null){
			resultData.message = "Admission number is mandatory";
			resultData.status = false;
			return resultData;
			}
		if(admission.getAdmissionDate()==null){
			resultData.message = "Admission date is mandatory";
			resultData.status = false;
			return resultData;
		}
		if(admission.getFirstName()==null){
			resultData.message = "First name is mandatory";
			resultData.status = false;
			return resultData;
		}
		if(admission.getLastName()==null){
			resultData.message = "Last name is mandatory";
			resultData.status = false;
			return resultData;
		}
		if(admission.getClass_()==null){
			resultData.message = "Class is mandatory";
			resultData.status = false;
			return resultData;
		}
		if(admission.getDateOfBirth()==null){
			resultData.message = "Date of birth is mandatory";
			resultData.status = false;
			return resultData;
		}
		if(admission.getGender()==null){
			resultData.message = "Gender is mandatory";
			resultData.status = false;
			return resultData;
		}
		if(admission.getAdmissionDate()==null){
			resultData.message = "Admission date is mandatory";
			resultData.status = false;
			return resultData;
		}
		if(admission.getBloodGroup()==null){
			resultData.message = "Blood group is mandatory";
			resultData.status = false;
			return resultData;
		}
		if(admission.getBirthPlace()==null){
			resultData.message = "Birth place is mandatory";
			resultData.status = false;
			return resultData;
		}
		if(admission.getNationality()==null){
			resultData.message = "Nationality is mandatory";
			resultData.status = false;
			return resultData;
		}
		if(admission.getReligion()==null){
			resultData.message = "Religion is mandatory";
			resultData.status = false;
			return resultData;
		}
		if(admission.getCastCategory()==null){
			resultData.message = "Cast category is mandatory";
			resultData.status = false;
			return resultData;
		}
		if(admission.getMotherToungue()==null){
			resultData.message = "Mother toungue is mandatory";
			resultData.status = false;
			return resultData;
		}
		if(admission.getLocalAddress()==null){
			resultData.message = "Local address is mandatory";
			resultData.status = false;
			return resultData;
		}
		if(admission.getCity()==null){
			resultData.message = "City is mandatory";
			resultData.status = false;
			return resultData;
		}
		if(admission.getState()==null){
			resultData.message = "State is mandatory";
			resultData.status = false;
			return resultData;
		}
		if(admission.getPincode()==null){
			resultData.message = "Pincode is mandatory";
			resultData.status = false;
			return resultData;
		}
		if(admission.getPermAddress()==null){
			resultData.message = "Permanent address is mandatory";
			resultData.status = false;
			return resultData;
		}
		if(admission.getPermCity()==null){
			resultData.message = "Permanent city is mandatory";
			resultData.status = false;
			return resultData;
		}
		if(admission.getPermState()==null){
			resultData.message = "Permanent state is mandatory";
			resultData.status = false;
			return resultData;
		}
		if(admission.getPermPincode()==null){
			resultData.message = "Permanent pincode is mandatory";
			resultData.status = false;
			return resultData;
		}
		if(admission.getPhoneNumber()==null){
			resultData.message = "Phone number is mandatory";
			resultData.status = false;
			return resultData;
		}
		if(admission.getEmail()==null){
			resultData.message = "email is mandatory";
			resultData.status = false;
			return resultData;
		}
		if(admission.getMotherFullName()==null){
			resultData.message = "Mother name is mandatory";
			resultData.status = false;
			return resultData;
		}
		if(admission.getMotherOccupation()==null){
			resultData.message = "Mother occupation is mandatory";
			resultData.status = false;
			return resultData;
		}
		if(admission.getFatherFullName()==null){
			resultData.message = "Father name is mandatory";
			resultData.status = false;
			return resultData;
		}
		if(admission.getFatherOccupation()==null){
			resultData.message = "Father occupation is mandatory";
			resultData.status = false;
			return resultData;
		}
		if(admission.getFatherIncome()==null){
			resultData.message = "Income is mandatory";
			resultData.status = false;
			return resultData;
		}
		if(admission.getPermCity()==null){
			resultData.message = "Permanent city is mandatory";
			resultData.status = false;
			return resultData;
		}
		if(admission.getPermCity()==null){
			resultData.message = "Permanent city is mandatory";
			resultData.status = false;
			return resultData;
		}
		if(admission.getPermCity()==null){
			resultData.message = "Permanent city is mandatory";
			resultData.status = false;
			return resultData;
		}
		if(admission.getPermCity()==null){
			resultData.message = "Permanent city is mandatory";
			resultData.status = false;
			return resultData;
		}
		
		return resultData;
		
	}
	
	/**
	 * To update admission
	 * @param id
	 * @param admission
	 * @return
	 */
	@RequestMapping(value = "/updateAdmission/{id}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	Map updateAdmission(@PathVariable String id, @RequestBody Admission admission) {
		ResultData resultData = new ResultData();
		Map mapData = new HashMap();
		try {
			resultData = admissionService.updateAdmission(id,admission);
			mapData.put("status", resultData.status);
			mapData.put("message", resultData.message);
		} catch (Exception e) {
			
			mapData.put("status", false);
			mapData.put("message",
					"Some thing went wrong please contact your admin");
			
		}
		
		return mapData;

	}
	
	/**
	 * To update student
	 * @param id
	 * @param student
	 * @return
	 */
	@RequestMapping(value = "/updateStudent", method = RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	void updateStudent(@RequestBody Student student) {
		System.out.println("===================");
		/*ResultData resultData = new ResultData();
		Map mapData = new HashMap();
		try {
			resultData = studentService.updateStudent(id,student);
			mapData.put("status", resultData.status);
			mapData.put("message", resultData.message);
		} catch (Exception e) {
			
			mapData.put("status", false);
			mapData.put("message",
					"Some thing went wrong please contact your admin");
			
		}
		
		return mapData;*/

	}
}
