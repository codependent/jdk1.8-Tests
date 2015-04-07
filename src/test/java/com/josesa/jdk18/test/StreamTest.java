package com.josesa.jdk18.test;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.josesa.jdk18.dto.Person;
import com.josesa.jdk18.util.RandomData;

@Test
public class StreamTest {

	private List<Person> people;
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@BeforeTest
	public void setUp(){
		people = RandomData.generatePeople(100000);
	}
	
	public void testStreamOperations(){
		LocalDate someDate = LocalDate.ofYearDay(1940, 1);
		
		Predicate<Person> predContainsStr =  p -> p.getName().contains("ana");
		Stream<Person> filtered = people.stream().parallel()
			.filter( predContainsStr )
			.peek( p -> logger.info("Person containing \"ana\" -> {}", p) );

		logger.info("Count containing ana -> {}", filtered.count());
		
		Predicate<Person> predBirthBefore =  p -> p.getBirthDate().compareTo(someDate) < 0;
		Stream<Person> filteredByBirth = people.stream().parallel()
			.filter( predBirthBefore )
			.limit(40)
			.peek( p ->  logger.info("Person born before 1940 -> {}", p) );
		
		Assert.assertTrue( filteredByBirth.count() <= 40 );
	}
	
	public void testStreamReduce(){
		LocalDate today = LocalDate.now();
		Stream<String> reducedList = people.stream()
			.filter( p -> { Period period = Period.between(p.getBirthDate(), today); 
							return period.getYears()<18; })
			.map( p -> p.getName() )
			.peek( p ->  logger.info("Underaged person -> {}", p) );

		logger.info("Count underaged -> {}", reducedList.count() );
		
	}
	
}