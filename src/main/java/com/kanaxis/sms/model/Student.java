package com.kanaxis.sms.model;

// Generated Oct 18, 2015 2:37:00 PM by Hibernate Tools 3.4.0.CR1

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Student generated by hbm2java
 */
@Entity
@Table(name = "student", catalog = "sms")
public class Student implements java.io.Serializable {

	private Integer id;
	private Section section;
	private Classes classes;
	private String firstName;
	private String lastName;
	private Integer rollNumber;
	private Date dateOfBirth;
	private String gender;
	private String bloodGroup;
	private String relegion;
	private String castCategory;
	private String subcast;
	private String motherTongue;
	private String localAddress;
	private String city;
	private String state;
	private Double pincode;
	private String permAddress;
	private String permCity;
	private String permState;
	private Double permPincode;
	private String motherFullName;
	private String motherOccupation;
	private String motherEducation;
	private String fatherFullName;
	private String fatherOccupation;
	private Double totalIncome;
	private String primaryContactNumber;
	private String secondaryContactNumber;
	private String email;
	private Date joinedDate;
	private Date createdDate;
	private String fatherEducation;
	private Byte physicalDisability;
	private Byte parentAsGuardian;
	private Set<WeekwiseAttendance> weekwiseAttendances = new HashSet<WeekwiseAttendance>(
			0);
	private Set<MarksTable> marksTables = new HashSet<MarksTable>(0);
	private Set<Rank> ranks = new HashSet<Rank>(0);
	private Set<Attendance> attendances = new HashSet<Attendance>(0);
	private Set<MonthlywiseAttendance> monthlywiseAttendances = new HashSet<MonthlywiseAttendance>(
			0);

	public Student() {
	}

	public Student(Section section, Classes classes, String firstName,
			String lastName, Integer rollNumber, Date dateOfBirth,
			String gender, String bloodGroup, String relegion,
			String castCategory, String subcast, String motherTongue,
			String localAddress, String city, String state, Double pincode,
			String permAddress, String permCity, String permState,
			Double permPincode, String motherFullName, String motherOccupation,
			String motherEducation, String fatherFullName,
			String fatherOccupation, Double totalIncome,
			String primaryContactNumber, String secondaryContactNumber,
			String email, Date joinedDate, Date createdDate,
			String fatherEducation, Byte physicalDisability,
			Byte parentAsGuardian, Set<WeekwiseAttendance> weekwiseAttendances,
			Set<MarksTable> marksTables, Set<Rank> ranks,
			Set<Attendance> attendances,
			Set<MonthlywiseAttendance> monthlywiseAttendances) {
		this.section = section;
		this.classes = classes;
		this.firstName = firstName;
		this.lastName = lastName;
		this.rollNumber = rollNumber;
		this.dateOfBirth = dateOfBirth;
		this.gender = gender;
		this.bloodGroup = bloodGroup;
		this.relegion = relegion;
		this.castCategory = castCategory;
		this.subcast = subcast;
		this.motherTongue = motherTongue;
		this.localAddress = localAddress;
		this.city = city;
		this.state = state;
		this.pincode = pincode;
		this.permAddress = permAddress;
		this.permCity = permCity;
		this.permState = permState;
		this.permPincode = permPincode;
		this.motherFullName = motherFullName;
		this.motherOccupation = motherOccupation;
		this.motherEducation = motherEducation;
		this.fatherFullName = fatherFullName;
		this.fatherOccupation = fatherOccupation;
		this.totalIncome = totalIncome;
		this.primaryContactNumber = primaryContactNumber;
		this.secondaryContactNumber = secondaryContactNumber;
		this.email = email;
		this.joinedDate = joinedDate;
		this.createdDate = createdDate;
		this.fatherEducation = fatherEducation;
		this.physicalDisability = physicalDisability;
		this.parentAsGuardian = parentAsGuardian;
		this.weekwiseAttendances = weekwiseAttendances;
		this.marksTables = marksTables;
		this.ranks = ranks;
		this.attendances = attendances;
		this.monthlywiseAttendances = monthlywiseAttendances;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "section_id")
	public Section getSection() {
		return this.section;
	}

	public void setSection(Section section) {
		this.section = section;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "classes_id")
	public Classes getClasses() {
		return this.classes;
	}

	public void setClasses(Classes classes) {
		this.classes = classes;
	}

	@Column(name = "first_name", length = 45)
	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	@Column(name = "last_name", length = 45)
	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@Column(name = "roll_number")
	public Integer getRollNumber() {
		return this.rollNumber;
	}

