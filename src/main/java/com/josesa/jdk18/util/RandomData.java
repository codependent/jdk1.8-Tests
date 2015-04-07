package com.josesa.jdk18.util;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.josesa.jdk18.dto.Person;

public class RandomData {
	
	private static SecureRandom random = new SecureRandom();
	
	public static List<Person> generatePeople(int number){
		List<Person> people = new ArrayList<Person>();
		for (int i = 0; i < number; i++) {
			people.add(new Person((i+1), RandomData.randomSting(), RandomData.randomLocalDate()));
		}
		return people;
	}
	
	public static LocalDate randomLocalDate() {
        return LocalDate.ofYearDay(randomIntBetween(1900, 2015), 1);
    }

    public static int randomIntBetween(int start, int end) {
        return start + (int)Math.round(Math.random() * (end - start));
    }
	
    public static String randomSting(){
    	return new BigInteger(130, random).toString(32);
    }
}
