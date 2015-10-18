package com.kanaxis.sms.services.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.kanaxis.sms.dao.TransactionDao;
import com.kanaxis.sms.services.TransactionService;
import com.kanaxis.sms.util.ResultData;

public class TransactionServiceImpl implements TransactionService{
	
	@Autowired
	TransactionDao transactionDao;

	@Override
	public ResultData addTransactionReport(String transactionJson) {
		return transactionDao.addTransactionReport(transactionJson);
	}

	@Override
	public ResultData getTransactionReport(String class_id, String section_id,
			String subject_id) {
		return transactionDao.getTransactionReport(class_id, section_id, subject_id);
	}

	@Override
	public ResultData getTransactionReportForInbox(String class_id,
			String section_id) {
		return transactionDao.getTransactionReportForInbox(class_id, section_id);
	}

	

}
