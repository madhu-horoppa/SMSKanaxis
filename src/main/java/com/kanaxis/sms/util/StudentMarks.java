package com.kanaxis.sms.util;

import java.util.Comparator;

public class StudentMarks {
	
	private double marks;
	private double rollNumber;
	private double maxMarks;
	private String dateOfExam;
	private double subjectWiseRank;
	
	
	
	

	public double getSubjectWiseRank() {
		return subjectWiseRank;
	}

	public void setSubjectWiseRank(double subjectWiseRank) {
		this.subjectWiseRank = subjectWiseRank;
	}

	public double getRollNumber() {
		return rollNumber;
	}

	public void setRollNumber(double rollNumber) {
		this.rollNumber = rollNumber;
	}

	

	public double getMaxMarks() {
		return maxMarks;
	}

	public void setMaxMarks(double maxMarks) {
		this.maxMarks = maxMarks;
	}

	public String getDateOfExam() {
		return dateOfExam;
	}

	public void setDateOfExam(String dateOfExam) {
		this.dateOfExam = dateOfExam;
	}	

	public static Comparator<StudentMarks> getMarksData() {
		return marksData;
	}

	public static void setMarksData(Comparator<StudentMarks> marksData) {
		StudentMarks.marksData = marksData;
	}

	public double getMarks() {
		return marks;
	}

	public void setMarks(double marks) {
		this.marks = marks;
	}
	
	
	


	@Override
	public String toString() {
		return "StudentMarks [marks=" + marks + ", rollNumber=" + rollNumber
				+ ", maxMarks=" + maxMarks + ", dateOfExam=" + dateOfExam + "]";
	}





	/*Comparator for sorting the list by roll no*/
    public static Comparator<StudentMarks> marksData = new Comparator<StudentMarks>() {

	public int compare(StudentMarks s1, StudentMarks s2) {

		double marks1 = s1.getMarks();
		double marks2 = s2.getMarks();

	   /*For ascending order*/
	   return (int) (marks2-marks1);

	   /*For descending order*/
	   //rollno2-rollno1;
   }};

	
	

}
