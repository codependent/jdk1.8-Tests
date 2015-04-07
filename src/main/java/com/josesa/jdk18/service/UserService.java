package com.josesa.jdk18.service;

import java.util.List;
import java.util.Map.Entry;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.josesa.jdk18.dto.Person;

public interface UserService extends Iterable<Entry<Integer, Person>> {

	public static final Logger logger = LoggerFactory.getLogger(UserService.class);

	Person getPerson(int id);
	List<Person> search(Predicate<Person> criteria);
	List<Person> search(Predicate<Person> criteria, int maxResults);
	void print(List<Person> people);
	Stream<Person> printAndReturnStream(List<Person> people);
	
	default void forEachCached(Consumer<Entry<Integer, Person>> c){
		for (Entry<Integer, Person> e : this) {
			c.accept(e);
		}
	}
}
