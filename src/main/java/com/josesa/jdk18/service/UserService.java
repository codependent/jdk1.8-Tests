package com.josesa.jdk18.service;

import java.util.Map.Entry;
import java.util.function.Consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.josesa.jdk18.dto.Person;

public interface UserService extends Iterable<Entry<Integer, Person>> {

	public static final Logger logger = LoggerFactory.getLogger(UserService.class);
	
	public Person getPerson(int id);
	
	default void forEachCached(Consumer<Entry<Integer, Person>> c){
		for (Entry<Integer, Person> e : this) {
			c.accept(e);
		}
	}
}
