package com.kanaxis.sms.util;


public class Students {
	
	public Integer id;
	public String section;
	public String classes;
	public String firstName;
	public String lastName;
	public String rollNumber;
	public String dateOfBirth;
	public String gender;
	public String bloodGroup;
	public String relegion;
	public String castCategory;
	public String subcast;
	public String motherTongue;
	public String localAddress;
	public String city;
	public String state;
	public String pincode;
	public String permAddress;
	public String permCity;
	public String permState;
	public String permPincode;
	public String motherFullName;
	public String motherOccupation;
	public String motherEducation;
	public String fatherFullName;
	public String fatherOccupation;
	public String totalIncome;
	public String primaryContactNumber;
	public String secondaryContactNumber;
	public String email;
	public String joinedDate;
	public String createdDate;
	public String fatherEducation;
	public String physicalDisability;
	public String parentAsGuardian;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getSection() {
		return section;
	}
	public void setSection(String section) {
		this.section = section;
	}
	public String getClasses() {
		return classes;
	}
	public void setClasses(String classes) {
		this.classes = classes;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getRollNumber() {
		return rollNumber;
	}
	public void setRollNumber(String rollNumber) {
		this.rollNumber = rollNumber;
	}
	public String getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getBloodGroup() {
		return bloodGroup;
	}
	public void setBloodGroup(String bloodGroup) {
		this.bloodGroup = bloodGroup;
	}
	public String getRelegion() {
		return relegion;
	}
	public void setRelegion(String relegion) {
		this.relegion = relegion;
	}
	public String getCastCategory() {
		return castCategory;
	}
	public void setCastCategory(String castCategory) {
		this.castCategory = castCategory;
	}
	public String getSubcast() {
		return subcast;
	}
	public void setSubcast(String subcast) {
		this.subcast = subcast;
	}
	public String getMotherTongue() {
		return motherTongue;
	}
	public void setMotherTongue(String motherTongue) {
		this.motherTongue = motherTongue;
	}
	public String getLocalAddress() {
		return localAddress;
	}
	public void setLocalAddress(String localAddress) {
		this.localAddress = localAddress;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getPincode() {
		return pincode;
	}
	public void setPincode(String pincode) {
		this.pincode = pincode;
	}
	public String getPermAddress() {
		return permAddress;
	}
	public void setPermAddress(String permAddress) {
		this.permAddress = permAddress;
	}
	public String getPermCity() {
		return permCity;
	}
	public void setPermCity(String permCity) {
		this.permCity = permCity;
	}
	public String getPermState() {
		return permState;
	}
	public void setPermState(String permState) {
		this.permState = permState;
	}
	public String getPermPincode() {
		return permPincode;
	}
	public void setPermPincode(String permPincode) {
		this.permPincode = permPincode;
	}
	public String getMotherFullName() {
		return motherFullName;
	}
	public void setMotherFullName(String motherFullName) {
		this.motherFullName = motherFullName;
	}
	public String getMotherOccupation() {
		return motherOccupation;
	}
	public void setMotherOccupation(String motherOccupation) {
		this.motherOccupation = motherOccupation;
	}
	public String getMotherEducation() {
		return motherEducation;
	}
	public void setMotherEducation(String motherEducation) {
		this.motherEducation = motherEducation;
	}
	public String getFatherFullName() {
		return fatherFullName;
	}
	public void setFatherFullName(String fatherFullName) {
		this.fatherFullName = fatherFullName;
	}
	public String getFatherOccupation() {
		return fatherOccupation;
	}
	public void setFatherOccupation(String fatherOccupation) {
		this.fatherOccupation = fatherOccupation;
	}
	public String getTotalIncome() {
		return totalIncome;
	}
	public void setTotalIncome(String totalIncome) {
		this.totalIncome = totalIncome;
	}
	public String getPrimaryContactNumber() {
		return primaryContactNumber;
	}
	public void setPrimaryContactNumber(String primaryContactNumber) {
		this.primaryContactNumber = primaryContactNumber;
	}
	public String getSecondaryContactNumber() {
		return secondaryContactNumber;
	}
	public void setSecondaryContactNumber(String secondaryContactNumber) {
		this.secondaryContactNumber = secondaryContactNumber;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getJoinedDate() {
		return joinedDate;
	}
	public void setJoinedDate(String joinedDate) {
		this.joinedDate = joinedDate;
	}
	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	public String getFatherEducation() {
		return fatherEducation;
	}
	public void setFatherEducation(String fatherEducation) {
		this.fatherEducation = fatherEducation;
	}
	public String getPhysicalDisability() {
		return physicalDisability;
	}
	public void setPhysicalDisability(String physicalDisability) {
		this.physicalDisability = physicalDisability;
	}
	public String getParentAsGuardian() {
		return parentAsGuardian;
	}
	public void setParentAsGuardian(String parentAsGuardian) {
		this.parentAsGuardian = parentAsGuardian;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((classes == null) ? 0 : classes.hashCode());
		//result = prime * result + ((email == null) ? 0 : email.toLowerCase().hashCode());
		result = prime * result	+ ((rollNumber == null) ? 0 : rollNumber.hashCode());
		result = prime * result + ((section == null) ? 0 : section.hashCode());
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
		Students other = (Students) obj;
		if (rollNumber == null) {
			if (other.rollNumber != null)
				return false;
		} else if (!rollNumber.equals(other.rollNumber))
			return false;		
 		if (classes == null) {
			if (other.classes != null)
				return false;
		} else if (!classes.equals(other.classes))
			return false;
		if (section == null) {
			if (other.section != null)
				return false;
		} else if (!section.equals(other.section))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Students [section=" + section + ", classes=" + classes
				+ ", firstName=" + firstName + ", rollNumber=" + rollNumber
				+ "]";
	}
	
	
	
	
	

}
