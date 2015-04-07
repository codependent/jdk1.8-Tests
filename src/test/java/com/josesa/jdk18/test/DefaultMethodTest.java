package com.josesa.jdk18.test;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
@ActiveProfiles({"not-cached", "cached"})
public class DefaultMethodTest extends AbstractTestNGSpringContextTests{

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	@Qualifier("userService")
	private UserService userService;
	
	@Autowired
	@Qualifier("cachedUserService")
	private UserService cachedUserService;
	
	@BeforeClass
	public void beforeClass(){
		List<Person> people = RandomData.generatePeople(100000);
		people.stream().forEach(userService::save);
	}
	
	@Test
	public void testPrintServiceCache(){
		Person person1 = cachedUserService.get(2500);
		Assert.assertEquals(person1.getId(), 2500);
		Person person2 = cachedUserService.get(9999);
		Assert.assertEquals(person2.getId(), 9999);
		cachedUserService.forEachCached( e -> logger.info("Cached entry - key[{}] - value[{}]", e.getKey(), e.getValue()) );
	}
	
	@Test(expectedExceptions=IllegalAccessError.class)
	public void testPrintServiceWithoutCache(){
		Person person1 = userService.get(2500);
		Assert.assertEquals(person1.getId(), 2500);
		Person person2 = userService.get(9999);
		Assert.assertEquals(person2.getId(), 9999);
		userService.forEachCached( e -> logger.info("Cached entry - key[{}] - value[{}]", e.getKey(), e.getValue()) );
	}
	
}