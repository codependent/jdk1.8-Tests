package com.josesa.jdk18.util;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.josesa.jdk18.entity.Person;

public class RandomData {
	
	private static SecureRandom random = new SecureRandom();
	
	private static enum MaleNames {Andrés, Antonio, Carlos, Daniel, David, Diego, Felipe, Javier, Jesús, Jose, Juan, Pedro};
	private static enum FemaleNames {Ana, Alejandra, Daniela, Elena, Felisa, María, Marta, Nuria, Rebeca, Ruth, Saray, Susana};
	private static enum Surnames {Arias, Carrillo, Chaparro, Fernández, García, Garrido, Gómez, Jiménez, Iglesias, Íñigo, Losada, Piña, Ramos, Suárez, Vázquez};
	
	public static List<Person> generatePeople(int number){
		List<Person> people = new ArrayList<Person>();
		for (int i = 0; i < number; i++) {
			int nameIndex = RandomData.randomIntBetween(0, MaleNames.values().length-1);
			int middleNameIndex = -1;
			if(random.nextBoolean()){
				do{
					middleNameIndex = RandomData.randomIntBetween(0, MaleNames.values().length-1);
				}while(middleNameIndex==nameIndex);
			}
			boolean male = random.nextBoolean();
			people.add( new Person((i+1), 
						male ? MaleNames.values()[nameIndex].name() : FemaleNames.values()[nameIndex].name(), 
						middleNameIndex != -1 ? male ? MaleNames.values()[middleNameIndex].name() : FemaleNames.values()[middleNameIndex].name() : null,
						Surnames.values()[RandomData.randomIntBetween(0, Surnames.values().length-1)].name(), 
						Surnames.values()[RandomData.randomIntBetween(0, Surnames.values().length-1)].name(), 
						RandomData.randomLocalDate()));
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
