package com.kanaxis.sms.util;

public class Holidays {
	
	private String holidayType;
	private String date;
	public String getHolidayType() {
		return holidayType;
	}
	public void setHolidayType(String holidayType) {
		this.holidayType = holidayType;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result
				+ ((holidayType == null) ? 0 : holidayType.hashCode());
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
		Holidays other = (Holidays) obj;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (holidayType == null) {
			if (other.holidayType != null)
				return false;
		} else if (!holidayType.equals(other.holidayType))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Holidays [holidayType=" + holidayType + ", date=" + date + "]";
	}
	
	

}
