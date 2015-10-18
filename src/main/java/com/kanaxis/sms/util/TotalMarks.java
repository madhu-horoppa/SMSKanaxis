package com.kanaxis.sms.util;


public class TotalMarks {
	
	private double totalMarks;
	private double maxMarks;
	private double percentage;
	
	public double getTotalMarks() {
		return totalMarks;
	}
	public void setTotalMarks(double totalMarks) {
		this.totalMarks = totalMarks;
	}
	public double getMaxMarks() {
		return maxMarks;
	}
	public void setMaxMarks(double maxMarks) {
		this.maxMarks = maxMarks;
	}
	public double getPercentage() {
		return percentage;
	}
	public void setPercentage(double percentage) {
		this.percentage = percentage;
	}
	@Override
	public String toString() {
		return "TotalMarks [totalMarks=" + totalMarks + ", maxMarks="
				+ maxMarks + ", percentage=" + percentage + "]";
	}
	
	
	
	
	
	
	
	

}
