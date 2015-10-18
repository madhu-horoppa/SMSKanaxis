package com.kanaxis.sms.services;

import com.kanaxis.sms.util.ResultData;

public interface TransactionService {

	ResultData addTransactionReport(String transactionJson);


	ResultData getTransactionReport(String class_id, String section_id,
			String subject_id);


	ResultData getTransactionReportForInbox(String class_id, String section_id);

}
