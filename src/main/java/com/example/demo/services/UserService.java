package com.example.demo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.example.demo.entities.User;
import com.example.demo.repositories.UserRepository;
import com.example.demo.services.exceptions.DatabaseException;
import com.example.demo.services.exceptions.ResourceNotFoundException;

@Service
public class UserService {

	@Autowired
	private UserRepository repository;
	
	public List<User> findAll(){
		return repository.findAll();
	}
	
	public User findById(Long id) {
		Optional<User> object = repository.findById(id);
		return object.orElseThrow(() -> new ResourceNotFoundException(id));
	}
	
	public User insert(User object) {
		return repository.save(object);
	}
	
	public void delete(Long id) {
		try{
			repository.deleteById(id);

		}catch(EmptyResultDataAccessException e){
			throw new ResourceNotFoundException(id);

		}catch(DataIntegrityViolationException ex){
			throw new DatabaseException(ex.getMessage());
		}
	}
	
	public User update(Long id, User object) {
		User entity = repository.getOne(id);
		updateData(entity, object);
		return repository.save(entity);
	}

	private void updateData(User entity, User object) {
		entity.setName(object.getName());
		entity.setPhone(object.getPhone());
		entity.setEmail(object.getEmail());		
	}
}
