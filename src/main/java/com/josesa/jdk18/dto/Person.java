package com.josesa.jdk18.dto;

import java.io.Serializable;
import java.time.LocalDate;

public class Person implements Serializable{

	private static final long serialVersionUID = -4951731015505244846L;

	private int id;
	
	private String name;
	
	private LocalDate birthDate;
	
	public Person(){}
	
	public Person(int id, String name, LocalDate birthDate) {
		super();
		this.id = id;
		this.name = name;
		this.birthDate = birthDate;
	}

	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LocalDate getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}

	@Override
	public String toString() {
		return "Person [id=" + id + ", name=" + name + ", birthDate="
				+ birthDate + "]";
	}
	
}
