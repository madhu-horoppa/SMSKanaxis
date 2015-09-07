<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<form method="POST" enctype="multipart/form-data"
		action="http://localhost:8080/SchoolManagementSystem/sms/updateTeacher">
		Id : <input type="text" name="teacherId">
		File to upload: <input type="file" name="file"><br />
		 Teacher Name: <input type="text" name="teacherName"><br /> <br /> 
		 Qualification: <input type="text" name="qualification"><br /> <br /> 
		 Email: <input type="text" name="email"><br /> <br />
		 Experience: <input type="text" name="exp"><br /> <br />
		 Phone1: <input type="text" name="phone1"><br /> <br />
		 Phone2: <input type="text" name="phone2"><br /> <br />
		 Address: <input type="text" name="address"><br /> <br />
		 Joining Date: <input type="text" name="joiningDate"><br /> <br />
		 Gender: <input type="text" name="gender"><br /> <br />
		 Job Title: <input type="text" name="jobTitle"><br /> <br />
		 <input type="submit"
			value="Upload"> Press here to upload the file!
	</form>
</body>
</html>