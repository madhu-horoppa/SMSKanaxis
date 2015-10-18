package com.kanaxis.sms.dao;

import com.kanaxis.sms.util.ResultData;

public interface TransactionDao {

	ResultData addTransactionReport(String transactionJson);


	ResultData getTransactionReport(String class_id, String section_id,
			String subject_id);


	ResultData getTransactionReportForInbox(String class_id, String section_id);

}
