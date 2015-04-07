package com.josesa.jdk18.service.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import com.josesa.jdk18.dao.PersonDAO;
import com.josesa.jdk18.entity.Person;
import com.josesa.jdk18.service.UserService;

@Service("cachedUserService")
@Profile("cached")
@Transactional
public class CachedUserServiceImpl implements UserService{

	@Autowired
	private PersonDAO personDAO;
	
	private Map<Long, Person> cache = new HashMap<Long, Person>();
	
	@Override
	public Person save(Person p) {
		return personDAO.save(p);
	}
	
	@Override
	public Person get(long id) {
		Optional<Person> searched = personDAO.findOne(id);
		if(searched.isPresent()){
			Person person = searched.get();
			cache.put(person.getId(), person);
			return person;
		}else{
			return null;
		}
	}
	
	@Override
	public List<Person> search(Predicate<Person> criteria){
		return search(criteria, 0);
	}
	
	@Override
	public List<Person> search(Predicate<Person> criteria, int maxResults){
		if(maxResults >0){
			return personDAO.streamAllPeople().parallel()
					.filter(criteria).limit(maxResults)
					.collect(Collectors.toList());
		}else{
			return personDAO.streamAllPeople().parallel()
					.filter(criteria)
					.collect(Collectors.toList());
		}
	}
	
	@Override
	public void print(List<Person> people) {
		logger.info("Printing {} people:", people.size());
		people.stream().parallel().forEach( p -> logger.info("{}", p) );
	}
	
	@Override
	public Stream<Person> printAndReturnStream(List<Person> people) {
		logger.info("Printing {} people:", people.size());
		return people.stream().parallel().peek( p ->  logger.info("{}", p) );
	}

	public Map<Long, Person> getCache() {
		return cache;
	}

	@Override
	public Iterator<Entry<Long, Person>> iterator() {
		return cache.entrySet().iterator();
	}

}
