package com.josesa.jdk18.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Optional;

import com.josesa.jdk18.dto.Person;
import com.josesa.jdk18.service.UserService;
import com.josesa.jdk18.util.RandomData;

public class UserServiceImpl implements UserService{

	private List<Person> mockedDataSource = new ArrayList<Person>();
	
	public UserServiceImpl(){
		mockedDataSource = RandomData.generatePeople(1000000);
	}
	
	@Override
	public Person getPerson(int id) {
		Optional<Person> searched = mockedDataSource.stream().filter( p -> p.getId() == id).findFirst();
		if(searched.isPresent()){
			return searched.get();
		}else{
			return null;
		}
	}

	@Override
	public Iterator<Entry<Integer, Person>> iterator() {
		throw new IllegalAccessError("Implementación UserServiceImpl no tiene caché y no puede iterarse");
	}

}
