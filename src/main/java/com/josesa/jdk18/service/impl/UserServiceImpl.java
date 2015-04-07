package com.josesa.jdk18.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
	public List<Person> search(Predicate<Person> criteria){
		return search(criteria, 0);
	}
	
	@Override
	public List<Person> search(Predicate<Person> criteria, int maxResults){
		if(maxResults >=0){
			return mockedDataSource.stream().parallel()
					.filter(criteria).limit(maxResults)
					.collect(Collectors.toList());
		}else{
			return mockedDataSource.stream().parallel()
					.filter(criteria)
					.collect(Collectors.toList());
		}
	}
	
	@Override
	public void print(List<Person> people) {
		logger.info("Printing {} people:", people.size());
		people.stream().parallel().forEach( p -> logger.info("{}", p) );
	}
	
	@Override
	public Stream<Person> printAndReturnStream(List<Person> people) {
		return people.stream().parallel().peek( p ->  logger.info("{}", p) );
	}

	@Override
	public Iterator<Entry<Integer, Person>> iterator() {
		throw new IllegalAccessError("Implementación UserServiceImpl no tiene caché y no puede iterarse");
	}

	

}
