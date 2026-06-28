package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import com.example.demo.dto.UserDto;
import com.example.demo.dto.UserFilterDto;
import com.example.demo.dto.UserSummary;
import com.example.demo.entity.User;
import com.example.demo.exceptions.UserNotFoundException;
import com.example.demo.repository.UserRepository;
import com.example.demo.specifictions.UserSpecification;

import lombok.RequiredArgsConstructor;

@Service
public class UserService {

	private final UserRepository repo;

	private static final Logger logger = LoggerFactory.getLogger(UserService.class);

	UserService(UserRepository repo) {
		this.repo = repo;
	}

	public User createUser(UserDto dto) { 
		logger.info("Saving user {}", dto.getEmail());
		User user = new User(dto.getName(),dto.getEmail());
		user = repo.save(user); 
		logger.info("User saved successfully");
		return user;
	}

	public User findUser(Long id) throws UserNotFoundException{
		logger.info("Fetching user {}", id);
		Optional<User> user = repo.findById(id);
		if(!user.isPresent()) throw new UserNotFoundException("User not found with id: " + id);
		logger.info("User Found name : {} email: {}", user.get().getName(), user.get().getEmail());
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

	public Page<User> getUsers(int page, int size, String sortBy,String direction) {

		Sort sort = direction.equalsIgnoreCase("asc")
				? Sort.by(sortBy).ascending()
						: Sort.by(sortBy).descending();

		Pageable pageable = PageRequest.of(page, size, sort);

		return repo.findAll(pageable);
	}

	public User getUserByEmail(String email) throws UserNotFoundException {

		return repo.findByEmail(email)
				.orElseThrow(() ->
				new UserNotFoundException("User not found by email "+email));
	}

	public User getUserByNameEmail( String name,String email) throws UserNotFoundException{
		return repo.findByNameAndEmail(name, email)
				.orElseThrow(() ->
				new UserNotFoundException("User not found by name "+name+" and email "+email));

	}

	public List<User> getUsersOrderByName() throws UserNotFoundException{
		return repo.getUsersOrderByName().orElseThrow(() ->
		new UserNotFoundException("Users not found "));
	}

	//Using DTO for Class type Projection. Other types of projection are Interface and Dynamic Projections.
	public List<UserSummary> getUserSummary() throws UserNotFoundException{
		return repo.getUserSummary().orElseThrow(() ->
		new UserNotFoundException("Users not found "));
	}

	public List<User> search(UserFilterDto filter){
		Specification<User> spec =
				Specification.where(
						UserSpecification.hasName(filter.getName()))
				.and(
						UserSpecification.hasEmail(filter.getEmail()));
		return repo.findAll(spec);
	}
}
