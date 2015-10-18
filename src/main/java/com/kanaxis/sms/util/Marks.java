package com.kanaxis.sms.util;

import java.util.Comparator;

public class Marks {

	String rollNumber;
	String subject;
	String marksObtained;
	String maxMarks;
	String dateOfExam;
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((rollNumber == null) ? 0 : rollNumber.hashCode());
		result = prime * result + ((subject == null) ? 0 : subject.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Marks other = (Marks) obj;
		if (rollNumber == null) {
			if (other.rollNumber != null)
				return false;
		} else if (!rollNumber.equals(other.rollNumber))
			return false;
		if (subject == null) {
			if (other.subject != null)
				return false;
		} else if (!subject.equals(other.subject))
			return false;
		return true;
	}
	
	public String getRollNumber() {
		return rollNumber;
	}
	public void setRollNumber(String rollNumber) {
		this.rollNumber = rollNumber;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getMarksObtained() {
		return marksObtained;
	}
	public void setMarksObtained(String marksObtained) {
		this.marksObtained = marksObtained;
	}
	public String getMaxMarks() {
		return maxMarks;
	}
	public void setMaxMarks(String maxMarks) {
		this.maxMarks = maxMarks;
	}
	public String getDateOfExam() {
		return dateOfExam;
	}
	public void setDateOfExam(String dateOfExam) {
		this.dateOfExam = dateOfExam;
	}
	@Override
	public String toString() {
		return "Marks [rollNumber=" + rollNumber + ", subject=" + subject
				+ ", marksObtained=" + marksObtained + ", maxMarks=" + maxMarks
				+ ", dateOfExam=" + dateOfExam + "]";
	}
	
	
	
	
	
	

	

}
