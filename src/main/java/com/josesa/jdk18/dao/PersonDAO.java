package com.josesa.jdk18.dao;

import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import com.josesa.jdk18.entity.Person;

public interface PersonDAO extends Repository<Person, Long>{

	Optional<Person> findOne(Long id);
	Person save(Person p);
	@Query("select p from Person p")
	Stream<Person> streamAllPeople();
}
