package com.josesa.jdk18.entity;
import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Person implements Serializable{

	private static final long serialVersionUID = -4951731015505244846L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	private String name;
	
	private String middleName;
	
	private String surname1;
	
	private String surname2;
	
	private LocalDate birthDate;
	
	public Person(){}
	
	public Person(long id, String name, String middleName, String surname1, String surname2, LocalDate birthDate) {
		super();
		this.id = id;
		this.name = name;
		this.middleName = middleName;
		this.surname1 = surname1;
		this.surname2 = surname2;
		this.birthDate = birthDate;
	}

	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getSurname1() {
		return surname1;
	}

	public void setSurname1(String surname1) {
		this.surname1 = surname1;
	}

	public String getSurname2() {
		return surname2;
	}

	public void setSurname2(String surname2) {
		this.surname2 = surname2;
	}

	public LocalDate getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}
	
	public String getFullName(){
		return name + (middleName != null ? " " + middleName : "") + " " +surname1+ " " + surname2;
	}

	@Override
	public String toString() {
		return "Person [id=" + id + ", name=" + name + ", middleName="
				+ middleName + ", surname1=" + surname1 + ", surname2="
				+ surname2 + ", birthDate=" + birthDate + "]";
	}
	
}
