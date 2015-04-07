package com.josesa.jdk18.test;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.josesa.jdk18.Application;
import com.josesa.jdk18.entity.Person;
import com.josesa.jdk18.service.UserService;
import com.josesa.jdk18.util.RandomData;

@ContextConfiguration(classes=Application.class)
@ActiveProfiles("cached")
public class StreamTest extends AbstractTestNGSpringContextTests{

	private static final int MAX_RESULTS = 100;
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private UserService userService;
	
	@BeforeClass
	public void beforeClass(){
		List<Person> people = RandomData.generatePeople(100000);
		people.stream().forEach(userService::save);
	}
	
	@Test
	public void testStreamOperations(){
		LocalDate someDate = LocalDate.ofYearDay(1940, 1);
		Predicate<Person> predFullName =  p -> p.getFullName().equals("David Piña Losada");
		Predicate<Person> predBirthBefore =  p -> p.getBirthDate().compareTo(someDate) < 0;
		
		List<Person> people = userService.search(predFullName);
		userService.print(people);
		
		people = userService.search(predBirthBefore, MAX_RESULTS);
		Stream<Person> filteredByBirth = userService.printAndReturnStream(people);
		
		Assert.assertTrue( filteredByBirth.count() <= MAX_RESULTS );
	}
	
	@Test
	public void testStreamReduce(){
		Predicate<Person> predUnderaged = ( p -> { Period period = Period.between(p.getBirthDate(), LocalDate.now()); 
												   return period.getYears()<18; });
		
		List<Person> people = userService.search(predUnderaged);
		Assert.assertTrue(people.size()>0);
		
		Stream<String> reducedList = people.stream().parallel()
				.map( p -> {Period period = Period.between(p.getBirthDate(), LocalDate.now()); 
							return p.getFullName() + " - " + period.getYears() + " años"; } )
				.peek( p ->  logger.info("Underaged person -> {}", p) );
		Assert.assertTrue(people.size() == reducedList.count());
		
		
	}
	
}