	public void setRollNumber(Integer rollNumber) {
		this.rollNumber = rollNumber;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "date_of_birth", length = 10)
	public Date getDateOfBirth() {
		return this.dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	@Column(name = "gender", length = 45)
	public String getGender() {
		return this.gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	@Column(name = "blood_group", length = 45)
	public String getBloodGroup() {
		return this.bloodGroup;
	}

	public void setBloodGroup(String bloodGroup) {
		this.bloodGroup = bloodGroup;
	}

	@Column(name = "relegion", length = 45)
	public String getRelegion() {
		return this.relegion;
	}

	public void setRelegion(String relegion) {
		this.relegion = relegion;
	}

	@Column(name = "cast_category", length = 100)
	public String getCastCategory() {
		return this.castCategory;
	}

	public void setCastCategory(String castCategory) {
		this.castCategory = castCategory;
	}

	@Column(name = "subcast", length = 45)
	public String getSubcast() {
		return this.subcast;
	}

	public void setSubcast(String subcast) {
		this.subcast = subcast;
	}

	@Column(name = "mother_tongue", length = 45)
	public String getMotherTongue() {
		return this.motherTongue;
	}

	public void setMotherTongue(String motherTongue) {
		this.motherTongue = motherTongue;
	}

	@Column(name = "local_address", length = 45)
	public String getLocalAddress() {
		return this.localAddress;
	}

	public void setLocalAddress(String localAddress) {
		this.localAddress = localAddress;
	}

	@Column(name = "city", length = 45)
	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@Column(name = "state", length = 45)
	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@Column(name = "pincode", precision = 22, scale = 0)
	public Double getPincode() {
		return this.pincode;
	}

	public void setPincode(Double pincode) {
		this.pincode = pincode;
	}

	@Column(name = "perm_address", length = 45)
	public String getPermAddress() {
		return this.permAddress;
	}

	public void setPermAddress(String permAddress) {
		this.permAddress = permAddress;
	}

	@Column(name = "perm_city", length = 45)
	public String getPermCity() {
		return this.permCity;
	}

	public void setPermCity(String permCity) {
		this.permCity = permCity;
	}

	@Column(name = "perm_state", length = 45)
	public String getPermState() {
		return this.permState;
	}

	public void setPermState(String permState) {
		this.permState = permState;
	}

	@Column(name = "perm_pincode", precision = 22, scale = 0)
	public Double getPermPincode() {
		return this.permPincode;
	}

	public void setPermPincode(Double permPincode) {
		this.permPincode = permPincode;
	}

	@Column(name = "mother_full_name", length = 45)
	public String getMotherFullName() {
		return this.motherFullName;
	}

	public void setMotherFullName(String motherFullName) {
		this.motherFullName = motherFullName;
	}

	@Column(name = "mother_occupation", length = 45)
	public String getMotherOccupation() {
		return this.motherOccupation;
	}

	public void setMotherOccupation(String motherOccupation) {
		this.motherOccupation = motherOccupation;
	}

	@Column(name = "mother_education", length = 45)
	public String getMotherEducation() {
		return this.motherEducation;
	}

	public void setMotherEducation(String motherEducation) {
		this.motherEducation = motherEducation;
	}

	@Column(name = "father_full_name", length = 45)
	public String getFatherFullName() {
		return this.fatherFullName;
	}

	public void setFatherFullName(String fatherFullName) {
		this.fatherFullName = fatherFullName;
	}

	@Column(name = "father_occupation", length = 45)
	public String getFatherOccupation() {
		return this.fatherOccupation;
	}

	public void setFatherOccupation(String fatherOccupation) {
		this.fatherOccupation = fatherOccupation;
	}

	@Column(name = "total_income", precision = 22, scale = 0)
	public Double getTotalIncome() {
		return this.totalIncome;
	}

	public void setTotalIncome(Double totalIncome) {
		this.totalIncome = totalIncome;
	}

	@Column(name = "primary_contact_number", length = 45)
	public String getPrimaryContactNumber() {
		return this.primaryContactNumber;
	}

	public void setPrimaryContactNumber(String primaryContactNumber) {
		this.primaryContactNumber = primaryContactNumber;
	}

	@Column(name = "secondary_contact_number", length = 45)
	public String getSecondaryContactNumber() {
		return this.secondaryContactNumber;
	}

	public void setSecondaryContactNumber(String secondaryContactNumber) {
		this.secondaryContactNumber = secondaryContactNumber;
	}

	@Column(name = "email", length = 45)
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "joined_date", length = 10)
	public Date getJoinedDate() {
		return this.joinedDate;
	}

	public void setJoinedDate(Date joinedDate) {
		this.joinedDate = joinedDate;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_date", length = 19)
	public Date getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	@Column(name = "father_education", length = 45)
	public String getFatherEducation() {
		return this.fatherEducation;
	}

	public void setFatherEducation(String fatherEducation) {
		this.fatherEducation = fatherEducation;
	}

	@Column(name = "physical_disability")
	public Byte getPhysicalDisability() {
		return this.physicalDisability;
	}

	public void setPhysicalDisability(Byte physicalDisability) {
		this.physicalDisability = physicalDisability;
	}

	@Column(name = "parent_as_guardian")
	public Byte getParentAsGuardian() {
		return this.parentAsGuardian;
	}

	public void setParentAsGuardian(Byte parentAsGuardian) {
		this.parentAsGuardian = parentAsGuardian;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "student")
	public Set<WeekwiseAttendance> getWeekwiseAttendances() {
		return this.weekwiseAttendances;
	}

	public void setWeekwiseAttendances(
			Set<WeekwiseAttendance> weekwiseAttendances) {
		this.weekwiseAttendances = weekwiseAttendances;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "student")
	public Set<MarksTable> getMarksTables() {
		return this.marksTables;
	}

	public void setMarksTables(Set<MarksTable> marksTables) {
		this.marksTables = marksTables;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "student")
	public Set<Rank> getRanks() {
		return this.ranks;
	}

	public void setRanks(Set<Rank> ranks) {
		this.ranks = ranks;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "student")
	public Set<Attendance> getAttendances() {
		return this.attendances;
	}

	public void setAttendances(Set<Attendance> attendances) {
		this.attendances = attendances;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "student")
	public Set<MonthlywiseAttendance> getMonthlywiseAttendances() {
		return this.monthlywiseAttendances;
	}

	public void setMonthlywiseAttendances(
			Set<MonthlywiseAttendance> monthlywiseAttendances) {
		this.monthlywiseAttendances = monthlywiseAttendances;
	}

}
