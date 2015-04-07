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
import com.josesa.jdk18.service.UserService;
import com.josesa.jdk18.service.impl.CachedUserServiceImpl;


public class StreamTest {

	private static final int MAX_RESULTS = 100;
	private Logger logger = LoggerFactory.getLogger(getClass());
	private UserService userService;
	
	@BeforeTest
	public void setUp(){
		userService = new CachedUserServiceImpl();
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