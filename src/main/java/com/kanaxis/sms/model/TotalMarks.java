package com.kanaxis.sms.model;

// Generated Nov 8, 2015 4:22:25 PM by Hibernate Tools 3.4.0.CR1

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * TotalMarks generated by hbm2java
 */
@Entity
@Table(name = "total_marks", catalog = "sms")
public class TotalMarks implements java.io.Serializable {

	private Integer id;
	private Section section;
	private Examtype examtype;
	private Classes classes;
	private Double totalMarksObtained;
	private Double maxMarks;
	private Double percentage;
	private Integer classWiseRank;
	private Integer rollNumber;

	public TotalMarks() {
	}

	public TotalMarks(Section section, Examtype examtype, Classes classes,
			Double totalMarksObtained, Double maxMarks, Double percentage,
			Integer classWiseRank, Integer rollNumber) {
		this.section = section;
		this.examtype = examtype;
		this.classes = classes;
		this.totalMarksObtained = totalMarksObtained;
		this.maxMarks = maxMarks;
		this.percentage = percentage;
		this.classWiseRank = classWiseRank;
		this.rollNumber = rollNumber;
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
	@JoinColumn(name = "examtype_id")
	public Examtype getExamtype() {
		return this.examtype;
	}

	public void setExamtype(Examtype examtype) {
		this.examtype = examtype;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "class_id")
	public Classes getClasses() {
		return this.classes;
	}

	public void setClasses(Classes classes) {
		this.classes = classes;
	}

	@Column(name = "total_marks_obtained", precision = 22, scale = 0)
	public Double getTotalMarksObtained() {
		return this.totalMarksObtained;
	}

	public void setTotalMarksObtained(Double totalMarksObtained) {
		this.totalMarksObtained = totalMarksObtained;
	}

	@Column(name = "max_marks", precision = 22, scale = 0)
	public Double getMaxMarks() {
		return this.maxMarks;
	}

	public void setMaxMarks(Double maxMarks) {
		this.maxMarks = maxMarks;
	}

	@Column(name = "percentage", precision = 22, scale = 0)
	public Double getPercentage() {
		return this.percentage;
	}

	public void setPercentage(Double percentage) {
		this.percentage = percentage;
	}

	@Column(name = "class_wise_rank")
	public Integer getClassWiseRank() {
		return this.classWiseRank;
	}

	public void setClassWiseRank(Integer classWiseRank) {
		this.classWiseRank = classWiseRank;
	}

	@Column(name = "roll_number")
	public Integer getRollNumber() {
		return this.rollNumber;
	}

	public void setRollNumber(Integer rollNumber) {
		this.rollNumber = rollNumber;
	}

}
