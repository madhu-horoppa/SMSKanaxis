<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<h1>Login Page</h1>

	<form action="http://localhost:8080/SchoolManagementSystem/sms/login" method="post">
		<p>
			User Name : <input type="text" name="userName" />
		</p>
		<p>
			Password : <input type="text" name="password" />
		</p>
		<input type="submit" value="Login" />
	</form>

</body>
</html>