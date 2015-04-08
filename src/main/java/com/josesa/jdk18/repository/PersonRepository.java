package com.josesa.jdk18.repository;

import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import com.josesa.jdk18.entity.Person;

public interface PersonRepository extends Repository<Person, Long>{

	Optional<Person> findOne(Long id);
	Person save(Person p);
	@Query("select p from Person p")
	Stream<Person> streamAllPeople();
}
