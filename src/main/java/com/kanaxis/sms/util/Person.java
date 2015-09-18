package com.kanaxis.sms.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.lang3.builder.CompareToBuilder;

public class Person {
	String classes, rollNumber, section;

	public Person(String rollNumber, String classes, String section) {
		this.rollNumber = rollNumber;
		this.classes = classes;
		this.section = section;
	}

	public Person() {
		// TODO Auto-generated constructor stub
	}

	public String getClasses() {
		return classes;
	}

	public void setClasses(String classes) {
		this.classes = classes;
	}

	public String getRollNumber() {
		return rollNumber;
	}

	public void setRollNumber(String rollNumber) {
		this.rollNumber = rollNumber;
	}

	public String getSection() {
		return section;
	}

	public void setSection(String section) {
		this.section = section;
	}

	
	         


	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((classes == null) ? 0 : classes.hashCode());
		result = prime * result
				+ ((rollNumber == null) ? 0 : rollNumber.hashCode());
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
		Person other = (Person) obj;
		if (classes == null) {
			if (other.classes != null)
				return false;
		} else if (!classes.equals(other.classes))
			return false;
		if (rollNumber == null) {
			if (other.rollNumber != null)
				return false;
		} else if (!rollNumber.equals(other.rollNumber))
			return false;
		if (section == null) {
			if (other.section != null)
				return false;
		} else if (!section.equals(other.section))
			return false;
		return true;
	}

	public static void main(String args[]) {
		List<Person> arrayList = new ArrayList<Person>();
		Person person = new Person();
		person.setRollNumber("1");
		person.setClasses("10th");
		person.setSection("A");
		arrayList.add(person);
		Person person1 = new Person();
		person1.setRollNumber("2");
		person1.setClasses("10th");
		person1.setSection("A");
		arrayList.add(person1);
		Person person2 = new Person();
		person2.setRollNumber("1");
		person2.setClasses("10th");
		person2.setSection("A");
		arrayList.add(person2);

		Set<Person> uniqueElements = new HashSet<Person>(arrayList);
		System.out.println("arraylist "+arrayList);
		System.out.println("set "+uniqueElements);
		Set<Person> duplicates = new HashSet<Person>();
		for(Person p : arrayList){
            if(uniqueElements.contains(p)){
            	duplicates.add(p);
            }
            else
                System.out.println(p.getClasses() +" "+ p.getRollNumber() +" "+p.getSection() +" is a duplicate");
        }
		/*arrayList.clear();
		arrayList.addAll(uniqueElements);*/
		//System.out.println(uniqueElements);
	}
}
