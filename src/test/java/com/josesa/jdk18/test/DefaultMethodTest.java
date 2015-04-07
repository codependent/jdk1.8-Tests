package com.josesa.jdk18.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.josesa.jdk18.dto.Person;
import com.josesa.jdk18.service.UserService;
import com.josesa.jdk18.service.impl.CachedUserServiceImpl;
import com.josesa.jdk18.service.impl.UserServiceImpl;

public class DefaultMethodTest {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Test
	public void testPrintServiceCache(){
		UserService userService = new CachedUserServiceImpl();
		
		Person person1 = userService.getPerson(2500);
		Assert.assertEquals(person1.getId(), 2500);
		Person person2 = userService.getPerson(999999);
		Assert.assertEquals(person2.getId(), 999999);
		
		userService.forEachCached( e -> logger.info("Cached entry - key[{}] - value[{}]", e.getKey(), e.getValue()) );
	}
	
	@Test(expectedExceptions=IllegalAccessError.class)
	public void testPrintServiceWithoutCache(){
		UserService userService = new UserServiceImpl();
		
		Person person1 = userService.getPerson(2500);
		Assert.assertEquals(person1.getId(), 2500);
		Person person2 = userService.getPerson(999999);
		Assert.assertEquals(person2.getId(), 999999);
		
		userService.forEachCached( e -> logger.info("Cached entry - key[{}] - value[{}]", e.getKey(), e.getValue()) );
	}
	
}