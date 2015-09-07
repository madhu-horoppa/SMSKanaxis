package com.kanaxis.sms.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.kanaxis.sms.dao.DataDao;
import com.kanaxis.sms.model.Employee;
import com.kanaxis.sms.services.DataServices;

public class DataServicesImpl implements DataServices {

	@Autowired
	DataDao dataDao;
	
	@Override
	public boolean addEntity(Employee employee) throws Exception {
		return dataDao.addEntity(employee);
	}

	@Override
	public Employee getEntityById(long id) throws Exception {
		return dataDao.getEntityById(id);
	}

	@Override
	public List<Employee> getEntityList() throws Exception {
		return dataDao.getEntityList();
	}

	@Override
	public boolean deleteEntity(long id) throws Exception {
		return dataDao.deleteEntity(id);
	}

}
