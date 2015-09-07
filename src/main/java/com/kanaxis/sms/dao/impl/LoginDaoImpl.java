package com.kanaxis.sms.dao.impl;

import org.apache.commons.codec.binary.Base64;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import com.kanaxis.sms.dao.LoginDao;
import com.kanaxis.sms.model.Employee;
import com.kanaxis.sms.model.User;
import com.kanaxis.sms.util.UserDetails;

public class LoginDaoImpl implements LoginDao{
	
	@Autowired
	SessionFactory sessionFactory;

	Session session = null;
	Transaction tx = null;

	@Override
	public UserDetails login(String userName, String password) throws Exception {
		UserDetails userDetails = new UserDetails();
		try{
		
		session = sessionFactory.openSession();		
		Query query = session.createQuery("FROM User user WHERE user.userName=:userName");
		query.setParameter("userName",userName);
		User user = (User) query.uniqueResult();
		//To deode the password
		byte[] valueDecoded= Base64.decodeBase64(user.getPassword().getBytes());
        String dbPassword = new String(valueDecoded);
        if(password.equals(dbPassword)){
        	userDetails.setName(user.getName());
        	userDetails.setRoleId(user.getRole().getId());
        	userDetails.setMessage("user successfully logedin");
        	userDetails.setStatus(true);        	
        }else{
        	userDetails.setMessage("userName/password wrong");
        	userDetails.setStatus(false);
        }
		}catch(Exception e){
			
			userDetails.setMessage("Some thing went wrong please contact your admin");
        	userDetails.setStatus(false);
		}

		return userDetails;
	}

}
