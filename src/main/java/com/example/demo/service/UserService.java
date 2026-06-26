package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.demo.dto.UserDto;
import com.example.demo.entity.User;
import com.example.demo.exceptions.UserNotFoundException;
import com.example.demo.repository.UserRepository;

@Service
public class UserService {

	public final UserRepository repo;

	UserService(UserRepository repo) {
		this.repo = repo;
	}
	
    public User createUser(UserDto dto) { 
    	User user = new User(dto.getName(),dto.getEmail());
    	return repo.save(user); 
    }

	public User findUser(Long id) throws UserNotFoundException{
		Optional<User> user = repo.findById(id);
		if(!user.isPresent()) throw new UserNotFoundException("User not found with id: " + id);
		return user.get();
	}

	public List<User> findUsers() {
		return repo.findAll();
	}
	
	public void updateUser(UserDto dto) {
        // Step 1: Retrieve the managed entity
        Optional<User> user = repo.findById(dto.getId());

        // Step 2: Map new data onto the entity
        user.get().setName(dto.getName());
        user.get().setEmail(dto.getEmail());

        // Step 3: Explicit save is optional but good for readability
        repo.save(user.get()); 
    }

	public void deleteUser(Long id) {
		repo.deleteById(id);
	}

}
